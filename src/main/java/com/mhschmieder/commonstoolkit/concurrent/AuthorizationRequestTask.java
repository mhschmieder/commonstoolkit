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
package com.mhschmieder.commonstoolkit.concurrent;

import java.net.HttpURLConnection;

import com.mhschmieder.commonstoolkit.net.AuthorizationServerResponse;
import com.mhschmieder.commonstoolkit.net.ClientProperties;
import com.mhschmieder.commonstoolkit.net.NetworkUtilities;
import com.mhschmieder.commonstoolkit.net.ServerRequestProperties;
import com.mhschmieder.commonstoolkit.security.LoginCredentials;
import com.mhschmieder.commonstoolkit.security.PredictionLoginCredentials;

import javafx.concurrent.Task;

public final class AuthorizationRequestTask extends Task< AuthorizationServerResponse > {

    /** The Request Type name that this task will pass to the server. */
    @SuppressWarnings("nls") public static String AUTHORIZATION_REQUEST_TYPE = "AuthorizeUser";

    /** Cache the Login Credentials to use for authorizing the request. */
    protected LoginCredentials                    loginCredentials;

    /**
     * Cache the Server Request Properties (Build ID, Client Type, etc.).
     */
    public ServerRequestProperties                serverRequestProperties;

    /**
     * Cache the Client Properties (System Type, Locale, etc.).
     */
    public ClientProperties                       clientProperties;

    public AuthorizationRequestTask( final LoginCredentials pLoginCredentials,
                                     final ServerRequestProperties pServerRequestProperties,
                                     final ClientProperties pClientProperties ) {
        // Always call the super-constructor first!
        super();

        // TODO: Wrap the Login Credentials in an AuthorizationRequestContext
        // class, for future-proof API expansion later on?
        loginCredentials = pLoginCredentials;
        serverRequestProperties = pServerRequestProperties;
        clientProperties = pClientProperties;
    }

    @Override
    protected AuthorizationServerResponse call() throws InterruptedException {
        // Open a connection to the Authorization Servlet.
        final HttpURLConnection httpURLConnection = NetworkUtilities
                .getHttpURLConnection( serverRequestProperties.urlHttpServlet );
        if ( httpURLConnection == null ) {
            final String urlConnectionStatus =
                                             "Server Connection Error: Authorization Service Not Found"; //$NON-NLS-1$
            final AuthorizationServerResponse authorizationServerResponse =
                                                                          new AuthorizationServerResponse( urlConnectionStatus,
                                                                                                           null,
                                                                                                           true,
                                                                                                           null,
                                                                                                           PredictionLoginCredentials.EXPIRATION_DATE_DEFAULT,
                                                                                                           null,
                                                                                                           HttpURLConnection.HTTP_UNAVAILABLE );
            return authorizationServerResponse;
        }

        // Add the HTTP request properties for the Authorization Servlet.
        NetworkUtilities.addServerRequestProperties( httpURLConnection,
                                                     AUTHORIZATION_REQUEST_TYPE,
                                                     loginCredentials,
                                                     serverRequestProperties,
                                                     clientProperties );

        // Request a user authorization based on Login Credentials.
        final String servletErrorMessage = NetworkUtilities.connectToServlet( httpURLConnection,
                                                                              "authorization" ); //$NON-NLS-1$
        if ( servletErrorMessage != null ) {
            final AuthorizationServerResponse authorizationServerResponse =
                                                                          new AuthorizationServerResponse( null,
                                                                                                           servletErrorMessage,
                                                                                                           true,
                                                                                                           null,
                                                                                                           PredictionLoginCredentials.EXPIRATION_DATE_DEFAULT,
                                                                                                           null,
                                                                                                           HttpURLConnection.HTTP_UNAVAILABLE );
            return authorizationServerResponse;
        }

        // Handle the authorization servlet's HTTP status, and echo the
        // formatted error response to the user if an HTTP error code is
        // detected and/or the authorization failed.
        final AuthorizationServerResponse authorizationServerResponse = NetworkUtilities
                .getAuthorizationServerResponse( httpURLConnection );
        final String serverStatusMessage = authorizationServerResponse.getServerStatusMessage();
        if ( serverStatusMessage != null ) {
            authorizationServerResponse.setServerStatusMessage( serverStatusMessage );
            authorizationServerResponse
                    .setHttpResponseCode( authorizationServerResponse.getHttpResponseCode() );
        }

        return authorizationServerResponse;
    }

}
