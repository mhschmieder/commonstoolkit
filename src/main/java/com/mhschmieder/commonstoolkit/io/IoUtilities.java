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

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.UnknownServiceException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.SwappedDataInputStream;

import com.mhschmieder.commonstoolkit.branding.ProductBranding;

/**
 * {@code IoUtilities} is a static utilities class for common I/O functionality
 * that at least wasn't part of Core Java at the time this library was written.
 * <p>
 * In particular, this class contains utilities for loading streams and files
 * into a StringBuilder, which Oracle recommends in place of StringBuffer.
 */
public final class IoUtilities {

    // NOTE: We might need to switch this to UTF-8 encoding.
    @SuppressWarnings("nls") public static final String LATIN_1 = "ISO-8859-1";

    /**
     * The default constructor is disabled, as this is a static utilities class
     */
    private IoUtilities() {}

    @SuppressWarnings("nls")
    public static String getJarResourceFilename( final String jarRelativePackagePath,
                                                 final String resourceNameUnqualified,
                                                 final String fileExtension ) {
        final StringBuilder jarResourceFilenameStringBuilder = new StringBuilder();

        jarResourceFilenameStringBuilder.append( jarRelativePackagePath );
        jarResourceFilenameStringBuilder.append( resourceNameUnqualified );
        jarResourceFilenameStringBuilder.append( "." );
        jarResourceFilenameStringBuilder.append( fileExtension );

        final String jarResourceFilename = jarResourceFilenameStringBuilder.toString();

        return jarResourceFilename;
    }

    // Generic method to fetch the contents of a named resource to a string.
    public static String getResourceAsString( final String resourceName ) {
        try ( final InputStream inputStream = IoUtilities.class
                .getResourceAsStream( resourceName ) ) {
            // Convert the text file to a standard string message.
            final String text = IOUtils.toString( inputStream,
                                                  Charsets.toCharset( StandardCharsets.UTF_8 ) );
            return text;
        }
        catch ( final Exception e ) {
            e.printStackTrace();
            return null;
        }
    }

