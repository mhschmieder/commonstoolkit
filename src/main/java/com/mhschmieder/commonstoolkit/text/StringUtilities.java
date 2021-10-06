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
package com.mhschmieder.commonstoolkit.text;

import java.text.NumberFormat;
import java.util.Vector;

/**
 * {@code StringUtilities} is a static utilities class for common string
 * formatting and manipulation functionality.
 */
public final class StringUtilities {

    // The constructor is disabled, as this is a static utilities class.
    private StringUtilities() {}

    /**
     * The line separator string. Under Windows, this would be "\r\n"; under
     * Unix, "\n"; Under macOS, "\r" or maybe "\n" since OS X.
     */
    public static final String                          LINE_SEPARATOR            =
                                                                       System.lineSeparator();

    /**
     * The degree symbol causes problems when entered directly vs. using
     * Unicode. Here we declare it on its own, and with temperature units.
     *
     * Note that the appearance of the special Unicode characters for Degrees
     * Celsius and Degrees Fahrenheit is rather illegible, so keeping the
     * Degrees Symbol separate from the Temperature Unit is still best.
     */
    @SuppressWarnings("nls") public static final String DEGREES_SYMBOL            = "\u00B0";
    @SuppressWarnings("nls") public static final String DEGREES_CELSIUS_SYMBOL    = "\u2103";
    @SuppressWarnings("nls") public static final String DEGREES_FAHRENHEIT_SYMBOL = "\u2109";
    @SuppressWarnings("nls") public static final String DEGREES_KELVIN            = " K";
    @SuppressWarnings("nls") public static final String DEGREES_CELSIUS           =
                                                                        DEGREES_SYMBOL + "C";
    @SuppressWarnings("nls") public static final String DEGREES_FAHRENHEIT        =
                                                                           DEGREES_SYMBOL + "F";

    // This method attaches a "+" sign, if absent. This is often necessary
    // when there is recursion during model/view syncing, as renderers shouldn't
    // display the "+" sign and number parsers can't handle the character.
    public static String attachPositiveSign( final String numberString ) {
        // Effectively treat this as a no-op if we have a null or empty string.
        if ( ( numberString == null ) || numberString.trim().isEmpty() ) {
            return numberString;
        }

        // If no minus sign present, assume this is a positive number, but don't
        // add the positive sign if already present.
        final StringBuilder signAttachedNumberStringBuilder = new StringBuilder();
        final char firstChar = numberString.charAt( 0 );
        if ( ( firstChar != '-' ) && ( firstChar != '+' ) ) {
            signAttachedNumberStringBuilder.append( '+' );
        }
        signAttachedNumberStringBuilder.append( numberString );

        final String signAttachedNumberString = signAttachedNumberStringBuilder.toString();

        return signAttachedNumberString;
    }

    // This method takes a string that is known to represent a number, and
    // defaults it to negative. This means checking for a qualified "+", without
    // which the number is assumed negative. Numbers qualified with a "+" are
    // pass-through; whereas other numbers insert a "-" sign (if not already
    // present) to negate the number. Numbers already qualified with a "-" are
    // also pass-through. This method does not deal with "negative-zero".
    // :TODO: Re-implement this using RegEx pattern matching?
    public static String defaultToNegativeNumber( final String numberString ) {
        // Effectively treat this as a no-op if we have a null or empty string.
        if ( ( numberString == null ) || numberString.trim().isEmpty() ) {
            return numberString;
        }

        String defaultToNegativeNumberString = numberString;
        final StringBuilder defaultToNegativeNumberStringBuilder =
                                                                 new StringBuilder( defaultToNegativeNumberString );
        final char[] chars = new char[ 1 ];

        // :NOTE: We should never get an exception here as we pre-check for null
        // or empty strings, so the try-catch-throw block is a fail-safe for
        // future-proof code.
        try {
            defaultToNegativeNumberStringBuilder.getChars( 0, 1, chars, 0 );
            if ( chars[ 0 ] == '+' ) {
                defaultToNegativeNumberStringBuilder.deleteCharAt( 0 );
                defaultToNegativeNumberString = defaultToNegativeNumberStringBuilder.toString();
            }
            else if ( chars[ 0 ] != '-' ) {
                defaultToNegativeNumberStringBuilder.insert( 0, '-' );
                defaultToNegativeNumberString = defaultToNegativeNumberStringBuilder.toString();
            }
        }
        catch ( final Exception e ) {
            e.printStackTrace();
            return numberString;
        }

        return defaultToNegativeNumberString;
    }

