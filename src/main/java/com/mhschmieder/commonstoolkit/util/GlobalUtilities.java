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
package com.mhschmieder.commonstoolkit.util;

import java.io.File;
import java.nio.file.Files;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

/**
 * {@code GlobalUtilities} is a utility class for global methods that are
 * oriented towards producing or fetching singleton instances of things that are
 * expensive to query more than once and which tend to be treated as singletons
 * within an application instance. Also, minimal API dependencies are expected.
 */
public class GlobalUtilities {

    /**
     * The default constructor is disabled, as this is a static utilities class.
     */
    private GlobalUtilities() {}

    /**
     * Make the Client Properties using Java JVM arguments and JNLP properties.
     * <p>
     * The general strategy is to try to find them in the named arguments, and
     * then if still uninitialized, fetch them from the the System Properties.
     *
     * @param namedArguments
     *            The named arguments passed to the application launcher,
     *            including JNLP parameters and command-line JVM arguments
     *
     * @return An instance of {@link ClientProperties} to avoid redundant
     *         run-time queries
     */
    @SuppressWarnings("nls")
    public static ClientProperties makeClientProperties( final Map< String, String > namedArguments ) {
        // First determine whether we have command-line or JNLP arguments.
        final boolean hasNamedArguments = !namedArguments.isEmpty();

        String osNameVerbose = "";
        try {
            String osName = null;
            String osVersion = null;
            String osArchitecture = null;
            if ( hasNamedArguments ) {
                osName = namedArguments.get( "os.name" );
                osVersion = namedArguments.get( "os.version" );
                osArchitecture = namedArguments.get( "os.arch" );
            }
            if ( osName == null ) {
                osName = System.getProperty( "os.name" );
            }
            if ( osVersion == null ) {
                osVersion = System.getProperty( "os.version" );
            }
            if ( osArchitecture == null ) {
                osArchitecture = System.getProperty( "os.arch" );
            }
            osNameVerbose = osName + " " + osVersion + " " + osArchitecture;
        }
        catch ( final Exception e ) {
            e.printStackTrace();
        }

        // Get the user's screen size, for Full Screen Mode and user statistics.
        // TODO: Also get and cache the minimum point, which may not be zero.
        final Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        final double screenWidth = visualBounds.getWidth();
        final double screenHeight = visualBounds.getHeight();

        // TODO: Get the locale from cached User Preferences instead.
        // final Locale locale = Locale.getDefault( Category.DISPLAY );
        final Locale locale = Locale.getDefault();

        // Due to problems with newer file systems (Windows Vista and beyond,
        // 64-bit Mac OS X), it is safer to default to the user default home
        // directory reported by the system vs. the top-level system file view
        // (which may throw exceptions at startup and therefore prevents the
        // application from launching). Anyway, the latter is Swing-based.
        File userDefaultDirectory = null;
        try {
            final File userDirectory = FileUtils.getUserDirectory();
            if ( Files.isDirectory( userDirectory.toPath() ) ) {
                userDefaultDirectory = userDirectory;
            }
        }
        catch ( final Exception e ) {
            e.printStackTrace();
        }

        // Make a Client Properties singleton for hosting global data.
        final ClientProperties clientProperties = new ClientProperties( osNameVerbose,
                                                                        screenWidth,
                                                                        screenHeight,
                                                                        locale,
                                                                        userDefaultDirectory );

        return clientProperties;
    }

    /**
     * Returns a Resource Bundle using lookup criteria and a security manager.
     * In most cases, this bundle has already been created by the JVM (usually
     * at startup during class loading), but it can be an expensive call so is
     * best queried once and cached within the application lifecycle.
     *
     * @param clientProperties
     *            The {@link ClientProperties} that include the User Locale
     * @param bundleName
     *            The Bundle Name that distinguishes this Resource Bundle from
     *            others in the system (generally this is a Class Name)
     * @param ignoreUserLocale
     *            {@code true} if the User Locale should be ignored in favor of
     *            using the default Locale of US-English
     * @return A {@link ResourceBundle} returned by the JVM's Security Manager
     */
    @SuppressWarnings("nls")
    public static ResourceBundle getResourceBundle( final ClientProperties clientProperties,
                                                    final String bundleName,
                                                    final boolean ignoreUserLocale ) {
        final Locale locale = ignoreUserLocale
            ? Locale.forLanguageTag( "en-US" )
            : clientProperties.locale;
        return ResourceBundle.getBundle( bundleName, locale );
    }

}
