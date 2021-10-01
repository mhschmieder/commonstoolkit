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
 * This file is part of the IoToolkit Library
 *
 * You should have received a copy of the MIT License along with the
 * GraphicsToolkit Library. If not, see <https://opensource.org/licenses/MIT>.
 *
 * Project: https://github.com/mhschmieder/iotoolkit
 */
package com.mhschmieder.iotoolkit.math;

/**
 * The class <code>Complex</code> defines a complex data type, and implements
 * operations with that data type. The methods in this class fall into two
 * categories--<b>instance methods</b> and <b>static methods</b>. The instance
 * methods are mostly designed to work on <code>Complex</code> objects and to
 * return references to <code>Complex</code> objects, so that they can be strung
 * together in expressions. For example, a <code>Complex</code> expression such
 * as
 *
 * <pre>
 * z3 = z1 * z2 + z3
 * </pre>
 *
 * can be implemented as
 *
 * <pre>
 * z3 = z1.mul( z2 ).add( z3 )
 * </pre>
 *
 * However, <i>these methods are always evaluated from left to right</i>, so if
 * the order of operations is not purely left-to-right, you will have to use
 * parentheses or re-arrange the order of the methods to get the order of
 * operations right. For example, a <code>Complex</code> expression such as
 *
 * <pre>
 * z3 = z1 + z2 * z3
 * </pre>
 *
 * requires that the multiplication occur before the addition. This can be
 * implemented as
 *
 * <pre>
 * z3 = z1.add( z2.mul( z3 ) );
 * </pre>
 *
 * or alternatively as
 *
 * <pre>
 * z3 = z2.mul( z3 ).add( z1 );
 * </pre>
 *
 * The instance methods in this class that can be used to build such expressions
 * include <code>add</code>, <code>sub</code>, <code>mul</code>,
 * <code>div</code>, <code>pow</code>, <code>inv</code>, <code>neg</code>,
 * <code>abs</code>, <code>arg</code>, <code>cong</code>, <code>re</code>, and
 * <code>im</code>.
 * <p>
 * This class also contains many static methods that accept <code>Complex</code>
 * arguments and return appropriate values. In general, the <code>static</code>
 * methods in this class are similar to the <code>static</code> methods in class
 * <code>java.lang.Math</code>. Examples include <code>sin(z)</code>,
 * <code>cos(z)</code>, <code>tan(z)</code>, <code>log(z)</code>, etc.
 * <p>
 * Class <code>Complex</code> is a subclass of <code>Number</code>, and it
 * overrides all of the abstract methods in that class. The overridden methods
 * in class <code>Complex</code> work with the <i>real part</i> of the
 * <code>Complex</code> number only.
 * <p>
 * This class also implements the <code>java.lang.Comparable</code> interface.
 * In this case, the comparisons are between the <i>absolute values</i> of the
 * two <code>Complex</code> numbers. :NOTE: Some of these methods and
 * constants are patterned after the API spec from the once-free JNL from
 * VisualNumerics, the Complex class from "Java for Engineers and Scientists"
 * (Stephen J. Chapman, Prentice-Hall), and the Complex class from
 * "The Java Programmer's Guide to Numerical Computing" (Ronald Mak,
 * Prentice-Hall).
 */
public final class Complex extends Number implements Cloneable, Comparable< Complex > {
    /**
     *
     */
    private static final long   serialVersionUID = 878946080717735643L;

    /**
     * Constant representing a zero vector along the real axis.
     */
    public static final Complex ZERO             = new Complex( 0d, 0d );

    /**
     * Constant representing a unit vector along the real axis.
     */
    public static final Complex UNITY            = new Complex( 1d, 0d );

    /**
     * Constant representing <i><b>i</b></i>, the square root of -1.
     */
    public static final Complex I                = new Complex( 0d, 1d );

    /**
     * Constant representing <i><b>i</b></i>, the square root of -1. (For
     * electrical engineers, who use the "J" terminology instead of "I").
     */
    public static final Complex J                = new Complex( 0d, 1d );

    /**
     * Returns the absolute value (magnitude) of a <tt>Complex</tt> number.
     *
     * @param z
     *            a <tt>Complex</tt> number
     * @return a <tt>double</tt> containing the absolute value of the
     *         <tt>Complex</tt> number
     * @see Complex#abs()
     */
    public static double abs( final Complex z ) {
        final double absRe = Math.abs( z._re );
        final double absIm = Math.abs( z._im );

        // This algorithm avoids overflows that might otherwise occur when
        // evaluating Math.sqrt(re*re + im*im), and essentially pre-factors that
        // equation for safety.
        if ( ( absRe == 0d ) && ( absIm == 0d ) ) {
            return 0d;
        }
        else if ( absRe >= absIm ) {
            final double d = z._im / z._re;
            return absRe * Math.sqrt( 1d + ( d * d ) );
        }
        else {
            final double d = z._re / z._im;
            return absIm * Math.sqrt( 1d + ( d * d ) );
        }
    }

    /**
     * Returns the principal inverse cosine of a <tt>Complex</tt> number.
     *
     * <pre>
     *     acos(z) = -&lt;i&gt;i&lt;/i&gt; * log( z + &lt;i&gt;i&lt;/i&gt; * sqrt(1 - z*z) )
     * </pre>
     *
     * @param z
     *            a <tt>Complex</tt> number
     * @return the <tt>Complex</tt> result
     * @see Complex#cos(Complex)
     */
    public static Complex acos( final Complex z ) {
        double re1, im1; // (1 - z*z)
        double re2, im2; // i * sqrt(1 - z*z)
        double re3, im3; // log(z + i * sqrt(1 - z*z))
        Complex result; // Result of intermediate calculations

        // Build (1 - z*z)
        re1 = 1d - ( ( z._re * z._re ) - ( z._im * z._im ) );
        im1 = 0d - ( ( z._re * z._im ) + ( z._im * z._re ) );

        // Build sqrt(1 - z*z)
        result = complex( re1, im1 );
        result = sqrt( result );

        // Build i * sqrt(1 - z*z)
        re2 = -result._im;
        im2 = +result._re;

        // Build z + i * sqrt(1 - z*z)
        result._re = z._re + re2;
        result._im = z._im + im2;

        // Build log(z + i * sqrt(1 - z*z))
        re3 = Math.log( abs( result ) );
        im3 = arg( result );

        // Build -i * log(z + i * sqrt(1 - z*z))
        result._re = im3;
        result._im = -re3;

        return result;
    }

