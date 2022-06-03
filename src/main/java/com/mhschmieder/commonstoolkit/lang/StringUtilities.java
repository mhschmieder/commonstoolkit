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
 * {@code StringUtilities} is a static utilities class for common string
 * manipulation functionality.
 */
public final class StringUtilities {

    /**
     * The default constructor is disabled, as this is a static utilities class.
     */
    private StringUtilities() {}

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
    // TODO: Re-implement this using RegEx pattern matching?
    public static String defaultToNegativeNumber( final String numberString ) {
        // Effectively treat this as a no-op if we have a null or empty string.
        if ( ( numberString == null ) || numberString.trim().isEmpty() ) {
            return numberString;
        }

        String defaultToNegativeNumberString = numberString;
        final StringBuilder defaultToNegativeNumberStringBuilder =
                                                                 new StringBuilder( defaultToNegativeNumberString );
        final char[] chars = new char[ 1 ];

        // NOTE: We should never get an exception here as we pre-check for null
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

}