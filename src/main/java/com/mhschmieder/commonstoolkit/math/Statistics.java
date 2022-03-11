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
package com.mhschmieder.commonstoolkit.math;

import java.util.Arrays;

/**
 * This class provides statistical methods which compute properties of a data
 * set and cumulative distribution methods for a few standard distributions.
 * NOTE: Some of these methods are patterned after the API spec from the
 * once-free JNL from VisualNumerics, and the Statistics class from
 * "Java for Engineers and Scientists" (Stephen J. Chapman, Prentice-Hall).
 */
public final class Statistics {

    // NOTE: The constructor is private, since this class only contains static
    // methods and therefore should not be instantiated.
    private Statistics() {}

    // This method calculates the cumulative probability curve of array x at
    // locations y.
    public static double[] cumulativeProbability( final double[] x, final double[] y ) {
        // Sort into ascending order
        Arrays.sort( x );

        // Create cumulative probability array
        final double[] cumProb = new double[ y.length ];

        // Calculate cumulative counts
        for ( int i = 0; i < y.length; i++ ) {
            for ( final double element : x ) {
                if ( element <= y[ i ] ) {
                    cumProb[ i ]++;
                }
            }
        }

        // Convert to cumulative probability
        for ( int i = 0; i < y.length; i++ ) {
            cumProb[ i ] = cumProb[ i ] / x.length;
        }

        return cumProb;
    }

    // This method linearly interpolates the (x,y) data at points x0. In this
    // method, if x0 < x[0], y0 = y[0], and if x0 > x[x.length-1], y0 =
    // y[x.length-1].
    public static double interp1( final double[] x, final double[] y, final double x0 ) {
        // We know data will be monotonically increasing, so don't bother to
        // check here. Get straight to it.
        double y0;
        if ( x0 < x[ 0 ] ) {
            y0 = y[ 0 ];
        }
        else if ( x0 >= x[ x.length - 1 ] ) {
            y0 = y[ x.length - 1 ];
        }
        else {
            // Find the proper interval.
            int i = 0;
            for ( i = 0; i < ( x.length - 1 ); i++ ) {
                if ( ( x0 >= x[ i ] ) && ( x0 <= x[ i + 1 ] ) ) {
                    break;
                }
            }

            // Now interpolate the value.
            if ( i < x.length ) {
                final double dx = ( x0 - x[ i ] ) / ( x[ i + 1 ] - x[ i ] );
                y0 = y[ i ] + ( ( y[ i + 1 ] - y[ i ] ) * dx );
            }
            else {
                y0 = y[ x.length - 1 ];
            }
        }

        return y0;
    }

    // This method linearly interpolates the (x,y) data at points x0. In this
    // method, if x0 < x[0], y0 = NaN, and if x0 > x[x.length-1], y0 = NaN.
    public static double interp2( final double[] x, final double[] y, final double x0 ) {
        // We know data will be monotonically increasing, so don't bother to
        // check here. Get straight to it.
        double y0;
        if ( x0 < x[ 0 ] ) {
            y0 = Double.NaN;
        }
        else if ( x0 > x[ x.length - 1 ] ) {
            y0 = Double.NaN;
        }
        else {
            // Find the proper interval.
            int i = 0;
            for ( i = 0; i < ( x.length - 1 ); i++ ) {
                if ( ( x0 >= x[ i ] ) && ( x0 <= x[ i + 1 ] ) ) {
                    break;
                }
            }

            // Now interpolate the value.
            if ( i < x.length ) {
                final double dx = ( x0 - x[ i ] ) / ( x[ i + 1 ] - x[ i ] );
                y0 = y[ i ] + ( ( y[ i + 1 ] - y[ i ] ) * dx );
            }
            else {
                y0 = y[ x.length - 1 ];
            }
        }

        return y0;
    }

    // This method calculates the kurtosis of a data set. Kurtosis is the fourth
    // central moment divided by the fourth power of the standard deviation.
    public static double kurtosis( final double[] x ) {
        if ( x.length < 2 ) {
            return 0.0d;
        }

        final double m4 = Statistics.moment( x, 4 );
        final double sm2 = Math.sqrt( Statistics.moment( x, 2 ) );
        return ( m4 / Math.pow( sm2, 4.0d ) );
    }

    public static double maximum( final double[] x ) {
        if ( x.length < 1 ) {
            return 0.0d;
        }

        double maxVal = x[ 0 ];
        double val;
        for ( int i = 1; i < x.length; i++ ) {
            val = x[ i ];
            if ( val > maxVal ) {
                maxVal = val;
            }
        }
        return maxVal;
    }

    // Properties of a data set.
    // NOTE: The arithmetic mean is the same as the average.
    public static double mean( final double[] x ) {
        if ( x.length < 1 ) {
            return 0.0d;
        }

        double sum = 0.0d;
        for ( final double element : x ) {
            sum += element;
        }
        return sum / x.length;
    }

