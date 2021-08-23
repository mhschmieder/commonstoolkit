/**
 * MIT License
 *
 * Copyright (c) 2020 Mark Schmieder
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
 * This file is part of the IoToolkit Library
 *
 * You should have received a copy of the MIT License along with the
 * GraphicsToolkit Library. If not, see <https://opensource.org/licenses/MIT>.
 *
 * Project: https://github.com/mhschmieder/iotoolkit
 */
package com.mhschmieder.iotoolkit.io;

import com.mhschmieder.iotoolkit.branding.ProductBranding;
import com.mhschmieder.iotoolkit.net.SessionContext;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.time.ZonedDateTime;
import java.util.Formatter;
import java.util.IllegalFormatException;
import java.util.Map;
import java.util.Map.Entry;

public final class LogUtilities {

    /**
     * The default constructor is disabled, as this is a static utilities class.
     */
    private LogUtilities() {}

    /**
     * Print critical info about the client, the user and their computer.
     *
     * @param sessionContext
     *            The @SessionContext for system and user-specific settings
     * @param productBranding
     *            The @ProductBranding terms approved by Marketing
     * @param cssStylesheet
     *            The name of the CSS Stylesheet used as the top-level default
     */
    public static void generateSessionLogHeader( final SessionContext sessionContext,
                                                 final ProductBranding productBranding,
                                                 final String cssStylesheet ) {
        final StringBuilder sessionLogHeader = new StringBuilder();

        final ZonedDateTime dateTime = ZonedDateTime.now();
        final String timeStamp = dateTime.toString();

        sessionLogHeader.append( productBranding.productVersion);
        sessionLogHeader.append( " - Session Log - " ); //$NON-NLS-1$
        sessionLogHeader.append( timeStamp );
        sessionLogHeader.append( System.lineSeparator() );
        sessionLogHeader.append( System.lineSeparator() );

        try ( final Formatter fmt = new Formatter() ) {
            final String userName = System.getProperty( "user.name" ); //$NON-NLS-1$

            sessionLogHeader.append( "User Name: " ); //$NON-NLS-1$
            sessionLogHeader.append( userName );
            sessionLogHeader.append( System.lineSeparator() );
            sessionLogHeader.append( "User Home Directory: " ); //$NON-NLS-1$
            sessionLogHeader.append( sessionContext.userHomeDirectory );
            sessionLogHeader.append( System.lineSeparator() );

            sessionLogHeader.append( "Operating System: " ); //$NON-NLS-1$
            sessionLogHeader.append( sessionContext.osNameVerbose );

            sessionLogHeader.append( System.lineSeparator() );
            final Runtime rt = Runtime.getRuntime();
            final long free = rt.freeMemory();
            final long total = rt.totalMemory();
            final long used = total - free;
            final long max = rt.maxMemory();
            final double memoryFactor = 1024d * 1024d * 1024d;
            fmt.format( "Memory Free: %.3f Gb", //$NON-NLS-1$
                    free / memoryFactor );
            fmt.format( System.lineSeparator() );
            fmt.format( "Memory Total: %.3f Gb", //$NON-NLS-1$
                    total / memoryFactor );
            fmt.format( System.lineSeparator() );
            fmt.format( "Memory Used: %.3f Gb", //$NON-NLS-1$
                    used / memoryFactor );
            fmt.format( System.lineSeparator() );
            fmt.format( "Memory Max: %.3f Gb", //$NON-NLS-1$
                    max / memoryFactor );
            sessionLogHeader.append( fmt );

            final RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
            final Map< String, String > propList = bean.getSystemProperties();
            for ( final Entry< String, String > entry : propList.entrySet() ) {
                switch ( entry.getKey() ) {
                    case "gopherProxySet": //$NON-NLS-1$
                    case "http.nonProxyHosts": //$NON-NLS-1$
                    case "ftp.nonProxyHosts": //$NON-NLS-1$
                    case "socksNonProxyHosts": //$NON-NLS-1$
                    case "user.language": //$NON-NLS-1$
                        sessionLogHeader.append( System.lineSeparator() );
                        sessionLogHeader.append( entry );
                        break;
                    default:
                        break;
                }
            }
            sessionLogHeader.append( System.lineSeparator() );
            sessionLogHeader.append( "Java Uptime: " ); //$NON-NLS-1$
            sessionLogHeader.append( bean.getUptime() );

            final String javaVersion = System.getProperty( "java.version" ); //$NON-NLS-1$
            final String javaFxVersion = System.getProperty( "javafx.version" ); //$NON-NLS-1$

            sessionLogHeader.append( System.lineSeparator() );
            sessionLogHeader.append( "Java Version: " ); //$NON-NLS-1$
            sessionLogHeader.append( javaVersion );
            sessionLogHeader.append( System.lineSeparator() );
            sessionLogHeader.append( "JavaFX Version: " ); //$NON-NLS-1$
            sessionLogHeader.append( javaFxVersion );
            sessionLogHeader.append( System.lineSeparator() );
        }
        catch ( final SecurityException | IllegalFormatException e ) {
            e.printStackTrace();
        }

        // Log the user agent CSS stylesheet used by the main application.
        sessionLogHeader.append( "JavaFX CSS Stylesheet: " ); //$NON-NLS-1$
        sessionLogHeader.append( cssStylesheet );
        sessionLogHeader.append( System.lineSeparator() );

        System.out.println( sessionLogHeader.toString() );
    }

    /**
     * Function for redirecting logging away from stdout and stderr to a file.
     *
     * @param sessionLogFileName
     *            Full path name of the session log file.
     */
    public static void redirectLogging( final String sessionLogFileName ) {
        // Chain a BufferedReader to an InputStreamReader to a FileInputStream,
        // for better performance.
        //
        // :NOTE: Using the Logger API causes deadlock on second use, so is
        // commented out until we adopt a full Logging Framework. For now, we
        // simply redirect to a file in the user's default temporary directory.
        // Also, we can't use try-with-resources as that causes the stream to
        // close right away vs. staying available for logging.
        // final LogOutputStream sessionLogOutputStream = new LogOutputStream();
        try {
            final FileOutputStream sessionLogOutputStream =
                    new FileOutputStream( sessionLogFileName );
            @SuppressWarnings("resource") final PrintStream stdout =
                    new PrintStream( sessionLogOutputStream,
                            true );
            System.setOut( stdout );
            System.setErr( stdout );
        }
        catch ( final NullPointerException | IOException e ) {
            e.printStackTrace();
        }
    }

}
