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
 * General utilities for working with time signals. These are fairly common
 * requests that pop up in many fields, so it seems appropriate to provide them
 * in a top-level commons library.
 */
public final class TimeSignalUtilities {

    /**
     * The default constructor is disabled, as this is a static utilities class
     */
    private TimeSignalUtilities() {}

    public static double[] adjustTimeSignal( final double[] amplitudeVector,
                                             final double timeSignalAdjustmentMs,
                                             final double sampleRateKhz ) {
        final int numberOfSamples = amplitudeVector.length;

        final double[] timeAdjustedAmplitudeVector = new double[ numberOfSamples ];

        // TODO: Determine whether we should use floor or ceil vs. round.
        // NOTE: Possibly we should use floor if time adjustment is negative
        // and ceil if time adjustment is positive?
        final int timeSignalAdjustmentSamples = ( int ) Math
                .round( timeSignalAdjustmentMs * sampleRateKhz );

        // Move the Time Signal.
        if ( timeSignalAdjustmentSamples > 0 ) {
            // Move time to the left.
            final int firstAdjustedSampleIndex = 0;
            final int firstUnMeasuredSampleIndex = numberOfSamples - timeSignalAdjustmentSamples;
            for ( int sampleIndex =
                                  firstAdjustedSampleIndex; sampleIndex < firstUnMeasuredSampleIndex; sampleIndex++ ) {
                timeAdjustedAmplitudeVector[ sampleIndex ] = amplitudeVector[ sampleIndex
                        + timeSignalAdjustmentSamples ];
            }

            // Zero-fill the unmeasured samples that open up to the right.
            for ( int sampleIndex =
                                  firstUnMeasuredSampleIndex; sampleIndex < numberOfSamples; sampleIndex++ ) {
                timeAdjustedAmplitudeVector[ sampleIndex ] = 0.0d;
            }
        }
        else {
            // Move time to the right.
            final int lastAdjustedSampleIndex = numberOfSamples - 1;
            final int lastUnmeasuredSampleIndex = 0 - timeSignalAdjustmentSamples;
            for ( int sampleIndex =
                                  lastAdjustedSampleIndex; sampleIndex > lastUnmeasuredSampleIndex; sampleIndex-- ) {
                timeAdjustedAmplitudeVector[ sampleIndex ] = amplitudeVector[ sampleIndex
                        + timeSignalAdjustmentSamples ];
            }

            // Zero-fill the unmeasured samples that open up to the left.
            for ( int sampleIndex = lastUnmeasuredSampleIndex; sampleIndex >= 0; sampleIndex-- ) {
                timeAdjustedAmplitudeVector[ sampleIndex ] = 0.0d;
            }
        }

        // Return the adjusted Time Signal.
        return timeAdjustedAmplitudeVector;
    }

    // TODO: Make sure this works for all amplitude vectors, and not just the
    // original client context for the authoring of this method, where the
    // values are normalized and centered about zero as the origin.
    public static int getPeakTimeIndex( final double[] amplitudeVector ) {
        // Get the maximum absolute value of the Time Signal.
        double y = 0.0d;
        double maxAbsValue = 0.0d;
        int peakTimeIndex = 0;

        final int sampleIndexLast = amplitudeVector.length - 1;
        for ( int sampleIndex = 0; sampleIndex <= sampleIndexLast; sampleIndex++ ) {
            y = amplitudeVector[ sampleIndex ];
            if ( Math.abs( y ) > maxAbsValue ) {
                maxAbsValue = Math.abs( y );
                peakTimeIndex = sampleIndex;
            }
        }

        return peakTimeIndex;
    }

    // TODO: Provide an alternate version that gives a begin/end range and a
    // time increment for an auto-generated or implied time index series?
    public static double getPeakTimeMs( final double[] amplitudeVector,
                                        final double[] timeRecordIncrements ) {
        final int peakTimeIndex = getPeakTimeIndex( amplitudeVector );
        final double peakTimeMs = getPeakTimeMs( peakTimeIndex, timeRecordIncrements );
        return peakTimeMs;
    }

    // TODO: Provide an alternate version that gives a begin/end range and a
    // time increment for an auto-generated or implied time index series?
    public static double getPeakTimeMs( final int peakTimeIndex,
                                        final double[] timeRecordIncrements ) {
        final double peakTimeMs = timeRecordIncrements[ peakTimeIndex ];
        return peakTimeMs;
    }
}
