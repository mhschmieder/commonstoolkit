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
package com.mhschmieder.jcommons.util;

import com.mhschmieder.jcommons.io.IoUtilities;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.prefs.Preferences;

/**
 * Utilities for managing preferences for common shared application attributes,
 * such as File Chooser directories, default directories, and MRU File Lists.
 */
public class PreferenceUtilities {

    /**
     * The MRU cache size is generally limited to in-line menu items using
     * numeric mnemonics from 1-9, as extending to letters gets confusing and
     * blocks those shortcuts from use with common File commands in menus.
     */
    public static final int MRU_CACHE_SIZE = 9;

    /**
     * The default constructor is disabled, as this is a static utilities class
     */
    public PreferenceUtilities() {}

    // Load the MRU Filename Cache from User Preferences.
    // TODO: Use the same collection or array type for load and save.
    public static String[] loadMruPreferences( final Preferences preferences ) {
        final int maximumNumberOfMruFiles = PreferenceUtilities.MRU_CACHE_SIZE;
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
        final int maximumNumberOfMruFiles = Math.min( PreferenceUtilities.MRU_CACHE_SIZE, 
                                                      mruFilenames.size() );
    
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
        final String workingDirectoryPath = IoUtilities.WORKING_DIRECTORY_PATH;
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

    // Load the Default Directory from User Preferences.
    public static File loadDefaultDirectoryPreference( final Preferences preferences ) {
        return PreferenceUtilities.loadDirectoryPreference( preferences, 
                                                            "defaultDirectory" );
    }

    // Save the Default Directory to User Preferences.
    public static void saveDefaultDirectoryPreference( final File defaultDirectory,
                                                       final Preferences preferences ) {
        PreferenceUtilities.saveDirectoryPreference( defaultDirectory, 
                                                     preferences, 
                                                     "defaultDirectory" );
    }
}
