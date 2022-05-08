/**
 * MIT License
 *
 * Copyright (c) 2022 Mark Schmieder
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
 * Utilities for packing and unpacking bit arrays; in particular, when not
 * divisible by eight and thus not representable using ByteBuffer or byte array.
 */
public class BitPackUtilities {

    /**
     * The default constructor is disabled, as this is a static utilities class.
     */
    private BitPackUtilities() {}

    protected static char mask[]    = { 0x00, 0x80, 0xc0, 0xf0, 0xf8, 0xfc, 0xfe };
    protected static char notmask[] = { 0xff, 0x7f, 0x3f, 0x1f, 0x0f, 0x07, 0x03, 0x01 };

    // NOTE: The C code from which this is borrowed, works with unsigned longs,
    // but Java does not have unsigned types and can't use longs as indices.
    // TODO: Improve and correct code comments as understanding deepens.
    public static long bitUnpack( char[] buffer, int startIndex, int numberOfBits ) {
        int startByte;
        int endByte;
        int startBit;
        int endBit;
        int bitIndex;
        long value;

        bitIndex = startIndex + numberOfBits;

        // Right shift the start and end by 3 bits. This is the same as dividing
        // by 8 but is faster. This computes the start and end bytes for the
        // field.
        startByte = startIndex >> 3;
        endByte = bitIndex >> 3;

        // Apply Logical AND to the start and end positions using 7. This is the
        // same as doing a modulation with 8 but is faster. This computes the
        // start and end bits within the start and end bytes for the field.
        startBit = startIndex & 7;
        endBit = bitIndex & 7;

        // Compute the number of bytes covered by the bit array field.
        bitIndex = endByte - startByte - 1;

        // If the value is stored in one byte, retrieve it.
        if ( startByte == endByte ) {
            // Mask anything prior to the start bit and after the end bit.
            value = buffer[ startByte ] & ( notmask[ startBit ] & mask[ endBit ] );

            // Shift the value to the right, still safely within a long.
            value >>= ( 8 - endBit );
        }
        // If the value covers more than 1 byte, retrieve it.
        else {
            // Mask data prior to the start bit of the first byte, and shift to
            // the left by the necessary amount.
            value = ( buffer[ startByte++ ] & notmask[ startBit ] ) << ( numberOfBits
                    - ( 8 - startBit ) );

            // Loop while decrementing the bit index.
            // NOTE: The original C code was unnecessarily confusing in its
            // syntax: "while(bitIndex--)". It is better to make the order of
            // condition evaluation, and variable modification, explicit, rather
            // than depending on side effects and making the code logic obtuse.
            while ( bitIndex > 0 ) {
                // Decrement the bit index before using it. This needs further
                // verification even though this should be an exact logical
                // match for the original C code, as it seems strange that the
                // previous setting of the bit index isn't ready for first use
                // in the shifting operations.
                bitIndex--;

                // Get the next 8 bits from the buffer.
                // TODO: Verify operator order and add parentheses.
                value += buffer[ startByte++ ] << ( ( bitIndex << 3 ) + endBit );
            }

            // For the last byte, we mask anything after the end bit, then shift
            // to the right (8 - endBit) bits.
            // TODO: Verify operator order and add parentheses.
            value += ( buffer[ startByte ] & mask[ endBit ] ) >> ( 8 - endBit );
        }

        return value;
    }

}
