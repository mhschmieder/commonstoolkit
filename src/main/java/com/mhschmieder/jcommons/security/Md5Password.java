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
 * This file is part of the JCommons Library
 *
 * You should have received a copy of the MIT License along with the
 * JCommons Library. If not, see <https://opensource.org/licenses/MIT>.
 *
 * Project: https://github.com/mhschmieder/jcommons
 */
package com.mhschmieder.jcommons.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This simple class uses the MD5 hash algorithm (RSA developed = very secure)
 * to develop a simple username / password protection scheme (see
 * http://www.ietf.org/rfc/rfc1321.txt for more info).
 * <p>
 * The goal is to take a username and use MD5 to create a unique four to six
 * digit integer password, which can then be compared against an input string.
 *
 * Please note that the passwords are case sensitive.
 */
public final class Md5Password {

    @SuppressWarnings("nls")
    public static int makePassword( final String username ) {
        byte[] buf = new byte[ username.length() ];
        try {
            buf = username.getBytes( "UTF-16" );
        }
        catch ( final UnsupportedEncodingException uee ) {
            System.out.println( uee );
        }

        MessageDigest algorithm = null;
        try {
            algorithm = MessageDigest.getInstance( "MD5" );
        }
        catch ( final NoSuchAlgorithmException nsae ) {
            System.out.println( nsae );
        }

        if ( algorithm == null ) {
            return 0;
        }

        algorithm.update( buf );
        final byte[] digest = algorithm.digest();

        final int testint1 = digest[ 0 ];
        final int testint2 = digest[ 1 ];
        final int newMd5Password = ( testint1 * testint1 ) + ( testint2 * testint2 );

        return newMd5Password;
    }

    // Declare MD5 password as an integer.
    private int md5Password = 0;

    public Md5Password( final String username ) {
        md5Password = Md5Password.makePassword( username );
    }

    public int getPassword() {
        return md5Password;
    }

    public boolean validatePassword( final String password ) {
        // Avoid throwing unnecessary exceptions by not attempting to use empty
        // strings.
        if ( ( password == null ) || ( password.length() <= 0 ) ) {
            return false;
        }

        // Convert the password string to an integer, then compare it to the
        // pre-computed integer output of the MD5 algorithm.
        int userPassword;
        try {
            userPassword = Integer.parseInt( password );
        }
        catch ( final NumberFormatException nfe ) {
            System.out.println( nfe );
            return false;
        }

        return userPassword == md5Password;
    }

}
