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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.prefs.Preferences;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.file.PathUtils;

import com.mhschmieder.commonstoolkit.branding.ProductBranding;

/**
 * {@code FileUtilities} is a static utilities class for common file
 * functionality that at least wasn't part of Core Java at the time this library
 * was written.
 */
public final class FileUtilities {

    /**
     * Cache the current user's Working Directory path, to avoid expensive
     * redundant calls and to enforce a consistent path that persists throughout
     * a session, unlike "." for the Current Directory. This also corresponds in
     * many cases to Application Launch Path, which helps encapsulate app files.
     */
    public static final String WORKING_DIRECTORY_PATH = System.getProperty( "user.dir" );

    /**
     * The MRU cache size is generally limited to in-line menu items using
     * numeric mnemonics from 1-9, as extending to letters gets confusing and
     * blocks those shortcuts from use with common File commands in menus.
     */
    public static final int    MRU_CACHE_SIZE         = 9;

    /**
     * The default constructor is disabled, as this is a static utilities class
     */
    private FileUtilities() {}

    // Get a package path from a jar-resident class instance, to use as a file
    // prefix when supplying only the file name.
    public static String getPackagePath( @SuppressWarnings("rawtypes") final Class classInstance ) {
        final String packagePath = "/"
                + classInstance.getClass().getPackage().getName().replaceAll( ".", "/" );
        return packagePath;
    }

    // Load the MRU Filename Cache from User Preferences.
    // TODO: Use the same collection or array type for load and save.
    public static String[] loadMruPreferences( final Preferences preferences ) {
        final int maximumNumberOfMruFiles = MRU_CACHE_SIZE;
        final String[] mruFilenames = new String[ maximumNumberOfMruFiles ];

        for ( int i = 0; i < maximumNumberOfMruFiles; i++ ) {
            final int mruFileNumber = i + 1;
            final String mruFilenameKey = "mruFilename" + Integer.toString( mruFileNumber );
            final String mruFilename = preferences.get( mruFilenameKey, "" );
            mruFilenames[ i ] = mruFilename;
        }

        return mruFilenames;
    }

    // Save the MRU Filename Cache to User Preferences.
    // TODO: Use the same collection or array type for load and save.
    public static void saveMruPreferences( final List< String > mruFilenames,
                                           final Preferences preferences ) {
        final int maximumNumberOfMruFiles = Math.min( MRU_CACHE_SIZE, mruFilenames.size() );

        for ( int i = 0; i < maximumNumberOfMruFiles; i++ ) {
            final String mruFilename = mruFilenames.get( i );
            final int mruFileNumber = i + 1;
            final String mruFilenameKey = "mruFilename" + Integer.toString( mruFileNumber );
            preferences.put( mruFilenameKey, mruFilename );
        }
    }

    // Load a specified Directory from User Preferences.
    public static File loadDirectoryPreference( final Preferences preferences,
                                                final String directoryKey ) {
        // Use the current user's Home Directory as the ultimate fallback.
        // This gets used if the preferred directory is malformed or doesn't
        // exist, or if an exception is thrown during preferences handling.
        File directory = FileUtils.getUserDirectory();

        // NOTE: The current user's Working Directory is set as the default if
        //  the preferred default directory hasn't been set as a preference yet.
        //  This often corresponds to the application installation directory and
        //  is a more convenient starting point for a Directory Chooser than the
        //  top of the file system or the current user's Home Directory.
        final String workingDirectoryPath = WORKING_DIRECTORY_PATH;
        try {
            final String preferredDirectoryPath = preferences.get( directoryKey,
                                                                   workingDirectoryPath );
            if ( ( preferredDirectoryPath != null ) && !preferredDirectoryPath.trim().isEmpty() ) {
                final File preferredDirectory = new File( preferredDirectoryPath );
                if ( Files.isDirectory( preferredDirectory.toPath() ) ) {
                    // If the preferred directory exists and is valid, set it.
                    directory = preferredDirectory;
                }
            }
        }
        catch ( final Exception e ) {
            e.printStackTrace();
        }

        return directory;
    }

    // Load the Default Directory from User Preferences.
    public static File loadDefaultDirectoryPreference( final Preferences preferences ) {
        return loadDirectoryPreference( preferences, "defaultDirectory" );
    }

    // Save a specified Directory to User Preferences.
    public static void saveDirectoryPreference( final File preferredDirectory,
                                                final Preferences preferences,
                                                final String directoryKey ) {
        if ( preferredDirectory == null ) {
            return;
        }

        try {
            if ( Files.isDirectory( preferredDirectory.toPath() ) ) {
                // If the preferred directory exists and is valid, save it.
                preferences.put( directoryKey, preferredDirectory.getCanonicalPath() );
            }
        }
        catch ( final Exception e ) {
            e.printStackTrace();
        }
    }

    // Save the Default Directory to User Preferences.
    public static void saveDefaultDirectoryPreference( final File defaultDirectory,
                                                       final Preferences preferences ) {
        saveDirectoryPreference( defaultDirectory, preferences, "defaultDirectory" );
    }

