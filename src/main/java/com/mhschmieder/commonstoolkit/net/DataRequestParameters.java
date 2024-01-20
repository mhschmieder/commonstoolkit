/**
 * MIT License
 *
 * Copyright (c) 2024 Mark Schmieder
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

import java.net.HttpURLConnection;

import com.mhschmieder.commonstoolkit.security.LoginCredentials;
import com.mhschmieder.commonstoolkit.util.DataUpdateType;

/**
 * Base class for parameters common to all data update requests to data servers.
 */
public class DataRequestParameters {

    /** The Data Request Type to cue the servlet which data engine to query. */
    protected String dataRequestType;
    
    /** The Data Update Type to switch the icon, title, etc. */
    protected DataUpdateType dataUpdateType;

    /** Cache the Login Credentials to use for authorizing the data request. */
    protected LoginCredentials loginCredentials;

    public DataRequestParameters( final String pDataRequestType,
                                  final DataUpdateType pDataUpdateType,
                                  final LoginCredentials pLoginCredentials ) {
        dataRequestType = pDataRequestType;
        dataUpdateType = pDataUpdateType;
        loginCredentials = pLoginCredentials;
    }
    
    public String getDataRequestType() {
        return dataRequestType;
    }

    public DataUpdateType getDataUpdateType() {
        return dataUpdateType;
    }

    public LoginCredentials getLoginCredentials() {
        return loginCredentials;
    }
    
    /**
     * Adds data request properties to the HTTP Request.
     * <p>
     * NOTE: The base class implementation is blank, as most data requests
     *  will use the file-based approach, but some requests are trivial
     *  enough to instead tag a few custom HTTP parameters to the URL.
     * 
     * @param httpURLConnection The HTTP URL Connection for the Request
     */
    public void addDataRequestProperties( final HttpURLConnection httpURLConnection ) {
    }
    
    /**
     * Returns a server status message related to the sending of data request
     * input parameters to the HTTP Request, or null if no message received,
     * such as when this method is not needed due to no input parameters.
     * <p>
     * NOTE: The base class implementation returns null, as not all data 
     *  requests will send additional input parameters; some are simple enough
     *  to instead tag a few custom HTTP parameters to the URL.
     * 
     * @param httpURLConnection The HTTP URL Connection for the Request
     * 
     * @return The server status message, or null if no input parameters sent
     */
    public String sendDataRequestInputParameters( final HttpURLConnection httpURLConnection ) {
        return null;
    }
}
