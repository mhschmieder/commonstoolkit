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
 * <p>
 * NOTE: The C code from which these are borrowed, works with unsigned longs,
 * but Java does not have unsigned types and also can't use longs as indices.
 * <p>
 * TODO: Work around the issue of needing char vs. byte strictly due to the
 * masks, as this might be a limitation of specifying hex based values. We
 * need 8 bits, not 7 bits, but 16 (as for a char) messes up the bit packing.
 * <p>
 * TODO: Improve and correct code comments and labels as understanding deepens.
 */
public class BitPackUtilities {

    /**
     * The default constructor is disabled, as this is a static utilities class.
     */
    private BitPackUtilities() {}

    protected static char mask[]    = { 0x00, 0x80, 0xc0, 0xe0, 0xf0, 0xf8, 0xfc, 0xfe };
    protected static char notmask[] = { 0xff, 0x7f, 0x3f, 0x1f, 0x0f, 0x07, 0x03, 0x01 };

    /**
     * Packs a long value into 'numberOfPackedBits' consecutive bits in a
     * supplied buffer, starting at a given bit position.
     * <p>
     * The buffer must be allocated correctly by the caller, if they pass it in.
     * If {@code null}, a new buffer is allocated of sufficient size to store
     * the packed bits. The buffer is returned regardless, for more flexibility.
     * <p>
     * TODO: Check the number of bits against the size of a long as well.
     * 
     * @param packedBuffer
     *            The buffer to use for storing the packed bits
     * @param startBitIndex
     *            The initial offset into the packed buffer
     * @param numberOfPackedBits
     *            The number of bits to retrieve from the buffer
     * @param value
     *            The long value to pack into consecutive bits
     * @return The buffer containing the packed bits
     * @throws IllegalArgumentException
     *             If the buffer is smaller than the stated number of bits
     */
    public static char[] bitPack( char[] packedBuffer,
                                  int startBitIndex,
                                  int numberOfPackedBits,
                                  long value )
            throws IllegalArgumentException {
        // As the buffer size is dictated by the caller, it is the caller's
        // responsibility to ensure that its size is large enough to cover
        // the stated number of packed bits, if they provided the buffer.
        if ( ( packedBuffer != null ) && ( packedBuffer.length < numberOfPackedBits ) ) {
            throw new IllegalArgumentException( "bitPack: buffer size is smaller than the stated number of packed bits" );
        }

        // If the user didn't pass in a buffer, allocate a new one of sufficient
        // size to contain the stated number of packed bits.
        // TODO: Verify that the resulting buffer is large enough when the
        // number of bits is not precisely divisible by 16.
        final char[] packedBits = ( packedBuffer != null )
            ? packedBuffer
            : new char[ numberOfPackedBits % 16 ];

        // Bits are packed from right to left, in BIG ENDIAN order.
        int bitIndex = startBitIndex + numberOfPackedBits;

        // Right shift the start and end by 3 bits. This is the same as dividing
        // by 8 but is faster. This computes the buffer's start and end bytes.
        int startByte = startBitIndex >> 3;
        int endByte = bitIndex >> 3;

        // Apply Logical AND to the start and end positions using 7. This is the
        // same as doing a modulation with 8 but is faster. This computes the
        // start and end bits within the buffer's start and end bytes.
        int startBit = startBitIndex & 7;
        int endBit = bitIndex & 7;

        // Compute the number of bytes covered by the packed buffer.
        bitIndex = endByte - startByte - 1;

        // If the start byte equals the end byte, the value is stored in 1 byte.
        if ( startByte == endByte ) {
            // Mask anything prior to the start bit and after the end bit, in
            // order to not corrupt data that has already been stored there.
            packedBits[ startByte ] &= ( mask[ startBit ] | notmask[ endBit ] );

            // Shift the value to the left, past the end bit.
            long valueShifted = value << ( 8 - endBit );

            // Mask anything in the left-shifted value that is prior to the
            // start bit and after the end bit.
            packedBits[ startByte ] |= ( valueShifted & ( notmask[ startBit ] | mask[ endBit ] ) );
        }
        else {
            // Mask data prior to the start bit of the first byte, then shift to
            // the right by the necessary amount.
            packedBits[ startByte ] &= mask[ startBit ];
            long valueShifted = value >> ( numberOfPackedBits - ( 8 - startBit ) );

            // Get the upper bits of the right-shifted value and mask anything
            // prior to the start bit.
            packedBits[ startByte ] |= ( valueShifted & notmask[ startBit ] );

            // Increment to the next byte. Safer than auto-increment, which can
            // cause bugs if additional code references the buffer.
            startByte++;

            // Loop while decrementing the bit index from right to left.
            while ( bitIndex > 0 ) {
                // Decrement the bit index before using it. This needs further
                // verification, even though this should be an exact logical
                // match for the original C code, as it seems strange that the
                // previous setting of the bit index isn't ready for first use
                // in the shifting operations.
                bitIndex--;

                // Clear the entire byte.
                // NOTE: This is likely incorrect due to using a char buffer.
                packedBits[ startByte ] &= 0;

                // Get the next 8 bits from the value, via right-shift.
                valueShifted = value >> ( ( bitIndex << 3 ) + endBit );
                packedBits[ startByte ] |= ( valueShifted & 255 );

                // Increment to the next byte. Safer than auto-increment, which
                // can cause bugs if additional code references the buffer.
                startByte++;
            }

            // For the last byte, we mask anything after the end bit.
            packedBits[ startByte ] &= notmask[ endBit ];

            // Get the last part of the value and stuff it into the end byte.
            // The left shift effectively erases anything above (8 - endBit)
            // bits in the value, so that it will fit in the last byte.
            valueShifted = value << ( 8 - endBit );
            packedBits[ startByte ] |= valueShifted;
        }

        return packedBits;
    }

