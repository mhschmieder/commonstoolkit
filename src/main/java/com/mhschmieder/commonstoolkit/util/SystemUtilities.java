/**
 * MIT License
 *
 * Copyright (c) 2020, 2024 Mark Schmieder
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

/**
 * Utilities for dealing with system-level stuff such as System Properties.
 */
public final class SystemUtilities {

    /**
     * The default constructor is disabled, as this is a static utilities class.
     */
    public SystemUtilities() {}

    /**
     * Sets system-specific properties, where relevant. This method should not
     * be called until the {@link SystemType} is known and set.
     * <p>
     * NOTE: It is still unclear whether the legacy Apple Menu Bar properties
     *  should be set anymore, as they likely are just for Swing, probably are
     *  superseded after macOS Java went from Apple to Oracle with Java 7, and
     *  as it seems that using the installer to take care of the menu bar and
     *  application name is sufficient and superior for JavaFX and as of Java 8.
     *  
     * @param systemType The OS type, at the coarsest level of granularity
     * @param applicationName The display name of your application, for About
     * @param setLegacyAppleMenuProperties {@code true} if the legacy macOS menu
     *                                     bar properties should be set
     * @param useGoogleSignin {@code true} if the system property should be set
     *                        that allows application access to Google Sign-in                                    
     */
    public static void setSystemProperties( final SystemType systemType,
                                            final String applicationName,
                                            final boolean setLegacyAppleMenuProperties,
                                            final boolean useGoogleSignin) {
        if ( setLegacyAppleMenuProperties && SystemType.MACOS.equals( systemType ) ) {
            // NOTE: We now programmatically set the Apple Menu Bar via system
            //  properties, as doing this via JVM arguments doesn't seem to work, 
            // starting with Oracle's Java 7 going forward.
            System.setProperty( "apple.laf.useScreenMenuBar", "true" );

            // NOTE: Unfortunately, we must also set the name of the About Menu
            //  Item on the Mac explicitly or else it defaults to the name of the 
            //  Application Menu, which in turn defaults to the name of the main 
            //  class declared as the application entry point.
            // TODO: Switch to the Product Branding string instead?
            System.setProperty( "com.apple.mrj.application.apple.menu.about.name", 
                                applicationName );
        }
        
        // Google Sign-in has become popular as a general sign-in technique. As
        // competing sign-ins aren't as popular, are more volatile, less documented
        // and much harder (such as Apple Sign-in), we only support Google for now.
        if ( useGoogleSignin ) {
            // This looks weird, but is proven to be required in order to get access
            // control permissions for loading the Google Sign-in webpage in WebView.
            System.setProperty( "sun.net.http.allowRestrictedHeaders", "true" );
        }
    }
}
