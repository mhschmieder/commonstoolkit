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
package com.mhschmieder.jcommons.util;

import com.mhschmieder.jcommons.lang.StringConstants;

import java.util.StringTokenizer;

/**
 * {@code StringTokenizerUtilities} is a static utilities class for common
 * string tokenizer functionality.
 */
public final class StringTokenizerUtilities {
    
    /**
     * Allowed delimiters for tokens in a text stream that follow traditional
     * standards for supported control characters that mimic old typewriters.
     * These symbols are currently used to separate fields in CSV type files.
     * <p>
     * NOTE: These delimiters are used as a combined string to be passed to
     *  StringTokenizer's {@code getNextToken()} method, to reset criteria.
     */
    public static final String TOKEN_DELIMITERS = StringConstants.SPACE 
            + StringConstants.TAB + StringConstants.LF + StringConstants.CR 
            + StringConstants.SHIFT_IN;

    /**
     * The default constructor is disabled, as this is a static utilities class.
     */
    private StringTokenizerUtilities() {}
    
    public static String extractQuotedString( final StringTokenizer t ) {
        // Quoted strings must be handled specially, by consuming the start
        // quotes and setting them as the new delimiter, then grabbing (i.e.,
        // extracting) the string itself, and finally consuming the end quotes
        // and setting the token delimiter back to the full default set.
        // NOTE: If two double-quotes surround an empty or blank string, the
        //  tokenizer skips beyond it, even if just to a blank delimiter, which
        //  isn't what we want, but it doesn't always help if the quotes enclose
        //  a single blank character (a space) vs. being empty, so there's no
        //  real workaround and therefore content producers should avoid making
        //  blank strings. It doesn't help to skip the second call either! Of
        //  course, we still skip past the token delimiters after that.
        t.nextToken( StringConstants.QUOTE );
        final String extractedString = t.nextToken( StringConstants.QUOTE );
        t.nextToken( TOKEN_DELIMITERS );
        return extractedString;
    }
}
