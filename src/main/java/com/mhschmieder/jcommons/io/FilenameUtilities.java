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

import com.mhschmieder.jcommons.lang.StringConstants;
import com.mhschmieder.jcommons.util.SystemType;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Utilities for working with filenames, such as making a filename unique.
 * <p>
 * Many of these methods wrap Apache Commons IO code. to reduce boilerplate.
 * <p>
 * NOTE: Most of these methods return a StringBuilder vs. a String, to allow
 *  for cascading calls.
 */
public class FilenameUtilities {

    /**
     * The default constructor is disabled, as this is a static utilities class
     */
    public FilenameUtilities() {}

    /**
     * Returns the OS-specific default working directory path.
     * <p>
     * Combines the user root default with the name of the working subdirectory,
     * to generate and return a full path for the working subdirectory.
     *
     * @param workingSubdirectoryName The name of the working subdirectory, sans
     *                                root and parent
     * @return The OS-specific default working directory path
     */
    public static String getWorkingDirectoryPathDefaultForOs(
            final String workingSubdirectoryName ) {
        return getWorkingDirectoryPathDefaultForOs( 
                IoUtilities.WORKING_DIRECTORY_PATH,
                workingSubdirectoryName );
    }

    /**
     * Returns the OS-specific default working directory path.
     * <p>
     * Combines the specified root directory with the name of the working
     * subdirectory and the OS-specific suffix, to generate and return a full
     * path for the working subdirectory.
     *
     * @param rootDirectory           The root directory that serves as the
     *                                parent of the working directory
     * @param workingSubdirectoryName The name of the working subdirectory, or
     *                                file, sans root and parent
     * @return The OS-specific default working directory path
     */
    public static String getWorkingDirectoryPathDefaultForOs(
            final String rootDirectory,
            final String workingSubdirectoryName ) {
        return getWorkingDirectoryPathDefault(
                rootDirectory, workingSubdirectoryName )
                + File.separator
                + SystemType.DETECTED_OS_NAME_SIMPLIFIED;
    }

    /**
     * Returns the OS-neutral default working directory path.
     * <p>
     * Combines the user root default with the name of the working subdirectory,
     * to generate and return a full path for the working subdirectory.
     *
     * @param workingSubdirectoryName The name of the working directory, or
     *                                file, sans root and parent
     * @return The OS-neutral default working directory path
     */
    public static String getWorkingDirectoryPathDefault(
            final String workingSubdirectoryName ) {
        return getWorkingDirectoryPathDefault( 
                IoUtilities.WORKING_DIRECTORY_PATH,
                workingSubdirectoryName );
    }

    /**
     * Returns the OS-neutral default working directory path.
     * <p>
     * Combines the specified root directory with the name of the working
     * subdirectory, to generate and return a full path for the working
     * subdirectory.
     *
     * @param rootDirectory           The root directory that serves as the
     *                                parent of the working directory
     * @param workingSubdirectoryName The name of the working directory sans
     *                                root and parent
     * @return The OS-neutral default working directory path
     */
    public static String getWorkingDirectoryPathDefault(
            final String rootDirectory,
            final String workingSubdirectoryName ) {
        return rootDirectory + File.separator + workingSubdirectoryName;
    }

    // Get a time and version tagged file, given a fully specified path as
    // candidate, alongside a client-supplied version string.
    public static StringBuilder getTimeAndVersionTaggedFilename(
            final StringBuilder filenameCandidate,
            final String version ) {
        // Get the initial version tagged filename candidate.
        final StringBuilder versionTaggedFilenameCandidate = getVersionTaggedFilename( 
                filenameCandidate,
                version );

        // Add a time stamp that will allow for easy traceability of origin.
        final String timeStamp = StringConstants.SPACE + new SimpleDateFormat( 
                "yyyyMMddHHmmss" ).format( new Date() );

        // Append the modified revision string to the filename candidate.
        final int fileSuffixIndex = filenameCandidate.lastIndexOf( StringConstants.PERIOD );
        final StringBuilder timeAndVersionTaggedFilenameCandidate = ( fileSuffixIndex != -1 )
            ? versionTaggedFilenameCandidate.insert( fileSuffixIndex, timeStamp )
            : versionTaggedFilenameCandidate.append( timeStamp );

        return timeAndVersionTaggedFilenameCandidate;
    }

