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

/**
 * The Distance Unit is the standard linear unit of measurement. It includes the
 * "Unitless" field because sometimes this is an interim value until units are
 * known, or units weren't specified but we need to track that as distinct from
 * units not initialized within the client code.
 */
public enum DistanceUnit {
    UNITLESS, METERS, CENTIMETERS, MILLIMETERS, YARDS, FEET, INCHES;

    public static DistanceUnit defaultValue() {
        return METERS;
    }

    public static DistanceUnit fromCanonicalString( final String distanceUnitCanonicalString ) {
        return ( distanceUnitCanonicalString != null )
            ? valueOf( distanceUnitCanonicalString.toUpperCase( Locale.ENGLISH ) )
            : defaultValue();
    }

    @SuppressWarnings("nls")
    public static DistanceUnit fromAbbreviatedString( final String distanceUnitAbbreviatedString ) {
        // NOTE: These abbreviated values account for the standard of leaving a
        // space between the numeric value and its associated unit.
        if ( " m".equalsIgnoreCase( distanceUnitAbbreviatedString ) ) {
            return METERS;
        }

        if ( " cm".equalsIgnoreCase( distanceUnitAbbreviatedString ) ) {
            return CENTIMETERS;
        }

        if ( " mm".equalsIgnoreCase( distanceUnitAbbreviatedString ) ) {
            return MILLIMETERS;
        }

        if ( " yd".equalsIgnoreCase( distanceUnitAbbreviatedString ) ) {
            return YARDS;
        }

        if ( " ft".equalsIgnoreCase( distanceUnitAbbreviatedString ) ) {
            return FEET;
        }

        if ( " in".equalsIgnoreCase( distanceUnitAbbreviatedString ) ) {
            return INCHES;
        }

        if ( "".equalsIgnoreCase( distanceUnitAbbreviatedString ) ) {
            return UNITLESS;
        }

        return UNITLESS;
    }

    public final String toCanonicalString() {
        return toString().toLowerCase( Locale.ENGLISH );
    }

    public final String toPresentationString() {
        return toAbbreviatedString();
    }

    @SuppressWarnings("nls")
    public final String toAbbreviatedString() {
        switch ( this ) {
        case METERS:
            return " m";
        case CENTIMETERS:
            return " cm";
        case MILLIMETERS:
            return " mm";
        case YARDS:
            return " yd";
        case FEET:
            return " ft";
        case INCHES:
            return " in";
        case UNITLESS:
            return "";
        default:
            final String errMessage = "Unexpected DistanceUnit " + this; //$NON-NLS-1$
            throw new IllegalArgumentException( errMessage );
        }
    }

}
