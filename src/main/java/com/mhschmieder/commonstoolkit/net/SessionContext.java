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
package com.mhschmieder.commonstoolkit.net;

import java.io.File;
import java.net.URL;
import java.util.Locale;

import com.mhschmieder.commonstoolkit.util.SystemType;

public class SessionContext {

    // Cache the verbose OS Name compounded from system toolkit queries.
    public String     osNameVerbose;

    // Cache the System Type to special-case for macOS, Linux, etc.
    public SystemType systemType;

    // Cache the screen size, for Full Screen Mode and user statistics.
    // :NOTE: We use simple types, to avoid Graphics API dependencies.
    public double     screenWidth;
    public double     screenHeight;

    // Cache the locale, so it can be easily queried and/or changed.
    public Locale     locale;

    // Cache the user default directory as it is expensive to query.
    public File       userHomeDirectory;

    // Cache the local host (client) name.
    public String     localHostName;

    // Cache the web host (server) name.
    public String     webHostName;

    // Cache the URL for local host to web host HTTP requests/responses.
    public URL        urlHttpServlet;

    @SuppressWarnings("nls")
    public SessionContext( final String osNameVerbose,
                           final double screenWidth,
                           final double screenHeight,
                           final Locale locale,
                           final File userHomeDirectory,
                           final String localHostName,
                           final String webHostName,
                           final int webServletPort,
                           final String webServletName ) {
        this.osNameVerbose = osNameVerbose;
        systemType = SystemType.valueFromOsName( osNameVerbose );
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.locale = locale;
        this.userHomeDirectory = userHomeDirectory;
        this.localHostName = localHostName;
        this.webHostName = webHostName;
        this.localHostName = localHostName;

        // Compile the URL for local host to web host HTTP requests/responses.
        // NOTE: We make this here instead of in the invoker, and don't cache
        // the servlet port or servlet name, because our needs may change and
        // this is more flexible than doing it outside this constructor and
        // then throwing away the uncached parameters.
        urlHttpServlet = NetworkUtilities
                .getRelativeURL( "http", webHostName, webServletPort, webServletName );
    }

}
