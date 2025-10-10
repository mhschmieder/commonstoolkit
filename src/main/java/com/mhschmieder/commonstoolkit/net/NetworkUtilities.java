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

import com.mhschmieder.commonstoolkit.io.IoUtilities;
import com.mhschmieder.commonstoolkit.security.LoginCredentials;
import com.mhschmieder.commonstoolkit.security.ServerLoginCredentials;
import com.mhschmieder.commonstoolkit.util.ClientProperties;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.NoRouteToHostException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;

public final class NetworkUtilities {

    // Define a generalized server connection error for the status bar, etc.
    public static final String SERVER_CONNECTION_ERROR_MESSAGE = "Error Connecting to Server."; //$NON-NLS-1$
    public static final String AUTHORIZATION_ERROR_MESSAGE     = "Server Authorization Error."; //$NON-NLS-1$

    // Declare the property value for UTF-8 encoded text content.
    public static final String UTF8_ENCODED_TEXT_CONTENT       = "text/plain; charset=utf-8";   //$NON-NLS-1$

    @SuppressWarnings("nls")
    public static void addServerRequestProperties( final HttpURLConnection httpURLConnection,
                                                   final String requestType,
                                                   final LoginCredentials loginCredentials,
                                                   final HttpServletRequestProperties httpServletRequestProperties,
                                                   final ClientProperties clientProperties,
                                                   final double screenWidth,
                                                   final double screenHeight ) {
        try {
            // Encoding format
            final String enc = "UTF-8";

            // Set the request type for the prediction request.
            httpURLConnection.setRequestProperty( "requestType", requestType );

            // Encode the username
            final String username = loginCredentials.getUserName();
            final String usernameEncoded = URLEncoder.encode( username, enc );
            httpURLConnection.setRequestProperty( "username", usernameEncoded );

            // Encode the password
            final String password = loginCredentials.getPassword();
            final String passwordEncoded = URLEncoder.encode( password, enc );
            httpURLConnection.setRequestProperty( "password", passwordEncoded );

            httpURLConnection
                    .setRequestProperty( "buildID",
                                         Integer.toString( httpServletRequestProperties.clientBuildId ) );
            httpURLConnection.setRequestProperty( "clientType",
                                                  httpServletRequestProperties.clientType );
            httpURLConnection.setRequestProperty( "client", httpServletRequestProperties.localHostName );
            httpURLConnection.setRequestProperty( "server", httpServletRequestProperties.webHostName );

            httpURLConnection.setRequestProperty( "clientOS", clientProperties.osNameVerbose );
            httpURLConnection.setRequestProperty( "screenSizeX", Double.toString( screenWidth ) );
            httpURLConnection.setRequestProperty( "screenSizeY", Double.toString( screenHeight ) );
        }
        catch ( final Exception e ) {
            e.printStackTrace();
        }
    }

    public static String connectToServlet( final HttpURLConnection httpURLConnection,
                                           final String serviceType ) {
        String statusMessage = null;

        try {
            // Open a communications link to the prediction servlet.
            httpURLConnection.connect();
        }
        catch ( final UnknownHostException uhe ) {
            uhe.printStackTrace();
            statusMessage = "Server Connection Error: Network Connection and/or Server Unavailable." //$NON-NLS-1$
                    + " Unable to connect to " + serviceType //$NON-NLS-1$
                    + " service."; //$NON-NLS-1$
            return statusMessage;
        }
        catch ( final NoRouteToHostException nrthe ) {
            nrthe.printStackTrace();
            statusMessage = "Server Routing Error: Server Routing Unavailable or Incorrect." //$NON-NLS-1$
                    + " Unable to connect to " + serviceType //$NON-NLS-1$
                    + " service."; //$NON-NLS-1$
            return statusMessage;
        }
        catch ( final SocketTimeoutException ste ) {
            ste.printStackTrace();
            statusMessage =
                          "Socket Timeout Error: Timeout Expired Before Server Connection Established." //$NON-NLS-1$
                                  + " Unable to connect to " + serviceType //$NON-NLS-1$
                                  + " service."; //$NON-NLS-1$
            return statusMessage;
        }
        catch ( final IOException ioe ) {
            ioe.printStackTrace();
            statusMessage =
                          "Server Connection Error: I/O Error Occurred While Opening Server Connection." //$NON-NLS-1$
                                  + "Unable to connect to " + serviceType //$NON-NLS-1$
                                  + " service."; //$NON-NLS-1$
            return statusMessage;
        }

        return statusMessage;
    }

