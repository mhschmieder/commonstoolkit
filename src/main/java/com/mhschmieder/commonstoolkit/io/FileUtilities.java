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

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.file.PathUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * {@code FileUtilities} is a static utilities class for common file
 * functionality that wasn't part of Core Java or Apache Commons IO at the time 
 * this library was created.
 */
public final class FileUtilities {

    /**
     * The default constructor is disabled, as this is a static utilities class
     */
    private FileUtilities() {}

    public static File makeFile( final String filePathname )
            throws IOException {
        final Path path = Paths.get( filePathname );
        final Path filePath = Files.createFile( path );
        return filePath.toFile();
    }

    public static File makeTempFile( final String tempDirectoryPathname,
                                     final String tempFileBaseName,
                                     final String suffix )
            throws IOException {
        final Path tempDirectoryPath = Paths.get( tempDirectoryPathname );
        final Path tempFilePath = Files.createTempFile(
                tempDirectoryPath, tempFileBaseName, suffix );
        return tempFilePath.toFile();
    }

    public static boolean deleteIfExists( final String filePath,
                                          final String fileType ) {
        try {
            final Path path = Paths.get( filePath );
            return Files.deleteIfExists( path );
        } catch ( final Exception e ) {
            e.printStackTrace();
            System.out.println(
                    ">> "
                    + fileType
                    + " Invalid or Denied Access: "
                    + filePath );
            return false;
        }
    }

    /**
     * Deletes all the files and subdirectories of the given directories, along
     * with the directory itself (conditional upon the supplied flag).
     *
     * @param rootDirectories The directories to delete (along with contents)
     * @return {@code true} if directories were deleted; {@code false} otherwise
     */
    public static boolean deleteDirectories(
            final File[] rootDirectories,
            final boolean retainDirectoryStructure ) {
        boolean sawErrors = false;

        for ( final File rootDirectory : rootDirectories ) {
            // Reset the root directory from any previous uses.
            if ( retainDirectoryStructure ) {
                sawErrors |= deleteDirectoryContents( rootDirectory );
            } else {
                sawErrors |= deleteDirectoryAndContents( rootDirectory );
            }
        }

        return sawErrors;
    }

    /**
     * Deletes all the files and subdirectories of the given directory, along
     * with the directory itself.
     * <p>
     * NOTE: This destroys the full structure of the current directory tree.
     *
     * @param rootDirectory The directory to delete (along with its contents)
     * @return {@code true} if directory was deleted; {@code false} otherwise
     */
    public static boolean deleteDirectoryAndContents( final File rootDirectory ) {
        try {
            if ( !FileUtils.isDirectory( rootDirectory ) ) {
                return false;
            }

            FileUtils.forceDelete( rootDirectory );
        }
        catch ( final Exception e ) {
            e.printStackTrace();
            return false;
        }

       return true;
    }

    /**
     * Deletes all the files of the given directory and its subdirectory files.
     * <p>
     * NOTE: This preserves the full structure of the current directory tree.
     *
     * @param rootDirectory The directory whose contents are to be deleted
     * @return {@code true} if contents were deleted; {@code false} otherwise
     */
    public static boolean deleteDirectoryContents( final File rootDirectory ) {
        try {
            if ( !FileUtils.isDirectory( rootDirectory ) ) {
                return false;
            }

            PathUtils.cleanDirectory( rootDirectory.toPath() );
        }
        catch ( final Exception e ) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    // Get a time and version tagged file, given a fully specified path as
    // candidate, alongside a client-supplied version string.
    public static File getTimeAndVersionTaggedFile( final StringBuilder filenameCandidate,
                                                    final String version ) {
        final StringBuilder timeAndVersionTaggedFilenameCandidate 
                = FilenameUtilities.getTimeAndVersionTaggedFilename( filenameCandidate,
                                                                     version );
        return new File( timeAndVersionTaggedFilenameCandidate.toString() );
    }

    // Get a unique version tagged file, given a fully specified path as
    // candidate, alongside a client-supplied version string.
    public static File getUniqueVersionTaggedFile( final StringBuilder fileNameCandidate,
                                                   final String version ) {
        // Get the initial version tagged file name candidate.
        final StringBuilder versionTaggedFileNameCandidate = FilenameUtilities
                .getVersionTaggedFilename( fileNameCandidate,
                                           version );

        // Conditionally revision tag the version tagged filename.
        return getUniqueRevisionTaggedFile( versionTaggedFileNameCandidate );
    }

    // Get a unique revision tagged file, given a fully specified path as the
    // filename candidate.
    public static File getUniqueRevisionTaggedFile( final StringBuilder filenameCandidate ) {
        File uniqueFile = null;
        try {
            final Path uniquePath = PathUtilities
                    .getUniqueRevisionTaggedFilePath( filenameCandidate );
            uniqueFile = uniquePath.toFile();
        }
        catch ( final Exception e ) {
            e.printStackTrace();
        }

        return uniqueFile;
    }
}