/**
 * MIT License
 *
 * Copyright (c) 2020, 2021 Mark Schmieder
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
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.SwappedDataInputStream;

/**
 * {@code IoUtilities} is a static utilities class for common I/O functionality
 * that at least wasn't part of Core Java at the time this library was written.
 * <p>
 * In particular, this class contains utilities for loading streams and files
 * into a StringBuilder, which Oracle recommends in place of StringBuffer.
 */
public final class IoUtilities {

    // :NOTE: We might need to switch this to UTF-8 encoding.
    @SuppressWarnings("nls") public static final String LATIN_1 = "ISO-8859-1";

    /**
     * The default constructor is disabled, as this is a static utilities class.
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
    // :NOTE: The data array must be pre-allocated, but is filled here.
    public static boolean loadIntoDoubleArray( final SwappedDataInputStream inputStream,
                                               final double[] data,
                                               final int dataOffset,
                                               final int numberOfDataPoints,
                                               final double defaultValue ) {
        // Avoid throwing unnecessary exceptions by not attempting to open bad
        // streams or use unallocated or incorrectly sized data arrays.
        // :NOTE: We mustn't assume the invoker requires all data to be loaded
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
                                                 final StringBuilder stringBuilder ) {
        try {
            String line = bufferedReader.readLine();
            while ( line != null ) {
                stringBuilder.append( line ).append( "\n" );
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
    public static boolean loadIntoStringBuilder( final File file, final StringBuilder buffer ) {
        // Chain a BufferedReader to a FileReader, for better performance.
        try ( final FileReader fileReader = new FileReader( file );
                final BufferedReader bufferedReader = new BufferedReader( fileReader ) ) {
            final boolean fileOpened = loadIntoStringBuilder( bufferedReader, buffer );
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
                                                 final StringBuilder buffer ) {
        // Chain a BufferedReader to an InputStreamReader, for better
        // performance.
        try ( final InputStreamReader inputStreamReader = new InputStreamReader( inputStream,
                                                                                 LATIN_1 );
                final BufferedReader bufferedReader = new BufferedReader( inputStreamReader ) ) {
            final boolean fileOpened = loadIntoStringBuilder( bufferedReader, buffer );
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

}