    /**
     * Returns the principal inverse hyperbolic cosine of a <tt>Complex</tt>
     * number.
     *
     * <pre>
     * acosh( z ) = log( z + sqrt( z * z - 1 ) )
     * </pre>
     *
     * @param z
     *            a <tt>Complex</tt> number
     * @return the <tt>Complex</tt> result
     * @see Complex#cosh(Complex)
     */
    public static Complex acosh( final Complex z ) {
        double re1, im1; // (z*z + 1)
        double re2, im2; // log(z + sqrt(z*z - 1))
        Complex result; // Result of intermediate calculations

        // Build (z*z - 1)
        re1 = ( ( z._re * z._re ) - ( z._im * z._im ) ) - 1d;
        im1 = ( ( z._re * z._im ) + ( z._im * z._re ) );

        // Build sqrt(z*z - 1)
        result = complex( re1, im1 );
        result = sqrt( result );

        // Build z + sqrt(z*z - 1)
        result._re = z._re + result._re;
        result._im = z._im + result._im;

        // Build log(z + sqrt(z*z - 1))
        re2 = Math.log( abs( result ) );
        im2 = arg( result );

        // Build acosh(z)
        result._re = re2;
        result._im = im2;

        return result;
    }

    /**
     * Returns the principal angle of a <tt>Complex</tt> number, in radians,
     * measured counter-clockwise from the real axis. The method <tt>arg(z)</tt>
     * always returns a <tt>double</tt> between -<tt><b>PI</b></tt> and +
     * <tt><b>PI</b></tt>.
     *
     * @param z
     *            <tt>Complex</tt> number
     * @return a <tt>double</tt> containing the principal angle of the
     *         <tt>Complex</tt> value
     * @see Complex#arg()
     */
    public static double arg( final Complex z ) {
        return Math.atan2( z._im, z._re );
    }

    /**
     * Returns the principal inverse sine of a <tt>Complex</tt> number.
     *
     * <pre>
     *     asin(z) = -&lt;i&gt;i&lt;/i&gt; * log(&lt;i&gt;i&lt;/i&gt;*z + sqrt(1 - z*z))
     * </pre>
     *
     * @param z
     *            a <tt>Complex</tt> number
     * @return the <tt>Complex</tt> result
     * @see Complex#sin(Complex)
     */
    public static Complex asin( final Complex z ) {
        double re1, im1; // (1 - z*z)
        double re2, im2; // i*z
        double re3, im3; // log (i*z + sqrt(1 - z*z))
        Complex result; // Result of intermediate calculations

        // Build (1 - z*z)
        re1 = 1d - ( ( z._re * z._re ) - ( z._im * z._im ) );
        im1 = 0d - ( ( z._re * z._im ) + ( z._im * z._re ) );

        // Build sqrt(1 - z*z)
        result = complex( re1, im1 );
        result = sqrt( result );

        // Build iz = i*z
        re2 = -z._im;
        im2 = +z._re;

        // Build i*z + sqrt(1 - z*z)
        result._re = re2 + result._re;
        result._im = im2 + result._im;

        // Build log (i*z + sqrt(1 - z*z))
        re3 = Math.log( abs( result ) );
        im3 = arg( result );

        // Build -i * log (i*z + sqrt(1 - z*z))
        result._re = im3;
        result._im = -re3;

        return result;
    }

    /**
     * Returns the principal inverse hyperbolic sine of a <tt>Complex</tt>
     * number.
     *
     * <pre>
     * asinh( z ) = log( z + sqrt( z * z + 1 ) )
     * </pre>
     *
     * @param z
     *            a <tt>Complex</tt> number
     * @return the <tt>Complex</tt> result
     * @see Complex#sinh(Complex)
     */
    public static Complex asinh( final Complex z ) {
        double re1, im1; // (z*z + 1)
        double re2, im2; // log(z + sqrt(z*z + 1))
        Complex result; // Intermediate results of calc

        // Build z*z + 1
        re1 = ( ( z._re * z._re ) - ( z._im * z._im ) ) + 1d;
        im1 = ( ( z._re * z._im ) + ( z._im * z._re ) );

        // Build sqrt(z*z + 1)
        result = complex( re1, im1 );
        result = sqrt( result );

        // Build z + sqrt(z*z + 1)
        result._re = z._re + result._re;
        result._im = z._im + result._im;

        // Build log(z + sqrt(z*z + 1))
        re2 = Math.log( result.abs() );
        im2 = result.arg();

        // Build asinh(z)
        result._re = re2;
        result._im = im2;

        return result;
    }

    /**
     * Returns the principal inverse tangent of a <tt>Complex</tt> number.
     *
     * <pre>
     *     atan(z) = -&lt;i&gt;i&lt;/i&gt;/2 * log( (&lt;i&gt;i&lt;/i&gt;-z)/(&lt;i&gt;i&lt;/i&gt;+z) )
     * </pre>
     *
     * @param z
     *            a <tt>Complex</tt> number
     * @return the <tt>Complex</tt> result
     * @see Complex#tan(Complex)
     */
    public static Complex atan( final Complex z ) {
        double re1, im1; // (i+z)
        double re2, im2; // log((i-z)/(i+z))
        Complex result; // Intermediate results of calculation

        // Build (i-z)
        result = complex( -z._re, 1d - z._im );

        // Build (i+z)
        re1 = z._re;
        im1 = 1d + z._im;

        // Build (i-z)/(i+z)
        result = div( result, re1, im1 );

        // Build log((i-z)/(i+z))
        re2 = Math.log( abs( result ) );
        im2 = arg( result );

        // Build -i/2 * log((i-z)/(i+z))
        result._re = 0.5d * im2;
        result._im = -0.5d * re2;

        return result;
    }

    /**
     * Returns the principal inverse hyperbolic tangent of a <tt>Complex</tt>
     * number.
     *
     * <pre>
     * atanh( z ) = 1 / 2 * log( ( 1 + z ) / ( 1 - z ) )
     * </pre>
     *
     * @param z
     *            a <tt>Complex</tt> number
     * @return the <tt>Complex</tt> result
     * @see Complex#tanh(Complex)
     */
    public static Complex atanh( final Complex z ) {
        double re1, im1; // (1 - z)
        double re2, im2; // log((1+z)/(1-z))
        Complex result; // Intermediate results of calculation

        // Build (1+z)
        result = complex( 1d + z._re, +z._im );

        // Build (1-z)
        re1 = 1d - z._re;
        im1 = -z._im;

        // Build (1+z)/(1-z)
        result = div( result, re1, im1 );

        // Build log((1+z)/(1-z))
        re2 = Math.log( abs( result ) );
        im2 = arg( result );

        // Build atanh(z) = 0.5*log((1+z)/(1-z))
        result._re = 0.5d * re2;
        result._im = 0.5d * im2;

        return result;
    }

