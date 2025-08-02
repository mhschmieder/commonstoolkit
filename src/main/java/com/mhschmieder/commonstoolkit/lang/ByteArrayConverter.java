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
 * This file is part of the CommonsToolkit Library
 *
 * You should have received a copy of the MIT License along with the
 * CommonsToolkit Library. If not, see <https://opensource.org/licenses/MIT>.
 *
 * Project: https://github.com/mhschmieder/commonstoolkit
 */
package com.mhschmieder.commonstoolkit.lang;

/**
 * NOTE: This was originally written as a tool to use with another toolkit
 *  that was produced in Spain, so some of the code remains in Spanish.
 * TODO: Replace with Endian utilities in Apache Commons I/O?
 */
public class ByteArrayConverter {

    private static final int BYTE_MASK = 0xFF;

    /**
     * Convierte un array de bytes a un float respetando el orden asignado
     *
     * @param valor
     *            array de bytes a convertir
     * @param endianness
     *            orden a utilizar en la conversion
     * @return valor equivalente despues de la conversion
     */
    public static float byteArrayToFloat( final byte[] valor, final EndianOrder endianness ) {
        final int enteroEquivalente = byteArrayToInt( valor, endianness );
        return Float.intBitsToFloat( enteroEquivalente );
    }

    /**
     * Convierte un array de bytes a un int respetando el orden asignado
     *
     * @param valor
     *            array de bytes a convertir
     * @param endianness
     *            orden a utilizar en la conversion
     * @return valor equivalente despues de la conversion
     */
    public static int byteArrayToInt( final byte[] valor, final EndianOrder endianness ) {
        if ( valor.length < 4 ) {
            throw new ArrayIndexOutOfBoundsException( valor.length );
        }
        int a, b, c, d;
        if ( endianness == EndianOrder.BIG_ENDIAN ) {
            a = ( valor[ 0 ] & BYTE_MASK ) << 24;
            b = ( valor[ 1 ] & BYTE_MASK ) << 16;
            c = ( valor[ 2 ] & BYTE_MASK ) << 8;
            d = valor[ 3 ] & BYTE_MASK;
        }
        else {
            a = ( valor[ 3 ] & BYTE_MASK ) << 24;
            b = ( valor[ 2 ] & BYTE_MASK ) << 16;
            c = ( valor[ 1 ] & BYTE_MASK ) << 8;
            d = valor[ 0 ] & BYTE_MASK;
        }
        return a | b | c | d;
    }

    /**
     * Convierte un array de bytes a un long respetando el orden asignado
     *
     * @param valor
     *            array de bytes a convertir
     * @param endianness
     *            orden a utilizar en la conversion
     * @return valor equivalente despues de la conversion
     */
    public static long byteArrayToLong( final byte[] valor, final EndianOrder endianness ) {
        if ( valor.length < 8 ) {
            throw new ArrayIndexOutOfBoundsException( valor.length );
        }
        long a, b, c, d, e, f, g, h;
        if ( endianness == EndianOrder.BIG_ENDIAN ) {
            a = ( long ) ( valor[ 0 ] & BYTE_MASK ) << 56;
            b = ( long ) ( valor[ 1 ] & BYTE_MASK ) << 48;
            c = ( long ) ( valor[ 2 ] & BYTE_MASK ) << 40;
            d = ( long ) ( valor[ 3 ] & BYTE_MASK ) << 32;
            e = ( long ) ( valor[ 4 ] & BYTE_MASK ) << 24;
            f = ( long ) ( valor[ 5 ] & BYTE_MASK ) << 16;
            g = ( long ) ( valor[ 6 ] & BYTE_MASK ) << 8;
            h = valor[ 7 ] & BYTE_MASK;
        }
        else {
            a = ( long ) ( valor[ 7 ] & BYTE_MASK ) << 56;
            b = ( long ) ( valor[ 6 ] & BYTE_MASK ) << 48;
            c = ( long ) ( valor[ 5 ] & BYTE_MASK ) << 40;
            d = ( long ) ( valor[ 4 ] & BYTE_MASK ) << 32;
            e = ( long ) ( valor[ 3 ] & BYTE_MASK ) << 24;
            f = ( long ) ( valor[ 2 ] & BYTE_MASK ) << 16;
            g = ( long ) ( valor[ 1 ] & BYTE_MASK ) << 8;
            h = valor[ 0 ] & BYTE_MASK;
        }
        return a | b | c | d | e | f | g | h;
    }

