/**
 * MIT License
 *
 * Copyright (c) 2020, 2025 Mark Schmieder
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
 * This file is part of the JCommons Library
 *
 * You should have received a copy of the MIT License along with the
 * JCommons Library. If not, see <https://opensource.org/licenses/MIT>.
 *
 * Project: https://github.com/mhschmieder/jcommons
 */
package com.mhschmieder.jcommons.text;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * This is a utility class for methods related to common use of Number Format
 * functionality. Many of these methods are placeholders for legacy code,
 * where we would prefer to eventually move to Number Converters instead. Use
 * {@code NumbertUtilities} for the unformatted versions of these methods.
 */
public final class NumberFormatUtilities {

    /**
     * The default constructor is disabled, as this is a static utilities class.
     */
    private NumberFormatUtilities() {}

    public static String formatInteger( final int integerValue, 
                                        final NumberFormat numberFormat ) {
        try {
            final String integerString = numberFormat.format( integerValue );

            return integerString;
        }
        catch ( final ArithmeticException ae ) {
            ae.printStackTrace();

            // If parsing fails, just return a simple string representation.
            return Integer.toString( integerValue );
        }
    }

    public static String formatLong( final long longValue, 
                                     final NumberFormat numberFormat ) {
        try {
            final String longString = numberFormat.format( longValue );

            return longString;
        }
        catch ( final ArithmeticException ae ) {
            ae.printStackTrace();

            // If parsing fails, just return a simple string representation.
            return Long.toString( longValue );
        }
    }

    public static String formatFloat( final float floatValue, 
                                      final NumberFormat numberFormat ) {
        if ( Float.isNaN( floatValue ) ) {
            return Float.toString( Float.NaN );
        }

        if ( Float.POSITIVE_INFINITY == floatValue ) {
            return Float.toString( Float.POSITIVE_INFINITY );
        }

        if ( Float.NEGATIVE_INFINITY == floatValue ) {
            return Float.toString( Float.NEGATIVE_INFINITY );
        }

        try {
            final String floatString = numberFormat.format( floatValue );

            return floatString;
        }
        catch ( final ArithmeticException ae ) {
            ae.printStackTrace();

            // If parsing fails, just return a simple string representation.
            // NOTE: This theoretically only happens if rounding mode was set to
            // unnecessary and then rounding was requested.
            return Float.toString( floatValue );
        }
    }

    public static String formatDouble( final double doubleValue, 
                                       final NumberFormat numberFormat ) {
        if ( Double.isNaN( doubleValue ) ) {
            return Double.toString( Float.NaN );
        }

        if ( Double.POSITIVE_INFINITY == doubleValue ) {
            return Double.toString( Double.POSITIVE_INFINITY );
        }

        if ( Double.NEGATIVE_INFINITY == doubleValue ) {
            return Double.toString( Double.NEGATIVE_INFINITY );
        }

        try {
            final String doubleString = numberFormat.format( doubleValue );

            return doubleString;
        }
        catch ( final ArithmeticException ae ) {
            ae.printStackTrace();

            // If parsing fails, just return a simple string representation.
            // NOTE: This theoretically only happens if rounding mode was set to
            //  unnecessary and then rounding was requested.
            return Double.toString( doubleValue );
        }
    }

    /**
     * Parses the provided string to an integer, using a number formatter.
     *
     * @param integerString
     *            The unconverted integer value, as a String
     * @param numberFormat
     *            The number formatter to use for determining precision
     * @return An integer converted from the provided String
     */
    public static int parseInteger( final String integerString, 
                                    final NumberFormat numberFormat ) {
        // In case of null or empty (non-numeric) string, default to zero.
        if ( ( integerString == null ) || integerString.isEmpty() ) {
            return 0;
        }

        try {
            final int integerValue = numberFormat.parse( integerString ).intValue();

            return integerValue;
        }
        catch ( final ParseException pe ) {
            pe.printStackTrace();

            // If parsing fails, just return a simple number conversion.
            try {
                return Integer.parseInt( integerString );
            }
            catch ( final NumberFormatException nfe ) {
                nfe.printStackTrace();

                // At this point, the only safe thing to return is zero.
                return 0;
            }
        }
    }