    /**
     * Creates a new <tt>Complex</tt> number from a <tt>double</tt>. The
     * imaginary part of the <tt>Complex</tt> number is initialized to zero.
     *
     * @param re
     *            real part
     * @return <tt>Complex</tt> of the form (re + i*0)
     * @see Complex#complex(double, double)
     */
    public static Complex complex( final double re ) {
        return new Complex( re, 0d );
    }

    /**
     * Creates a new <tt>Complex</tt> from real and imaginary parts.
     *
     * @param re
     *            Real part
     * @param im
     *            Imaginary part
     * @return <tt>Complex</tt> number from Rectangular coordinates
     * @see Complex#complex(double)
     */
    public static Complex complex( final double re, final double im ) {
        return new Complex( re, im );
    }

    /**
     * Returns the <tt>Complex</tt> conjugate of a <tt>Complex</tt> number. The
     * <tt>Complex</tt> conjugate of a number is the complex number having the
     * same real component and the negative of the imaginary component.
     *
     * @param z
     *            <tt>Complex</tt> input parameter
     * @return <tt>Complex</tt> conjugate of <tt>z</tt>.
     */
    public static Complex conj( final Complex z ) {
        return complex( z._re, -z._im );
    }

    /**
     * Returns the cosine of a <tt>Complex</tt> number.
     *
     * <pre>
     *     cos(z) = ( exp(&lt;i&gt;i&lt;/i&gt;*z) + exp(-&lt;i&gt;i&lt;/i&gt;*z) ) / (2*&lt;i&gt;i&lt;/i&gt;)
     * </pre>
     *
     * @param z
     *            an <tt>Complex</tt> angle
     * @return the <tt>Complex</tt> cosine
     * @see Complex#acos(Complex)
     */
    public static Complex cos( final Complex z ) {
        double scalar;
        double izRe, izIm; // Real and imag parts of iz
        double re1, im1; // Real and imag parts of exp(i*z)
        double re2, im2; // Real and imag parts of exp(-i*z)

        // Build i*z
        izRe = -z._im;
        izIm = z._re;

        // Build exp(i*z) using Euler's equation
        scalar = Math.exp( izRe );
        re1 = scalar * Math.cos( izIm );
        im1 = scalar * Math.sin( izIm );

        // Build exp(-i*z)
        scalar = Math.exp( -izRe );
        re2 = scalar * Math.cos( -izIm );
        im2 = scalar * Math.sin( -izIm );

        // Build exp(i*z) + exp(-i*z)
        re1 = re1 + re2;
        im1 = im1 + im2;

        // result: (exp(i*z) + exp(-i*z)) / 2
        return complex( 0.5d * re1, 0.5d * im1 );
    }

    /**
     * Returns the hyperbolic cosine of a <tt>Complex</tt> number.
     *
     * <pre>
     * cosh( z ) = ( exp( z ) + exp( -z ) ) / 2
     * </pre>
     *
     * @param z
     *            a <tt>Complex</tt> value
     * @return the <tt>Complex</tt> hyperbolic cosine
     * @see Complex#acosh(Complex)
     */
    public static Complex cosh( final Complex z ) {
        double re1, im1; // Real and imag parts of exp(z)
        double re2, im2; // Real and imag parts of exp(-z)
        double scalar;

        // Build exp(z)
        scalar = Math.exp( z._re );
        re1 = scalar * Math.cos( z._im );
        im1 = scalar * Math.sin( z._im );

        // Build exp(-z)
        scalar = Math.exp( -z._re );
        re2 = scalar * Math.cos( -z._im );
        im2 = scalar * Math.sin( -z._im );

        // Build exp(z) + exp(-z)
        re1 = re1 + re2;
        im1 = im1 + im2;

        // cosh(z) = ( exp(z) + exp(-z) ) / 2
        return complex( 0.5d * re1, 0.5d * im1 );
    }

    /**
     * Implements division between two complex numbers, one expressed as as a
     * <tt>Complex</tt> and the other expressed as a pair of <tt>double</tt>s.
     * 
     * @param z
     *            The {@link Complex} number that we will divide
     * @param x
     *            The "real" part of the complex number to use as the divisor
     * @param y
     *            The "imaginary" part of the complex number to use as the
     *            divisor
     * @return The {@link Complex} number that results from the complex division
     */
    public static Complex div( final Complex z, final double x, final double y ) {
        double ratio, scalar, zRe, zIm;

        // Calculate to minimize roundoff errors. This algorithm is inspired by
        // "Numerical Recipes in Fortran 77: The Art of Scientific Computing".
        if ( Math.abs( x ) >= Math.abs( y ) ) {
            ratio = y / x;
            scalar = 1d / ( x + ( y * ratio ) );
            zRe = scalar * ( z._re + ( z._im * ratio ) );
            zIm = scalar * ( z._im - ( z._re * ratio ) );
        }
        else {
            ratio = x / y;
            scalar = 1d / ( ( x * ratio ) + y );
            zRe = scalar * ( ( z._re * ratio ) + z._im );
            zIm = scalar * ( ( z._im * ratio ) - z._re );
        }

        // Return results in a new complex object
        return complex( zRe, zIm );
    }

    /**
     * Returns the exponential number <i>e</i> (i.e., 2.718...) raised to the
     * power of the <code>Complex</code> value z.
     *
     * @param z
     *            a <code>double</code> value.
     * @return the value <i>e</i><sup>z</sup>, where <i>e</i> is the base of the
     *         natural logarithms.
     * @see Complex#log(Complex)
     */
    public static Complex exp( final Complex z ) {
        final double scalar = Math.exp( z._re );

        return complex( scalar * Math.cos( z._im ), scalar * Math.sin( z._im ) );
    }

    /**
     * Returns the imaginary part of a <tt>Complex</tt> as a <tt>double</tt>.
     *
     * @param z
     *            <tt>Complex</tt> number
     * @return <tt>double</tt> containing the imaginary part of z
     * @see Complex#im()
     */
    public static double im( final Complex z ) {
        return z._im;
    }

