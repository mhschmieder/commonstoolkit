/**
 * MIT License
 *
 * Copyright (c) 2024, 2025 Mark Schmieder
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
package com.mhschmieder.commonstoolkit.time;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.math3.util.FastMath;

/**
 * Utility methods for core date/time facilities; especially those dedicated
 * to specialized string formatting for display. These methods are especially
 * useful for timeline readouts that accompany animation sliders.
 */
public class TimeUtilities {

    /**
     * The default constructor is disabled, as this is a static utilities class.
     */
    private TimeUtilities() {}


    /**
     * Returns the time formatted as an hours:minutes:seconds string.
     *
     * @param timeMilliseconds The unformatted time in milliseconds (long)
     * @return The time formatted as an hours:minutes:seconds string
     */
    public static String millisecondsToFormattedHoursMinutesSeconds(
            final long timeMilliseconds ) {
        return secondsToFormattedHoursMinutesSeconds( ( int ) FastMath.floor(
                0.001d * timeMilliseconds ) );
    }

    /**
     * Returns time formatted as an hours:minutes:seconds string.
     *
     * @param timeSeconds The unformatted time in seconds (integer)
     * @return The time formatted as an hours:minutes:seconds string
     */
    public static String secondsToFormattedHoursMinutesSeconds(
            final int timeSeconds ) {
        final DateTimeFormatter formatter = DateTimeFormatter
                .ISO_LOCAL_TIME;
        final LocalTime localTime = LocalTime.ofSecondOfDay(
                timeSeconds );
        return localTime.format( formatter );
    }
}