    // Generic method to get the "saved from" text for a document.
    public static String getSavedFrom( final ProductBranding productBranding,
                                       final Locale locale ) {
        final StringBuilder savedFrom = new StringBuilder();
        savedFrom.append( "Saved from " );
        savedFrom.append( productBranding.productVersionProtected );
        savedFrom.append( "; Locale = " );
        savedFrom.append( locale.getDisplayName() );

        return savedFrom.toString();
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
        final String fileExtension = FileUtilities.getExtension( file );
        return adjustFileExtension( fileExtension );
    }

    public static StringBuilder getFileNameWithNewSuffix( final File file,
                                                          final String fileSuffix ) {
        try {
            final String canonicalPath = file.getCanonicalPath();
            if ( canonicalPath == null ) {
                return null;
            }

            // Replace the old suffix with the required suffix.
            final String newSuffixFileName = FilenameUtils.removeExtension( canonicalPath ) + '.'
                    + fileSuffix;
            final StringBuilder filenameBuffer = new StringBuilder( newSuffixFileName );
            return filenameBuffer;
        }
        catch ( final Exception e ) {
            e.printStackTrace();
            return null;
        }
    }

    // Copy a file using streams (the oldest approach, but sometimes fastest).
    // NOTE: There is no need to provide a wrapper for Java NIO Files.copy() as
    //  an additional option, as Apache Commons IO FileUtils.copyFile() does that.
    public static long copyFileUsingStreams( final File source, final File dest ) {
        long totalNumberOfBytesRead = 0L;

        try ( final InputStream is = new FileInputStream( source );
                final OutputStream os = new FileOutputStream( dest ) ) {
            totalNumberOfBytesRead = copyFileStream( is, os );
        }
        catch ( final Exception e ) {
            e.printStackTrace();
        }

        return totalNumberOfBytesRead;
    }

    // Copy a file stream using traditional Java IO.
    public static long copyFileStream( final InputStream is, final OutputStream os ) {
        long totalNumberOfBytesRead = 0L;

        try {
            byte[] buffer = new byte[ 8192 ];
            int numberOfBytesRead;
            while ( ( numberOfBytesRead = is.read( buffer ) ) > 0 ) {
                os.write( buffer, 0, numberOfBytesRead );
                totalNumberOfBytesRead += numberOfBytesRead;
            }
        }
        catch ( final Exception e ) {
            e.printStackTrace();
        }

        return totalNumberOfBytesRead;
    }

    // Copy a file using channels (a somewhat newer approach, sometimes
    // fastest).
    // NOTE: There is no need to provide a wrapper for Java NIO Files.copy() as
    //  an additional option, as Apache Commons IO FileUtils.copyFile() does that.
    public static long copyFileUsingChannels( final File source, final File dest ) {
        long totalNumberOfBytesRead = 0L;

        try ( final FileInputStream sourceStream = new FileInputStream( source );
                final FileChannel sourceChannel = sourceStream.getChannel();
                final FileOutputStream destStream = new FileOutputStream( dest );
                final FileChannel destChannel = destStream.getChannel() ) {
            totalNumberOfBytesRead = destChannel
                    .transferFrom( sourceChannel, 0L, sourceChannel.size() );
        }
        catch ( final Exception e ) {
            e.printStackTrace();
        }

        return totalNumberOfBytesRead;
    }