    /**
     * Returns the reciprocal of a <tt>Complex</tt> number (1/z).
     *
     * @param z
     *            <tt>Complex</tt> number
     * @return The <tt>Complex</tt> reciprocal
     */
    public static Complex inv( final Complex z ) {
        final double x = z._re;
        final double y = z._im;
        double ratio, scalar, zRe, zIm;

        // Calculate to minimize roundoff errors. This algorithm is inspired by
        // "Numerical Recipes in Fortran 77: The Art of Scientific Computing".
        if ( Math.abs( x ) >= Math.abs( y ) ) {
            ratio = y / x;
            scalar = 1d / ( x + ( y * ratio ) );
            zRe = scalar;
            zIm = -scalar * ratio;
        }
        else {
            ratio = x / y;
            scalar = 1d / ( ( x * ratio ) + y );
            zRe = scalar * ratio;
            zIm = -scalar;
        }

        return complex( zRe, zIm );
    }

    /**
     * Returns the principal natural logarithm of a <tt>Complex</tt> number.
     *
     * @param z
     *            a <tt>Complex</tt> number.
     * @return the value ln&nbsp;<code>z</code>, the natural logarithm of
     *         <code>z</code>.
     * @see Complex#exp(Complex)
     */
    public static Complex log( final Complex z ) {
        return complex( Math.log( abs( z ) ), arg( z ) );
    }

    /**
     * Returns the L2 norm of a <tt>Complex</tt> number, which is the sum of the
     * squares of the real and imaginary parts.
     *
     * @param z
     *            a <tt>Complex</tt> number
     * @return a <tt>double</tt> containing the norm of the <tt>Complex</tt>
     *         number
     */
    public static double norm( final Complex z ) {
        return ( MathExt.sqr( z._re ) + MathExt.sqr( z._im ) );
    }

    /**
     * Returns the phase of a <tt>Complex</tt> value. Note that this method
     * duplicates the functionality of the <tt>arg</tt> method.
     * 
     * @param c
     *            The {@link Complex} number whose phase we need to calculate
     *
     * @return Phase of <tt>Complex</tt> value in <i> radians</i>
     */
    public static double phase( final Complex c ) {
        return Math.atan2( c._im, c._re );
    }

    /**
     * Returns a new <tt>Complex</tt> from a magnitude and angle.
     *
     * @param r
     *            Magnitude
     * @param theta
     *            Angle (in <i>radians</i>)
     * @return <tt>Complex</tt> from Polar coordinates
     */
    public static Complex polar( final double r, final double theta ) {
        double rUnwrapped = r;
        double thetaUnwrapped = theta;
        if ( ( float ) rUnwrapped < 0f ) {
            thetaUnwrapped += Math.PI;
            rUnwrapped = -rUnwrapped;
        }
        thetaUnwrapped %= MathConstants.TWO_PI;

        return complex( rUnwrapped * Math.cos( thetaUnwrapped ),
                        rUnwrapped * Math.sin( thetaUnwrapped ) );
    }

    /**
     * Returns the <tt>Complex</tt> base raised to the power of the
     * <tt>Complex</tt> exponent.
     *
     * @param base
     *            The <tt>Complex</tt> base value
     * @param exponent
     *            The <tt>Complex</tt> exponent
     * @return <tt>Complex</tt> base raised to the power of the <tt>Complex</tt>
     *         exponent.
     * @see Complex#pow(double, Complex)
     * @see Complex#pow(Complex, double)
     */
    public static Complex pow( final Complex base, final Complex exponent ) {
        // return exp( exponent * log(base) );

        final double re = Math.log( base.abs() );
        final double im = base.arg();

        final double re2 = ( re * exponent._re ) - ( im * exponent._im );
        final double im2 = ( re * exponent._im ) + ( im * exponent._re );

        final double scalar = Math.exp( re2 );

        return complex( scalar * Math.cos( im2 ), scalar * Math.sin( im2 ) );
    }

    /**
     * Returns the <tt>Complex</tt> base raised to the power of the
     * <tt>double</tt> exponent.
     *
     * @param base
     *            The <tt>Complex</tt> base value
     * @param exponent
     *            The <tt>double</tt> exponent
     * @return <tt>Complex</tt> base raised to the power of the exponent.
     * @see Complex#pow(double, Complex)
     * @see Complex#pow(Complex, Complex)
     */
    public static Complex pow( final Complex base, final double exponent ) {
        // return exp( exponent * log(base) );

        final double re = exponent * Math.log( base.abs() );
        final double im = exponent * base.arg();

        final double scalar = Math.exp( re );

        return complex( scalar * Math.cos( im ), scalar * Math.sin( im ) );
    }

    /**
     * Returns the <tt>double</tt> base raised to the power of the
     * <tt>Complex</tt> exponent.
     *
     * @param base
     *            The <tt>double</tt> base value
     * @param exponent
     *            The <tt>Complex</tt> exponent
     * @return base raised to the power of the <tt>Complex</tt> exponent.
     * @see Complex#pow(Complex, double)
     * @see Complex#pow(Complex, Complex)
     */
    public static Complex pow( final double base, final Complex exponent ) {
        // return exp( exponent * log(base) );

        final double re = Math.log( Math.abs( base ) );
        final double im = Math.atan2( 0d, base );

        final double re2 = ( re * exponent._re ) - ( im * exponent._im );
        final double im2 = ( re * exponent._im ) + ( im * exponent._re );

        final double scalar = Math.exp( re2 );

        return complex( scalar * Math.cos( im2 ), scalar * Math.sin( im2 ) );
    }

    /**
     * Returns the real part of a <tt>Complex</tt> as a <tt>double</tt>.
     *
     * @param z
     *            <tt>Complex</tt> number
     * @return <tt>double</tt> containing the real part of z
     * @see Complex#re()
     */
    public static double re( final Complex z ) {
        return z._re;
    }

    /**
     * Returns the sine of a <tt>Complex</tt> number.
     *
     * <pre>
     *     sin(z) = ( exp(&lt;i&gt;i&lt;/i&gt;*z) - exp(-&lt;i&gt;i&lt;/i&gt;*z) ) / (2*&lt;i&gt;i&lt;/i&gt;)
     * </pre>
     *
     * @param z
     *            an angle, in radians.
     * @return the <tt>Complex</tt> sine
     * @see Complex#asin(Complex)
     */
    public static Complex sin( final Complex z ) {
        double scalar;
        double izRe, izIm; // Real and imag parts of iz
        double re1, im1; // Real and imag parts of exp(i*z)
        double re2, im2; // Real and imag parts of exp(-i*z)

        // Build i*z
        izRe = -z._im;
        izIm = z._re;

        // Build exp(i*z)
        scalar = Math.exp( izRe );
        re1 = scalar * Math.cos( izIm );
        im1 = scalar * Math.sin( izIm );

        // Build exp(-i*z)
        scalar = Math.exp( -izRe );
        re2 = scalar * Math.cos( -izIm );
        im2 = scalar * Math.sin( -izIm );

        // Build exp(i*z) - exp(-i*z)
        re1 = re1 - re2;
        im1 = im1 - im2;

        // result: (exp(i*z) - exp(-i*z)) / 2
        return ( complex( 0.5d * im1, -0.5d * re1 ) );
    }

