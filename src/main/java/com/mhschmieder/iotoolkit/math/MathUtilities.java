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
 * This file is part of the IoToolkit Library
 *
 * You should have received a copy of the MIT License along with the
 * GraphicsToolkit Library. If not, see <https://opensource.org/licenses/MIT>.
 *
 * Project: https://github.com/mhschmieder/iotoolkit
 */
package com.mhschmieder.iotoolkit.math;

import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Utility methods for math; primarily consisting of mappers and formatters.
 */
public final class MathUtilities {

    // :NOTE: The constructor is disabled, since this is a static utilities
    // class.
    private MathUtilities() {}

    public static String formatAngle( final double angle,
                                      final NumberFormat angleFormat,
                                      final AngleUnit angleUnit ) {
        final String formattedAngle = formatDouble( angle, angleFormat )
                + angleUnit.toPresentationString();

        return formattedAngle;
    }

    @SuppressWarnings("nls")
    public static String formatDouble( final double doubleValue, final NumberFormat numberFormat ) {
        if ( Double.isNaN( doubleValue ) ) {
            return "";
        }

        try {
            final String formattedValue = numberFormat.format( doubleValue );
            return formattedValue;
        }
        catch ( final ArithmeticException ae ) {
            ae.printStackTrace();

            // If parsing fails, just return a simple string representation.
            return Double.toString( doubleValue );
        }
    }

    public static String formatInteger( final int integerValue, final NumberFormat numberFormat ) {
        try {
            final String formattedValue = numberFormat.format( integerValue );
            return formattedValue;
        }
        catch ( final ArithmeticException ae ) {
            ae.printStackTrace();

            // If parsing fails, just return a simple string representation.
            return Integer.toString( integerValue );
        }
    }

    public static double getFloatAsDouble( final float value ) {
        return Double.valueOf( Float.valueOf( value ).toString() ).doubleValue();
    }

    public static Double getFloatAsDouble( final Float fValue ) {
        return Double.valueOf( fValue.toString() );
    }

    /**
     * Normalize a given angle, accounting for the counter-clockwise convention
     * for positive angles and normalizing to the ( -180, +180 ) range.
     *
     * @param angleRadians
     *            A given angle in radians
     * @return The angle normalized to the ( -180, +180 ) range
     */
    public static double getNormalizedAngleRadians( final double angleRadians ) {
        double normalizedAngleRadians = angleRadians;

        if ( ( normalizedAngleRadians + Math.PI ) < 0d ) {
            normalizedAngleRadians += MathConstants.TWO_PI;
        }

        if ( ( normalizedAngleRadians - Math.PI ) > 0d ) {
            normalizedAngleRadians -= MathConstants.TWO_PI;
        }

        return normalizedAngleRadians;
    }

    public static double parseAngle( final String formattedAngle,
                                     final NumberFormat angleFormat,
                                     final AngleUnit angleUnit ) {
        try {
            // Fetch the presentation string representation for Angle Unit.
            final String angleUnitString = angleUnit.toPresentationString();

            // Make sure to strip the Angle Unit before converting to a double.
            final int angleUnitIndex = formattedAngle.indexOf( angleUnitString );
            final String strippedFormattedAngle = ( angleUnitIndex > 0 )
                ? formattedAngle.substring( 0, angleUnitIndex )
                : formattedAngle;
            final double angle = parseDouble( strippedFormattedAngle, angleFormat );

            return angle;
        }
        catch ( final IndexOutOfBoundsException | NullPointerException e ) {
            e.printStackTrace();

            final double angle = parseDouble( formattedAngle, angleFormat );

            return angle;
        }
    }

    public static double parseDouble( final String formattedValue,
                                      final NumberFormat numberFormat ) {
        // In case of null or empty (non-numeric) strings, default to zero.
        if ( ( formattedValue == null ) || formattedValue.isEmpty() ) {
            return 0d;
        }

        try {
            final double doubleValue = numberFormat.parse( formattedValue ).doubleValue();

            return doubleValue;
        }
        catch ( final ParseException | NullPointerException e ) {
            e.printStackTrace();

            // If parsing fails, just return a simple number conversion.
            try {
                return Double.parseDouble( formattedValue );
            }
            catch ( final NumberFormatException | NullPointerException e2 ) {
                e2.printStackTrace();

                // At this point, the only safe thing to return is zero.
                return 0d;
            }
        }
    }

    public static int parseInteger( final String formattedValue, final NumberFormat numberFormat ) {
        // In case of null or empty (non-numeric) strings, default to zero.
        if ( ( formattedValue == null ) || formattedValue.isEmpty() ) {
            return 0;
        }

        try {
            final int integerValue = numberFormat.parse( formattedValue ).intValue();

            return integerValue;
        }
        catch ( final ParseException | NullPointerException e ) {
            e.printStackTrace();

            // If parsing fails, just return a simple number conversion.
            try {
                return Integer.parseInt( formattedValue );
            }
            catch ( final NumberFormatException | NullPointerException e2 ) {
                e2.printStackTrace();

                // At this point, the only safe thing to return is zero.
                return 0;
            }
        }
    }

}
