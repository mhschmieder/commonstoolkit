/**
 * MIT License
 *
 * Copyright (c) 2020, 2022 Mark Schmieder
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

public final class PredictionServerResponse {

    // Server Status Message determined by a number of factors.
    private String _serverStatusMessage;

    // Servlet Error Message conditionally returned via HTTP Header.
    private String _servletErrorMessage;

    // Specific Unauthorized User Message inclusive of Expiration Date.
    private String _unauthorizedUserMessage;

    // Combined HTTP Response Code and Response Message (formatted).
    private String _httpResponse;

    // Forwarding of HTTP Response Code for further post-processing.
    private int    _httpResponseCode;

    // Server Response Data Stream, returned when relevant to context.
    private byte[] _serverResponseData;

    public PredictionServerResponse( final String serverStatusMessage,
                                     final String servletErrorMessage,
                                     final String unauthorizedUserMessage,
                                     final String httpResponse,
                                     final int httpResponseCode,
                                     final byte[] serverResponseData ) {
        // Always call the superclass constructor first!
        super();

        _serverStatusMessage = serverStatusMessage;
        _servletErrorMessage = servletErrorMessage;
        _unauthorizedUserMessage = unauthorizedUserMessage;
        _httpResponse = httpResponse;
        _httpResponseCode = httpResponseCode;
        _serverResponseData = serverResponseData;
    }

    public String getHttpResponse() {
        return _httpResponse;
    }

    public int getHttpResponseCode() {
        return _httpResponseCode;
    }

    public byte[] getServerResponseData() {
        return _serverResponseData;
    }

    public String getServerStatusMessage() {
        return _serverStatusMessage;
    }

    public String getServletErrorMessage() {
        return _servletErrorMessage;
    }

    public String getUnauthorizedUserMessage() {
        return _unauthorizedUserMessage;
    }

    public void setHttpResponse( final String httpResponse ) {
        _httpResponse = httpResponse;
    }

    public void setHttpResponseCode( final int httpResponseCode ) {
        _httpResponseCode = httpResponseCode;
    }

    public void setServerResponseData( final byte[] serverResponseData ) {
        _serverResponseData = serverResponseData;
    }

    public void setServerStatusMessage( final String serverStatusMessage ) {
        _serverStatusMessage = serverStatusMessage;
    }

    public void setServletErrorMessage( final String servletErrorMessage ) {
        _servletErrorMessage = servletErrorMessage;
    }

    public void setUnauthorizedUserMessage( final String unauthorizedUserMessage ) {
        _unauthorizedUserMessage = unauthorizedUserMessage;
    }

}