    /**
     * Returns the hyperbolic sine of a <tt>Complex</tt> number.
     *
     * <pre>
     * sinh( z ) = ( exp( z ) - exp( -z ) ) / 2
     * </pre>
     *
     * @param z
     *            a <tt>Complex</tt> value
     * @return the <tt>Complex</tt> hyperbolic sine
     * @see Complex#asinh(Complex)
     */
    public static Complex sinh( final Complex z ) {
        double re1, im1; // Real and imag parts of exp(z)
        double re2, im2; // Real and imag parts of exp(-z)
        double scalar;

        // Build exp(z)
        scalar = Math.exp( z._re );
        re1 = scalar * Math.cos( z._im );
        im1 = scalar * Math.sin( z._im );

        // Build exp(-z)
        scalar = Math.exp( -z._re );
        re2 = scalar * Math.cos( -z._im );
        im2 = scalar * Math.sin( -z._im );

        // Build exp(z) - exp(-z)
        re1 = re1 - re2;
        im1 = im1 - im2;

        // Build sinh(z) = ( exp(z) - exp(-z) ) / 2
        return complex( 0.5d * re1, 0.5d * im1 );
    }

    /**
     * Returns the square root of a <code>Complex</code> value.
     *
     * @param z
     *            a <code>Complex</code> value.
     * @return the square root of <code>z</code>.
     */
    public static Complex sqrt( final Complex z ) {
        // This algorithm is inspired by "Numerical Recipes in C".
        double re = 0d, im = 0d;
        final double mag = z.abs();

        if ( mag > 0d ) {
            if ( z._re > 0d ) {
                final double temp = Math.sqrt( 0.5d * ( mag + z._re ) );
                re = temp;
                im = ( 0.5d * z._im ) / temp;
            }
            else {
                double temp = Math.sqrt( 0.5d * ( mag - z._re ) );
                if ( z._im < 0d ) {
                    temp = -temp;
                }
                re = ( 0.5d * z._im ) / temp;
                im = temp;
            }
        }

        return complex( re, im );
    }

    /**
     * Returns the tangent of a <tt>Complex</tt> number.
     *
     * <pre>
     * tan( z ) = sin( z ) / cos( z )
     * </pre>
     *
     * @param z
     *            a <tt>Complex</tt> angle
     * @return the <tt>Complex</tt> tangent
     * @see Complex#atan(Complex)
     */
    public static Complex tan( final Complex z ) {
        Complex result; // Result of calculation
        double scalar; // Scalar
        double izRe, izIm; // Real and imag parts of iz
        double re1, im1; // Real and imag parts of exp(i*z)
        double re2, im2; // Real and imag parts of exp(-i*z)
        double re3, im3; // exp(i*z) - exp(-i*z)
        double re4, im4; // exp(i*z) + exp(i*z)
        double cosRe, cosIm; // cos(z)

        // First, calculate sin(z):

        // Build iz = i * z
        izRe = -z._im;
        izIm = z._re;

        // Build z1 = exp(iz)
        scalar = Math.exp( izRe );
        re1 = scalar * Math.cos( izIm );
        im1 = scalar * Math.sin( izIm );

        // Build z2 = exp(-iz)
        scalar = Math.exp( -izRe );
        re2 = scalar * Math.cos( -izIm );
        im2 = scalar * Math.sin( -izIm );

        // Build z3 = z1 - z2
        re3 = re1 - re2;
        im3 = im1 - im2;

        // Build sin(z) = z3 / (2*i)
        result = complex( 0.5d * im3, -0.5d * re3 );

        // Now, calculate cos(z):

        // z4 = z1 + z2
        re4 = re1 + re2;
        im4 = im1 + im2;

        // cos(z) = z4 / 2
        cosRe = 0.5d * re4;
        cosIm = 0.5d * im4;

        // tan(z) = sin(z) / cos(z)
        result = div( result, cosRe, cosIm );

        return result;
    }

    /**
     * Returns the hyperbolic tangent of a <tt>Complex</tt> number.
     *
     * <pre>
     * tanh( z ) = sinh( z ) / cosh( z )
     * </pre>
     *
     * @param z
     *            a <tt>Complex</tt> value
     * @return the <tt>Complex</tt> hyperbolic tangent
     * @see Complex#atanh(Complex)
     */
    public static Complex tanh( final Complex z ) {
        // tanh(z) = sinh(z) / cosh(z)

        Complex result; // Result of calculation
        double scalar; // Scalar
        double re1, im1; // Real and imag parts of exp(z)
        double re2, im2; // Real and imag parts of exp(-z)
        double re3, im3; // exp(z) - exp(-z)
        double re4, im4; // exp(z) + exp(z)

        // sinh(z) = (exp(z) - exp(-z)) / 2
        // cosh(z) = (exp(z) + exp(-z)) / 2
        // tanh(z) = (exp(z) - exp(-z)) / (exp(z) + exp(-z))

        // z1 = exp(z)
        scalar = Math.exp( z._re );
        re1 = scalar * Math.cos( z._im );
        im1 = scalar * Math.sin( z._im );

        // z2 = exp(-z)
        scalar = Math.exp( -z._re );
        re2 = scalar * Math.cos( -z._im );
        im2 = scalar * Math.sin( -z._im );

        // z3 = exp(z) - exp(-z)
        re3 = re1 - re2;
        im3 = im1 - im2;

        // z4 = exp(z) + exp(-z)
        re4 = re1 + re2;
        im4 = im1 + im2;

        // tanh(z) = z3 / z4
        final Complex z3 = complex( re3, im3 );
        result = div( z3, re4, im4 );

        return result;
    }

    /**
     * Real part of <tt>Complex</tt> number.
     */
    private double _re;

    /**
     * imaginary part of <tt>Complex</tt> number.
     */
    private double _im;

    /**
     * Constructs a <tt>Complex</tt> object representing the number zero.
     */
    public Complex() {
        _re = 0d;
        _im = 0d;
    }

    /**
     * Constructs a new <tt>Complex</tt> object from an existing
     * <tt>Complex</tt> object.
     *
     * @param z
     *            A <tt>Complex</tt> value
     */
    public Complex( final Complex z ) {
        _re = z._re;
        _im = z._im;
    }

