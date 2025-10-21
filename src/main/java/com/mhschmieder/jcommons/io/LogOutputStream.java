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
 * This file is part of the JCommons Library
 *
 * You should have received a copy of the MIT License along with the
 * JCommons Library. If not, see <https://opensource.org/licenses/MIT>.
 *
 * Project: https://github.com/mhschmieder/jcommons
 */
package com.mhschmieder.jcommons.io;

import com.mhschmieder.jcommons.lang.StringConstants;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;

/**
 * This class is a workaround for legacy code that uses System.out and
 * System.err println() methods, alongside the use of printStackTrace() calls
 * in exceptions. It also formats the output for cross-platform conformance.
 */
public class LogOutputStream extends OutputStream {

    /** Define a static Logger to log the legacy STDERR/STDOUT messages. */
    private static Logger      LOGGER                  =
                                      Logger.getLogger( LogOutputStream.class.getName() );

    private final StringBuffer buffer                  = new StringBuffer();

    private final char         lineSeparatorEndUnixDos = 'n';
    private final char         lineSeparatorEndMac     = 'r';

    @Override
    public void write( final int b ) throws IOException {
        // Convert the integer-sized byte to a character.
        final char ch = ( char ) b;

        // Keep writing to the logger's continuous buffer.
        buffer.append( ch );

        // Detect line separators.
        // NOTE: This is more complicated than it could be for performance
        // reasons. Because the line separator is different on Unix and Windows
        // system, the only way to get cross-platform code is to use the
        // newline.separator System Property. But this property is a string, and
        // substring searches are slower than character comparisons (especially
        // since we are already looping through characters in any case). So, the
        // lineSeparatorEnd char is used to detect probable line separators and
        // then a substring search is used (with the actual line separator) to
        // confirm it and to print out the contents of the buffer.
        if ( ( ch == lineSeparatorEndUnixDos ) || ( ch == lineSeparatorEndMac ) ) {
            // Check on a char by char basis for speed.
            final String s = buffer.toString();
            if ( s.indexOf( StringConstants.LINE_SEPARATOR ) != -1 ) {
                // The whole separator string is written.
                // NOTE: We set the Logger to Info-level to avoid needing to
                // configure the levels, as that is the default level for
                // logging. This may not catch all third-party tracing though.
                LOGGER.info( s.substring( 0,
                                          s.length() - StringConstants.LINE_SEPARATOR.length() ) );
                buffer.setLength( 0 );
            }
        }
    }

}