    /**
     * Unpacks a long value from 'numberOfPackedBits' consecutive bits in a
     * supplied buffer, starting at a given bit position.
     * <p>
     * TODO: Check the number of bits against the size of a long as well.
     * 
     * @param packedBuffer
     *            The buffer containing the packed bits
     * @param startBitIndex
     *            The initial offset into the packed buffer
     * @param numberOfPackedBits
     *            The number of packed bits to retrieve from the buffer
     * @return An unpacked long value
     * @throws IllegalArgumentException
     *             If the buffer is smaller than the stated number of bits
     */
    public static long bitUnpack( char[] packedBuffer, int startBitIndex, int numberOfPackedBits )
            throws IllegalArgumentException {
        // As the buffer size is dictated by the caller, it is the caller's
        // responsibility to ensure that its size is large enough to cover
        // the stated number of packed bits.
        if ( packedBuffer == null ) {
            throw new IllegalArgumentException( "bitUnpack: buffer is null; no data to unpack" );
        }
        if ( packedBuffer.length < numberOfPackedBits ) {
            throw new IllegalArgumentException( "bitUnpack: buffer size is smaller than the stated number of packed bits" );
        }

        // Bits are packed from right to left, in BIG ENDIAN order.
        int bitIndex = startBitIndex + numberOfPackedBits;

        // Right shift the start and end by 3 bits. This is the same as dividing
        // by 8 but is faster. This computes the buffer's start and end bytes.
        int startByte = startBitIndex >> 3;
        int endByte = bitIndex >> 3;

        // Apply Logical AND to the start and end positions using 7. This is the
        // same as doing a modulation with 8 but is faster. This computes the
        // start and end bits within the buffer's start and end bytes.
        int startBit = startBitIndex & 7;
        int endBit = bitIndex & 7;

        // Compute the number of bytes covered by the packed buffer.
        bitIndex = endByte - startByte - 1;

        // If the start byte equals the end byte, the value is stored in 1 byte.
        long value;
        if ( startByte == endByte ) {
            // Mask anything prior to the start bit and after the end bit.
            value = packedBuffer[ startByte ] & ( notmask[ startBit ] & mask[ endBit ] );

            // Shift the value to the right, still safely within a long.
            value >>= ( 8 - endBit );
        }
        else {
            // Mask data prior to the start bit of the first byte, then shift to
            // the left by the necessary amount.
            value = packedBuffer[ startByte ] & notmask[ startBit ];
            value <<= ( numberOfPackedBits - ( 8 - startBit ) );

            // Increment to the next byte. Safer than auto-increment, which can
            // cause bugs if additional code references the buffer.
            startByte++;

            // Loop while decrementing the bit index from right to left.
            while ( bitIndex > 0 ) {
                // Decrement the bit index before using it. This needs further
                // verification, even though this should be an exact logical
                // match for the original C code, as it seems strange that the
                // previous setting of the bit index isn't ready for first use
                // in the shifting operations.
                bitIndex--;

                // Get the next 8 bits from the packed buffer, via left-shift.
                char nextBufferValue = packedBuffer[ startByte ];
                long bufferValueShifted = ( nextBufferValue << ( ( bitIndex << 3 ) + endBit ) );
                value += bufferValueShifted;

                // Increment to the next byte. Safer than auto-increment, which
                // can cause bugs if additional code references the buffer.
                startByte++;
            }

            // For the last byte, we mask anything after the end bit, then shift
            // to the right by (8 - endBit) bits.
            char nextBufferValue = packedBuffer[ startByte ];
            long bufferValueMasked = nextBufferValue & mask[ endBit ];
            long bufferValueShifted = ( bufferValueMasked ) >> ( 8 - endBit );
            value += bufferValueShifted;
        }

        return value;
    }

}