    // Generalized method to load a float array from a Little Endian Input
    // Stream to a double array with possible offset and default-fill.
    // NOTE: The data array must be pre-allocated, but is filled here.
    public static boolean loadIntoDoubleArray( final SwappedDataInputStream inputStream,
                                               final double[] data,
                                               final int dataOffset,
                                               final int numberOfDataPoints,
                                               final double defaultValue ) {
        // Avoid throwing unnecessary exceptions by not attempting to open bad
        // streams or use unallocated or incorrectly sized data arrays.
        // NOTE: We mustn't assume the invoker requires all data to be loaded
        // when comparing allocated data vector size, so we pick the smallest.
        if ( ( inputStream == null ) || ( data == null ) ) {
            return false;
        }

        final int totalDataLength = dataOffset + numberOfDataPoints;
        final int dataIndexLast = Math.min( totalDataLength, data.length ) - 1;

        try {
            // Load data into a double-precision array, pre-filling and/or
            // post-filling with the supplied default value if there is a data
            // offset and/or the number of items is less than the data length.
            int dataIndex = 0;
            while ( dataIndex < dataOffset ) {
                data[ dataIndex++ ] = defaultValue;
            }
            while ( dataIndex <= dataIndexLast ) {
                data[ dataIndex++ ] = inputStream.readFloat();
            }
            while ( dataIndex < data.length ) {
                data[ dataIndex++ ] = defaultValue;
            }
        }
        catch ( final Exception e ) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    // Generic method to load the contents of a reader into a string builder.
    @SuppressWarnings("nls")
    public static boolean loadIntoStringBuilder( final BufferedReader bufferedReader,
                                                 final StringBuilder fileContent ) {
        try {
            String line = bufferedReader.readLine();
            while ( line != null ) {
                fileContent.append( line ).append( "\n" );
                line = bufferedReader.readLine();
            }
            return true;
        }
        catch ( final Exception e ) {
            e.printStackTrace();
            return false;
        }
    }

    // Generic method to load the contents of a file into a string builder.
    public static boolean loadIntoStringBuilder( final File file,
                                                 final StringBuilder fileContent ) {
        // Chain a BufferedReader to a FileReader, for better performance.
        try ( final FileReader fileReader = new FileReader( file );
                final BufferedReader bufferedReader = new BufferedReader( fileReader ) ) {
            final boolean fileOpened = loadIntoStringBuilder( bufferedReader, fileContent );
            return fileOpened;
        }
        catch ( final Exception e ) {
            e.printStackTrace();
            return false;
        }
    }

    // Generic method to load the contents of an input stream into a string
    // builder.
    public static boolean loadIntoStringBuilder( final InputStream inputStream,
                                                 final StringBuilder fileContent ) {
        // Chain a BufferedReader to an InputStreamReader, for better
        // performance.
        try ( final InputStreamReader inputStreamReader = new InputStreamReader( inputStream,
                                                                                 LATIN_1 );
                final BufferedReader bufferedReader = new BufferedReader( inputStreamReader ) ) {
            final boolean fileOpened = loadIntoStringBuilder( bufferedReader, fileContent );
            return fileOpened;
        }
        catch ( final Exception e ) {
            e.printStackTrace();
            return false;
        }
    }

    // Generic method to load the contents of a zip file entry of a specified
    // content type into a string builder.
    // TODO: Write another version of this method that takes a specific zip
    // entry name vs. content type, as in some cases there may be more than one
    // entry of the same type and unlike in this case the name of the zip entry
    // of interest may be known.
    // TODO: Find a way to report errors if not a legitimate ZIP file.
    public static boolean loadFromZipIntoStringBuilder( final File file,
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
                fileOpened = loadIntoStringBuilder( inputStream, fileContent );
            }

            return fileOpened;
        }
        catch ( final Exception e ) {
            e.printStackTrace();
            return false;
        }
    }

    // Generic method to read an entire text file's contents into a String.
    public static String readFile( final String fileName, final Charset encoding ) {
        try {
            final byte[] encodedContents = Files.readAllBytes( Paths.get( fileName ) );
            return encoding.decode( ByteBuffer.wrap( encodedContents ) ).toString();
        }
        catch ( final Exception e ) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returns a {@link String} containing the full contents of a supplied
     * {@link InputStream}.
     * <p>
     * This method convert the contents of an {@link InputStream} to a
     * {@link String} using the specified {@link Charset} for the character
     * encoding.
     * <p>
     * This method buffers the input internally, so there is no need to use a
     * {@code BufferedInputStream} wrapper.
     *
     * @param inputStream
     *            The {@link InputStream} to read from
     * @param charset
     *            The {@link Charset} to use for the character encoding of the
     *            output; {@code null} means to use the platform default, which
     *            is usually UTF-16 for Java
     * @return The original {@link InputStream} converted to a {@link String}
     * @throws NullPointerException
     *             If the {@link InputStream} is null
     * @throws IOException
     *             If an I/O error occurs
     *
     * @since 1.0
     */
    public static String streamToString( final InputStream inputStream, final Charset charset )
            throws IOException {
        try ( final InputStreamReader inputStreamReader = new InputStreamReader( inputStream,
                                                                                 charset ) ) {
            final int bufferSize = 4096;
            final char[] buffer = new char[ bufferSize ];
            final StringBuilder builder = new StringBuilder( bufferSize );

            long numberOfCharactersReadInTotal = 0;
            int numberOfCharactersRead = 0;

            // Read until the stream reaches its end, unless we exceed the
            // maximum size, which is set to the largest integer value as the
            // StringBuilder class' capacity and length are stored as integers.
            while ( ( numberOfCharactersRead = inputStreamReader.read( buffer ) ) != -1 ) {
                // Although it is unlikely we could exceed the maximum size of a
                // StringBuilder, it is better to exit with a partial conversion
                // if so, rather than a null or empty String.
                numberOfCharactersReadInTotal += numberOfCharactersRead;
                if ( numberOfCharactersReadInTotal > Integer.MAX_VALUE ) {
                    break;
                }

                // Add the current buffer to the output StringBuilder target.
                builder.append( buffer, 0, numberOfCharactersRead );
            }

            return builder.toString();
        }
        catch ( final IOException ioe ) {
            ioe.printStackTrace();
            return null;
        }
    }

    // Function to import a remote stream to a byte array cache, usually sent
    // as a ZIP stream and especially necessary to save as raw bytes so as to
    // preserve all of the ZIP structure details for later retrieval.
    //
    // The returned string is meant as a status message for a progress bar.
    public static byte[] saveRemoteStreamToByteArray( final HttpURLConnection httpURLConnection,
                                                      final StringBuilder statusMessage ) {
        // Attempt to load the data stream from the server.
        //
        // In order to minimize the dependency on the server connection, and
        // also to compensate for servlet response streams being consumed in a
        // "read once" manner, we immediately perform a byte array copy of the
        // entire stream into local memory.
        //
        // Reading data directly from a URL can be very expensive, as each read
        // access reaches across the internet or to a local system cache that is
        // not in local program address space.
        //
        // This code streams the data from the remote file object into a local
        // byte array stream, preserving byte order so that floating-point
        // interpretation (Little Endian/Big Endian) can be deferred to a
        // downstream filter.
        try ( final InputStream servletResponseStream = httpURLConnection.getInputStream() ) {
            // Immediately cache the server response to a byte array in local
            // memory, for efficient and flexible reuse, and due to the a
            // frequent need to cycle through this stream twice (as a result of
            // order-dependent processing).
            final byte[] byteArray = saveStreamToByteArray( servletResponseStream, statusMessage );
            return byteArray;
        }
        catch ( final NullPointerException npe ) {
            npe.printStackTrace();
            statusMessage.append( "Server Communication Error: Invalid Response Stream Handle." ); //$NON-NLS-1$
        }
        catch ( final UnknownServiceException use ) {
            use.printStackTrace();
            statusMessage
                    .append( "Server Protocol Error: Server Does Not Support Response Streams." ); //$NON-NLS-1$
        }
        catch ( final SocketTimeoutException ste ) {
            ste.printStackTrace();
            statusMessage.append( "Server Communication Error: Socket Read Timeout." ); //$NON-NLS-1$
        }
        catch ( final IOException ioe ) {
            ioe.printStackTrace();
            statusMessage.append( "Server Communication Error: Response Stream I/O Problems." ); //$NON-NLS-1$
        }

        return null;
    }

    // Function to import a stream to a byte array cache.
    //
    // The returned string is meant as a status message for a progress bar.
    public static byte[] saveStreamToByteArray( final InputStream inputStream,
                                                final StringBuilder statusMessage )
            throws IOException {
        // Attempt to load the ZIP data stream into a byte array.
        //
        // This code streams the data from the remote file object into a local
        // byte array stream, preserving byte order so that floating-point
        // interpretation (Little Endian/Big Endian) can be deferred to a
        // downstream filter.
        try ( final InputStream bufferedInputStream =
                                                    IOUtils.toBufferedInputStream( inputStream ) ) {
            final byte[] byteArray = IOUtils.toByteArray( bufferedInputStream );
            return byteArray;
        }
    }

    // Function to export a byte array to a ZIP file with product signature.
    public static FileStatus saveByteArrayToZip( final byte[] byteArray,
                                                 final ProductBranding productBranding,
                                                 final Locale locale,
                                                 final ZipOutputStream zipOutputStream ) {
        try {
            // Add a header so that WINZIP and other tools can easily identify
            // this as a ZIP file produced from MAPP (vs. other apps).
            prepareZipForWrite( zipOutputStream, productBranding, locale );

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
        final String savedFrom = FileUtilities.getSavedFrom( productBranding, locale );
        zipOutputStream.setComment( savedFrom );
        zipOutputStream.setMethod( ZipOutputStream.DEFLATED );
        zipOutputStream.setLevel( 9 );
    }

}
