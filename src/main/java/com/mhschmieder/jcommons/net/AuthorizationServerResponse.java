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
package com.mhschmieder.jcommons.net;

/**
 * Wrapper for everything related to a server response for user authorization.
 * <p>
 * Ofttimes the authorization is on a different server from the database or a
 * computation engine that processes requests and computes data to serve back,
 * so this class provides several variables that detail authorization status.
 */
public final class AuthorizationServerResponse extends HttpServletResponse {

    /** Simple flag for whether user is authorized on server or not. */
    private boolean _authorizedOnServer;

    /** Detailed description of user authorization status. */
    private String  _authorizationMessage;

    /** Long date format of user account expiration (ISO date format). */
    private long    _expirationDate;

    public AuthorizationServerResponse( final String serverStatusMessage,
                                        final String servletErrorMessage,
                                        final boolean authorizedOnServer,
                                        final String authorizationMessage,
                                        final long expirationDate,
                                        final String httpResponse,
                                        final int httpResponseCode ) {
        // Always call the superclass constructor first!
        super( serverStatusMessage,
               servletErrorMessage,
               httpResponse,
               httpResponseCode );

        _authorizedOnServer = authorizedOnServer;
        _authorizationMessage = authorizationMessage;
        _expirationDate = expirationDate;
    }

    public boolean isAuthorizedOnServer() {
        return _authorizedOnServer;
    }

    public void setAuthorizedOnServer( final boolean authorizedOnServer ) {
        _authorizedOnServer = authorizedOnServer;
    }

    public String getAuthorizationMessage() {
        return _authorizationMessage;
    }

    public void setAuthorizationMessage( final String authorizationMessage ) {
        _authorizationMessage = authorizationMessage;
    }

    public long getExpirationDate() {
        return _expirationDate;
    }

    public void setExpirationDate( final long expirationDate ) {
        _expirationDate = expirationDate;
    }
}
