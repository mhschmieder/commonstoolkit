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
 * This file is part of the JCommons Library
 *
 * You should have received a copy of the MIT License along with the
 * JCommons Library. If not, see <https://opensource.org/licenses/MIT>.
 *
 * Project: https://github.com/mhschmieder/jcommons
 */
package com.mhschmieder.jcommons.util;

import java.io.File;
import java.util.Locale;

public class ClientProperties {

    // Cache the verbose OS Name compounded from system toolkit queries.
    public String     osNameVerbose;

    // Cache the System Type to special-case for macOS, Linux, etc.
    public SystemType systemType;

    // Cache the locale, so it can be easily queried and/or changed.
    public Locale     locale;

    // Cache the user default directory as it is expensive to query.
    public File       userHomeDirectory;

    public ClientProperties( final String pOsNameVerbose,
                             final Locale pLocale,
                             final File pUserHomeDirectory ) {
        osNameVerbose = pOsNameVerbose;
        systemType = SystemType.valueFromOsName( pOsNameVerbose );
        locale = pLocale;
        userHomeDirectory = pUserHomeDirectory;
    }
}
