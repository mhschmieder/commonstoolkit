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
import com.mhschmieder.commonstoolkit.math.MathExt;

public final class SmoothingUtilities extends Object {

    /**
     * The default constructor is disabled, as this is a static utilities class
     */
    private SmoothingUtilities() {}

    public static boolean gaussianSmooth( final double in[],
                                          final double out[],
                                          final int numberOfBins,
                                          final int octaveDivider,
                                          final double[][] smoothingTable ) {
        // TODO: We only support third octave and sixth octave smoothing, but
        // the octave divider isn't used here as we merely look up pre-computed
        // arrays, so either we should make those into static references and
        // fetch the correct one in this method based on octave divider, or
        // we should remove that argument from the method parameter list.
        if ( ( octaveDivider != 6 ) && ( octaveDivider != 3 ) ) {
            return false;
        }

        // NOTE: The window's left and right edge bin indices might be
        // incorrectly named and may even be related to the smoothing table's
        // second dimension, which is presumed to be 30. If so, then that is a
        // bad programming practice as assumptions should not be made in
        // independent methods vs. passed as arguments or queried at run-time
        // such as using an array's length field.
        for ( int binIndex = 0; binIndex < numberOfBins; binIndex++ ) {
            out[ binIndex ] = 0.0d;

            double smoothingRatio = 0.0d;

            int windowLeftEdgeBinIndex = binIndex - 15;
            if ( windowLeftEdgeBinIndex < 0 ) {
                windowLeftEdgeBinIndex = 0;
            }
            final int windowRightEdgeBinIndex = binIndex + 15;

            int smoothingIndex = 0;
            while ( ( windowLeftEdgeBinIndex < numberOfBins )
                    && ( windowLeftEdgeBinIndex < windowRightEdgeBinIndex ) ) {
                out[ binIndex ] += smoothingTable[ binIndex ][ smoothingIndex ]
                        * in[ windowLeftEdgeBinIndex ];
                smoothingRatio += smoothingTable[ binIndex ][ smoothingIndex ];

                windowLeftEdgeBinIndex++;
                smoothingIndex++;
            }

            if ( smoothingRatio > 0 ) {
                out[ binIndex ] /= smoothingRatio;
            }
        }

        return true;
    }

    public static void makeSmoothingTable( final double[] frequencyBins,
                                           final int numberOfBins,
                                           final double smoothingTable[][],
                                           final int octaveDivider ) {
        // First, we need to convert from decibels to linear units.
        final double powerRatioDb = -6.0d;
        final double voltageRatio = FrequencySignalUtilities.getVoltageRatio( powerRatioDb );

        // TODO: Review these variable names relative to usage.
        final double windowCenter = StrictMath.exp( MathConstants.LN2 / ( octaveDivider * 2.0d ) );
        final double lnWindowCenter = StrictMath.log( windowCenter );
        final double windowWidth = -MathExt.sqr( lnWindowCenter ) / StrictMath.log( voltageRatio );

        // NOTE: The window's left and right edge bin indices might be
        // incorrectly named and may even be related to the smoothing table's
        // second dimension, which is presumed to be 30. If so, then that is a
        // bad programming practice as assumptions should not be made in
        // independent methods vs. passed as arguments or queried at run-time
        // such as using an array's length field.
        for ( int binIndex = 0; binIndex < numberOfBins; binIndex++ ) {
            int windowLeftEdgeBinIndex = binIndex - 15;
            if ( windowLeftEdgeBinIndex < 0 ) {
                windowLeftEdgeBinIndex = 0;
            }
            final int windowRightEdgeBinIndex = binIndex + 15;

            final double referenceBin = frequencyBins[ binIndex ];
            final double lnReferenceBin = StrictMath.log( referenceBin );

            int smoothingIndex = 0;
            while ( ( windowLeftEdgeBinIndex < numberOfBins )
                    && ( windowLeftEdgeBinIndex < windowRightEdgeBinIndex ) ) {
                final double windowBin = frequencyBins[ windowLeftEdgeBinIndex ];
                final double lnWindowBin = StrictMath.log( windowBin );

                final double lnF = lnWindowBin - lnReferenceBin;

                final double e = -MathExt.sqr( lnF );

                final double y = StrictMath.exp( e / windowWidth );

                smoothingTable[ binIndex ][ smoothingIndex ] = y;

                windowLeftEdgeBinIndex++;
                smoothingIndex++;
            }
        }
    }

    public static void makeSmoothingTables( final double[] frequencyBins,
                                            final int numberOfBins,
                                            final double thirdOctaveSmoothingTable[][],
                                            final double sixthOctaveSmoothingTable[][] ) {
        makeSmoothingTable( frequencyBins, numberOfBins, thirdOctaveSmoothingTable, 3 );
        makeSmoothingTable( frequencyBins, numberOfBins, sixthOctaveSmoothingTable, 6 );
    }

}