    /**
     * Constructs a <tt>Complex</tt> from a real number. The imaginary part is
     * zero.
     * <p>
     *
     * @param re
     *            The real number
     * @see Complex#complex(double)
     */
    public Complex( final double re ) {
        _re = re;
        _im = 0d;
    }

    /**
     * Constructs a <tt>Complex</tt> object from real and imaginary parts.
     * <p>
     *
     * @param re
     *            Real part
     * @param im
     *            Imaginary part
     * @see Complex#complex(double, double)
     */
    public Complex( final double re, final double im ) {
        _re = re;
        _im = im;
    }

    /**
     * Returns the absolute value (magnitude, a.k.a. modulus) of a
     * <tt>Complex</tt> object.
     *
     * @return <tt>double</tt> containing the absolute value of the
     *         <tt>Complex</tt> number
     * @see Complex#abs(Complex)
     * @see Complex#arg()
     * @see Complex#arg(Complex)
     */
    public double abs() {
        final double absRe = Math.abs( _re );
        final double absIm = Math.abs( _im );

        // This algorithm avoids overflows that might otherwise occur when
        // evaluating Math.sqrt(re*re + im*im), and essentially pre-factors that
        // equation for safety.
        if ( ( absRe == 0d ) && ( absIm == 0d ) ) {
            return 0d;
        }
        else if ( absRe >= absIm ) {
            final double d = _im / _re;
            return absRe * Math.sqrt( 1d + ( d * d ) );
        }
        else {
            final double d = _re / _im;
            return absIm * Math.sqrt( 1d + ( d * d ) );
        }
    }

    /**
     * Returns the sum of two <tt>Complex</tt> values. To perform z1 + z2, write
     * <tt>z1.add(z2)</tt>. Note that this method returns a <tt>Complex</tt>
     * object, so calls to these methods can be chained. For example,
     *
     * <pre>
     * z1 + z2 + z3 == <tt> z1.add( z2 ).add( z3 ) </tt>
     *
     * </pre>
     *
     * @param z
     *            A Complex number
     * @return <tt>Complex</tt> sum z1 + z2
     * @see Complex#add(double)
     * @see Complex#sub(Complex)
     * @see Complex#sub(double)
     * @see Complex#mul(Complex)
     * @see Complex#mul(double)
     * @see Complex#div(Complex)
     * @see Complex#div(double)
     * @see Complex#sqr()
     */
    public Complex add( final Complex z ) {
        return complex( _re + z._re, _im + z._im );
    }

    /**
     * Returns the sum of a <tt>Complex</tt> and a <tt>double</tt> value. To
     * perform z1 + d2, write <tt>z1.add(d2)</tt>.
     *
     * @param d
     *            A <tt>double</tt> value
     * @return <tt>Complex</tt> sum z1 + d2
     * @see Complex#add(Complex)
     * @see Complex#sub(Complex)
     * @see Complex#sub(double)
     * @see Complex#mul(Complex)
     * @see Complex#mul(double)
     * @see Complex#div(Complex)
     * @see Complex#div(double)
     * @see Complex#sqr()
     */
    public Complex add( final double d ) {
        return complex( _re + d, _im );
    }

    /**
     * Returns the principal angle of a <tt>Complex</tt> number, in radians,
     * measured counter-clockwise from the real axis. <tt>arg()</tt> always
     * returns a <tt>double</tt> between -<tt><b>PI</b></tt> and +
     * <tt><b>PI</b></tt>.
     *
     * @return <tt>double</tt> containing the principal angle of the
     *         <tt>Complex</tt> value
     * @see Complex#arg(Complex)
     */
    public double arg() {
        return Math.atan2( _im, _re );
    }

    /**
     * Returns the value of the <i><b>real</b></i> part of the specified number
     * as a <code>byte</code>. This may involve rounding.
     *
     * @return the numeric value represented by the real part this object after
     *         conversion to type <code>byte</code>.
     */
    @Override
    public byte byteValue() {
        return ( byte ) Math.floor( _re + 0.5d );
    }

    /**
     * Compares two <code>Complex</code> values numerically according to their
     * absolute values.
     *
     * @param anotherComplex
     *            the <code>Complex</code> to be compared.
     * @return the value <code>0</code> if the absolute value of the argument
     *         <code>anotherComplex</code> is equal to the absolute value of
     *         this <code>Complex</code>; a value less than <code>0</code> if
     *         the absolute value of this <code>Complex</code> is numerically
     *         less than the <code>Complex</code> argument; and a the absolute
     *         value of this <code>Complex</code> is numerically greater than
     *         the <code>Complex</code> argument. (signed comparison).
     * @see java.lang.Comparable
     */
    @Override
    public int compareTo( final Complex anotherComplex ) {
        final double thisVal = abs();
        final double anotherVal = anotherComplex.abs();

        return ( thisVal < anotherVal ? -1 : ( thisVal == anotherVal ? 0 : 1 ) );
    }

    /**
     * Returns the <tt>Complex</tt> conjugate of a <tt>Complex</tt> object. The
     * <tt>Complex</tt> conjugate of a number is the complex number having the
     * same real component and the negative of the imaginary component.
     *
     * @return <tt>Complex</tt> conjugate.
     * @see Complex#conj(Complex)
     */
    public Complex conj() {
        return complex( _re, -_im );
    }

    /**
     * Returns the division of two <tt>Complex</tt> values. To perform z1 / z2,
     * write <tt>z1.div(z2)</tt>. Note that this method returns a
     * <tt>Complex</tt> object, so calls to these methods can be chained. For
     * example,
     *
     * <pre>
     * z1 * z2 / z3 == <tt> z1.mul( z2 ).div( z3 ) </tt>
     *
     * </pre>
     *
     * @param z
     *            A Complex number
     * @return <tt>Complex</tt> result of z1 / z2
     * @see Complex#add(Complex)
     * @see Complex#add(double)
     * @see Complex#sub(Complex)
     * @see Complex#sub(double)
     * @see Complex#mul(Complex)
     * @see Complex#mul(double)
     * @see Complex#div(double)
     * @see Complex#sqr()
     */
    public Complex div( final Complex z ) {
        return div( this, z._re, z._im );
    }

    /**
     * Returns the division of a <tt>Complex</tt> value by a <tt>double</tt>
     * value. To perform z1 / d2, write <tt>z1.div(d2)</tt>.
     *
     * @param d
     *            A <tt>double</tt> number
     * @return <tt>Complex</tt> result of z1 / d2
     * @see Complex#add(Complex)
     * @see Complex#add(double)
     * @see Complex#sub(Complex)
     * @see Complex#sub(double)
     * @see Complex#mul(Complex)
     * @see Complex#mul(double)
     * @see Complex#div(Complex)
     * @see Complex#sqr()
     */
    public Complex div( final double d ) {
        return complex( _re / d, _im / d );
    }

