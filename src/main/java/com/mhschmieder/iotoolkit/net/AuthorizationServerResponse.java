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
package com.mhschmieder.iotoolkit.net;

public final class AuthorizationServerResponse {

    /** Server Status Message determined by a number of factors. */
    private String  _serverStatusMessage;

    /** Servlet Error Message conditionally returned via HTTP Header. */
    private String  _servletErrorMessage;

    /** Simple flag for whether user is authorized on server or not. */
    private boolean _authorizedOnServer;

    /** Detailed description of user authorization status. */
    private String  _authorizationMessage;

    /** Long date format of user account expiration (ISO date format). */
    private long    _expirationDate;

    /** Combined HTTP Response Code and Response Message (formatted). */
    private String  _httpResponse;

    /** Forwarding of HTTP Response Code for further post-processing. */
    private int     _httpResponseCode;

    public AuthorizationServerResponse( final String serverStatusMessage,
                                        final String servletErrorMessage,
                                        final boolean authorizedOnServer,
                                        final String authorizationMessage,
                                        final long expirationDate,
                                        final String httpResponse,
                                        final int httpResponseCode ) {
        // Always call the superclass constructor first!
        super();

        _serverStatusMessage = serverStatusMessage;
        _servletErrorMessage = servletErrorMessage;
        _authorizedOnServer = authorizedOnServer;
        _authorizationMessage = authorizationMessage;
        _expirationDate = expirationDate;
        _httpResponse = httpResponse;
        _httpResponseCode = httpResponseCode;
    }

    public String getAuthorizationMessage() {
        return _authorizationMessage;
    }

    public long getExpirationDate() {
        return _expirationDate;
    }

    public String getHttpResponse() {
        return _httpResponse;
    }

    public int getHttpResponseCode() {
        return _httpResponseCode;
    }

    public String getServerStatusMessage() {
        return _serverStatusMessage;
    }

    public String getServletErrorMessage() {
        return _servletErrorMessage;
    }

    public boolean isAuthorizedOnServer() {
        return _authorizedOnServer;
    }

    public void setAuthorizationMessage( final String authorizationMessage ) {
        _authorizationMessage = authorizationMessage;
    }

    public void setAuthorizedOnServer( final boolean authorizedOnServer ) {
        _authorizedOnServer = authorizedOnServer;
    }

    public void setExpirationDate( final long expirationDate ) {
        _expirationDate = expirationDate;
    }

    public void setHttpResponse( final String httpResponse ) {
        _httpResponse = httpResponse;
    }

    public void setHttpResponseCode( final int httpResponseCode ) {
        _httpResponseCode = httpResponseCode;
    }

    public void setServerStatusMessage( final String serverStatusMessage ) {
        _serverStatusMessage = serverStatusMessage;
    }

    public void setServletErrorMessage( final String servletErrorMessage ) {
        _servletErrorMessage = servletErrorMessage;
    }

}
