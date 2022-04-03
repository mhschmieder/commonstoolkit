/**
 * MIT License
 *
 * Copyright (c) 2020, 2022 Mark Schmieder
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * This file is part of the CommonsToolkit Library
 *
 * You should have received a copy of the MIT License along with the
 * CommonsToolkit Library. If not, see <https://opensource.org/licenses/MIT>.
 *
 * Project: https://github.com/mhschmieder/commonstoolkit
 */
package com.mhschmieder.commonstoolkit.io;

import java.io.File;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.FilenameUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

/**
 * {@code XmlUtilities} is a static utilities class for common XML functionality
 * that at least wasn't part of Core Java at the time this library was written.
 * <p>
 * In particular, this class contains utilities related to grabbing XML content
 * from ZIP files, and converting XML to HTML (e.g. for displaying in WebKit).
 */
public class XmlUtilities {

    /**
     * The default constructor is disabled, as this is a static utilities class
     */
    private XmlUtilities() {}

    public static void setTextValue( final Element rootElement,
                                     final String TagName,
                                     final String textValue ) {
        if ( ( textValue != null ) && ( textValue.length() > 0 ) ) {
            rootElement.getElementsByTagName( TagName ).item( 0 ).getFirstChild()
                    .setNodeValue( textValue );
        }
    }

    // This is a null-safe replacement for auto-boxing of Integers to ints.
    public static int xmlIntegerToInt( final Integer integer ) {
        return ( integer != null ) ? integer.intValue() : 0;
    }

    public static double getElementDouble( final String tagName, final Document domDocument ) {
        final Element root = domDocument.getDocumentElement();
        final Element child = ( Element ) root.getElementsByTagName( tagName ).item( 0 );

        final double doubleValue = Double.parseDouble( child.getTextContent() );

        return doubleValue;
    }

    public static int getElementInteger( final String tagName, final Document domDocument ) {
        final Element root = domDocument.getDocumentElement();
        final Element child = ( Element ) root.getElementsByTagName( tagName ).item( 0 );

        final int integerValue = Integer.parseInt( child.getTextContent() );

        return integerValue;
    }

    public static String getElementString( final String tagName, final Document domDocument ) {
        final Element root = domDocument.getDocumentElement();
        final Element child = ( Element ) root.getElementsByTagName( tagName ).item( 0 );

        final String stringValue = child.getTextContent();

        return stringValue;
    }

    public static String getTransformerExceptionDetails( final TransformerException te ) {
        final StringBuilder builder = new StringBuilder();
        builder.append( te );
        builder.append( " at " ); //$NON-NLS-1$
        builder.append( te.getLocationAsString() );
        final Throwable clause = te.getCause();
        if ( ( clause != null ) && !clause.equals( te ) ) {
            builder.append( '\n' );
            builder.append( clause );
        }
        return builder.toString();
    }

    // NOTE: This method only works with JAR-resident XSLT files.
    // TODO: Rewrite this so that it works with XSLT's in any file location.
    @SuppressWarnings("nls")
    public static boolean convertXmlToHtml( final File file,
                                            final StringBuilder htmlBuffer,
                                            final String jarRelativeXsltFilename ) {
        final StringBuilder fileContent = new StringBuilder( 1024 );

        final String fileName = file.getName();
        final String fileNameCaseInsensitive = fileName.toLowerCase( Locale.ENGLISH );
        if ( FilenameUtils.isExtension( fileNameCaseInsensitive, "xml" ) ) {
            // Load the XML file into an in-memory string builder.
            final boolean fileOpened = IoUtilities.loadIntoStringBuilder( file, fileContent );
            if ( !fileOpened ) {
                return false;
            }
        }
        else if ( FilenameUtils.isExtension( fileNameCaseInsensitive, "zip" ) ) {
            // Load the XML data from a ZIP file. Send the file vs. a
            // ZipInputStream, due to the need to cycle twice, and due to
            // problems with ZipInputStream.
            final boolean fileOpened = IoUtilities
                    .loadFromZipIntoStringBuilder( file, fileContent, "xml" );
            if ( !fileOpened ) {
                return false;
            }
        }

        if ( fileContent.length() <= 0 ) {
            return false;
        }

        // Need to remove UTF-8 encoding as it will break the parser if it's not
        // actually UTF-8.
        // TODO: Review this decision, and all that follows.
        final String fileContentString = fileContent.toString()
                .replaceAll( "(?i)encoding=\"UTF-8\"", "encoding=\"ISO-8859-1\"" );

        // Parse the file content string into an XML-DOM Document.
        try {
            final DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            final Document doc = docBuilder
                    .parse( new InputSource( new StringReader( fileContentString ) ) );

            // Apply the XSLT to the XML to produce an HTML formatted
            // equivalent.
            final StringWriter html = new StringWriter( 1024 );
            try ( final InputStream xsltStream = XmlUtilities.class
                    .getResourceAsStream( jarRelativeXsltFilename ) ) {
                final TransformerFactory xformerFactory = TransformerFactory.newInstance();
                final Transformer xformer = xformerFactory
                        .newTransformer( new StreamSource( xsltStream ) );
                xformer.transform( new DOMSource( doc ), new StreamResult( html ) );
                htmlBuffer.append( html.toString() );
            }
            catch ( final TransformerException te ) {
                // NOTE: We catch this exception separately because it needs
                // special handling in order to dump useful information.
                System.err.println( com.mhschmieder.commonstoolkit.io.XmlUtilities
                        .getTransformerExceptionDetails( te ) );
                return false;
            }
        }
        catch ( final Exception e ) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
