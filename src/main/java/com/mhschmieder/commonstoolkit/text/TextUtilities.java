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
package com.mhschmieder.commonstoolkit.text;

import java.text.NumberFormat;

import com.mhschmieder.commonstoolkit.lang.StringUtilities;

/**
 * {@code TextUtilities} is a static utilities class for common string
 * formatting functionality.
 */
public class TextUtilities {

    /**
     * The default constructor is disabled, as this is a static utilities class.
     */
    private TextUtilities() {}

    /**
     * This method generates a generic tool tip text string that guarantees
     * consistency within an application when presenting allowed value ranges
     * for manual entry in some sort of editor control.
     *
     * @param valueDescriptor
     *            The descriptor for the value category for the control that the
     *            tool tip will be used on
     * @param minimumValue
     *            The minimum allowed value for the control this tool tip text
     *            is designated for
     * @param maximumValue
     *            The maximum allowed value for the control this tool tip text
     *            is designated for
     * @param numberFormat
     *            The number formatter to apply for the number representation
     * @return The tool tip text for the provided value range
     */
    public static String getValueRangeTooltipText( final String valueDescriptor,
                                                   final Number minimumValue,
                                                   final Number maximumValue,
                                                   final NumberFormat numberFormat ) {
        String minimumValueString = String.valueOf( minimumValue );
        String maximumValueString = String.valueOf( maximumValue );
        try {
            minimumValueString = numberFormat.format( minimumValue );
            maximumValueString = numberFormat.format( maximumValue );
        }
        catch ( final IllegalArgumentException iae ) {
            iae.printStackTrace();
        }

        final StringBuilder tooltipTextBuilder = new StringBuilder();
        tooltipTextBuilder.append( "Enter " ); //$NON-NLS-1$
        tooltipTextBuilder.append( valueDescriptor );
        tooltipTextBuilder.append( " between " ); //$NON-NLS-1$
        tooltipTextBuilder.append( minimumValueString );
        tooltipTextBuilder.append( " and " ); //$NON-NLS-1$
        tooltipTextBuilder.append( maximumValueString );

        final String tooltipText = tooltipTextBuilder.toString();

        return tooltipText;
    }

    /**
     * This method cuts down on code bloat and cut/paste errors by formalizing
     * the @String formatting of paired quantities that are in the same units.
     *
     * @param quantity1
     *            The first quantity in the quantity pair
     * @param quantity2
     *            The second quantity in the quantity pair
     * @param numberFormat
     *            The number formatter for precision and locale
     * @param unitLabel
     *            The @String version of the unit for the quantities
     * @return A @String representation of the Formatted Quantity Pair
     */
    @SuppressWarnings("nls")
    public static String getFormattedQuantityPair( final double quantity1,
                                                   final double quantity2,
                                                   final NumberFormat numberFormat,
                                                   final String unitLabel ) {
        final StringBuilder formattedQuantityPair = new StringBuilder();

        final String quantity1cleaned = getNegativeSignStrippedQuantity( quantity1, numberFormat );
        final String quantity2cleaned = getNegativeSignStrippedQuantity( quantity2, numberFormat );

        formattedQuantityPair.append( "(" );
        formattedQuantityPair.append( quantity1cleaned );
        formattedQuantityPair.append( ", " );
        formattedQuantityPair.append( quantity2cleaned );
        formattedQuantityPair.append( ") " );
        formattedQuantityPair.append( unitLabel );

        return formattedQuantityPair.toString();
    }

