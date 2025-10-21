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
package com.mhschmieder.jcommons.io;

/**
 * TODO: Replace with Endian utilities in Apache Commons I/O?
 */
public class ByteArrayConverter {

    private static final int BYTE_MASK = 0xFF;

    /**
     * Converts an array of bytes to a short, respective to Endianness.
     *
     * @param value
     *            array of bytes to convert
     * @param endianness
     *            the Endianness to apply to byte order during conversion
     * @return a short value equivalent to the array prior to conversion
     */
    public static short byteArrayToShort( final byte[] value, 
                                          final Endianness endianness ) {
        if ( value.length < 2 ) {
            throw new ArrayIndexOutOfBoundsException( value.length );
        }
        
        short a, b;
        final String byteOrder = endianness.toByteOrderString();
        switch ( byteOrder ) {
            case "LITTLE_ENDIAN":
                a = ( short ) ( ( value[ 1 ] & BYTE_MASK ) << 8 );
                b = ( short ) ( value[ 0 ] & BYTE_MASK );
               break;
            case "BIG_ENDIAN":
                a = ( short ) ( ( value[ 0 ] & BYTE_MASK ) << 8 );
                b = ( short ) ( value[ 1 ] & BYTE_MASK );
                break;
            default:
                a = 0;
                b = 0;
                break;
        }

        return ( short ) ( a | b );
    }

    /**
     * Converts an array of bytes to an int, respective to Endianness.
     *
     * @param value
     *            array of bytes to convert
     * @param endianness
     *            the Endianness to apply to byte order during conversion
     * @return an int value equivalent to the array prior to conversion
     */
    public static int byteArrayToInt( final byte[] value, 
                                      final Endianness endianness ) {
        if ( value.length < 4 ) {
            throw new ArrayIndexOutOfBoundsException( value.length );
        }
        
        int a, b, c, d;
        final String byteOrder = endianness.toByteOrderString();
        switch ( byteOrder ) {
            case "LITTLE_ENDIAN":
                a = ( value[ 3 ] & BYTE_MASK ) << 24;
                b = ( value[ 2 ] & BYTE_MASK ) << 16;
                c = ( value[ 1 ] & BYTE_MASK ) << 8;
                d = value[ 0 ] & BYTE_MASK;
               break;
            case "BIG_ENDIAN":
                a = ( value[ 0 ] & BYTE_MASK ) << 24;
                b = ( value[ 1 ] & BYTE_MASK ) << 16;
                c = ( value[ 2 ] & BYTE_MASK ) << 8;
                d = value[ 3 ] & BYTE_MASK;
                break;
            default:
                a = 0;
                b = 0;
                c = 0;
                d = 0;
                break;
        }

        return a | b | c | d;
    }

    /**
     * Converts an array of bytes to a long, respective to Endianness.
     *
     * @param value
     *            array of bytes to convert
     * @param endianness
     *            the Endianness to apply to byte order during conversion
     * @return a long value equivalent to the array prior to conversion
     */
    public static long byteArrayToLong( final byte[] value, 
                                        final Endianness endianness ) {
        if ( value.length < 8 ) {
            throw new ArrayIndexOutOfBoundsException( value.length );
        }
        
        long a, b, c, d, e, f, g, h;
        final String byteOrder = endianness.toByteOrderString();
        switch ( byteOrder ) {
            case "LITTLE_ENDIAN":
                a = ( long ) ( value[ 7 ] & BYTE_MASK ) << 56;
                b = ( long ) ( value[ 6 ] & BYTE_MASK ) << 48;
                c = ( long ) ( value[ 5 ] & BYTE_MASK ) << 40;
                d = ( long ) ( value[ 4 ] & BYTE_MASK ) << 32;
                e = ( long ) ( value[ 3 ] & BYTE_MASK ) << 24;
                f = ( long ) ( value[ 2 ] & BYTE_MASK ) << 16;
                g = ( long ) ( value[ 1 ] & BYTE_MASK ) << 8;
                h = value[ 0 ] & BYTE_MASK;
               break;
            case "BIG_ENDIAN":
                a = ( long ) ( value[ 0 ] & BYTE_MASK ) << 56;
                b = ( long ) ( value[ 1 ] & BYTE_MASK ) << 48;
                c = ( long ) ( value[ 2 ] & BYTE_MASK ) << 40;
                d = ( long ) ( value[ 3 ] & BYTE_MASK ) << 32;
                e = ( long ) ( value[ 4 ] & BYTE_MASK ) << 24;
                f = ( long ) ( value[ 5 ] & BYTE_MASK ) << 16;
                g = ( long ) ( value[ 6 ] & BYTE_MASK ) << 8;
                h = value[ 7 ] & BYTE_MASK;
                break;
            default:
                a = 0;
                b = 0;
                c = 0;
                d = 0;
                e = 0;
                f = 0;
                g = 0;
                h = 0;
                break;
        }

        return a | b | c | d | e | f | g | h;
    }

    /**
     * Converts an array of bytes to a float, respective to Endianness.
     *
     * @param value
     *            array of bytes to convert
     * @param endianness
     *            the Endianness to apply to byte order during conversion
     * @return a float value equivalent to the array prior to conversion
     */
    public static float byteArrayToFloat( final byte[] value, 
                                          final Endianness endianness ) {
        final int intEquivalent = byteArrayToInt( value, endianness );
        return Float.intBitsToFloat( intEquivalent );
    }