    // Get a version tagged filename, given a fully specified path as
    // candidate, alongside a client-supplied version string.
    public static StringBuilder getVersionTaggedFilename( 
            final StringBuilder filenameCandidate,
            final String version ) {
        // Avoid throwing null pointer exceptions.
        if ( filenameCandidate == null ) {
            return null;
        }
    
        // Make sure the supplied version string doesn't contain directory
        // indirection markers; use dashes in place of dots for version string.
        String versionTag = "_v" + version;
        versionTag = versionTag.replace( '.', '_' );
    
        // Append the modified version string to the filename candidate.
        final int fileSuffixIndex = filenameCandidate.lastIndexOf( StringConstants.PERIOD );
        final StringBuilder versionTaggedFilenameCandidate = ( fileSuffixIndex != -1 )
            ? filenameCandidate.insert( fileSuffixIndex, versionTag )
            : filenameCandidate.append( versionTag );
    
        return versionTaggedFilenameCandidate;
    }

    // Get a revision tagged filename, given a fully specified path as the
    // filename candidate.
    public static StringBuilder getRevisionTaggedFilename( 
            final StringBuilder filenameCandidate,
            final int fileRevision ) {
        // Append the specified revision number.
        final String fileRevisionTag = "_r" + Integer.toString( fileRevision );

        // Append the modified revision string to the filename.
        final int fileSuffixIndex = filenameCandidate.lastIndexOf( 
                StringConstants.PERIOD );
        final StringBuilder revisionTaggedFilenameCandidate = ( fileSuffixIndex != -1 )
            ? filenameCandidate.insert( fileSuffixIndex, fileRevisionTag )
            : filenameCandidate.append( fileRevisionTag );

        return revisionTaggedFilenameCandidate;
    }

    public static StringBuilder getFilenameWithNewSuffix( final File file,
                                                          final String fileSuffix ) {
        try {
            final String canonicalPath = file.getCanonicalPath();
            if ( canonicalPath == null ) {
                return null;
            }
    
            // Replace the old suffix with the required suffix.
            final String newSuffixFilename = removeExtension( canonicalPath ) 
                    + '.'
                    + fileSuffix;
            final StringBuilder filenameBuffer = new StringBuilder( newSuffixFilename );
            return filenameBuffer;
        }
        catch ( final Exception e ) {
            e.printStackTrace();
            return null;
        }
    }

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

    /**
     * Return the file's name minus the extension portion.
     *
     * @param file
     *            The file whose extension should be stripped
     * @return The original filename sans extension
     */
    public static String removeExtension( final File file ) {
        if ( file == null ) {
            return null;
        }
    
        final String filename = file.getName();
        return removeExtension( filename );
    }

    /**
     * Return the filename minus the extension portion.
     *
     * @param filename
     *            The filename whose extension should be stripped
     * @return The original filename sans extension
     */
    public static String removeExtension( final String filename ) {
        return FilenameUtils.removeExtension( filename );
    }

    /**
     * Returns an adjusted file extension, set to the preferred variant.
     * <p>
     * Many file formats have multiple file extensions in use, but there is
     * usually one that has become dominant or preferred over time, and in
     * some cases enforcing a specific extension can also force downstream
     * behavior to assume modern standards are met by the associated file.
     * For example, ".pptx" signifies the current XML format for PowerPoint.
     *
     * @param file
     *            The file whose extension may need adjustment
     * @return an adjusted file extension, set to the preferred variant
     */
    public static String getAdjustedFileExtension( final File file ) {
        final String fileExtension = getExtension( file );
        return adjustFileExtension( fileExtension );
    }

    /**
     * Returns an adjusted file extension, set to the preferred variant.
     * <p>
     * Many file formats have multiple file extensions in use, but there is
     * usually one that has become dominant or preferred over time, and in
     * some cases enforcing a specific extension can also force downstream
     * behavior to assume modern standards are met by the associated file.
     * For example, ".pptx" signifies the current XML format for PowerPoint.
     *
     * @param fileExtension
     *            The original file extension
     * @return an adjusted file extension, set to the preferred variant
     */
    public static String adjustFileExtension( final String fileExtension ) {
        String fileExtensionAdjusted = fileExtension;
    
        switch ( fileExtension ) {
        case "bmp":
        case "dib":
            fileExtensionAdjusted = "bmp";
            break;
        case "eps":
        case "epsf":
            fileExtensionAdjusted = "eps";
            break;
        case "jpg":
        case "jpeg":
        case "jpe":
            fileExtensionAdjusted = "jpg";
            break;
        case "pptx":
        case "ppt":
            fileExtensionAdjusted = "pptx";
            break;
        case "tif":
        case "tiff":
            fileExtensionAdjusted = "tiff";
            break;
        case "wbm":
        case "wbmp":
            fileExtensionAdjusted = "wbmp";
            break;
        case "gif":
        case "pdf":
        case "png":
        case "pnm":
        case "svg":
        default:
            // For these formats, use the file extension as-is; no variants.
            break;
        }
    
        return fileExtensionAdjusted;
    }
}