    /**
     * Parses the provided string to a long, using a number formatter.
     *
     * @param longString
     *            The unconverted long value, as a String
     * @param numberFormat
     *            The number formatter to use for determining precision
     * @return A long converted from the provided String
     */
    public static long parseLong( final String longString, 
                                  final NumberFormat numberFormat ) {
        // In case of null or empty (non-numeric) string, default to zero.
        if ( ( longString == null ) || longString.isEmpty() ) {
            return 0L;
        }

        try {
            final long longValue = numberFormat.parse( longString ).longValue();

            return longValue;
        }
        catch ( final ParseException pe ) {
            pe.printStackTrace();

            // If parsing fails, just return a simple number conversion.
            try {
                return Long.parseLong( longString );
            }
            catch ( final NumberFormatException nfe ) {
                nfe.printStackTrace();

                // At this point, the only safe thing to return is zero.
                return 0L;
            }
        }
    }

    /**
     * Parses the provided string to a single-precision float, using a number
     * formatter, but first looking for infinity and NaN.
     *
     * @param floatString
     *            The unconverted single-precision value, as a String
     * @param numberFormat
     *            The number formatter to use for determining precision
     * @return The converted single-precision value, as a float, or zero
     */
    public static float parseFloat( final String floatString, 
                                    final NumberFormat numberFormat ) {
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

        try {
            final float floatValue = numberFormat.parse( floatString ).floatValue();

            return floatValue;
        }
        catch ( final ParseException pe ) {
            pe.printStackTrace();

            // If parsing fails, just return a simple number conversion.
            try {
                return Float.parseFloat( floatString );
            }
            catch ( final NumberFormatException nfe ) {
                nfe.printStackTrace();

                // At this point, the only safe thing to return is zero.
                return 0.0f;
            }
        }
    }

    /**
     * Parses the provided string to a double-precision float, using a number
     * formatter, but first looking for infinity and NaN.
     *
     * @param doubleString
     *            The unconverted double-precision value, as a String
     * @param numberFormat
     *            The number formatter to use for determining precision
     * @return The converted double-precision value, as a double, or zero
     */
    public static double parseDouble( final String doubleString, 
                                      final NumberFormat numberFormat ) {
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

        try {
            final double doubleValue = numberFormat.parse( doubleString ).doubleValue();

            return doubleValue;
        }
        catch ( final ParseException pe ) {
            pe.printStackTrace();

            // If parsing fails, just return a simple number conversion.
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

    public static NumberFormat getUniquefierNumberFormat( final Locale locale ) {
        final NumberFormat uniquefierNumberFormat = NumberFormat.getNumberInstance( locale );

        // NOTE: The choice of three digits for name uniqueness, is to allow
        //  for table row sorting in numeric order by forcing leading zeroes. Any
        //  more than three digits might cause a comma or other locale-specific
        //  delimiter to be inserted.
        if ( uniquefierNumberFormat instanceof DecimalFormat ) {
            final DecimalFormat decimalFormat = ( DecimalFormat ) uniquefierNumberFormat;
            decimalFormat.applyPattern( "_000" );
            decimalFormat.setDecimalSeparatorAlwaysShown( false );
        }

        return uniquefierNumberFormat;
    }

    public static NumberFormat getUnitDecoratedDecimalFormat( final String numericFormatterPattern,
                                                              final String measurementUnitString,
                                                              final Locale locale ) {
        // Use a decimal formatter that defaults to integers or floating point
        // when possible, determined by the supplied numeric formatter pattern.
        // NOTE: Not all locales support decimal formatting. In such cases, we
        //  also forego units as we don't want to have to defer the expensive
        //  creation of the number formatter to the callback methods, where we
        //  could alternately format the number with the unit but no localization
        //  of number representation. This should be revisited for commonality.
        final NumberFormat numberFormat = NumberFormat.getNumberInstance( locale );
        if ( numberFormat instanceof DecimalFormat ) {
            final DecimalFormat decimalFormat = ( DecimalFormat ) numberFormat;
            final String augmentedPattern = ( ( measurementUnitString != null )
                    && !measurementUnitString.trim().isEmpty() )
                        ? numericFormatterPattern + measurementUnitString
                        : numericFormatterPattern;
            decimalFormat.applyPattern( augmentedPattern );
            decimalFormat.setDecimalSeparatorAlwaysShown( false );
        }

        return numberFormat;
    }
}
