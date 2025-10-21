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
 * This file is part of the CommonsToolkit Library
 *
 * You should have received a copy of the MIT License along with the
 * CommonsToolkit Library. If not, see <https://opensource.org/licenses/MIT>.
 *
 * Project: https://github.com/mhschmieder/commonstoolkit
 */
package com.mhschmieder.commonstoolkit.lang;

import org.apache.commons.lang3.StringUtils;

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
    public static String stripNegativeSign( final String numberString ) {
        final String signStrippedNumberString = numberString.replaceAll( 
                "^-(?=0(\\.0*)?$)", "" );

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

        return signStrippedNumericStringBuilder.toString();
    }

    /**
     * Replace all occurrences of <i>pattern</i> in the specified string with
     * <i>replacement</i>. Note that the pattern is NOT a regular expression,
     * and that relative to the String.replaceAll() method in jdk1.4, this
     * method is extremely slow. This method does not work well with backslashes.
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
     * Quote the given string; sometimes referred to as escaping the string, but
     * that action doesn't always use the double quote character.
     * <p>
     * This method is most useful for outputting file names and titles to text
     * based output files that use token separators, so that strings with spaces
     * remain intact during parsing.
     * <p>
     * NOTE: The Java StringTokenizer seems to fail if there isn't a space ahead
     *  of the quotes. Usually this is the case, except at the head of a line,
     *  but as most elements on a line will have spaces or other delimiters 
     *  anyway, it seems best not to prepend the space here, and to instead make
     *  clients aware that they may need to prepend one (or a numeric field) at
     *  their end if the quoted string is at the head of a line in a text file.
     *  The tokenizer gets confused for some reason otherwise, but Apache's 
     *  StringUtils in their Commons Lang library has similar issues.
     * <p>
     * NOTE: We adjust for null strings by substituting a single-blank string,
     *  as we otherwise just return a single quote vs an empty quote-enclosed
     *  string. An empty string doesn't work, as StringTokenizer then skips past
     *  the closing quotes and is forever out-of-sync for the remainder of the 
     *  line. This is only partially successful, as StringTokenizer still skips
     *  past the end quotes and gets out of whack, but at least this approach
     *  should work well with safer parsers such as Apache CsvParser (with space
     *  as the delimiter).
     * 
     * @param unquotedString The string to be quoted
     * @return A quoted version of the given unquoted string
     */
    public static String quote( final String unquotedString ) {
        final String safeString = ( ( unquotedString != null )
                && !unquotedString.isEmpty() )
                ? unquotedString
                : StringConstants.SPACE;

        return StringConstants.QUOTE
                + safeString
                + StringConstants.QUOTE;
    }

    /**
     * Unquote the given string; sometimes referred to as unescaping the string, 
     * but that action doesn't always use the double quote character.
     * <p>
     * This method is most useful for inputting file names and titles to text
     * based output files that use token separators, so that strings with spaces
     * remain intact during parsing.
     * <p>
     * NOTE: The Java StringTokenizer seems to fail if there isn't a space ahead
     *  of the quotes. Usually this is the case, except at the head of a line,
     *  but as most elements on a line will have spaces or other delimiters 
     *  anyway, it seems best not to overly complicate this method to account 
     *  for no leading space at the head of a line, and to instead make clients 
     *  aware that they may need to prepend one (or a numeric field) at their
     *  end if the quoted string is at the head of a line in a text file. The 
     *  tokenizer gets confused for some reason otherwise, but Apache's string
     *  replacement (used here) in Commons Lang has similar issues.
     * 
     * @param quotedString The string to be unquoted
     * @return An unquoted version of the given quoted string
     */
    public static String unquote( final String quotedString ) {
        if ( ( quotedString == null ) || quotedString.isEmpty() ) {
            return StringConstants.EMPTY;
        }
        
        String unquotedString = StringUtils.removeStart( quotedString, 
                                                         StringConstants.QUOTE );
        unquotedString = StringUtils.removeEnd( unquotedString, 
                                                StringConstants.QUOTE );
        return unquotedString;
    }
}