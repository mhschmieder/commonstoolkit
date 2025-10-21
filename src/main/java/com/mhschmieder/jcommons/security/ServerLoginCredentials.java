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
package com.mhschmieder.jcommons.security;

import com.mhschmieder.jcommons.net.AuthorizationServerResponse;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class ServerLoginCredentials extends LoginCredentials {

    // By default, the user is not authorized on the server.
    public static final boolean AUTHORIZED_ON_SERVER_DEFAULT = false;

    // The default expiration date is the beginning of computer time (1970).
    public static final long    EXPIRATION_DATE_DEFAULT      = 0L;

    // Declare a flag for whether the user is authorized on this client/server
    // combination or not.
    private boolean             authorizedOnServer;

    // Cache the user's expiration date for their license, in milliseconds.
    private long                expirationDateEpochMs;

    // Fully Qualified Constructor.
    public ServerLoginCredentials( final String pUserName,
                                   final String pPassword,
                                   final boolean pAuthorizedOnServer,
                                   final long pExpirationDate ) {
        setLoginCredentials( pUserName, pPassword, pAuthorizedOnServer, pExpirationDate );
    }

    // Copy Constructor; offered in place of clone() to guarantee that the
    // source object is never modified by the new target object created here.
    public ServerLoginCredentials( final ServerLoginCredentials loginCredentials ) {
        this( loginCredentials.getUserName(),
              loginCredentials.getPassword(),
              loginCredentials.isAuthorizedOnServer(),
              loginCredentials.getExpirationDate() );
    }

    public boolean isAuthorizedOnServer() {
        return authorizedOnServer;
    }

    public void setAuthorizedOnServer( final boolean pAuthorizedOnServer ) {
        authorizedOnServer = pAuthorizedOnServer;
    }

    public long getExpirationDate() {
        return expirationDateEpochMs;
    }

    public void setExpirationDate( final long pExpirationDate ) {
        expirationDateEpochMs = pExpirationDate;
    }

    // TODO: Write a separate method to determine when we are near expiry.
    public boolean isExpired() {
        // Get the current date/time and compare for expiry.
        // NOTE: The client receives a long integer form of the expiry
        // representing "ms" since 1 JAN 1970 GMT (in server's locale).
        final LocalDateTime dateTime = LocalDateTime.now();
        final long epochSecond = Math.round( 0.001 * expirationDateEpochMs );
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
        // NOTE: Do not reset the expiration date, as that should still be
        // queried later; it is often the cause of a user not being authorized
        // by the server!
        setAuthorizedOnServer( AUTHORIZED_ON_SERVER_DEFAULT );
    }

    public void setLoginCredentials( final String pUserName,
                                     final String pPassword,
                                     final boolean pAuthorizedOnServer,
                                     final long pExpirationDate ) {
        setLogin( pUserName, pPassword );
        setAuthorizedOnServer( pAuthorizedOnServer );
        setExpirationDate( pExpirationDate );
    }

    public void setLoginCredentials( final ServerLoginCredentials loginCredentials ) {
        setLoginCredentials( loginCredentials.getUserName(),
                             loginCredentials.getPassword(),
                             loginCredentials.isAuthorizedOnServer(),
                             loginCredentials.getExpirationDate() );
    }

    public void updateUserAuthorizationStatus( final AuthorizationServerResponse authorizationServerResponse ) {
        // Cache the new "authorized on server" status.
        final boolean isAuthorizedOnServer = authorizationServerResponse.isAuthorizedOnServer();
        setAuthorizedOnServer( isAuthorizedOnServer );

        if ( isAuthorizedOnServer ) {
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
