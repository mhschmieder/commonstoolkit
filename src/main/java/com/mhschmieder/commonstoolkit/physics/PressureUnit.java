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
package com.mhschmieder.commonstoolkit.physics;

import java.util.Locale;

public enum PressureUnit {
    KILOPASCALS, PASCALS, MILLIBARS, ATMOSPHERES;

    public static PressureUnit defaultValue() {
        return PASCALS;
    }

    public static PressureUnit defaultValueForAir() {
        return KILOPASCALS;
    }

    public static PressureUnit fromCanonicalString( final String pressureUnitCanonicalString ) {
        return ( pressureUnitCanonicalString != null )
            ? valueOf( pressureUnitCanonicalString.toUpperCase( Locale.ENGLISH ) )
            : defaultValue();
    }

    @SuppressWarnings("nls")
    public static PressureUnit fromAbbreviatedString( final String pressureUnitAbbreviatedString ) {
        // NOTE: These abbreviated values account for the standard of leaving a
        // space between the numeric value and its associated unit.
        if ( " kPa".equalsIgnoreCase( pressureUnitAbbreviatedString ) ) {
            return KILOPASCALS;
        }

        if ( " Pa".equalsIgnoreCase( pressureUnitAbbreviatedString ) ) {
            return PASCALS;
        }

        if ( " mb".equalsIgnoreCase( pressureUnitAbbreviatedString ) ) {
            return MILLIBARS;
        }

        if ( " atm".equalsIgnoreCase( pressureUnitAbbreviatedString ) ) {
            return ATMOSPHERES;
        }

        return defaultValue();
    }

    public final String toCanonicalString() {
        return toString().toLowerCase( Locale.ENGLISH );
    }

    public final String toPresentationString() {
        return toAbbreviatedString();
    }

    public final String toAbbreviatedString() {
        switch ( this ) {
        case KILOPASCALS:
            return " kPa"; //$NON-NLS-1$
        case PASCALS:
            return " Pa"; //$NON-NLS-1$
        case MILLIBARS:
            return " mb"; //$NON-NLS-1$
        case ATMOSPHERES:
            return " atm"; //$NON-NLS-1$
        default:
            final String errMessage = "Unexpected PressureUnit " + this; //$NON-NLS-1$
            throw new IllegalArgumentException( errMessage );
        }
    }

}
