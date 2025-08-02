/**
 * MIT License
 *
 * Copyright (c) 2020, 2025 Mark Schmieder
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
 * CommonsToolkit Library. If not, see https://opensource.org/licenses/MIT.
 *
 * Project: https://github.com/mhschmieder/commonstoolkit
 */
package com.mhschmieder.commonstoolkit.io;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import com.mhschmieder.commonstoolkit.branding.BrandingUtilities;
import com.mhschmieder.commonstoolkit.branding.ProductBranding;

/**
 * Utilities for working with ZIP files.
 */
public class ZipUtilities {

    /**
     * The default constructor is disabled, as this is a static utilities class
     */
    public ZipUtilities() {}

    /**
     * This method finds an occurrence of a particular file name in a
     * supplied ZIP file, and returns a ready-to-use ZIP Entry wrapped in an
     * Optional so as to avoid null pointer exceptions.
     *
     * @param zipFile
     *            The ZIP File to search for a specific File Name
     * @param fileName
     *            The simple file name to search for, sans path
     * @return A ZIP Entry corresponding to a file match, or null if not
     *         present, but wrapped as an Optional for code safety
     */
    public static Optional< ? extends ZipEntry > findFileNameInZip( final ZipFile zipFile,
                                                                    final String fileName ) {
        // Each file access has to start a new stream due to auto-close.
        final Stream< ? extends ZipEntry > zipStream = zipFile.stream();
        final Predicate< ZipEntry > isCorrectFileName = zipEntry -> zipEntry.getName()
                .equals( fileName );
    
        final Predicate< ZipEntry > isFile = zipEntry -> !zipEntry.isDirectory();
        final Optional< ? extends ZipEntry > optionalZipEntry = zipStream
                .filter( isFile.and( isCorrectFileName ) ).findFirst();
    
        return optionalZipEntry;
    }

    /**
     * This method finds the first occurrence of a particular file type in a
     * supplied ZIP file, and returns a ready-to-use ZIP Entry wrapped in an
     * Optional so as to avoid null pointer exceptions.
     *
     * @param zipFile
     *            The ZIP File to search for a specific File Type
     * @param fileType
     *            The simple file extension used to search for the File Type
     * @return A ZIP Entry corresponding to the first match, or null if not
     *         present, but wrapped as an Optional for code safety
     */
    public static Optional< ? extends ZipEntry > findFileTypeInZip( final ZipFile zipFile,
                                                                    final String fileType ) {
        // Each file access has to start a new stream due to auto-close.
        final Stream< ? extends ZipEntry > zipStream = zipFile.stream();
        final Predicate< ZipEntry > isCorrectFileType = zipEntry -> FilenameUtils
                .isExtension( zipEntry.getName().toLowerCase( Locale.ENGLISH ), fileType );
    
        final Predicate< ZipEntry > isFile = zipEntry -> !zipEntry.isDirectory();
        final Optional< ? extends ZipEntry > optionalZipEntry = zipStream
                .filter( isFile.and( isCorrectFileType ) ).findFirst();
    
        return optionalZipEntry;
    }

    // Generic method to load the contents of a zip file entry of a specified
    // content type into a string builder.
    // TODO: Write another version of this method that takes a specific zip
    //  entry name vs. content type, as in some cases there may be more than one
    //  entry of the same type and unlike in this case the name of the zip entry
    //  of interest may be known.
    // TODO: Find a way to report errors if not a legitimate ZIP file.
    public static boolean loadZipToStringBuilder( final File file,
                                                  final StringBuilder fileContent,
                                                  final String contentType ) {
        // NOTE: If the file is not a valid ZIP file (whether or not it has a
        // ".zip" extension), an exception will be caught, so it is up to the
        // caller whether to check in advance for file type validity.
        try ( final ZipFile zipFile = new ZipFile( file ) ) {
            final Predicate< ZipEntry > isFile = zipEntry -> !zipEntry.isDirectory();
            final Predicate< ZipEntry > isXml = zipEntry -> FilenameUtils
                    .isExtension( zipEntry.getName().toLowerCase( Locale.ENGLISH ), contentType );
    
            final Optional< ? extends ZipEntry > optionalXmlEntry = zipFile.stream()
                    .filter( isFile.and( isXml ) ).findFirst();
    
            // There must be a valid entry of the specified content type, in
            // order for this ZIP file to be considered valid.
            if ( !optionalXmlEntry.isPresent() ) {
                return false;
            }
    
            // Read in the relevant zip entry as an in-memory stream.
            boolean fileOpened = false;
            try ( final InputStream inputStream =
                                                zipFile.getInputStream( optionalXmlEntry.get() ) ) {
                fileOpened = IoUtilities.loadIntoStringBuilder( inputStream, fileContent );
            }
    
            return fileOpened;
        }
        catch ( final Exception e ) {
            e.printStackTrace();
            return false;
        }
    }

    // Function to export a byte array to a ZIP file with product signature.
    public static FileStatus saveByteArrayToZip( final byte[] byteArray,
                                                 final ProductBranding productBranding,
                                                 final Locale locale,
                                                 final ZipOutputStream zipOutputStream ) {
        try {
            // Add a header so that WINZIP and other tools can easily identify
            // this as a ZIP file produced from a specific application.
            ZipUtilities.prepareZipForWrite( zipOutputStream, productBranding, locale );
    
            // Chain a ZipInputStream to a ByteArrayInputStream to the byte
            // array, to inflate the ZIP entries.
            try ( final ByteArrayInputStream byteArrayInputStream =
                                                                  new ByteArrayInputStream( byteArray );
                    final ZipInputStream zipInputStream =
                                                        new ZipInputStream( byteArrayInputStream ) ) {
                ZipEntry zipEntry = null;
                while ( ( zipEntry = zipInputStream.getNextEntry() ) != null ) {
                    // Echo the current ZIP entry to the saved ZIP file (this
                    // should also preserve the original server time/date
                    // stamp).
                    zipOutputStream.putNextEntry( zipEntry );
    
                    // Copy the current ZIP entry from the byte array directly
                    // to the new ZIP output file.
                    // NOTE: We don't care about the returned data size; if it
                    // was "-1", it simply means it was too large to fit in an
                    // int.
                    IOUtils.copy( zipInputStream, zipOutputStream );
    
                    // Close the current ZIP input entry to prepare to read the
                    // next entry.
                    zipInputStream.closeEntry();
    
                    // Close the current ZIP output entry (copy) to prevent
                    // further modification.
                    zipOutputStream.closeEntry();
                }
            }
        }
        catch ( final Exception e ) {
            e.printStackTrace();
            return FileStatus.WRITE_ERROR;
        }
    
        return FileStatus.SAVED;
    }

    // Utility to prepare a ZIP output stream for write operations (i.e. adding
    // entries). This means adding a comment, setting the deflation method and
    // the compression level.
    public static void prepareZipForWrite( final ZipOutputStream zipOutputStream,
                                           final ProductBranding productBranding,
                                           final Locale locale ) {
        final String savedFrom = BrandingUtilities.getSavedFrom( productBranding, locale );
        zipOutputStream.setComment( savedFrom );
        zipOutputStream.setMethod( ZipOutputStream.DEFLATED );
        zipOutputStream.setLevel( 9 );
    }
}