    public static double median( final double[] x ) {
        double median = 0;

        if ( x.length < 1 ) {
            return 0.0d;
        }

        // Get local copy of data.
        final double[] out = new double[ x.length ];
        System.arraycopy( x, 0, out, 0, x.length );

        // Sort the data.
        Arrays.sort( out );

        // Get the median.
        final int medianIndex = ( int ) Math.round( 0.5d * out.length );
        if ( ( out.length % 2 ) == 0 ) {
            median = 0.5d * ( out[ medianIndex - 1 ] + out[ medianIndex ] );
        }
        else {
            median = out[ medianIndex ];
        }

        return median;
    }

    public static double minimum( final double[] x ) {
        if ( x.length < 1 ) {
            return 0.0d;
        }

        double minVal = x[ 0 ];
        double val;
        for ( int i = 1; i < x.length; i++ ) {
            val = x[ i ];
            if ( val < minVal ) {
                minVal = val;
            }
        }
        return minVal;
    }

    public static double moment( final double[] x, final int order ) {
        if ( order == 1 ) {
            return 0.0d;
        }

        final double mu = Statistics.mean( x );
        double sum = 0;
        for ( final double element : x ) {
            sum += Math.pow( ( element - mu ), order );
        }
        return ( sum / x.length );
    }

    // This method calculates the locations of the specified percentiles p of
    // array x.
    public static double[] percentile( final double[] x, final double[] p ) {
        // Get percentile locations array
        final double[] percLoc = new double[ p.length ];

        // Sort into ascending order.
        Arrays.sort( x );

        // Get minimum and maximum.
        final double max = x[ x.length - 1 ];
        final double min = x[ 0 ];

        // Get independent array for cumProb calculation.
        final double dx = ( max - min ) / 99d;
        final double[] xval = new double[ 100 ];
        for ( int i = 0; i < xval.length; i++ ) {
            xval[ i ] = min + ( i * dx );
        }

        // Create cumulative probability array.
        final double[] cumProb = new double[ xval.length ];

        // Calculate cumulative counts.
        for ( int i = 0; i < xval.length; i++ ) {
            for ( final double element : x ) {
                if ( element <= xval[ i ] ) {
                    cumProb[ i ]++;
                }
            }
        }

        // Convert to cumulative probability.
        for ( int i = 0; i < xval.length; i++ ) {
            cumProb[ i ] = cumProb[ i ] / x.length;
        }

        // Calculate desired percentiles.
        for ( int i = 0; i < p.length; i++ ) {
            percLoc[ i ] = Statistics.interp2( cumProb, xval, p[ i ] );
        }

        return percLoc;
    }

    /*
     * // Least squares linear fit to a data set. // If coef is the returned
     * array from linearFit then // the line of fit is: y = coef[0] + (x
     * coef[1]). public static double[] linearFit( double[] x, double[] y ) { }
     * public static double slope( double[] x, double[] y ) { } // Cumulative F
     * distribution function and inverse. public static double FCdf( double x,
     * double degreesFreedomNumerator, double degreesFreedomDenominator ) { }
     * public static double inverseFCdf( double p, double
     * degreesFreedomNumerator, double degreesFreedomDenominator ) { } //
     * Cumulative Student's t distribution function and inverse. public static
     * double tCdf( double t, double degreesFreedom ) { } public static double
     * inverseTCdf( double p, double degreesFreedom ) { } // Cumulative
     * Normal/Gaussian distribution function and inverse. public static double
     * normal Cdf( double x ) { } public static double inverseNormalCdf( double
     * p ) { }
     */

    public static double range( final double[] x ) {
        return Statistics.maximum( x ) - Statistics.minimum( x );
    }

    // This method calculates the skewness of a data set. Skewness is the third
    // central moment divided by the third power of the standard deviation.
    public static double skew( final double[] x ) {
        if ( x.length < 2 ) {
            return 0.0d;
        }

        final double m3 = Statistics.moment( x, 3 );
        final double sm2 = Math.sqrt( Statistics.moment( x, 2 ) );
        return ( m3 / Math.pow( sm2, 3.0d ) );
    }

    // Sample standard deviation (dividing by n - 1). This method calculates
    // standard deviation of a data set. This method normalizes by n-1, where n
    // is the sequence length, so it is the best unbiased estimate of standard
    // deviation if the data set is sampled from a normal distribution.
    public static double standardDeviation( final double[] x ) {
        return Math.sqrt( Statistics.variance( x ) );
    }

    // The sample variance (dividing by n - 1). This method calculates variance
    // of a data set. This method normalizes by n-1, where n is the sequence
    // length, so it is the best unbiased estimate of variance if the data set
    // is sampled from a normal distribution.
    public static double variance( final double[] x ) {
        if ( x.length < 2 ) {
            return 0.0d;
        }

        double sum = 0.0d;
        double sum2 = 0.0d;
        for ( final double element : x ) {
            sum += element;
            sum2 += element * element;
        }

        return ( ( x.length * sum2 ) - MathExt.sqr( sum ) ) / ( x.length * ( x.length - 1 ) );
    }

}
