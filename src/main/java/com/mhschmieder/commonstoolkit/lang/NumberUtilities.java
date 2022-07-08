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
package com.mhschmieder.commonstoolkit.lang;

/**
 * This is a utility class for methods that work with numbers generically,
 * without the use of any math theory or number formatters. Use
 * {@code NumberFormatUtilities} for the formatted versions of these methods.
 */
public final class NumberUtilities {

    /**
     * The default constructor is disabled, as this is a static utilities class.
     */
    private NumberUtilities() {}

    /**
     * Returns {@code true} if the argument is a finite floating-point value;
     * returns {@code false} otherwise (for NaN and infinity arguments).
     * <p>
     * NOTE: This is a substitute method for Float.isFinite() from Java 8, and
     * is provided as a courtesy to those who use this library with Java 6.
     * 
     * @param floatValue
     *            The {@code float} value to be tested
     * @return {@code true} if the argument is a finite floating-point value;
     *         {@code false} otherwise (for NaN and infinity arguments)
     */
    public static boolean isFinite( final float floatValue ) {
        return !Float.isNaN( floatValue ) && !Float.isInfinite( floatValue );
    }

    /**
     * Returns {@code true} if the argument is a finite floating-point value;
     * returns {@code false} otherwise (for NaN and infinity arguments).
     * <p>
     * NOTE: This is a substitute method for Double.isFinite() from Java 8, and
     * is provided as a courtesy to those who use this library with Java 6.
     * 
     * @param doubleValue
     *            The {@code float} value to be tested
     * @return {@code true} if the argument is a finite floating-point value;
     *         {@code false} otherwise (for NaN and infinity arguments)
     */
    public static boolean isFinite( final double doubleValue ) {
        return !Double.isNaN( doubleValue ) && !Double.isInfinite( doubleValue );
    }

    // This is a null-safe replacement for auto-boxing of Integers to ints.
    public static int getIntegerAsInt( final Integer integer ) {
        return ( integer != null ) ? integer.intValue() : 0;
    }

    public static double getFloatAsDouble( final float value ) {
        return Double.valueOf( Float.valueOf( value ).toString() ).doubleValue();
    }

    public static Double getFloatAsDouble( final Float fValue ) {
        return ( fValue != null ) ? Double.valueOf( fValue.toString() ) : Double.valueOf( 0.0d );
    }

    /**
     * Parses the provided string to an integer.
     *
     * @param integerString
     *            The unconverted integer value, as a String
     * @return An integer converted from the provided String
     */
    public static int parseInteger( final String integerString ) {
        // In case of null or empty (non-numeric) string, default to zero.
        if ( ( integerString == null ) || integerString.isEmpty() ) {
            return 0;
        }

        // Return a simple number conversion, sans formatting.
        try {
            return Integer.parseInt( integerString );
        }
        catch ( final NumberFormatException nfe ) {
            nfe.printStackTrace();

            // At this point, the only safe thing to return is zero.
            return 0;
        }
    }

    /**
     * Parses the provided string to a single-precision float, first looking for
     * infinity and NaN.
     *
     * @param floatString
     *            The unconverted single-precision value, as a String
     * @return The converted single-precision value, as a float, or zero
     */
    public static float parseFloat( final String floatString ) {
        // In case of null or empty (non-numeric) string, default to zero.
        if ( ( floatString == null ) || floatString.isEmpty() ) {
            return 0.0f;
        }

        if ( floatString.equals( Float.toString( Float.NaN ) ) ) {
            return Float.NaN;
        }

        if ( floatString.equals( Float.toString( Float.POSITIVE_INFINITY ) ) ) {
            return Float.POSITIVE_INFINITY;
        }

        if ( floatString.equals( Float.toString( Float.NEGATIVE_INFINITY ) ) ) {
            return Float.NEGATIVE_INFINITY;
        }

        // Return a simple number conversion, sans formatting.
        try {
            return Float.parseFloat( floatString );
        }
        catch ( final NumberFormatException nfe ) {
            nfe.printStackTrace();

            // At this point, the only safe thing to return is zero.
            return 0.0f;
        }
    }

    /**
     * Parses the provided string to a double-precision float, first looking for
     * infinity and NaN.
     *
     * @param doubleString
     *            The unconverted double-precision value, as a String
     * @return The converted double-precision value, as a double, or zero
     */
    public static double parseDouble( final String doubleString ) {
        // In case of null or empty (non-numeric) string, default to zero.
        if ( ( doubleString == null ) || doubleString.isEmpty() ) {
            return 0.0d;
        }

        if ( doubleString.equals( Double.toString( Double.NaN ) ) ) {
            return Double.NaN;
        }

        if ( doubleString.equals( Double.toString( Double.POSITIVE_INFINITY ) ) ) {
            return Double.POSITIVE_INFINITY;
        }

        if ( doubleString.equals( Double.toString( Double.NEGATIVE_INFINITY ) ) ) {
            return Double.NEGATIVE_INFINITY;
        }

        // Return a simple number conversion, sans formatting.
        try {
            return Double.parseDouble( doubleString );
        }
        catch ( final NumberFormatException nfe ) {
            nfe.printStackTrace();

            // At this point, the only safe thing to return is zero.
            return 0.0d;
        }
    }

}
