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

import com.mhschmieder.commonstoolkit.text.StringUtilities;

public enum TemperatureUnit {
    KELVIN, CELSIUS, FAHRENHEIT;

    public static TemperatureUnit defaultValue() {
        return KELVIN;
    }

    public static final TemperatureUnit defaultValueForAir() {
        return CELSIUS;
    }

    public static TemperatureUnit fromCanonicalString( final String temperatureUnitCanonicalString ) {
        return ( temperatureUnitCanonicalString != null )
            ? valueOf( temperatureUnitCanonicalString.toUpperCase( Locale.ENGLISH ) )
            : null;
    }

    public static TemperatureUnit fromAbbreviatedString( final String temperatureUnitAbbreviatedString ) {
        if ( StringUtilities.DEGREES_KELVIN.equalsIgnoreCase( temperatureUnitAbbreviatedString ) ) {
            return KELVIN;
        }

        if ( StringUtilities.DEGREES_CELSIUS
                .equalsIgnoreCase( temperatureUnitAbbreviatedString ) ) {
            return CELSIUS;
        }

        if ( StringUtilities.DEGREES_FAHRENHEIT
                .equalsIgnoreCase( temperatureUnitAbbreviatedString ) ) {
            return FAHRENHEIT;
        }

        return defaultValue();
    }

    public final String toCanonicalString() {
        String canonicalString = toString();

        // NOTE: Temperature Units are all capitalized, unlike most other
        // units, as they are named after people.
        canonicalString = canonicalString.substring( 0, 1 )
                .concat( canonicalString.substring( 1 ).toLowerCase( Locale.ENGLISH ) );

        return canonicalString;
    }

    public final String toPresentationString() {
        return toAbbreviatedString();
    }

    public final String toAbbreviatedString() {
        switch ( this ) {
        case KELVIN:
            return StringUtilities.DEGREES_KELVIN;
        case CELSIUS:
            return StringUtilities.DEGREES_CELSIUS;
        case FAHRENHEIT:
            return StringUtilities.DEGREES_FAHRENHEIT;
        default:
            final String errMessage = "Unexpected TemperatureUnit " + this; //$NON-NLS-1$
            throw new IllegalArgumentException( errMessage );
        }
    }

}
