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
 * CommonsToolkit Library. If not, see <https://opensource.org/licenses/MIT>.
 *
 * Project: https://github.com/mhschmieder/commonstoolkit
 */
package com.mhschmieder.commonstoolkit.io;

import com.mhschmieder.commonstoolkit.lang.StringConstants;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.file.PathUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Facilities for moving files safely.
 */
public class FileMover {

    /**
     * The default constructor is disabled, as this is a static utilities class
     */
    public FileMover() {}

    public static String moveFileToCache(
            final String originalDirectoryBasePathname,
            final String originalFilename,
            final String cacheDirectoryPathname,
            final String cacheFileBaseName,
            final boolean copyToCache ) {
        return moveFileToCache(
                originalDirectoryBasePathname,
                originalFilename,
                cacheDirectoryPathname,
                cacheFileBaseName,
                null,
                copyToCache );
    }

    public static String moveFileToCache(
            final String originalDirectoryBasePathname,
            final String originalFilename,
            final String cacheDirectoryPathname,
            final String cacheFileBaseName,
            final String cacheFileSuffix,
            final boolean copyToCache ) {
        // This is the file path that we start with, but it gets replaced by the
        // cache directory path if the file moves are successful.
        final String originalFilePath
                = FilenameUtilities.getWorkingDirectoryPathDefault(
                originalDirectoryBasePathname,
                originalFilename );

        try {
            // Check that the original file was properly created.
            final File originalFileLocation = new File( originalFilePath );
            if ( PathUtils.isEmptyFile( originalFileLocation.toPath() ) ) {
                return null;
            }

            final String suffix = ( cacheFileSuffix != null )
                    && !cacheFileSuffix.isEmpty()
                    ? cacheFileSuffix
                    : StringConstants.PERIOD + FilenameUtilities.getExtension( 
                            originalFilename );

            final File cacheFileLocation = FileUtilities.makeTempFile(
                    cacheDirectoryPathname, cacheFileBaseName, suffix );
            final String cacheFilePathname = cacheFileLocation.getAbsolutePath();

            // Conditionally copy or rename the original file to the cache
            // directory location. Typically this is meant to preserve interim
            // results rather than overwrite the same cached file over and over.
            // NOTE: The NIO Files.copy() approach is by far the fastest, at
            //  least on Windows running Java 8, but some say the old stream
            //  copy approach is the fastest. Maybe only before Java 8, as our
            //  own testing consistently shows the NIO Files.copy() approach to
            //  be by far the fastest, with the NIO FileChannel approach a bit
            //  faster than the old stream copy approach.
            // NOTE: We use the Apache Commons I/O wrapper for NIO Files.copy(),
            //  for better vetting by peers than the direct solution copied off
            //  the web. File Channels are safe for concurrent multi-threaded
            //  code blocks such as we have here, so are a valid alternative.
            // NOTE: This code previously did a move operation, which frequently
            //  fails if the file is produced by an external C++ application and
            //  may still be open or not have its file lock released yet. As the
            //  source file is always the same for each exec call anyway, there
            //  is no need to delete it afterwards. This is much faster!
            if ( copyToCache ) {
                // NOTE: Until we are 100% on Java 8 and can supply the extra
                //  Copy Options parameters, we need to pre-delete the target
                //  file to make sure it doesn't already exist and throw errors.
                // NOTE: If we don't force delete, the invoker may slow to a
                //  crawl and have to be terminated as it could take weeks to
                //  complete! At any rate, this code gives incorrect results and
                //  run-time errors, even though the only difference is that
                //  Apache recovers from a failed renameTo() by doing an Apache
                //  copyFile() followed by a File.delete() on the source file
                //  then a "delete quietly" call on the destination file if the
                //  source file deletion failed.
                FileUtils.forceDelete( cacheFileLocation );
                FileUtils.copyFile(
                        originalFileLocation,
                        cacheFileLocation,
                        StandardCopyOption.REPLACE_EXISTING );
            } else {
                // Apache Commons moveFile checks if target file exists and
                // fails if so. In our case the target file already exists
                // because the cached file is generated using Java core calls
                // that create it on disc rather than just making a temp file,
                // so our alternative to copyFile must be the older renameTo.
                // NOTE: This has been revised, as forcing the cached file to
                //  pre-delete may allow Apache Commons to move the file, or to
                //  call the standard renameTo() method if the file move fails.
                // TODO: Change the file cache strategy to avoid this step, or
                //  pass in the ultimate cached name of each cached file to any
                //  invoked application in C++/etc. and avoid the post-run move.
                if ( !originalFileLocation.renameTo( cacheFileLocation ) ) {
                    System.out.println(
                            ">> Trouble Moving File to Cache Directory: "
                                    + originalFilePath );
                    return originalFilePath;
                }
            }

            // Now it is safe to use the cache directory as the cached file path.
            return cacheFilePathname;
        }
        catch ( final Exception e ) {
            e.printStackTrace();
            System.out.println(
                    ">> Exception Moving or Copying File to Cache Directory: "
                            + originalFilePath );
            return originalFilePath;
        }
    }

    // Move or rename a source file to a target file (system-specific).
    public static boolean moveFile( final File sourceFile, 
                                    final File targetFile ) {
        // TODO: Verify that network drives work as the target location, as
        //  it is illegal to rename (vs. copy) a file from one system to
        //  another. Probably Java NIO2 deals with this for us.
        final Path sourcePath = sourceFile.toPath();
        final Path targetPath = targetFile.toPath();
    
        try {
            // Do not move invalid or empty source files.
            if ( !PathUtils.isRegularFile( sourcePath, LinkOption.NOFOLLOW_LINKS )
                    || PathUtils.isEmptyFile( sourcePath ) ) {
                return false;
            }
    
            // NOTE: File status can change suddenly, so it is best to
            //  re-check whether a target file is writable before a move or
            //  rename operation. We always replace an existing file, and
            //  depend on the file chooser to alert the user of overwrites.
            final boolean isTargetFile = Files.exists( targetPath, LinkOption.NOFOLLOW_LINKS )
                    && !Files.notExists( targetPath, LinkOption.NOFOLLOW_LINKS );
            if ( !isTargetFile ) {
                // If the target file doesn't exist, create it.
                Files.createFile( targetPath );
            }
            if ( !Files.isWritable( targetPath ) ) {
                return false;
            }
    
            // NOTE: If we also specify to copy the attributes, we get
            //  run-time exceptions on Windows 10 due to security issues.
            Files.move( sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING );
        }
        catch ( final Exception e ) {
            e.printStackTrace();
            return false;
        }
    
        return true;
    }
}