    /**
     * Returns the value of the <i><b>real</b></i> part of the specified number
     * as a <code>double</code>.
     *
     * @return the numeric value represented by the real part this object.
     */
    @Override
    public double doubleValue() {
        return _re;
    }

    /**
     * Decides if two <tt>Complex</tt> numbers are "sufficiently" alike to be
     * considered equal. This test should be used cautiously because of the
     * inexact nature of floating-point representations on computers. This test
     * takes the form <tt> z1.equals(z2)</tt>. The tolerance for this comparison
     * is scaled automatically based on the sizes of the numbers being compared.
     *
     * @param z
     *            A <tt>Complex</tt> number
     * @return <tt>true</tt> if equal; <tt>false</tt> otherwise
     * @see Complex#equals(Complex,double)
     */
    public boolean equals( final Complex z ) {
        final double tol1 = 1.0E-13 * abs( this );

        return ( Math.abs( _re - z._re ) <= tol1 ) && ( Math.abs( _im - z._im ) <= tol1 );
    }

    /**
     * Decides if two <tt>Complex</tt> numbers are "sufficiently" alike to be
     * considered equal. This test should be used cautiously because of the
     * inexact nature of floating-point representations on computers. This test
     * takes the form <tt> z1.equals(z2,tol)</tt>. The argument <tt>tol</tt> is
     * a scale factor used to determine the maximum magnitude of the difference
     * between them before they are considered <i>not</i> equal. This value is
     * multiplied by the absolute value of the first of the two numbers being
     * compared to make the tolerance relative to the magnitude of the numbers
     * being compared.
     *
     * @param z
     *            A <tt>Complex</tt> number
     * @param tol
     *            The tolerance with which to compare the two numbers
     * @return <tt>true</tt> if equal; <tt>false</tt> otherwise
     * @see Complex#equals(Complex)
     */
    public boolean equals( final Complex z, final double tol ) {
        final double tol1 = Math.abs( tol ) * abs( this );

        return ( Math.abs( _re - z._re ) <= tol1 ) && ( Math.abs( _im - z._im ) <= tol1 );
    }

    /**
     * Returns the value of the <i><b>real</b></i> part of the specified number
     * as a <code>float</code>. This may involve rounding.
     *
     * @return the numeric value represented by the real part this object after
     *         conversion to type <code>double</code>.
     */
    @Override
    public float floatValue() {
        return ( float ) _re;
    }

    /**
     * Extracts the imaginary part of a <tt>Complex</tt> object as a
     * <tt>double</tt>.
     *
     * @return <tt>double</tt> containing the imaginary part of the
     *         <tt>Complex</tt> object
     * @see Complex#im(Complex)
     */
    public double im() {
        return _im;
    }

    /**
     * Returns the value of the <i><b>real</b></i> part of the specified number
     * as an <code>int</code>. This may involve rounding.
     *
     * @return the numeric value represented by the real part this object after
     *         conversion to type <code>int</code>.
     */
    @Override
    public int intValue() {
        return ( int ) Math.floor( _re + 0.5d );
    }

    /**
     * Returns the reciprocal of a <tt>Complex</tt> number (1/z).
     *
     * @return The <tt>Complex</tt> reciprocal
     */
    public Complex inv() {
        return inv( this );
    }

    /**
     * Returns <tt>true</tt> if either the real or imaginary component of this
     * <tt>Complex</tt> has an infinite value.
     *
     * @return <tt>true</tt> if either component of the <tt>Complex</tt> object
     *         is infinite; <tt>false</tt>, otherwise.
     */
    public boolean isInfinite() {
        return ( Double.isInfinite( _re ) || Double.isInfinite( _im ) );
    }

    /**
     * Returns <tt>true</tt> if either the real or the imaginary component of
     * this <tt>Complex</tt> is a Not-a-Number (<tt>NaN</tt>) value.
     *
     * @return <tt>true</tt> if either component of the <tt>Complex</tt> object
     *         is <tt>NaN</tt>; <tt>false</tt>, otherwise.
     */
    public boolean isNaN() {
        return ( Double.isNaN( _re ) || Double.isNaN( _im ) );
    }

    /**
     * Returns the value of the <i><b>real</b></i> part of the specified number
     * as a <code>long</code>. This may involve rounding.
     *
     * @return the numeric value represented by the real part this object after
     *         conversion to type <code>long</code>.
     */
    @Override
    public long longValue() {
        return ( long ) Math.floor( _re + 0.5d );
    }

    /**
     * Returns the product of two <tt>Complex</tt> values. To perform z1 * z2,
     * write <tt>z1.mul(z2)</tt>. Note that this method returns a
     * <tt>Complex</tt> object, so calls to these methods can be chained. For
     * example,
     *
     * <pre>
     * z1 * z2 / z3 == <tt> z1.mul( z2 ).div( z3 ) </tt>
     *
     * </pre>
     *
     * @param z
     *            A Complex number
     * @return <tt>Complex</tt> product z1 * z2
     * @see Complex#add(Complex)
     * @see Complex#add(double)
     * @see Complex#sub(Complex)
     * @see Complex#sub(double)
     * @see Complex#mul(double)
     * @see Complex#div(Complex)
     * @see Complex#div(double)
     * @see Complex#sqr()
     */
    public Complex mul( final Complex z ) {
        return complex( ( _re * z._re ) - ( _im * z._im ), ( _re * z._im ) + ( _im * z._re ) );
    }

    /**
     * Returns the product of a <tt>Complex</tt> and a <tt>double</tt> value. To
     * perform z1 * d2, write <tt>z1.mul(d2)</tt>.
     *
     * @param d
     *            A <tt>double</tt> value
     * @return <tt>Complex</tt> product z1 * d2
     * @see Complex#add(Complex)
     * @see Complex#add(double)
     * @see Complex#sub(Complex)
     * @see Complex#sub(double)
     * @see Complex#mul(Complex)
     * @see Complex#div(Complex)
     * @see Complex#div(double)
     * @see Complex#sqr()
     */
    public Complex mul( final double d ) {
        return complex( _re * d, _im * d );
    }

    /**
     * Returns the negative of a <tt>Complex</tt> value.
     *
     * @return Negative of <tt>Complex</tt> value
     */
    public Complex neg() {
        return complex( -_re, -_im );
    }

    /**
     * Returns the phase of a <tt>Complex</tt> object. Note that this method
     * duplicates the functionality of the <tt>arg()</tt> method.
     *
     * @return Phase of <tt>Complex</tt> object in <i> radians</i>
     */
    public double phase() {
        return phase( this );
    }

