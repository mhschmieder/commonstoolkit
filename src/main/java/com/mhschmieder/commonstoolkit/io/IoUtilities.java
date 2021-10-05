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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * {@code IoUtilities} is a static utilities class for common I/O functionality
 * that at least wasn't part of Core Java at the time this library was written.
 */
public final class IoUtilities {

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
