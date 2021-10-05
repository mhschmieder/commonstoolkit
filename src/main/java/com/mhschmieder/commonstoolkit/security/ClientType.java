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
 * This file is part of the CommonsToolkit Library
 *
 * You should have received a copy of the MIT License along with the
 * CommonsToolkit Library. If not, see <https://opensource.org/licenses/MIT>.
 *
 * Project: https://github.com/mhschmieder/commonstoolkit
 */
package com.mhschmieder.commonstoolkit.security;

public enum ClientType {
    PUBLIC, CLEARED;

    @SuppressWarnings("nls")
    public static ClientType abbreviatedValueOf( final String clientType ) {
        return "public".equalsIgnoreCase( clientType )
            ? PUBLIC
            : "cleared".equalsIgnoreCase( clientType ) ? CLEARED : defaultValue();
    }

    public static ClientType defaultValue() {
        return PUBLIC;
    }

    public final String toAbbreviatedString() {
        switch ( this ) {
        case PUBLIC:
            return "public"; //$NON-NLS-1$
        case CLEARED:
            return "cleared"; //$NON-NLS-1$
        default:
            final String errMessage = "Unexpected " //$NON-NLS-1$
                    + this.getClass().getSimpleName() + " " + this; //$NON-NLS-1$
            throw new IllegalArgumentException( errMessage );
        }
    }

    public final String toBrandingString() {
        switch ( this ) {
        case PUBLIC:
            return "Public"; //$NON-NLS-1$
        case CLEARED:
            return "Cleared"; //$NON-NLS-1$
        default:
            final String errMessage = "Unexpected " //$NON-NLS-1$
                    + this.getClass().getSimpleName() + " " + this; //$NON-NLS-1$
            throw new IllegalArgumentException( errMessage );
        }
    }

    public final String toPresentationString() {
        switch ( this ) {
        case PUBLIC:
            return "PUBLIC"; //$NON-NLS-1$
        case CLEARED:
            return "CLEARED"; //$NON-NLS-1$
        default:
            final String errMessage = "Unexpected " //$NON-NLS-1$
                    + this.getClass().getSimpleName() + " " + this; //$NON-NLS-1$
            throw new IllegalArgumentException( errMessage );
        }
    }

    @Override
    public final String toString() {
        // NOTE: As of Java 6, enums include the underscore in their string
        // representation, which is a problem for backward-compatibility with
        // XML parsers. Thus, we need to strip the underscores and replace them
        // with spaces, to behave like Java 5.
        final String value = super.toString();
        return value.replaceAll( "_", " " ); //$NON-NLS-1$ //$NON-NLS-2$
    }

}
