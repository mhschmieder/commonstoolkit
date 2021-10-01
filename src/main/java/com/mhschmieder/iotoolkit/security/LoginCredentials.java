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

import javafx.util.Pair;

/**
 * TODO: Remove the JavaFX dependency and use a different Pair implementation?
 */
public class LoginCredentials {

    // Declare default constants, where appropriate, for all fields.
    @SuppressWarnings("nls") public static final String USER_NAME_DEFAULT = "";
    @SuppressWarnings("nls") public static final String PASSWORD_DEFAULT  = "";

    private Pair< String, String >                      login;

    // Default Constructor; sets all instance variables to default values.
    public LoginCredentials() {
        this( USER_NAME_DEFAULT, PASSWORD_DEFAULT );
    }

    // Copy Constructor; offered in place of clone() to guarantee that the
    // source object is never modified by the new target object created here.
    public LoginCredentials( final LoginCredentials loginCredentials ) {
        this( loginCredentials.getLogin() );
    }

    // Fully Qualified Constructor; when initialization values are consolidated.
    public LoginCredentials( final Pair< String, String > pLogin ) {
        setLogin( pLogin );
    }

    // Atomic Constructor; when initialization values are gathered piecemeal.
    public LoginCredentials( final String userName, final String password ) {
        this( new Pair<>( userName, password ) );
    }

    // :NOTE: Cloning is disabled as it is dangerous; use the copy constructor
    // instead.
    @Override
    protected final Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public final Pair< String, String > getLogin() {
        return login;
    }

    public final String getPassword() {
        return login.getValue();
    }

    public final String getUserName() {
        return login.getKey();
    }

    public final boolean isValid() {
        return ( ( getUserName().trim().length() > 0 ) && ( getPassword().trim().length() > 0 ) );
    }

    public void reset() {
        setLogin( LoginCredentials.USER_NAME_DEFAULT, LoginCredentials.PASSWORD_DEFAULT );
    }

    public final void setLogin( final LoginCredentials loginCredentials ) {
        setLogin( loginCredentials.getLogin() );
    }

    public final void setLogin( final Pair< String, String > pLogin ) {
        login = pLogin;
    }

    public final void setLogin( final String userName, final String password ) {
        final Pair< String, String > newLogin = new Pair<>( userName, password );
        setLogin( newLogin );
    }

}
