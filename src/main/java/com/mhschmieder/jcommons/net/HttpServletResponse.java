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
 * Base class for all response categories for Servlet based HTTP calls.
 */
public class HttpServletResponse {

    /** Server Status Message determined by a number of factors. */
    protected String  _serverStatusMessage;

    /** Servlet Error Message conditionally returned via HTTP Header. */
    protected String  _servletErrorMessage;

    /** Combined HTTP Response Code and Response Message (formatted). */
    protected String  _httpResponse;

    /** Forwarding of HTTP Response Code for further post-processing. */
    protected int     _httpResponseCode;

    public HttpServletResponse( final String serverStatusMessage,
                                final String servletErrorMessage,
                                final String httpResponse,
                                final int httpResponseCode ) {
        _serverStatusMessage = serverStatusMessage;
        _servletErrorMessage = servletErrorMessage;
        _httpResponse = httpResponse;
        _httpResponseCode = httpResponseCode;
    }

    public String getServerStatusMessage() {
        return _serverStatusMessage;
    }

    public void setServerStatusMessage( final String serverStatusMessage ) {
        _serverStatusMessage = serverStatusMessage;
    }

    public String getServletErrorMessage() {
        return _servletErrorMessage;
    }

    public void setServletErrorMessage( final String servletErrorMessage ) {
        _servletErrorMessage = servletErrorMessage;
    }

    public String getHttpResponse() {
        return _httpResponse;
    }

    public void setHttpResponse( final String httpResponse ) {
        _httpResponse = httpResponse;
    }

    public int getHttpResponseCode() {
        return _httpResponseCode;
    }

    public void setHttpResponseCode( final int httpResponseCode ) {
        _httpResponseCode = httpResponseCode;
    }
}
