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

import java.io.File;
import java.util.Locale;

import org.apache.commons.io.FilenameUtils;

/**
 * {@code FileUtilities} is a static utilities class for common file
 * functionality that at least wasn't part of Core Java at the time this library
 * was written.
 */
public final class FileUtilities {

    /**
     * The default constructor is disabled, as this is a static utilities class.
     */
    private FileUtilities() {}

    /**
     * Return the extension portion of the file's name, in lower case.
     * 
     * @param file
     *            The file whose extension we need
     * @return The extension of the provided file
     */
    public static String getExtension( final File file ) {
        if ( file == null ) {
            return null;
        }

        final String filename = file.getName();
        return getExtension( filename );
    }

    /**
     * Return the extension portion of the filename, in lower case.
     * 
     * @param filename
     *            The name of the file whose extension we need
     * @return The extension of the file associated with the provided filename
     */
    public static String getExtension( final String filename ) {
        final String extension = FilenameUtils.getExtension( filename );
        return extension.toLowerCase( Locale.ENGLISH );
    }

}