    // Utility method to get an Authorization Server Response after handling
    // its Response Code, Response Message and Header Fields.
    @SuppressWarnings("nls")
    public static AuthorizationServerResponse getAuthorizationServerResponse( final HttpURLConnection httpURLConnection ) {
        // Pre-load a generalized server connection error, separate from
        // detailed error and exception logging.
        String serverStatusMessage = AUTHORIZATION_ERROR_MESSAGE;

        // Pre-load a null combined HTTP Response in case of exceptions.
        String httpResponse = null;

        // Pre-load an uncategorized HTTP Response Code in case of exceptions.
        int httpResponseCode = HttpURLConnection.HTTP_NOT_FOUND;

        // If there were any servlet errors, forward for post-processing.
        final String servletErrorMessage = httpURLConnection.getHeaderField( "errorMessage" );

        // Find out if the user is authorized, and if not, erase their login
        // information to make it harder for them to hack into the system.
        // NOTE: We give the client the benefit of the doubt if we were unable
        // to return a valid user authorization status, as this indicates some
        // sort of system-level failure or broken network connection (in which
        // case, no harm done if we try to load the prediction response).
        // NOTE: The "unauthorized" message shouldn't be used unless we also
        // attach a special HTTP Authorization header field, so we are
        // disobeying the HTTP spec (we're supposed to provide password hints).
        final boolean authorizedOnServer =
                                         httpURLConnection.getHeaderFieldInt( "validUser", 1 ) != 0;

        // Get the detailed user authorization message.
        final String authorizationMessage = httpURLConnection
                .getHeaderField( "authorizationMessage" );

        // Overload the cached user license expiration date if a valid one was
        // returned; otherwise do not disturb the current cached value, as this
        // could lead to annoying multiple login dialogs.
        final long expirationDate = httpURLConnection
                .getHeaderFieldDate( "expirationDate",
                                     ServerLoginCredentials.EXPIRATION_DATE_DEFAULT );

        try {
            // Get the combined HTTP Response returned by the URL Connection.
            httpResponse = NetworkUtilities.getHttpResponse( httpURLConnection );

            // Switch on the HTTP Response Code for pre-processing.
            httpResponseCode = httpURLConnection.getResponseCode();
            switch ( httpResponseCode ) {
            case HttpURLConnection.HTTP_OK:
                // Nothing to do; don't return a message as that means an
                // error was seen on the server.
                serverStatusMessage = null;
                break;
            case HttpURLConnection.HTTP_INTERNAL_ERROR:
                // Don't punish or confuse the user if there was an internal
                // server error, as we don't let those affect downstream
                // data. The error message was already dumped to the log.
                serverStatusMessage = null;
                break;
            case HttpURLConnection.HTTP_SEE_OTHER:
                // This HTTP code is for a server indirection, which we have
                // little if any control over and therefore should not punish
                // the user.
                serverStatusMessage = null;
                break;
            case HttpURLConnection.HTTP_PRECON_FAILED:
            case HttpURLConnection.HTTP_UNAUTHORIZED:
            case HttpURLConnection.HTTP_NO_CONTENT:
            case HttpURLConnection.HTTP_NOT_FOUND:
            default:
                // Propagate the HTTP error code to the caller to avoid
                // loading non-existent resources.
                break;
            }
        }
        catch ( final IOException ioe ) {
            // Forward HTTP Response extraction exceptions for post-processing.
            serverStatusMessage = ioe.toString();
        }

        // Construct an Authorization Server Response object for status/context.
        final AuthorizationServerResponse authorizationServerResponse =
                                                                      new AuthorizationServerResponse( serverStatusMessage,
                                                                                                       servletErrorMessage,
                                                                                                       authorizedOnServer,
                                                                                                       authorizationMessage,
                                                                                                       expirationDate,
                                                                                                       httpResponse,
                                                                                                       httpResponseCode );
        return authorizationServerResponse;
    }

