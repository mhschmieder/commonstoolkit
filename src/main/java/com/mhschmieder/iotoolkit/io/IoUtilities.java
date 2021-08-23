/**
 * MIT License
 *
 * Copyright (c) 2020 Mark Schmieder
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
 * This file is part of the IoToolkit Library
 *
 * You should have received a copy of the MIT License along with the
 * GraphicsToolkit Library. If not, see <https://opensource.org/licenses/MIT>.
 *
 * Project: https://github.com/mhschmieder/iotoolkit
 */
package com.mhschmieder.iotoolkit.io;

/**
 * {@code IoUtilities} is a static utilities class for common I/O functionality
 * that at least wasn't part of Core Java at the time this library was written.
 */
public final class IoUtilities {

    /**
     * The default constructor is disabled, as this is a static utilities class.
     */
    private IoUtilities() {
    }

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

}
