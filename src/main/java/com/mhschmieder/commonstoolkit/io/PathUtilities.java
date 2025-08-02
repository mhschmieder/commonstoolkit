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

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Utilities for working with Java NIO paths, such as making a filename unique.
 * <p>
 * NOTE: Most of these methods return a Path, to quickly and safely make a File.
 */
public class PathUtilities {

    /**
     * The default constructor is disabled, as this is a static utilities class
     */
    public PathUtilities() {}

    // Get a unique revision tagged filename, given a fully specified path as the
    // filename candidate.
    public static Path getUniqueRevisionTaggedFilePath( 
            final StringBuilder filenameCandidate ) {
        Path path = Paths.get( filenameCandidate.toString() );
        int fileRevision = 1;
        while ( Files.isRegularFile( path, LinkOption.NOFOLLOW_LINKS ) ) {
            // Append the modified revision string to the filename.
            final StringBuilder revisionTaggedFilenameCandidate
                    = FilenameUtilities.getRevisionTaggedFilename( filenameCandidate, 
                                                                   fileRevision );
    
            // Make a new path to check for uniqueness within the directory.
            path = Paths.get( revisionTaggedFilenameCandidate.toString() );
            
            // Increment the revision number until we have a unique filename in the
            // specified directory.
            fileRevision++;
        }
    
        return path;
    }
}
