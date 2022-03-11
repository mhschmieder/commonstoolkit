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
package com.mhschmieder.commonstoolkit.security;

public class LoginCredentials {

    // Declare default constants, where appropriate, for all fields.
    @SuppressWarnings("nls") public static final String USER_NAME_DEFAULT = "";
    @SuppressWarnings("nls") public static final String PASSWORD_DEFAULT  = "";

    private String                                      userName;
    private String                                      password;

    // Default Constructor; sets all instance variables to default values.
    public LoginCredentials() {
        this( USER_NAME_DEFAULT, PASSWORD_DEFAULT );
    }

    // Fully Qualified Constructor.
    public LoginCredentials( final String pUserName, final String pPassword ) {
        setLogin( pUserName, pPassword );
    }

    // Copy Constructor; offered in place of clone() to guarantee that the
    // source object is never modified by the new target object created here.
    public LoginCredentials( final LoginCredentials loginCredentials ) {
        this( loginCredentials.getUserName(), loginCredentials.getPassword() );
    }

    // NOTE: Cloning is disabled as it is dangerous; use the copy constructor
    // instead.
    @Override
    protected final Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public final String getUserName() {
        return userName;
    }

    public final String getPassword() {
        return password;
    }

    public final boolean isValid() {
        return ( ( getUserName().trim().length() > 0 ) && ( getPassword().trim().length() > 0 ) );
    }

    public void reset() {
        setLogin( LoginCredentials.USER_NAME_DEFAULT, LoginCredentials.PASSWORD_DEFAULT );
    }

    public final void setLogin( final String pUserName, final String pPassword ) {
        userName = pUserName;
        password = pPassword;
    }

    public final void setLogin( final LoginCredentials loginCredentials ) {
        setLogin( getUserName(), getPassword() );
    }

}
