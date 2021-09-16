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
 * This file is part of the IoToolkit Library
 *
 * You should have received a copy of the MIT License along with the
 * GraphicsToolkit Library. If not, see <https://opensource.org/licenses/MIT>.
 *
 * Project: https://github.com/mhschmieder/iotoolkit
 */
package com.mhschmieder.iotoolkit.security;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.mhschmieder.iotoolkit.net.AuthorizationServerResponse;

import javafx.util.Pair;

public final class PredictionLoginCredentials extends LoginCredentials {

    // By default, the user is not authorized on the server.
    public static final boolean AUTHORIZED_ON_SERVER_DEFAULT = false;

    // The default expiration date is the beginning of computer time (1970).
    public static final long    EXPIRATION_DATE_DEFAULT      = 0L;

    // Declare a flag for whether the user is authorized on this client/server
    // combination or not.
    private boolean             _authorizedOnServer;

    // Cache the user's expiration date for their license, in milliseconds.
    private long                _expirationDateEpochMs;

    // Default Constructor; sets all instance variables to default values.
    public PredictionLoginCredentials() {
        this( USER_NAME_DEFAULT, PASSWORD_DEFAULT );
    }

    // Partial Constructor; used when the base values are consolidated.
    public PredictionLoginCredentials( final Pair< String, String > login ) {
        this( login, AUTHORIZED_ON_SERVER_DEFAULT, EXPIRATION_DATE_DEFAULT );
    }

    // Fully Qualified Constructor; when initialization values are consolidated.
    public PredictionLoginCredentials( final Pair< String, String > login,
                                       final boolean authorizedOnServer,
                                       final long expirationDate ) {
        setLoginCredentials( login, authorizedOnServer, expirationDate );
    }

    // Copy Constructor; offered in place of clone() to guarantee that the
    // source object is never modified by the new target object created here.
    public PredictionLoginCredentials( final PredictionLoginCredentials loginCredentials ) {
        this( loginCredentials.getLogin(),
              loginCredentials.isAuthorizedOnServer(),
              loginCredentials.getExpirationDate() );
    }

    // Partial Constructor; used when the base values are gathered piecemeal.
    public PredictionLoginCredentials( final String userName, final String password ) {
        this( new Pair<>( userName, password ) );
    }

    // Atomic Constructor; when initialization values are gathered piecemeal.
    public PredictionLoginCredentials( final String userName,
                                       final String password,
                                       final boolean authorizedOnServer,
                                       final long expirationDate ) {
        this( new Pair<>( userName, password ), authorizedOnServer, expirationDate );
    }

    public long getExpirationDate() {
        return _expirationDateEpochMs;
    }

    public boolean isAuthorizedOnServer() {
        return _authorizedOnServer;
    }

    // :TODO: Write a separate method to determine when near expiry.
    public boolean isExpired() {
        // Get the current date/time and compare for expiry.
        // :NOTE: The client receives a long integer form of the expiry
        // representing "ms" since 1 JAN 1970 GMT (in server's locale).
        final LocalDateTime dateTime = LocalDateTime.now();
        final long epochSecond = Math.round( 0.001 * _expirationDateEpochMs );
        final LocalDateTime expirationDate = LocalDateTime
                .ofEpochSecond( epochSecond, 0, ZoneOffset.UTC );
        return dateTime.compareTo( expirationDate ) > 0;
    }

    @Override
    public void reset() {
        // Reset the new User Name and Password to default values, so that the
        // user is not authorized (due to non-empty saved preferences) the next
        // time they start the application, and thereby has the opportunity to
        // try again later.
        super.reset();

        // Don't forget to clear the authorized flag as well!
        // :NOTE: Do not reset the expiration date, as that should still be
        // queryable as it is often the cause of the user not being authorized
        // by the server!
        setAuthorizedOnServer( AUTHORIZED_ON_SERVER_DEFAULT );
    }

    public void setAuthorizedOnServer( final boolean authorizedOnServer ) {
        _authorizedOnServer = authorizedOnServer;
    }

    public void setExpirationDate( final long expirationDate ) {
        _expirationDateEpochMs = expirationDate;
    }

    public void setLoginCredentials( final Pair< String, String > login,
                                     final boolean authorizedOnServer,
                                     final long expirationDate ) {
        setLogin( login );
        setAuthorizedOnServer( authorizedOnServer );
        setExpirationDate( expirationDate );
    }

    public void setLoginCredentials( final PredictionLoginCredentials loginCredentials ) {
        setLoginCredentials( loginCredentials.getLogin(),
                             loginCredentials.isAuthorizedOnServer(),
                             loginCredentials.getExpirationDate() );
    }

    public void setLoginCredentials( final String userName,
                                     final String password,
                                     final boolean authorizedOnServer,
                                     final long expirationDate ) {
        final Pair< String, String > login = new Pair<>( userName, password );
        setLoginCredentials( login, authorizedOnServer, expirationDate );
    }

    public void updateUserAuthorizationStatus( final AuthorizationServerResponse authorizationServerResponse ) {
        // Cache the new "authorized on server" status.
        final boolean authorizedOnServer = authorizationServerResponse.isAuthorizedOnServer();
        setAuthorizedOnServer( authorizedOnServer );

        if ( authorizedOnServer ) {
            // Overload the cached user license expiration date if a valid one
            // was returned; otherwise do not disturb the current cached value,
            // as this could lead to annoying multiple login dialogs.
            final long expirationDate = authorizationServerResponse.getExpirationDate();
            if ( expirationDate > EXPIRATION_DATE_DEFAULT ) {
                // Cache the new expiration date, in milliseconds since 1970.
                setExpirationDate( expirationDate );
            }
        }
    }

}
