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

import com.mhschmieder.commonstoolkit.text.StringUtilities;

public enum AngleUnit {
    DEGREES, RADIANS;

    public static AngleUnit abbreviatedValueOf( final String abbreviatedAngleUnit ) {
        return ( StringUtilities.DEGREES_SYMBOL.equalsIgnoreCase( abbreviatedAngleUnit ) )
            ? DEGREES
            : ( " rad".equalsIgnoreCase( abbreviatedAngleUnit ) ) //$NON-NLS-1$
                ? RADIANS
                : defaultValue();
    }

    public static AngleUnit canonicalValueOf( final String canonicalAngleUnit ) {
        return ( canonicalAngleUnit != null )
            ? valueOf( canonicalAngleUnit.toUpperCase( Locale.ENGLISH ) )
            : defaultValue();
    }

    public static AngleUnit defaultValue() {
        return DEGREES;
    }

    public final String toAbbreviatedString() {
        switch ( this ) {
        case DEGREES:
            return StringUtilities.DEGREES_SYMBOL;
        case RADIANS:
            return " rad"; //$NON-NLS-1$
        default:
            final String errMessage = "Unexpected AngleUnit " + this; //$NON-NLS-1$
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
