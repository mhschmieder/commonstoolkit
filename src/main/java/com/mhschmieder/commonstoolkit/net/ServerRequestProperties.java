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
package com.mhschmieder.commonstoolkit.net;

import java.net.URL;
import java.util.Objects;

/**
 * {@code ServerRequestProperties} is a container for request properties
 * typically sent to a server using the HTTP protocol, whether for authorization
 * of login credentials, or some sort of data or client/server query.
 */
public class ServerRequestProperties {

    /** Cache the Client Build ID passed in by the client application. */
    public int    clientBuildId;

    /** Cache the Client Type passed in by the client application by name. */
    public String clientType;

    /** Cache the Local Host (i.e. Client) Name. */
    public String localHostName;

    /** Cache the Web Host (i.e. Server) Name. */
    public String webHostName;

    /** Cache the URL for Local Host to Web Host HTTP Requests/Responses. */
    public URL    urlHttpServlet;

    @SuppressWarnings("nls")
    public ServerRequestProperties( final int pClientBuildId,
                                    final String pClientType,
                                    final String pLocalHostName,
                                    final String pWebHostName,
                                    final int pWebServletPort,
                                    final String pWebServletName ) {
        clientBuildId = pClientBuildId;
        clientType = pClientType;
        localHostName = pLocalHostName;
        webHostName = pWebHostName;

        // Compile the URL for local host to web host HTTP requests/responses.
        // NOTE: We make this here instead of in the invoker, and don't cache
        // the servlet port or servlet name, because our needs may change and
        // this is more flexible than doing it outside this constructor and
        // then throwing away the uncached parameters.
        urlHttpServlet = NetworkUtilities
                .getRelativeURL( "http", pWebHostName, pWebServletPort, pWebServletName );
    }

    @Override
    public boolean equals( final Object other ) {
        if ( this == other ) {
            return true;
        }
        if ( ( other == null ) || ( getClass() != other.getClass() ) ) {
            return false;
        }
        final ServerRequestProperties otherServerRequestProperties =
                                                                   ( ServerRequestProperties ) other;
        return ( clientBuildId == otherServerRequestProperties.clientBuildId )
                && Objects.equals( clientType, otherServerRequestProperties.clientType )
                && Objects.equals( localHostName, otherServerRequestProperties.localHostName )
                && Objects.equals( webHostName, otherServerRequestProperties.webHostName )
                && Objects.equals( urlHttpServlet, otherServerRequestProperties.urlHttpServlet );
    }

    @Override
    public int hashCode() {
        return Objects.hash( Integer.valueOf( clientBuildId ),
                             clientType,
                             localHostName,
                             webHostName,
                             urlHttpServlet );
    }

}
