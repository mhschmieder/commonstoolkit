/*
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
 * IoToolkit Library. If not, see <https://opensource.org/licenses/MIT>.
 *
 * Project: https://github.com/mhschmieder/iotoolkit
 */
package com.mhschmieder.iotoolkit.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.mhschmieder.iotoolkit.net.AuthorizationServerResponse;
import com.mhschmieder.iotoolkit.net.SessionContext;
import com.mhschmieder.iotoolkit.security.LoginCredentials;

import javafx.concurrent.Service;
import javafx.scene.control.Dialog;
import javafx.util.Pair;

public final class AuthorizationRequestService extends Service< AuthorizationServerResponse > {

    /** Cache the Login Credentials to use for authorizing the prediction. */
    protected LoginCredentials                 _loginCredentials;

    /** Reference to a Login Dialog that instigates the authorization. */
    protected Dialog< Pair< String, String > > _loginDialog;

    /** Cache the Build ID passed in by the client application. */
    protected int                              _clientBuildId;

    /**
     * Cache the full Session Context (System Type, Locale, Client Type, etc.).
     */
    public SessionContext                      _sessionContext;

    public AuthorizationRequestService( final int clientBuildId,
                                        final SessionContext sessionContext ) {
        // Always call the superclass constructor first!
        super();

        _clientBuildId = clientBuildId;
        _sessionContext = sessionContext;

        _loginCredentials = new LoginCredentials();

        // Not all authorization requests are launched from Login Dialogs.
        _loginDialog = null;

        // Set the Service to use a Cached Thread Pool vs. the default daemon,
        // to protect against run-time cross-threading issues (especially in a
        // hybrid app), suspended threads, and for better performance.
        final ExecutorService executorService = Executors.newCachedThreadPool();
        setExecutor( executorService );
    }

    @Override
    protected AuthorizationRequestTask createTask() {
        // Create a new Authorization Request Task.
        final AuthorizationRequestTask authorizationrequestTask =
                                                                new AuthorizationRequestTask( _loginCredentials,
                                                                                              _clientBuildId,
                                                                                              _sessionContext );

        return authorizationrequestTask;
    }

    public Dialog< Pair< String, String > > getLoginDialog() {
        return _loginDialog;
    }

    public void requestUserAuthorization( final LoginCredentials loginCredentials ) {
        // Disregard empty, null, or invalid Login Credentials.
        if ( !loginCredentials.isValid() ) {
            return;
        }

        // Make sure the authorization parameter sources are up to date.
        setLoginCredentials( loginCredentials );

        // Restart the Service as this also cancels old tasks and then resets.
        restart();
    }

    public void setLoginCredentials( final LoginCredentials loginCredentials ) {
        _loginCredentials = loginCredentials;
    }

    public void setLoginDialog( final Dialog< Pair< String, String > > loginDialog ) {
        _loginDialog = loginDialog;
    }

}
