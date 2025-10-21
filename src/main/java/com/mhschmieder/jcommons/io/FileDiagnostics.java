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
 * This file is part of the JCommons Library
 *
 * You should have received a copy of the MIT License along with the
 * JCommons Library. If not, see https://opensource.org/licenses/MIT.
 *
 * Project: https://github.com/mhschmieder/jcommons
 */
package com.mhschmieder.jcommons.io;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * {@code FileDiagnostics} is a static utilities class for common file
 * diagnostics that wasn't part of Core Java or Apache Commons IO at the time 
 * this library was created.
 */
public class FileDiagnostics {

    /**
     * The default constructor is disabled, as this is a static utilities class
     */
    public FileDiagnostics() {}

    public static boolean checkFileExists( final String filePath,
                                           final String fileType,
                                           final boolean reportNotExists ) {
        try {
            final Path path = Paths.get( filePath );
            final boolean fileExists = Files.exists(
                    path, LinkOption.NOFOLLOW_LINKS );
            if ( !fileExists ) {
                if ( reportNotExists ) {
                    System.out.println(
                            ">> "
                            + fileType
                            + " Does Not Exist or is Empty: "
                            + filePath );
                }
                return false;
            }
        } catch ( final Exception e ) {
            e.printStackTrace();
            System.out.println(
                    ">> "
                    + fileType
                    + " Invalid or Denied Access: "
                    + filePath );
            return false;
        }

        return true;
    }

    /**
     * Returns {@code true} if the directory has no files or subdirectories.
     *
     * @param directory The directory to check for files and subdirectories
     * @return {@code true} if the directory has no files or subdirectories
     */
    public static boolean isDirectoryEmpty( final File directory ) {
        final File[] children = directory.listFiles();
        return ( children == null ) || ( children.length <= 0 );
    }
}
