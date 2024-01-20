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
package com.mhschmieder.commonstoolkit.security;

public enum ClassificationLevel {
    UNCLASSIFIED, CONFIDENTIAL, SECRET, TOP_SECRET;

    public static ClassificationLevel defaultValue() {
        return UNCLASSIFIED;
    }

    @SuppressWarnings("nls")
    public static ClassificationLevel fromAbbreviatedString( final String clientTypeAbbreviatedString ) {
        if ( "unclassified".equalsIgnoreCase( clientTypeAbbreviatedString ) ) {
            return UNCLASSIFIED;
        }

        if ( "confidential".equalsIgnoreCase( clientTypeAbbreviatedString ) ) {
            return CONFIDENTIAL;
        }

        if ( "secret".equalsIgnoreCase( clientTypeAbbreviatedString ) ) {
            return SECRET;
        }

        if ( ( "top secret".equalsIgnoreCase( clientTypeAbbreviatedString )
                || "top_secret".equalsIgnoreCase( clientTypeAbbreviatedString ) ) ) {
            return TOP_SECRET;
        }

        return defaultValue();
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

    public final String toAbbreviatedString() {
        String abbreviatedString = null;
        
        switch ( this ) {
        case UNCLASSIFIED:
            abbreviatedString = "unclassified"; //$NON-NLS-1$
            break;
        case CONFIDENTIAL:
            abbreviatedString = "confidential"; //$NON-NLS-1$
            break;
        case SECRET:
            abbreviatedString = "secret"; //$NON-NLS-1$
            break;
        case TOP_SECRET:
            abbreviatedString = "top secret"; //$NON-NLS-1$
            break;
        default:
            final String errMessage = "Unexpected " //$NON-NLS-1$
                    + this.getClass().getSimpleName() + " " + this; //$NON-NLS-1$
            throw new IllegalArgumentException( errMessage );
        }
        
        return abbreviatedString;
    }

    public final String toPresentationString() {
        String presentationString = null;
        
        switch ( this ) {
        case UNCLASSIFIED:
            presentationString = "Unclassified"; //$NON-NLS-1$
            break;
        case CONFIDENTIAL:
            presentationString = "Confidential"; //$NON-NLS-1$
            break;
        case SECRET:
            presentationString = "Secret"; //$NON-NLS-1$
            break;
        case TOP_SECRET:
            presentationString = "Top Secret"; //$NON-NLS-1$
            break;
        default:
            final String errMessage = "Unexpected " //$NON-NLS-1$
                    + this.getClass().getSimpleName() + " " + this; //$NON-NLS-1$
            throw new IllegalArgumentException( errMessage );
        }
        
        return presentationString;
    }

}
