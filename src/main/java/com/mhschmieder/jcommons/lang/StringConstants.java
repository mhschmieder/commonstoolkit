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
package com.mhschmieder.jcommons.lang;

import org.apache.commons.lang3.StringUtils;

/**
 * {@code StringConstants} is a static constants class for common strings that
 * are used as well-known symbols and keys. Use {@link CharConstants} for the
 * single-character primitive char versions of these constants.
 */
public class StringConstants {

    /**
     * The default constructor is disabled, as this is a static constants class.
     */
    private StringConstants() {}

    /**
     * The line separator string. Under Windows, this would be "\r\n"; under
     * Unix, "\n"; Under macOS, "\r" or maybe "\n" since OS X.
     */
    public static final String LINE_SEPARATOR = System.lineSeparator();

    /**
     * Reuse control character constants from Apache Commons Lang, to avoid
     * reinventing the wheel and to consolidate the references with our extended
     * collection in order to simplify mixtures of the two sets and imports.
     * <p>
     * TODO: Continue looking for constants in third-party libraries that we
     * already build atop of, to reduce redundancies and name collisions.
     */
    public static final String EMPTY = StringUtils.EMPTY;
    public static final String SPACE = StringUtils.SPACE;
    public static final String CR = StringUtils.CR;
    public static final String LF = StringUtils.LF;
    public static final String CRLF = CR + LF;
    
    /**
     * It is often necessary to escape or unescape a quote character as a
     * literal, so declaring a constant avoids adding too many to the heap and
     * also makes for more legible code that is less likely to be incorrect.
     */
    public static final String QUOTE = "\"";

    /**
     * Periods are the same across all platforms, so it is safer and makes for
     * easier coding, if we define a global constant for its character value.
     * <p>
     * NOTE: Periods (aka decimal point) and commas invert their purpose in some
     *  locales, when it comes to number representation.
     */
    public static final String PERIOD = ".";

    /**
     * Commas are the same across all platforms, so it is safer and makes for
     * easier coding, if we define a global constant for its character value.
     * <p>
     * NOTE: Commas are often used as delimiters, so it especially helps in that
     *  context, and also makes for more readable and understandable code.
     */
    public static final String COMMA = ",";

    /**
     * Semicolons are the same across all platforms, so it is safer and makes
     * for easier coding, if we define a global constant for its character value.
     * <p>
     * NOTE: Semicolons are often used as delimiters, so it especially helps in
     *  that context, and also makes for more readable and understandable code.
     */
    public static final String SEMICOLON = ";";

    /**
     * Tabs are the same across all platforms, so it is safer and makes for
     * easier coding, if we define a global constant for its character value.
     */
    public static final String TAB = "\t";

    /**
     * Shift Out is the same across all platforms, so it is safer and makes for
     * easier coding, if we define a global constant for its character value.
     * <p>
     * NOTE: We have to use Unicode for the char literal in this one case.
     */
    public static final String SHIFT_OUT = "\u000E";

    /**
     * Shift In is the same across all platforms, so it is safer and makes for
     * easier coding, if we define a global constant for its character value.
     */
    public static final String SHIFT_IN = "\f";

    /**
     * The degrees symbol causes problems when entered directly vs. using
     * Unicode. Here we declare it on its own, and with temperature units.
     *
     * Note that the appearance of the special Unicode characters for Degrees
     * Celsius and Degrees Fahrenheit is rather illegible, so keeping the
     * Degrees Symbol separate from the Temperature Unit is still best.
     */
    public static final String DEGREES_SYMBOL = "\u00B0";
    public static final String DEGREES_CELSIUS_SYMBOL = "\u2103";
    public static final String DEGREES_FAHRENHEIT_SYMBOL = "\u2109";
    public static final String DEGREES_KELVIN = " K";
    public static final String DEGREES_CELSIUS = DEGREES_SYMBOL + "C";
    public static final String DEGREES_FAHRENHEIT = DEGREES_SYMBOL + "F";
}
