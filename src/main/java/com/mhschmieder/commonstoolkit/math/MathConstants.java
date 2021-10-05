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
package com.mhschmieder.commonstoolkit.math;

/**
 * Container for mathematical constants.
 */
public final class MathConstants {

    // For use in calculating log base 2. An ln(x) times this is a log base 2.
    public static final double LN2                  = Math.log( 2d );
    public static final double LN2_SCALE            = 1d / LN2;

    // For use in calculating log base 10. An ln(x) times this is a log base 10.
    public static final double LN10                 = Math.log( 10d );
    public static final double LN10_SCALE           = 1d / LN10;

    // Smallest relative spacing for doubles.
    public static final double EPSILON_SMALL        = 1.11022302462515e-16;

    // Largest relative spacing for doubles.
    public static final double EPSILON_LARGE        = 2.2204460492503e-16;

    // Common ratios, cached for efficiency.
    // :NOTE: Redundant ratios are provided for equation clarity.
    public static final double ONE_HALF             = 0.5d;
    public static final double ONE_THIRD            = 1d / 3d;
    public static final double ONE_FOURTH           = 0.25d;
    public static final double ONE_FIFTH            = 0.2d;
    public static final double ONE_SIXTH            = 1d / 6d;
    public static final double ONE_SEVENTH          = 1d / 7d;
    public static final double ONE_EIGHTH           = 0.125d;
    public static final double ONE_NINTH            = 1d / 9d;
    public static final double ONE_ELEVENTH         = 1d / 11d;
    public static final double ONE_TWELFTH          = 1d / 12d;
    public static final double TWO_HALVES           = 1d;
    public static final double TWO_THIRDS           = 2d / 3d;
    public static final double TWO_FOURTHS          = ONE_HALF;
    public static final double TWO_FIFTHS           = 0.4d;
    public static final double TWO_SIXTHS           = ONE_THIRD;
    public static final double TWO_SEVENTHS         = 2d / 7d;
    public static final double TWO_EIGHTHS          = ONE_FOURTH;
    public static final double TWO_NINTHS           = 2d / 9d;
    public static final double THREE_HALVES         = 1.5d;
    public static final double THREE_THIRDS         = 1d;
    public static final double THREE_FOURTHS        = 0.75d;
    public static final double THREE_FIFTHS         = 0.6d;
    public static final double THREE_SIXTHS         = ONE_HALF;
    public static final double THREE_SEVENTHS       = 3d / 7d;
    public static final double THREE_EIGHTHS        = 3d * ONE_EIGHTH;
    public static final double THREE_NINTHS         = ONE_THIRD;
    public static final double FOUR_EIGHTHS         = ONE_HALF;
    public static final double FIVE_EIGHTHS         = 5d * ONE_EIGHTH;
    public static final double SIX_EIGHTHS          = THREE_FOURTHS;
    public static final double SEVEN_EIGHTHS        = 7d * ONE_EIGHTH;

    // Common square roots, cached for efficiency.
    public static final double SQRT_TWO             = Math.sqrt( 2d );
    public static final double SQRT_THREE           = Math.sqrt( 3d );

    // Euler's constant.
    public static final double EULERS_CONSTANT      = 0.57721566490153286d;

    // Trigonometric constants.
    public static final double HALF_PI              = 0.5d * Math.PI;
    public static final double TWO_PI               = 2d * Math.PI;
    public static final double THREE_PI             = 3d * Math.PI;
    public static final double FOUR_PI              = 4d * Math.PI;
    public static final double FIVE_PI              = 5d * Math.PI;
    public static final double SIX_PI               = 6d * Math.PI;
    public static final double SEVEN_PI             = 7d * Math.PI;
    public static final double EIGHT_PI             = 8d * Math.PI;
    public static final double SQRT_TWO_PI          = Math.sqrt( TWO_PI );

    // Gamma coefficients.
    public static final double GAMMA_COEFFICIENTS[] = {
                                                        1.000000000190015d,
                                                        76.18009172947146d,
                                                        -86.50532032941677d,
                                                        24.01409824083091d,
                                                        -1.231739572450155d,
                                                        0.1208650973866179d,
                                                        -0.5395239384953e-5 };

}
