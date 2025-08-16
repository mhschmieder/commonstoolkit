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
package com.mhschmieder.commonstoolkit.util;

import java.util.Locale;

public enum SystemType {
    WINDOWS, 
    MACOS, 
    LINUX, 
    UNIX, 
    SOLARIS, 
    OTHER;

    /** Cache the detected System Type to limit property queries. */
    @SuppressWarnings("nls") public static final SystemType DETECTED_SYSTEM_TYPE        =
                                                                                 valueFromOsName( System
                                                                                         .getProperty( "os.name" ) );

    /** Cache the simplified version of the detected OS Name. */
    public static final String                              DETECTED_OS_NAME_SIMPLIFIED =
                                                                                        getSimplifiedOsName( DETECTED_SYSTEM_TYPE );
    
    public static SystemType defaultValue() {
        return DETECTED_SYSTEM_TYPE;
    }

    // NOTE: This method must be able to parse verbose OS Names.
    public static SystemType valueFromOsName( final String osName ) {
        // NOTE: Starting with Sierra, Apple changed their naming scheme.
        // NOTE: There are other OS names referenced by a similar Apache Commons
        // example, but it is unlikely we will encounter them.
        // TODO: Borrow code and ideas from Oracle's Ensemble sample app via the
        // PlatformFeatures.java module, to cover iOS, Android, etc.?
        final String osNameAdjusted = osName.toLowerCase( Locale.ENGLISH );
        if ( osNameAdjusted.contains( "win" ) ) {
            return WINDOWS;
        }

        if ( osNameAdjusted.contains( "os x" ) || osNameAdjusted.contains( "macos" ) ) {
            return MACOS;
        }

        if ( osNameAdjusted.contains( "linux" ) ) {
            return LINUX;
        }

        if ( osNameAdjusted.contains( "unix" ) ) {
            return UNIX;
        }

        if ( osNameAdjusted.contains( "solaris" ) ) {
            return SOLARIS;
        }

        return OTHER;
    }

    public static String getSimplifiedOsName( final SystemType systemType ) {
        String simplifiedOsName;
        switch ( systemType ) {
        case WINDOWS:
            simplifiedOsName = "windows";
            break;
        case MACOS:
            simplifiedOsName = "macos";
            break;
        case LINUX:
            simplifiedOsName = "linux";
            break;
        case UNIX:
            simplifiedOsName = "unix";
            break;
        case SOLARIS:
            simplifiedOsName = "solaris";
            break;
        case OTHER:
            simplifiedOsName = "other";
            break;
        default:
            simplifiedOsName = "unknown";
            break;
        }

        return simplifiedOsName;
    }

    public String getSimplifiedOsName() {
        return getSimplifiedOsName( this );
    }
}