    /**
     * Converts an array of bytes to a double, respective to Endianness.
     *
     * @param value
     *            array of bytes to convert
     * @param endianness
     *            the Endianness to apply to byte order during conversion
     * @return a double value equivalent to the array prior to conversion
     */
    public double byteArrayToDouble( final byte[] value, 
                                     final Endianness endianness ) {
        final long longEquivalent = byteArrayToLong( value, endianness );
        return Double.longBitsToDouble( longEquivalent );
    }

    /**
     * Converts a short to an array of bytes, respective to Endianness.
     *
     * @param value
     *            short value to convert
     * @param endianness
     *            the Endianness to apply to byte order during conversion
     * @return an array of bytes equivalent to the short prior to conversion
     */
    public static byte[] shortToByteArray( final short value, 
                                           final Endianness endianness ) {
        byte[] result;
        final byte b1 = ( byte ) ( ( value >> 8 ) & BYTE_MASK );
        final byte b0 = ( byte ) ( value & BYTE_MASK );
        
        final String byteOrder = endianness.toByteOrderString();
        switch ( byteOrder ) {
            case "LITTLE_ENDIAN":
                result = new byte[] { b0, b1 };
                break;
            case "BIG_ENDIAN":
                result = new byte[] { b1, b0 };
                break;
            default:
                result = new byte[] { 0, 0 };
                break;
        }
        
        return result;
    }

    /**
     * Converts an int to an array of bytes, respective to Endianness.
     *
     * @param value
     *            int value to convert
     * @param endianness
     *            the Endianness to apply to byte order during conversion
     * @return an array of bytes equivalent to the int prior to conversion
     */
    public static byte[] intToByteArray( final int value, 
                                         final Endianness endianness ) {
        byte[] result;
        final byte b3 = ( byte ) ( ( value >> 24 ) & BYTE_MASK );
        final byte b2 = ( byte ) ( ( value >> 16 ) & BYTE_MASK );
        final byte b1 = ( byte ) ( ( value >> 8 ) & BYTE_MASK );
        final byte b0 = ( byte ) ( value & BYTE_MASK );
        
        final String byteOrder = endianness.toByteOrderString();
        switch ( byteOrder ) {
            case "LITTLE_ENDIAN":
                result = new byte[] { b0, b1, b2, b3 };
                break;
            case "BIG_ENDIAN":
                result = new byte[] { b3, b2, b1, b0 };
                break;
            default:
                result = new byte[] { 0, 0, 0, 0 };
                break;
        }
        
        return result;
    }

    /**
     * Converts a long to an array of bytes, respective to Endianness.
     *
     * @param value
     *            long value to convert
     * @param endianness
     *            the Endianness to apply to byte order during conversion
     * @return an array of bytes equivalent to the long prior to conversion
     */
    public static byte[] longToByteArray( final long value, 
                                          final Endianness endianness ) {
        byte[] result;
        final byte b7 = ( byte ) ( ( value >> 56 ) & BYTE_MASK );
        final byte b6 = ( byte ) ( ( value >> 48 ) & BYTE_MASK );
        final byte b5 = ( byte ) ( ( value >> 40 ) & BYTE_MASK );
        final byte b4 = ( byte ) ( ( value >> 32 ) & BYTE_MASK );
        final byte b3 = ( byte ) ( ( value >> 24 ) & BYTE_MASK );
        final byte b2 = ( byte ) ( ( value >> 16 ) & BYTE_MASK );
        final byte b1 = ( byte ) ( ( value >> 8 ) & BYTE_MASK );
        final byte b0 = ( byte ) ( value & BYTE_MASK );
        
        final String byteOrder = endianness.toByteOrderString();
        switch ( byteOrder ) {
            case "LITTLE_ENDIAN":
                result = new byte[] { b0, b1, b2, b3, b4, b5, b6, b7 };
                break;
            case "BIG_ENDIAN":
                result = new byte[] { b7, b6, b5, b4, b3, b2, b1, b0 };
                break;
            default:
                result = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0 };
                break;
        }
        
        return result;
    }

    /**
     * Converts a float to an array of bytes, respective to Endianness.
     *
     * @param value
     *            float value to convert
     * @param endianness
     *            the Endianness to apply to byte order during conversion
     * @return an array of bytes equivalent to the float prior to conversion
     */
    public static byte[] floatToByteArray( final float value, 
                                           final Endianness endianness ) {
        final int intEquivalent = Float.floatToIntBits( value );
        return intToByteArray( intEquivalent, endianness );
    }

    /**
     * Converts a double to an array of bytes, respective to Endianness.
     *
     * @param value
     *            double value to convert
     * @param endianness
     *            the Endianness to apply to byte order during conversion
     * @return an array of bytes equivalent to the double prior to conversion
     */
    public static byte[] doubleToByteArray( final double value, 
                                            final Endianness endianness ) {
        final long longEquivalent = Double.doubleToLongBits( value );
        return longToByteArray( longEquivalent, endianness );
    }
}