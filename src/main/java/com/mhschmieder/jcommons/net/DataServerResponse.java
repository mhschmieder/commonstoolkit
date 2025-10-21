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

import java.net.HttpURLConnection;

/**
 * Wrapper for everything related to a server response for a data request.
 * <p>
 * Ofttimes the authorization is on a different server from the database or a
 * computation engine that processes requests and computes data to serve back,
 * so this class only contains a single message to represent authorized status.
 */
public final class DataServerResponse extends HttpServletResponse {

    // Specific Unauthorized User Message inclusive of Expiration Date.
    private String _unauthorizedUserMessage;

    // Server Response Data Stream, returned when relevant to context.
    private byte[] _serverResponseData;

    // No-args constructor to call for an initially empty server response.
    public DataServerResponse() {
        this( null,
              null,
              null,
              null,
              HttpURLConnection.HTTP_UNAVAILABLE,
              null );
    }
    
    // Fully classified constructor for a data server response.
    public DataServerResponse( final String serverStatusMessage,
                               final String servletErrorMessage,
                               final String unauthorizedUserMessage,
                               final String httpResponse,
                               final int httpResponseCode,
                               final byte[] serverResponseData ) {
        // Always call the superclass constructor first!
        super( serverStatusMessage,
               servletErrorMessage,
               httpResponse,
               httpResponseCode );

        _unauthorizedUserMessage = unauthorizedUserMessage;
        _serverResponseData = serverResponseData;
    }

    public String getUnauthorizedUserMessage() {
        return _unauthorizedUserMessage;
    }

    public void setUnauthorizedUserMessage( final String unauthorizedUserMessage ) {
        _unauthorizedUserMessage = unauthorizedUserMessage;
    }

    public byte[] getServerResponseData() {
        return _serverResponseData;
    }

    public void setServerResponseData( final byte[] serverResponseData ) {
        _serverResponseData = serverResponseData;
    }
}
