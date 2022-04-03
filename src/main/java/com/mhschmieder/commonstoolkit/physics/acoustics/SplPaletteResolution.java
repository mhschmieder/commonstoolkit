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
package com.mhschmieder.commonstoolkit.physics.acoustics;

import com.mhschmieder.commonstoolkit.math.MathConstants;

/**
 * This is an enumeration of possible resolution values for an SPL Palette. It
 * is done as an enumeration as some values are absolute and others are relative
 * to the overall SPL Dynamic Range.
 * 
 * Note that there are two categories of values, where the first two are
 * strictly based on the number of unique color hues shown, and are based on
 * built-in limitations of standard color processing for basic image file types.
 * 
 * The second set of values are more in the acoustics domain and are commonly
 * used in visualization contexts to help users see where values pass a
 * threshold. This is especially useful for showing where sound levels double.
 */
public enum SplPaletteResolution {
    // Although these enumeration entries seem overly verbose and unnecessary,
    // the java programming language does not allow variables to start with a
    // number vs. a letter.
    RES_256, RES_64, RES_1DB, RES_2DB, RES_3DB;

    @SuppressWarnings("nls")
    public static SplPaletteResolution abbreviatedValueOf( final String splPaletteResolution ) {
        switch ( splPaletteResolution ) {
        case "256":
            return RES_256;
        case "64":
            return RES_64;
        case "1db":
            return RES_1DB;
        case "2db":
            return RES_2DB;
        case "3db":
            return RES_3DB;
        default:
            return defaultValue();
        }
    }

    public static final SplPaletteResolution defaultValue() {
        return RES_64;
    }

    public final int getNumberOfPaletteColors( final int splRangeDb ) {
        switch ( this ) {
        case RES_256:
            return 256;
        case RES_64:
            return 64;
        case RES_1DB:
            return splRangeDb;
        case RES_2DB:
            return ( int ) Math.ceil( MathConstants.ONE_HALF * splRangeDb );
        case RES_3DB:
            return ( int ) Math.ceil( MathConstants.ONE_THIRD * splRangeDb );
        default:
            return 64;
        }
    }

    @SuppressWarnings("nls")
    public final String toAbbreviatedString() {
        switch ( this ) {
        case RES_256:
            return "256";
        case RES_64:
            return "64";
        case RES_1DB:
            return "1db";
        case RES_2DB:
            return "2db";
        case RES_3DB:
            return "3db";
        default:
            return "64";
        }
    }

}
