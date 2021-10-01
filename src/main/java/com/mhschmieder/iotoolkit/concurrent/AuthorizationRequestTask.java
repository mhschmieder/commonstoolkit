/*
 * MIT License
 * Copyright (c) 2020, 2021 Mark Schmieder
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * This file is part of the IoToolkit Library
 * You should have received a copy of the MIT License along with the
 * IoToolkit Library. If not, see <https://opensource.org/licenses/MIT>.
 * Project: https://github.com/mhschmieder/iotoolkit
 */
package com.mhschmieder.iotoolkit.concurrent;

import java.net.HttpURLConnection;

import com.mhschmieder.iotoolkit.net.AuthorizationServerResponse;
import com.mhschmieder.iotoolkit.net.NetworkUtilities;
import com.mhschmieder.iotoolkit.net.SessionContext;
import com.mhschmieder.iotoolkit.security.LoginCredentials;
import com.mhschmieder.iotoolkit.security.PredictionLoginCredentials;

import javafx.concurrent.Task;

public final class AuthorizationRequestTask extends Task< AuthorizationServerResponse > {

    /** Cache the Login Credentials to use for authorizing the prediction. */
    protected LoginCredentials _loginCredentials;

    /** Cache the Build ID passed in by the client application. */
    protected int              _clientBuildId;

    /**
     * Cache the full Session Context (System Type, Locale, Client Type, etc.).
     */
    public SessionContext      _sessionContext;

    public AuthorizationRequestTask( final LoginCredentials loginCredentials,
                                     final int clientBuildId,
                                     final SessionContext sessionContext ) {
        // Always call the super-constructor first!
        super();

        _loginCredentials = loginCredentials;
        _clientBuildId = clientBuildId;
        _sessionContext = sessionContext;
    }

    @Override
    protected AuthorizationServerResponse call() throws InterruptedException {
        // Open a connection to the Authorization Servlet.
        final HttpURLConnection httpURLConnection = NetworkUtilities
                .getHttpURLConnection( _sessionContext.urlHttpServlet );
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
                                                     "AuthorizeUser", //$NON-NLS-1$
                                                     _loginCredentials,
                                                     _clientBuildId,
                                                     _sessionContext );

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

        // Handle the MAPP authorization servlet's HTTP status, and echo
        // the formatted error response to the user if an HTTP error
        // code is detected and/or the authorization failed.
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
