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
package com.mhschmieder.commonstoolkit.physics;

import java.util.Locale;

public enum PressureUnit {
    KILOPASCALS, PASCALS, MILLIBARS, ATMOSPHERES;

    public static PressureUnit abbreviatedValueOf( final String abbreviatedPressureUnit ) {
        return ( " kPa".equalsIgnoreCase( abbreviatedPressureUnit ) ) //$NON-NLS-1$
            ? KILOPASCALS
            : ( " Pa".equalsIgnoreCase( abbreviatedPressureUnit ) ) //$NON-NLS-1$
                ? PASCALS
                : ( " mb".equalsIgnoreCase( abbreviatedPressureUnit ) ) //$NON-NLS-1$
                    ? MILLIBARS
                    : ( " atm".equalsIgnoreCase( abbreviatedPressureUnit ) ) //$NON-NLS-1$
                        ? ATMOSPHERES
                        : defaultValue();
    }

    public static PressureUnit canonicalValueOf( final String canonicalPressureUnit ) {
        return ( canonicalPressureUnit != null )
            ? valueOf( canonicalPressureUnit.toUpperCase( Locale.ENGLISH ) )
            : defaultValue();
    }

    public static PressureUnit defaultValue() {
        return PASCALS;
    }

    public static PressureUnit defaultValueForAir() {
        return KILOPASCALS;
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

    public final String toCanonicalString() {
        return toString().toLowerCase( Locale.ENGLISH );
    }

    public final String toPresentationString() {
        return toAbbreviatedString();
    }

}