    // Move or rename a source file to a target file (system-specific).
    public static boolean moveFile( final File sourceFile, final File targetFile ) {
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

    // Get a time and version tagged file, given a fully specified path as
    // candidate, alongside a client-supplied version string.
    public static File getTimeAndVersionTaggedFile( final StringBuilder fileNameCandidate,
                                                    final String version ) {
        // Get the initial version tagged file name candidate.
        final StringBuilder versionTaggedFileNameCandidate =
                                                           getVersionTaggedFileName( fileNameCandidate,
                                                                                     version );

        // Add a time stamp that will allow for easy traceability of origin.
        final String timeStamp =
                               "_" + new SimpleDateFormat( "yyyyMMddHHmmss" ).format( new Date() );

        // Append the modified revision string to the file name candidate.
        final int fileSuffixIndex = fileNameCandidate.lastIndexOf( "." );
        final StringBuilder timeAndVersionTaggedFileNameCandidate = ( fileSuffixIndex != -1 )
            ? versionTaggedFileNameCandidate.insert( fileSuffixIndex, timeStamp )
            : versionTaggedFileNameCandidate.append( timeStamp );
        final File timeAndVersionTaggedFile = new File( timeAndVersionTaggedFileNameCandidate
                .toString() );

        return timeAndVersionTaggedFile;
    }

    // Get a unique version tagged file, given a fully specified path as
    // candidate, alongside a client-supplied version string.
    public static File getUniqueVersionTaggedFile( final StringBuilder fileNameCandidate,
                                                   final String version ) {
        // Get the initial version tagged file name candidate.
        final StringBuilder versionTaggedFileNameCandidate =
                                                           getVersionTaggedFileName( fileNameCandidate,
                                                                                     version );

        // Conditionally revision tag the version tagged file name.
        final File uniqueFile = getUniqueRevisionTaggedFile( versionTaggedFileNameCandidate );

        return uniqueFile;
    }

    // Get a unique revision tagged file, given a fully specified path as the
    // file name candidate.
    public static File getUniqueRevisionTaggedFile( final StringBuilder fileNameCandidate ) {
        File uniqueFile = null;

        try {
            Path path = Paths.get( fileNameCandidate.toString() );
            int fileRevision = 1;
            while ( Files.isRegularFile( path, LinkOption.NOFOLLOW_LINKS ) ) {
                // Append a recursively incremented revision number until we
                // have a unique file name in the specified directory.
                final String fileRevisionTag = "_r" + Integer.toString( fileRevision++ );

                // Append the modified revision string to the file name.
                final int fileSuffixIndex = fileNameCandidate.lastIndexOf( "." );
                final StringBuilder revisionTaggedFileNameCandidate = ( fileSuffixIndex != -1 )
                    ? fileNameCandidate.insert( fileSuffixIndex, fileRevisionTag )
                    : fileNameCandidate.append( fileRevisionTag );

                // Make a new path to check for uniqueness within the directory.
                path = Paths.get( revisionTaggedFileNameCandidate.toString() );
            }

            uniqueFile = path.toFile();
        }
        catch ( final Exception e ) {
            e.printStackTrace();
        }

        return uniqueFile;
    }

    // Get a version tagged file name, given a fully specified path as
    // candidate, alongside a client-supplied version string.
    public static StringBuilder getVersionTaggedFileName( final StringBuilder fileNameCandidate,
                                                          final String version ) {
        // Avoid throwing null pointer exceptions.
        if ( fileNameCandidate == null ) {
            return null;
        }

        // Make sure the supplied version string doesn't contain directory
        // indirection markers; use dashes in place of dots for version string.
        String versionTag = "_v" + version;
        versionTag = versionTag.replace( '.', '_' );

        // Append the modified version string to the file name candidate.
        final int fileSuffixIndex = fileNameCandidate.lastIndexOf( "." );
        final StringBuilder versionTaggedFileNameCandidate = ( fileSuffixIndex != -1 )
            ? fileNameCandidate.insert( fileSuffixIndex, versionTag )
            : fileNameCandidate.append( versionTag );

        return versionTaggedFileNameCandidate;
    }

    /**
     * This method finds the first occurrence of a particular file type in a
     * supplied ZIP file, and returns a ready-to-use ZIP Entry wrapped in an
     * Optional so as to avoid null pointer exceptions.
     *
     * @param zipFile
     *            The ZIP File to search for a specific File Type
     * @param fileType
     *            The simple file extension used to search for the File Type
     * @return A ZIP Entry corresponding to the first match, or null if not
     *         present, but wrapped as an Optional for code safety
     */
    public static Optional< ? extends ZipEntry > findFileTypeInZip( final ZipFile zipFile,
                                                                    final String fileType ) {
        // Each file access has to start a new stream due to auto-close.
        final Stream< ? extends ZipEntry > zipStream = zipFile.stream();
        final Predicate< ZipEntry > isCorrectFileType = zipEntry -> FilenameUtils
                .isExtension( zipEntry.getName().toLowerCase( Locale.ENGLISH ), fileType );

        final Predicate< ZipEntry > isFile = zipEntry -> !zipEntry.isDirectory();
        final Optional< ? extends ZipEntry > optionalZipEntry = zipStream
                .filter( isFile.and( isCorrectFileType ) ).findFirst();

        return optionalZipEntry;
    }

    /**
     * This method finds an occurrence of a particular file name in a
     * supplied ZIP file, and returns a ready-to-use ZIP Entry wrapped in an
     * Optional so as to avoid null pointer exceptions.
     *
     * @param zipFile
     *            The ZIP File to search for a specific File Name
     * @param fileName
     *            The simple file name to search for, sans path
     * @return A ZIP Entry corresponding to a file match, or null if not
     *         present, but wrapped as an Optional for code safety
     */
    public static Optional< ? extends ZipEntry > findFileNameInZip( final ZipFile zipFile,
                                                                    final String fileName ) {
        // Each file access has to start a new stream due to auto-close.
        final Stream< ? extends ZipEntry > zipStream = zipFile.stream();
        final Predicate< ZipEntry > isCorrectFileName = zipEntry -> zipEntry.getName()
                .equals( fileName );

        final Predicate< ZipEntry > isFile = zipEntry -> !zipEntry.isDirectory();
        final Optional< ? extends ZipEntry > optionalZipEntry = zipStream
                .filter( isFile.and( isCorrectFileName ) ).findFirst();

        return optionalZipEntry;
    }
}