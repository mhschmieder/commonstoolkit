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

import java.util.Locale;

public enum SystemType {
    WINDOWS, MACOS, LINUX, UNIX, SOLARIS, OTHER;

    public static SystemType defaultValue() {
        return WINDOWS;
    }

    // :NOTE: This method must be able to parse verbose OS Names.
    @SuppressWarnings("nls")
    public static SystemType valueFromOsName( final String osName ) {
        // :NOTE: Starting with Sierra, Apple changed their naming scheme.
        // :NOTE: There are other OS names referenced by a similar Apache
        // Commons example, but it is unlikely we will encounter them.
        // :TODO: Borrow code and ideas from Oracle's Ensemble sample app via
        // the PlatformFeatures.java module, to cover iOS, Android, etc.?
        final String osNameAdjusted = osName.toLowerCase( Locale.ENGLISH );
        return osNameAdjusted.contains( "win" )
            ? WINDOWS
            : osNameAdjusted.contains( "os x" ) || osNameAdjusted.contains( "macos" )
                ? MACOS
                : osNameAdjusted.contains( "linux" )
                    ? LINUX
                    : osNameAdjusted.contains( "unix" )
                        ? UNIX
                        : osNameAdjusted.contains( "solaris" ) ? SOLARIS : OTHER;
    }

    @SuppressWarnings("nls")
    @Override
    public final String toString() {
        // :NOTE: As of Java 6, enums include the underscore in their string
        // representation, which is a problem for backward-compatibility with
        // XML parsers. Thus, we need to strip the underscores and replace them
        // with spaces, to behave like Java 5.
        // :TODO: Review whether this is could mess up any database interaction
        // or tracking, as we disabled this code in most or all other enums.
        final String value = super.toString();
        return value.replaceAll( "_", " " );
    }

}
