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
 * {@code LongitudeCardinalDirection} collects the cardinal directions used for
 * longitude. Keeping these separate from latitude helps avoid mismatches.
 */
public enum LongitudeCardinalDirection {
    EAST, WEST;

    public static LongitudeCardinalDirection defaultValue() {
        return EAST;
    }

    public static LongitudeCardinalDirection canonicalValueOf( final String canonicalLongitudeCardinalDirection ) {
        return ( canonicalLongitudeCardinalDirection != null )
            ? valueOf( canonicalLongitudeCardinalDirection.toUpperCase( Locale.ENGLISH ) )
            : defaultValue();
    }

    public static LongitudeCardinalDirection presentationValueOf( final String presentationLongitudeCardinalDirection ) {
        return canonicalValueOf( presentationLongitudeCardinalDirection );
    }

    @SuppressWarnings("nls")
    public static LongitudeCardinalDirection abbreviatedValueOf( final String abbreviatedLongitudeCardinalDirection ) {
        if ( "E".equalsIgnoreCase( abbreviatedLongitudeCardinalDirection ) ) {
            return EAST;
        }

        if ( "W".equalsIgnoreCase( abbreviatedLongitudeCardinalDirection ) ) {
            return WEST;
        }

        return defaultValue();
    }

    public final String toCanonicalString() {
        return toString().toLowerCase( Locale.ENGLISH );
    }

    public final String toPresentationString() {
        switch ( this ) {
        case EAST:
            return "East"; //$NON-NLS-1$
        case WEST:
            return "West"; //$NON-NLS-1$
        default:
            final String errorMessage = "Unexpected LongitudeCardinalDirection " + this; //$NON-NLS-1$
            throw new IllegalArgumentException( errorMessage );
        }
    }

    public final String toAbbreviatedString() {
        switch ( this ) {
        case EAST:
            return "E"; //$NON-NLS-1$
        case WEST:
            return "W"; //$NON-NLS-1$
        default:
            final String errorMessage = "Unexpected LongitudeCardinalDirection " + this; //$NON-NLS-1$
            throw new IllegalArgumentException( errorMessage );
        }
    }

}
