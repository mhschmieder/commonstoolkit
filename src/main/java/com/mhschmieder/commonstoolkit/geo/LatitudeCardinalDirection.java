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
package com.mhschmieder.commonstoolkit.geo;

import java.util.Locale;

/**
 * {@code LatitudeCardinalDirection} collects the cardinal directions used for
 * latitude. Keeping these separate from longitude helps avoid mismatches.
 */
public enum LatitudeCardinalDirection {
    NORTH, SOUTH;

    public static LatitudeCardinalDirection defaultValue() {
        return NORTH;
    }

    public static LatitudeCardinalDirection canonicalValueOf( final String canonicalLatitudeCardinalDirection ) {
        return ( canonicalLatitudeCardinalDirection != null )
            ? valueOf( canonicalLatitudeCardinalDirection.toUpperCase( Locale.ENGLISH ) )
            : defaultValue();
    }

    public static LatitudeCardinalDirection presentationValueOf( final String presentationLatitudeCardinalDirection ) {
        return canonicalValueOf( presentationLatitudeCardinalDirection );
    }

    @SuppressWarnings("nls")
    public static LatitudeCardinalDirection abbreviatedValueOf( final String abbreviatedLatitudeCardinalDirection ) {
        if ( "N".equalsIgnoreCase( abbreviatedLatitudeCardinalDirection ) ) {
            return NORTH;
        }

        if ( "S".equalsIgnoreCase( abbreviatedLatitudeCardinalDirection ) ) {
            return SOUTH;
        }

        return defaultValue();
    }

    public final String toCanonicalString() {
        return toString().toLowerCase( Locale.ENGLISH );
    }

    public final String toPresentationString() {
        switch ( this ) {
        case NORTH:
            return "North"; //$NON-NLS-1$
        case SOUTH:
            return "South"; //$NON-NLS-1$
        default:
            final String errorMessage = "Unexpected LatitudeCardinalDirection " + this; //$NON-NLS-1$
            throw new IllegalArgumentException( errorMessage );
        }
    }

    public final String toAbbreviatedString() {
        switch ( this ) {
        case NORTH:
            return "N"; //$NON-NLS-1$
        case SOUTH:
            return "S"; //$NON-NLS-1$
        default:
            final String errorMessage = "Unexpected LatitudeCardinalDirection " + this; //$NON-NLS-1$
            throw new IllegalArgumentException( errorMessage );
        }
    }

}