    // Utility method to get a URL for a CGI script.
    // NOTE: This is hard-wired for now, but we should investigate whether
    // there are any available method calls that will return the CGI directory
    // of the website automatically.
    @SuppressWarnings("nls")
    public static URL getCgiFileURL( final String protocol,
                                     final String hostname,
                                     final int port,
                                     final String filename ) {
        return NetworkUtilities.getRelativeURL( protocol, hostname, port, "/cgi-bin/" + filename );
    }

    // Utility method to get a string representing an HTTP Response Code
    // combined with its Response Message description.
    public static String getHttpResponse( final HttpURLConnection httpURLConnection )
            throws IOException {
        final StringBuilder httpResponse = new StringBuilder();
        httpResponse.append( "HTTP Response: " ); //$NON-NLS-1$

        // Append the HTTP Response Code returned by the URL Connection.
        final int httpResponseCode = httpURLConnection.getResponseCode();
        httpResponse.append( httpResponseCode );

        // Append the HTTP Response Message returned by the URL Connection.
        String httpResponseMessage;
        httpResponseMessage = httpURLConnection.getResponseMessage();
        if ( httpResponseMessage != null ) {
            httpResponse.append( ' ' );
            httpResponse.append( httpResponseMessage );
        }

        return httpResponse.toString();
    }

    // Utility method to get an HTTP connection for a given URL, using the
    // default POST request method and UTF-8 encoded text content type.
    @SuppressWarnings("nls")
    public static HttpURLConnection getHttpURLConnection( final URL url ) {
        // NOTE: The "POST" request method is inferred by "setDoOutput" but
        // could also be "PUT" or "HEAD" in that context.
        return getHttpURLConnection( url, "POST" );
    }

    // Utility method to get an HTTP connection for a given URL and request
    // method, using the default UTF-8 encoded text content type.
    // TODO: Review the Content-Type to make sure it's appropriate for ZIP.
    public static HttpURLConnection getHttpURLConnection( final URL url,
                                                          final String requestMethod ) {
        return getHttpURLConnection( url, requestMethod, UTF8_ENCODED_TEXT_CONTENT );
    }

    // Utility method to get an HTTP connection for a given URL, request
    // method, and content type.
    @SuppressWarnings("nls")
    public static HttpURLConnection getHttpURLConnection( final URL url,
                                                          final String requestMethod,
                                                          final String contentType ) {
        try {
            final HttpURLConnection httpURLConnection = ( HttpURLConnection ) url.openConnection();

            // The request method is one of: DELETE, GET, HEAD, OPTIONS, POST,
            // PUT, TRACE.
            httpURLConnection.setRequestMethod( requestMethod );

            // This is the default; we set it explicitly for the server
            // response.
            httpURLConnection.setDoInput( true );

            // This is not the default; we set it for the client request.
            httpURLConnection.setDoOutput( true );

            // Avoid caching, as each server request is unique.
            httpURLConnection.setUseCaches( false );

            // The content type is for what is sent via a server stream.
            httpURLConnection.setRequestProperty( "Content-Type", contentType );

            return httpURLConnection;
        }
        catch ( final NullPointerException npe ) {
            npe.printStackTrace();
            return null;
        }
        catch ( final IllegalStateException ise ) {
            ise.printStackTrace();
            return null;
        }
        catch ( final SecurityException se ) {
            se.printStackTrace();
            return null;
        }
        catch ( final ProtocolException pe ) {
            pe.printStackTrace();
            return null;
        }
        catch ( final IOException ioe ) {
            ioe.printStackTrace();
            return null;
        }
    }

    public static URL getJarResourceAsUrl( final String jarRelativePackagePath,
                                           final String resourceNameUnqualified,
                                           final String fileExtension ) {
        final String jarResourceFilename = IoUtilities
                .getJarResourceFilename( jarRelativePackagePath,
                                         resourceNameUnqualified,
                                         fileExtension );

        // Get the URL associated with the JAR-loaded resource.
        // NOTE: This doesn't work well between projects and libraries, so
        //  should be replaced by local uses of getResource() vs. calls here.
        final URL jarResourceUrl = NetworkUtilities.class.getResource( jarResourceFilename );

        return jarResourceUrl;
    }

