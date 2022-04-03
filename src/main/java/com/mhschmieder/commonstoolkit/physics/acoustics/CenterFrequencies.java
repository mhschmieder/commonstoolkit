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

/**
 * Container for lists of standard "musical" center frequencies (i.e. understood
 * by most people, and concise enough to display as axis tick labels in data
 * charts), for the coarsest Relative bandwidth values (i.e. one octave, and one
 * third octave).
 * 
 * TODO: Provide center frequencies for the lowest octaves that are below human
 * hearing ranges and also outside the realm of most audio special effects in
 * movies, as they may be scientifically pertinent for marine mammal studies and
 * low frequency submarine communications, for instance.
 */
public final class CenterFrequencies {

    /**
     * The default constructor is disabled, as this is a static constants class
     */
    private CenterFrequencies() {}

    public static final double NOMINAL_FULL_OCTAVE_CENTER_FREQUENCIES[]  = {
                                                                             Double.NaN,     // 0
                                                                             Double.NaN,     // 1
                                                                             Double.NaN,     // 2
                                                                             8.5d,           // 3
                                                                             16d,            // 4
                                                                             31.5d,          // 5
                                                                             63d,            // 6
                                                                             125d,           // 7
                                                                             250d,           // 8
                                                                             500d,           // 9
                                                                             1000d,          // 10
                                                                             2000d,          // 11
                                                                             4000d,          // 12
                                                                             8000d,          // 13
                                                                             16000d,         // 14
                                                                             31500d          // 15
    };

    public static final double NOMINAL_THIRD_OCTAVE_CENTER_FREQUENCIES[] = {
                                                                             Double.NaN,     // 0
                                                                             Double.NaN,     // 1
                                                                             Double.NaN,     // 2
                                                                             Double.NaN,     // 3
                                                                             Double.NaN,     // 4
                                                                             Double.NaN,     // 5
                                                                             Double.NaN,     // 6
                                                                             Double.NaN,     // 7
                                                                             Double.NaN,     // 8
                                                                             Double.NaN,     // 9
                                                                             10.0d,          // 10
                                                                             12.5d,          // 11
                                                                             16d,            // 12
                                                                             20.0d,          // 13
                                                                             25d,            // 14
                                                                             31.5d,          // 15
                                                                             40d,            // 16
                                                                             50d,            // 17
                                                                             63d,            // 18
                                                                             80d,            // 19
                                                                             100d,           // 20
                                                                             125d,           // 21
                                                                             160d,           // 22
                                                                             200d,           // 23
                                                                             250d,           // 24
                                                                             315d,           // 25
                                                                             400d,           // 26
                                                                             500d,           // 27
                                                                             630d,           // 28
                                                                             800d,           // 29
                                                                             1000d,          // 30
                                                                             1250d,          // 31
                                                                             1600d,          // 32
                                                                             2000d,          // 33
                                                                             2500d,          // 34
                                                                             3150d,          // 35
                                                                             4000d,          // 36
                                                                             5000d,          // 37
                                                                             6300d,          // 38
                                                                             8000d,          // 39
                                                                             10000d,         // 40
                                                                             12500d,         // 41
                                                                             16000d,         // 42
                                                                             20000d,         // 43
                                                                             25000d,         // 44
                                                                             31500d          // 45
    };

}