    /**
     * This method cuts down on code bloat and cut/paste errors by formalizing
     * the @String formatting of paired quantities that are in different units.
     *
     * @param quantity1
     *            The first quantity in the quantity pair
     * @param quantity2
     *            The second quantity in the quantity pair
     * @param numberFormat1
     *            The first number formatter for precision and locale
     * @param numberFormat2
     *            The second number formatter for precision and locale
     * @param unitLabel1
     *            The @String version of the first unit for the quantities
     * @param unitLabel2
     *            The @String version of the second unit for the quantities
     * @return A @String representation of the Formatted Quantity Pair
     */
    @SuppressWarnings("nls")
    public static String getFormattedQuantityPair( final double quantity1,
                                                   final double quantity2,
                                                   final NumberFormat numberFormat1,
                                                   final NumberFormat numberFormat2,
                                                   final String unitLabel1,
                                                   final String unitLabel2 ) {
        final StringBuilder formattedQuantityPair = new StringBuilder();

        final String quantity1cleaned = getNegativeSignStrippedQuantity( quantity1, numberFormat1 );
        final String quantity2cleaned = getNegativeSignStrippedQuantity( quantity2, numberFormat2 );

        formattedQuantityPair.append( "(" );
        formattedQuantityPair.append( quantity1cleaned );
        formattedQuantityPair.append( unitLabel1 );
        formattedQuantityPair.append( ", " );
        formattedQuantityPair.append( quantity2cleaned );
        formattedQuantityPair.append( unitLabel2 );
        formattedQuantityPair.append( ")" );

        return formattedQuantityPair.toString();
    }

    /**
     * This method cuts down on code bloat and cut/paste errors by formalizing
     * the @String formatting of paired quantities that are in different units.
     * <p>
     * NOTE: The third quantity is optional, so we check for NaN condition.
     *
     * @param quantity1
     *            The first quantity in the quantity triplet
     * @param quantity2
     *            The second quantity in the quantity triplet
     * @param quantity3
     *            The third quantity in the quantity triplet
     * @param numberFormat1
     *            The first number formatter for precision and locale
     * @param numberFormat2
     *            The second number formatter for precision and locale
     * @param numberFormat3
     *            The third number formatter for precision and locale
     * @param unitLabel1
     *            The @String version of the first unit for the quantities
     * @param unitLabel2
     *            The @String version of the second unit for the quantities
     * @param unitLabel3
     *            The @String version of the third unit for the quantities
     * @return A @String representation of the Formatted Quantity Triplet
     */
    @SuppressWarnings("nls")
    public static String getFormattedQuantityTriplet( final double quantity1,
                                                      final double quantity2,
                                                      final double quantity3,
                                                      final NumberFormat numberFormat1,
                                                      final NumberFormat numberFormat2,
                                                      final NumberFormat numberFormat3,
                                                      final String unitLabel1,
                                                      final String unitLabel2,
                                                      final String unitLabel3 ) {
        final StringBuilder formattedQuantityTriplet = new StringBuilder();

        final String quantity1cleaned = getNegativeSignStrippedQuantity( quantity1, numberFormat1 );
        final String quantity2cleaned = getNegativeSignStrippedQuantity( quantity2, numberFormat2 );
        final String quantity3cleaned = getNegativeSignStrippedQuantity( quantity3, numberFormat3 );

        formattedQuantityTriplet.append( "(" );
        formattedQuantityTriplet.append( quantity1cleaned );
        formattedQuantityTriplet.append( unitLabel1 );
        formattedQuantityTriplet.append( ", " );
        formattedQuantityTriplet.append( quantity2cleaned );
        formattedQuantityTriplet.append( unitLabel2 );
        if ( !Double.isNaN( quantity3 ) ) {
            formattedQuantityTriplet.append( ", " );
            formattedQuantityTriplet.append( quantity3cleaned );
            formattedQuantityTriplet.append( unitLabel3 );
        }
        formattedQuantityTriplet.append( ")" );

        return formattedQuantityTriplet.toString();
    }

    public static String getNegativeSignStrippedQuantity( final double quantity,
                                                          final NumberFormat numberFormat ) {
        final String numberString = numberFormat.format( quantity );
        final String signStrippedNumberString = StringUtilities.stripNegativeSign( numberString );
        return signStrippedNumberString;
    }

    @SuppressWarnings("nls")
    public static String getUniquefierAppendix( final int uniquefierNumber,
                                                final NumberFormat uniquefierNumberFormat ) {
        // Ignore numbers less than one, as that is the only way -- in a
        // recursive algorithm -- to easily distinguish initial conditions where
        // the original label should be used as-is (when unique) vs. cases where
        // an appendix is always required.
        final String uniquefierAppendix = ( uniquefierNumber > 0 )
            ? uniquefierNumberFormat.format( uniquefierNumber )
            : "";
        return uniquefierAppendix;
    }

}