    /**
     * Convierte un array de bytes a un short respetando el orden asignado
     *
     * @param valor
     *            array de bytes a convertir
     * @param endianness
     *            orden a utilizar en la conversion
     * @return valor equivalente despues de la conversion
     */
    public static short byteArrayToShort( final byte[] valor, final EndianOrder endianness ) {
        if ( valor.length < 2 ) {
            throw new ArrayIndexOutOfBoundsException( valor.length );
        }
        short a, b;
        if ( endianness == EndianOrder.BIG_ENDIAN ) {
            a = ( short ) ( ( valor[ 0 ] & BYTE_MASK ) << 8 );
            b = ( short ) ( valor[ 1 ] & BYTE_MASK );
        }
        else {
            a = ( short ) ( ( valor[ 1 ] & BYTE_MASK ) << 8 );
            b = ( short ) ( valor[ 0 ] & BYTE_MASK );
        }
        return ( short ) ( a | b );
    }

    /**
     * Convierte double a un byte array respetando el orden asignado
     *
     * @param valor
     *            valor a convertir
     * @param endianness
     *            orden a utilizar en la conversion
     * @return array de bytes equivalentes a valor
     */
    public static byte[] doubleToByteArray( final double valor, final EndianOrder endianness ) {
        final long longEquivalente = Double.doubleToLongBits( valor );
        return longToByteArray( longEquivalente, endianness );
    }

    /**
     * Convierte float a un byte array respetando el orden asignado
     *
     * @param valor
     *            valor a convertir
     * @param endianness
     *            orden a utilizar en la conversion
     * @return array de bytes equivalentes a valor
     */
    public static byte[] floatToByteArray( final float valor, final EndianOrder endianness ) {
        final int enteroEquivalente = Float.floatToIntBits( valor );
        return intToByteArray( enteroEquivalente, endianness );
    }

    /**
     * Convierte int a un byte array respetando el orden asignado
     *
     * @param valor
     *            valor a convertir
     * @param endianness
     *            orden a utilizar en la conversion
     * @return array de bytes equivalentes a valor
     */
    public static byte[] intToByteArray( final int valor, final EndianOrder endianness ) {
        byte[] resultado;
        final byte b3 = ( byte ) ( ( valor >> 24 ) & BYTE_MASK );
        final byte b2 = ( byte ) ( ( valor >> 16 ) & BYTE_MASK );
        final byte b1 = ( byte ) ( ( valor >> 8 ) & BYTE_MASK );
        final byte b0 = ( byte ) ( valor & BYTE_MASK );
        if ( endianness == EndianOrder.BIG_ENDIAN ) {
            resultado = new byte[] { b3, b2, b1, b0 };
        }
        else {
            resultado = new byte[] { b0, b1, b2, b3 };
        }
        return resultado;
    }

    /**
     * Convierte long a un byte array respetando el orden asignado
     *
     * @param valor
     *            valor a convertir
     * @param endianness
     *            orden a utilizar en la conversion
     * @return array de bytes equivalentes a valor
     */
    public static byte[] longToByteArray( final long valor, final EndianOrder endianness ) {
        byte[] resultado;
        final byte b7 = ( byte ) ( ( valor >> 56 ) & BYTE_MASK );
        final byte b6 = ( byte ) ( ( valor >> 48 ) & BYTE_MASK );
        final byte b5 = ( byte ) ( ( valor >> 40 ) & BYTE_MASK );
        final byte b4 = ( byte ) ( ( valor >> 32 ) & BYTE_MASK );
        final byte b3 = ( byte ) ( ( valor >> 24 ) & BYTE_MASK );
        final byte b2 = ( byte ) ( ( valor >> 16 ) & BYTE_MASK );
        final byte b1 = ( byte ) ( ( valor >> 8 ) & BYTE_MASK );
        final byte b0 = ( byte ) ( valor & BYTE_MASK );
        if ( endianness == EndianOrder.BIG_ENDIAN ) {
            resultado = new byte[] { b7, b6, b5, b4, b3, b2, b1, b0 };
        }
        else {
            resultado = new byte[] { b0, b1, b2, b3, b4, b5, b6, b7 };
        }
        return resultado;
    }

    /**
     * Convierte short a un byte array respetando el orden asignado
     *
     * @param valor
     *            valor a convertir
     * @param endianness
     *            orden a utilizar en la conversion
     * @return array de bytes equivalentes a valor
     */
    public static byte[] shortToByteArray( final short valor, final EndianOrder endianness ) {
        byte[] resultado;
        final byte b1 = ( byte ) ( ( valor >> 8 ) & BYTE_MASK );
        final byte b0 = ( byte ) ( valor & BYTE_MASK );
        if ( endianness == EndianOrder.BIG_ENDIAN ) {
            resultado = new byte[] { b1, b0 };
        }
        else {
            resultado = new byte[] { b0, b1 };
        }
        return resultado;
    }

    /**
     * Convierte un array de bytes a un double respetando el orden asignado
     *
     * @param valor
     *            array de bytes a convertir
     * @param endianness
     *            orden a utilizar en la conversion
     * @return valor equivalente despues de la conversion
     */
    public double byteArrayToDouble( final byte[] valor, final EndianOrder endianness ) {
        final long longEquivalente = byteArrayToLong( valor, endianness );
        return Double.longBitsToDouble( longEquivalente );
    }

}