    /**
     * Given a string, replace all the instances of XML special characters with
     * their corresponding XML entities. This is necessary to allow arbitrary
     * strings to be encoded within XML.
     * <p>
     *
     * @see #unescapeForXML(String)
     * @param string
     *            The string to escape.
     * @return A new string with special characters replaced.
     */
    @SuppressWarnings("nls")
    public static String escapeForXML( final String string ) {
        // This method gets called quite a bit when parsing large files, so
        // rather than calling substitute() many times, we combine all the loops
        // in one pass.

        // TODO: Find a way to get this into Javadocs without causing errors
        //
        // In this method, we make the following translations:
        //
        // &amp; becomes &amp;amp;
        // " becomes &amp;quot;
        // < becomes &amp;lt;
        // > becomes &amp;gt;
        // newline becomes &amp;#10;
        // carriage return becomes &amp;#13;

        // A different solution might be to scan the string for escaped xml
        // characters and if any are found, then create a StringBuilder and do
        // the conversion. Using a profiler would help here.
        if ( string == null ) {
            return null;
        }

        final StringBuilder stringBuilder = new StringBuilder( string );
        int i = 0;
        int length = string.length();
        while ( i < length ) {
            switch ( stringBuilder.charAt( i ) ) {
            case '\n':
                stringBuilder.deleteCharAt( i );
                stringBuilder.insert( i, "&#10;" );
                length += 4;
                break;
            case '\r':
                stringBuilder.deleteCharAt( i );
                stringBuilder.insert( i, "&#13;" );
                length += 4;
                break;
            case '"':
                stringBuilder.deleteCharAt( i );
                stringBuilder.insert( i, "&quot;" );
                length += 5;
                break;
            case '&':
                stringBuilder.deleteCharAt( i );
                stringBuilder.insert( i, "&amp;" );
                length += 4;
                break;
            case '<':
                stringBuilder.deleteCharAt( i );
                stringBuilder.insert( i, "&lt;" );
                length += 3;
                break;
            case '>':
                stringBuilder.deleteCharAt( i );
                stringBuilder.insert( i, "&gt;" );
                length += 3;
                break;
            default:
                break;
            }
            i++;
        }

        return stringBuilder.toString();
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
     * <p>
     * :NOTE: The third quantity is optional, so we check for NaN condition.
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

    @SuppressWarnings("nls")
    public static StringBuilder getHtmlTableColumnHeader( final String tableColumnHeader ) {
        final StringBuilder htmlTableColumnHeader = new StringBuilder( "<th><b><i>" );
        htmlTableColumnHeader.append( tableColumnHeader );
        htmlTableColumnHeader.append( "</i></b></th>" );
        return htmlTableColumnHeader;
    }

    @SuppressWarnings("nls")
    public static StringBuilder getImageHeader( final String imageLabel ) {
        final StringBuilder imageHeader = new StringBuilder( "<p /><h1 align=\"center\">" );
        imageHeader.append( imageLabel );
        imageHeader.append( "</h1>" );
        return imageHeader;
    }

    public static String getNegativeSignStrippedQuantity( final double quantity,
                                                          final NumberFormat numberFormat ) {
        final String numberString = numberFormat.format( quantity );
        final String signStrippedNumberString = stripNegativeSign( numberString );
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

    // This method performs a byte-wise copy from one stream to another,
    // thereby preserving byte order regardless of the data type or stream
    // format (ASCII, binary, etc.).
    public static String replace( final String string,
                                  final String searchPattern,
                                  final String replacePattern ) {
        int start = 0;
        int end = 0;
        final StringBuilder result = new StringBuilder();

        while ( ( end = string.indexOf( searchPattern, start ) ) >= 0 ) {
            result.append( string.substring( start, end ) );
            result.append( replacePattern );
            start = end + searchPattern.length();
        }
        result.append( string.substring( start ) );

        return result.toString();
    }

    // Pad a vector of string vectors to the maximum column count.
    // :NOTE: This is designed as a generic preparatory task for loading
    // string-based data into a table or possibly a database (e.g. SQL).
    @SuppressWarnings("nls")
    public static int stringVectorPadToMaxColumn( final Vector< Vector< String > > vector ) {
        // Figure out maximum number of columns in a line.
        int maxColumn = 0;
        for ( final Vector< String > line : vector ) {
            maxColumn = Math.max( maxColumn, line.size() );
        }

        // Expand all lines to the maximum number of columns.
        for ( final Vector< String > line : vector ) {
            while ( line.size() < maxColumn ) {
                line.add( "" );
            }
        }

        // Return the maximum column count for the data vector.
        return maxColumn;
    }

    // This is a utility method to strip typical HTML formatting from labels.
    @SuppressWarnings("nls")
    public static String stripHtmlFormatting( final String formattedString ) {
        if ( formattedString == null ) {
            return null;
        }

        String unformattedString = formattedString;
        unformattedString = StringUtilities.replace( unformattedString, "<html>", "" );
        unformattedString = StringUtilities.replace( unformattedString, "</html>", "" );
        unformattedString = StringUtilities.replace( unformattedString, "<br>", ". " );
        return unformattedString;
    }

    // This method strips the "-" sign, when unnecessary due to absolute zero.
    @SuppressWarnings("nls")
    public static String stripNegativeSign( final String numberString ) {
        final String signStrippedNumberString = numberString.replaceAll( "^-(?=0(\\.0*)?$)", "" );

        return signStrippedNumberString;
    }

    /**
     * Returns a copy of the original numeric string, stripped of the positive
     * sign if present.
     * <p>
     * This is often necessary when there is recursion during mode/view syncing,
     * as renderers shouldn't display the superfluous "+" sign as numbers are
     * positive by default (when the minus sign isn't present), and as most
     * number parsers can't handle the character and will throw an exception.
     * <p>
     * This method is borrowed from a more general utility toolkit that has not
     * yet been published, so is a temporary copy/paste placeholder and will be
     * removed once the basic commons toolkits are published.
     *
     * @param numericString
     *            The numeric string to search for the superfluous positive sign
     * @return The numeric string stripped of the positive sign if present
     */
    public static String stripPositiveSign( final String numericString ) {
        // Effectively treat this as a no-op if we have a null or empty string.
        if ( ( numericString == null ) || numericString.trim().isEmpty() ) {
            return numericString;
        }

        final StringBuilder signStrippedNumericStringBuilder = new StringBuilder( numericString );
        if ( numericString.charAt( 0 ) == '+' ) {
            signStrippedNumericStringBuilder.deleteCharAt( 0 );
        }

        final String signStrippedNumericString = signStrippedNumericStringBuilder.toString();

        return signStrippedNumericString;
    }

    /**
     * Replace all occurrences of <i>pattern</i> in the specified string with
     * <i>replacement</i>. Note that the pattern is NOT a regular expression,
     * and that relative to the String.replaceAll() method in jdk1.4, this
     * method is extremely slow. This method does not work well with back
     * slashes.
     *
     * @param string
     *            The string to edit.
     * @param pattern
     *            The string to replace.
     * @param replacement
     *            The string to replace it with.
     * @return A new string with the specified replacements.
     */
    public static String substitute( final String string,
                                     final String pattern,
                                     final String replacement ) {
        if ( string == null ) {
            return null;
        }

        String substituteString = string;
        int start = substituteString.indexOf( pattern );

        while ( start != -1 ) {
            final StringBuilder builder = new StringBuilder( substituteString );
            builder.delete( start, start + pattern.length() );
            builder.insert( start, replacement );
            substituteString = new String( builder );
            start = substituteString.indexOf( pattern, start + replacement.length() );
        }

        return substituteString;
    }

    /**
     * Given a string, replace all the instances of XML entities with their
     * corresponding XML special characters. This is necessary to allow
     * arbitrary strings to be encoded within XML.
     *
     * @see #escapeForXML(String)
     * @param string
     *            The string to escape.
     * @return A new string with special characters replaced.
     */
    @SuppressWarnings("nls")
    public static String unescapeForXML( final String string ) {
        // TODO: Find a way to get this into Javadocs without causing errors
        //
        // In this method, we make the following translations:
        //
        // &amp;amp; becomes &amp
        // &amp;quot; becomes "
        // &amp;lt; becomes <
        // &amp;gt; becomes >
        // &amp;#10; becomes newline
        // &amp;#13; becomes carriage return
        String unescapeString = string;
        if ( string.indexOf( '&' ) != -1 ) {
            unescapeString = substitute( unescapeString, "&amp;", "&" );
            unescapeString = substitute( unescapeString, "&quot;", "\"" );
            unescapeString = substitute( unescapeString, "&lt;", "<" );
            unescapeString = substitute( unescapeString, "&gt;", ">" );
            unescapeString = substitute( unescapeString, "&#10;", "\n" );
            unescapeString = substitute( unescapeString, "&#13;", "\r" );
        }
        return unescapeString;
    }

}