    /**
     * Returns the <tt>Complex</tt> base raised to the power of the
     * <tt>Complex</tt> exponent.
     *
     * @param exponent
     *            The <tt>Complex</tt> exponent
     * @return <tt>Complex</tt> base raised to the power of the <tt>Complex</tt>
     *         exponent.
     * @see Complex#pow(double)
     * @see Complex#pow(Complex, double)
     * @see Complex#pow(double, Complex)
     * @see Complex#pow(Complex, Complex)
     */
    public Complex pow( final Complex exponent ) {
        // return exp( exponent * log(this) );

        final double real = Math.log( abs() );
        final double imag = arg();

        final double re2 = ( real * exponent._re ) - ( imag * exponent._im );
        final double im2 = ( real * exponent._im ) + ( imag * exponent._re );

        final double scalar = Math.exp( re2 );

        return complex( scalar * Math.cos( im2 ), scalar * Math.sin( im2 ) );
    }

    /**
     * Returns the <tt>Complex</tt> value raised to the power of the
     * <tt>double</tt> exponent.
     *
     * @param exponent
     *            The <tt>double</tt> exponent
     * @return <tt>Complex</tt> base raised to the power of the exponent.
     * @see Complex#pow(Complex)
     * @see Complex#pow(Complex, double)
     * @see Complex#pow(double, Complex)
     * @see Complex#pow(Complex, Complex)
     */
    public Complex pow( final double exponent ) {
        // return exp( exponent * log( this ) );

        final double real = exponent * Math.log( abs() );
        final double imag = exponent * arg();

        final double scalar = Math.exp( real );

        return complex( scalar * Math.cos( imag ), scalar * Math.sin( imag ) );
    }

    /**
     * Extracts the real part of a <tt>Complex</tt> object as a <tt>double</tt>.
     *
     * @return <tt>double</tt> containing the real part of the <tt>Complex</tt>
     *         object
     * @see Complex#re(Complex)
     */
    public double re() {
        return _re;
    }

    /**
     * Sets a <tt>double</tt> value into the imaginary part of a
     * <tt>Complex</tt> object.
     * 
     * @param pIm
     *            The value to use to set this complex number's imaginary part
     *
     * @see Complex#im()
     * @see Complex#setRe(double)
     * @see Complex#setZ(double,double)
     */
    public void setIm( final double pIm ) {
        _im = pIm;
    }

    /**
     * Sets a <tt>double</tt> value into the real part of a <tt>Complex</tt>
     * object.
     * 
     * @param pRe
     *            The value to use to set this complex number's real part
     *
     * @see Complex#re()
     * @see Complex#setIm(double)
     * @see Complex#setZ(double,double)
     */
    public void setRe( final double pRe ) {
        _re = pRe;
    }

    /**
     * Sets two <tt>double</tt> values into the real and imaginary parts of a
     * <tt>Complex</tt> object.
     * 
     * @param pRe
     *            The value to use for setting the real part of this Complex
     *            number
     * @param pIm
     *            The value to use for setting the imaginary part of this
     *            Complex number
     *
     * @see Complex#re()
     * @see Complex#setRe(double)
     * @see Complex#setIm(double)
     */
    public void setZ( final double pRe, final double pIm ) {
        _re = pRe;
        _im = pIm;
    }

    /**
     * Returns the value of the <i><b>real</b></i> part of the specified number
     * as a <code>short</code>. This may involve rounding.
     *
     * @return the numeric value represented by the real part this object after
     *         conversion to type <code>short</code>.
     */
    @Override
    public short shortValue() {
        return ( short ) Math.floor( _re + 0.5d );
    }

    /**
     * Returns the square of the current <tt>Complex</tt> value.
     *
     * @return <tt>Complex</tt> square z1 * z1
     * @see Complex#add(Complex)
     * @see Complex#add(double)
     * @see Complex#sub(Complex)
     * @see Complex#sub(double)
     * @see Complex#mul(Complex)
     * @see Complex#mul(double)
     * @see Complex#div(Complex)
     * @see Complex#div(double)
     */
    public Complex sqr() {
        return complex( MathExt.sqr( _re ) - MathExt.sqr( _im ), 2d * ( _re * _im ) );
    }

    /**
     * Returns the difference of two <tt>Complex</tt> values. To perform z1 -
     * z2, write <tt>z1.sub(z2)</tt>. Note that this method returns a
     * <tt>Complex</tt> object, so calls to these methods can be chained. For
     * example,
     *
     * <pre>
     * z1 - z2 + z3 == <tt> z1.sub( z2 ).add( z3 ) </tt>
     *
     * </pre>
     *
     * @param z
     *            A Complex number
     * @return <tt>Complex</tt> difference z1 - z2
     * @see Complex#add(Complex)
     * @see Complex#add(double)
     * @see Complex#sub(double)
     * @see Complex#mul(Complex)
     * @see Complex#mul(double)
     * @see Complex#div(Complex)
     * @see Complex#div(double)
     * @see Complex#sqr()
     */
    public Complex sub( final Complex z ) {
        return complex( _re - z._re, _im - z._im );
    }

    /**
     * Returns the difference between a <tt>Complex</tt> and a <tt>double</tt>
     * value. To perform z1 - d2, write <tt>z1.sub(d2)</tt>.
     *
     * @param d
     *            A <tt>double</tt> value
     * @return <tt>Complex</tt> difference z1 - d2
     * @see Complex#add(Complex)
     * @see Complex#add(double)
     * @see Complex#sub(Complex)
     * @see Complex#mul(Complex)
     * @see Complex#mul(double)
     * @see Complex#div(Complex)
     * @see Complex#div(double)
     * @see Complex#sqr()
     */
    public Complex sub( final double d ) {
        return complex( _re - d, _im );
    }

    /**
     * Converts a <tt>Complex</tt> value into a <tt>String</tt> of the form
     * <tt>(</tt><i>re</i><tt> + i</tt><i>im</i><tt>)</tt>.
     *
     * @return <tt>String</tt> containing the rectangular coordinate
     *         representation of the number
     */
    @Override
    public String toString() {
        if ( _im < 0d ) {
            return ( "(" + _re + " - i " + ( -_im ) + ")" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        }
        else if ( ( 1d / _im ) == Double.NEGATIVE_INFINITY ) {
            return ( "(" + _re + " + i " + 0d + ")" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        }
        else {
            return ( "(" + _re + " + i " + ( +_im ) + ")" ); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
        }
    }

}