    // Utility method to get a Data Server Response after handling its
    // Response Code, Response Message and Header Fields.
    public static DataServerResponse getDataServerResponse( final HttpURLConnection httpURLConnection ) {
        // Pre-load a generalized server connection error, separate from
        // detailed error and exception logging.
        String serverStatusMessage = SERVER_CONNECTION_ERROR_MESSAGE;

        // Pre-load a null combined HTTP Response in case of exceptions.
        String httpResponse = null;

        // Pre-load an uncategorized HTTP Response Code in case of exceptions.
        int httpResponseCode = HttpURLConnection.HTTP_NOT_FOUND;

        // If there were any servlet errors, forward for post-processing.
        final String servletErrorMessage = httpURLConnection.getHeaderField( "errorMessage" ); //$NON-NLS-1$

        // In case it's needed, forward any Unauthorized User message.
        final String unauthorizedUserMessage = httpURLConnection
                .getHeaderField( "authorizationMessage" ); //$NON-NLS-1$

        try {
            // Get the combined HTTP Response returned by the URL Connection.
            httpResponse = NetworkUtilities.getHttpResponse( httpURLConnection );

            // Switch on the HTTP Response Code for pre-processing.
            httpResponseCode = httpURLConnection.getResponseCode();
            switch ( httpResponseCode ) {
            case HttpURLConnection.HTTP_OK:
                // Nothing to do; don't return a message as that means an
                // error was seen on the server.
                serverStatusMessage = null;
                break;
            case HttpURLConnection.HTTP_INTERNAL_ERROR:
                // Don't punish or confuse the user if there was an internal
                // server error, as we don't let those affect downstream
                // data. The error message was already dumped to the log.
                serverStatusMessage = null;
                break;
            case HttpURLConnection.HTTP_SEE_OTHER:
                // This HTTP code is for a server indirection, which we have
                // little if any control over and therefore should not punish
                // the user.
                serverStatusMessage = null;
                break;
            case HttpURLConnection.HTTP_PRECON_FAILED:
            case HttpURLConnection.HTTP_UNAUTHORIZED:
            case HttpURLConnection.HTTP_NO_CONTENT:
            case HttpURLConnection.HTTP_NOT_FOUND:
            default:
                // Propagate the HTTP error code to the caller to avoid
                // loading non-existent resources.
                break;
            }
        }
        catch ( final IOException ioe ) {
            ioe.printStackTrace();
            serverStatusMessage = "Server Communication Error: Response Incomplete or Not Received." //$NON-NLS-1$
                    + "\nUnable to extract data response from server."; //$NON-NLS-1$
        }

        // Construct a Data Server Response object for status/context.
        final DataServerResponse dataServerResponse = new DataServerResponse( serverStatusMessage,
                                                                              servletErrorMessage,
                                                                              unauthorizedUserMessage,
                                                                              httpResponse,
                                                                              httpResponseCode,
                                                                              null );
        return dataServerResponse;
    }

    // Utility method to get a relative URL for a server file.
    // NOTE: We specify the website directly rather than using getCodebase(),
    // as the latter gives us a location on the user's hard drive. Note also
    // that we don't specify "/html" for the main web page directory, as the
    // web server itself is a relative URL.
    @SuppressWarnings("nls")
    public static URL getRelativeURL( final String protocol,
                                      final String hostname,
                                      final int port,
                                      final String filename ) {
        try {
            final String filepath = "/" + filename;
            return new URL( protocol, hostname, port, filepath );
        }
        catch ( final MalformedURLException mue ) {
            mue.printStackTrace();
            return null;
        }
    }

    // Utility method to send a serialized object over an HTTP connection.
    public static void sendRequestObject( final HttpURLConnection httpURLConnection,
                                          final Object requestObject,
                                          final boolean closeStream ) {
        try {
            // Send the request query object to the server.
            final ObjectOutputStream oos = new ObjectOutputStream( httpURLConnection
                    .getOutputStream() );
            oos.writeObject( requestObject );
            oos.flush();
            if ( closeStream ) {
                oos.close();
            }
        }
        catch ( final IOException ioe ) {
            ioe.printStackTrace();
        }
    }
}
