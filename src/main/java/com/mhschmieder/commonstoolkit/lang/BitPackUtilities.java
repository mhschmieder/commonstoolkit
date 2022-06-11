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
 * Java does not have unsigned types and cannot use longs as indices into static
 * arrays, so some clever binary logic must be applied in bitwise fashion.
 */
public class BitPackUtilities {

    /**
     * The default constructor is disabled, as this is a static utilities class.
     */
    private BitPackUtilities() {}

    /**
     * Hexadecimal 8-bit masks that consecutively turn on the next bit from left
     * to right in a byte, ignoring the sign bit. Each consecutive value, when
     * combined with the same index in the hexadecimal inverse masks below,
     * results in all-ones, allowing for invertibility.
     */
    protected static final byte[] mask    = {
                                              0x00,
                                              ( byte ) 0x80,
                                              ( byte ) 0xc0,
                                              ( byte ) 0xe0,
                                              ( byte ) 0xf0,
                                              ( byte ) 0xf8,
                                              ( byte ) 0xfc,
                                              ( byte ) 0xfe };

    /**
     * Hexadecimal 8-bit inverse masks that consecutively turn off the next bit
     * from left to right in a byte, ignoring the sign bit. Each consecutive
     * value, when combined with the same index in the hexadecimal masks above,
     * results in all-ones, allowing for invertibility.
     */
    protected static final byte[] notMask = {
                                              ( byte ) 0xff,
                                              0x7f,
                                              0x3f,
                                              0x1f,
                                              0x0f,
                                              0x07,
                                              0x03,
                                              0x01 };

    /**
     * Packs a long value into 'numberOfPackedBits' consecutive bits in a
     * supplied byte array, starting at a given bit position.
     * <p>
     * The byte array must be allocated correctly by the caller, if they pass it
     * in. If {@code null}, a new byte array is allocated of sufficient size to
     * store the packed bits. The byte array is returned regardless, for more
     * flexibility.
     * <p>
     * <b>TODO:</b> Check the number of bits against the size of a long as well.
     * <p>
     * <b>TODO:</b> Verify and test this method as it was derived from a C
     * function but has not been verified for proper handling of the sign bit.
     *
     * @param packedBuffer
     *            The byte array to use for storing the packed bits
     * @param startBitIndex
     *            The initial offset into packedBuffer
     * @param numberOfPackedBits
     *            The number of bits to retrieve from packedBuffer
     * @param value
     *            The long value to pack into consecutive bits
     * @return The byte array containing the packed bits
     * @throws IllegalArgumentException
     *             If packedBuffer is smaller than the stated number of bits
     */
    public static byte[] bitPack( final byte[] packedBuffer,
                                  final int startBitIndex,
                                  final int numberOfPackedBits,
                                  final long value )
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
        final byte[] packedBits = ( packedBuffer != null )
            ? packedBuffer
            : new byte[ numberOfPackedBits % 16 ];

        // Bits are packed from right to left, in BIG ENDIAN order.
        int bitIndex = startBitIndex + numberOfPackedBits;

        // Right shift the start and end by 3 bits. This is the same as dividing
        // by 8 but is faster. This computes the buffer's start and end bytes.
        int startByte = startBitIndex >> 3;
        final int endByte = bitIndex >> 3;

        // Apply Logical AND to the start and end positions using 7. This is the
        // same as doing a modulation with 8 but is faster. This computes the
        // start and end bits within the buffer's start and end bytes.
        final int startBit = startBitIndex & 7;
        final int endBit = bitIndex & 7;

        // Compute the number of bytes covered by the packed buffer.
        bitIndex = endByte - startByte - 1;

        // If the start byte equals the end byte, the value is stored in 1 byte.
        if ( startByte == endByte ) {
            // Mask anything prior to the start bit and after the end bit, in
            // order to not corrupt data that has already been stored there.
            packedBits[ startByte ] &= ( mask[ startBit ] | notMask[ endBit ] );

            // Shift the value to the left, past the end bit.
            final long valueShifted = value << ( 8 - endBit );

            // Mask anything in the left-shifted value that is prior to the
            // start bit and after the end bit.
            packedBits[ startByte ] |= ( valueShifted & ( notMask[ startBit ] | mask[ endBit ] ) );
        }
        else {
            // Mask data prior to the start bit of the first byte, then shift to
            // the right by the necessary amount.
            packedBits[ startByte ] &= mask[ startBit ];
            long valueShifted = value >> ( numberOfPackedBits - ( 8 - startBit ) );

            // Get the upper bits of the right-shifted value and mask anything
            // prior to the start bit.
            packedBits[ startByte ] |= ( valueShifted & notMask[ startBit ] );

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
                packedBits[ startByte ] &= 0;

                // Get the next 8 bits from the value, via right-shift.
                valueShifted = value >> ( ( bitIndex << 3 ) + endBit );
                packedBits[ startByte ] |= ( valueShifted & 255 );

                // Increment to the next byte. Safer than auto-increment, which
                // can cause bugs if additional code references the buffer.
                startByte++;
            }

            // For the last byte, we mask anything after the end bit.
            packedBits[ startByte ] &= notMask[ endBit ];

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
     * supplied byte array, starting at a given bit position.
     * <p>
     *
     * @param packedBuffer
     *            The byte array containing the packed bits
     * @param startBitIndex
     *            The initial offset into packedBuffer
     * @param numberOfPackedBits
     *            The number of packed bits to retrieve from packedBuffer
     * @return An unpacked long value
     * @throws IllegalArgumentException
     *             If packedBuffer is smaller than the stated number of bits, or
     *             either the start bit or the request number of bits is out of
     *             range
     */
    public static long bitUnpack( final byte[] packedBuffer,
                                  final int startBitIndex,
                                  final int numberOfPackedBits )
            throws IllegalArgumentException {
        // As the buffer size is dictated by the caller, it is the caller's
        // responsibility to ensure that its size is large enough to cover
        // the stated number of packed bits.
        if ( packedBuffer == null ) {
            throw new IllegalArgumentException( "bitUnpack: buffer is null; no data to unpack" );
        }
        if ( numberOfPackedBits < 1 ) {
            throw new IllegalArgumentException( "bitUnpack: number of packed bits is less than one; impossible to store data" );
        }
        if ( packedBuffer.length < numberOfPackedBits ) {
            throw new IllegalArgumentException( "bitUnpack: buffer size is smaller than the stated number of packed bits" );
        }
        if ( ( startBitIndex < 0 ) || ( startBitIndex >= packedBuffer.length ) ) {
            throw new IllegalArgumentException( "bitUnpack: start bit index is out of range of buffer" );
        }

        // Bits are packed from right to left, in BIG ENDIAN order.
        int endBitIndex = startBitIndex + numberOfPackedBits;

        // Right shift the start and end by 3 bits. This is the same as dividing
        // by 8 but is faster. This computes the buffer's start and end bytes.
        final int startByteIndex = startBitIndex >> 3;
        final int endByteIndex = endBitIndex >> 3;

        // Apply Logical AND to the start and end positions using 7. This is the
        // same as doing a modulation with 8 but is faster. This computes the
        // start and end bits within the buffer's start and end bytes.
        final int startBit = startBitIndex & 7;
        final int endBit = endBitIndex & 7;

        // Declare a long to hold the return value as packed bits.
        long longValue;

        // If the start byte and the end byte are the same, the value is stored
        // in just one byte within the long to return.
        if ( startByteIndex == endByteIndex ) {
            // Mask anything prior to the start bit and after the end bit.
            final byte startByte = packedBuffer[ startByteIndex ];
            final byte maskedStartByte = ( byte ) ( startByte
                    & ( notMask[ startBit ] & mask[ endBit ] ) );

            // Store the masked result as the only byte in the long to return.
            longValue = toCorrectedLongValue( maskedStartByte );

            // Shift the value to the right, still safely within a long.
            longValue >>>= ( 8 - endBit );
        }
        else {
            // Mask data prior to the start bit of the start byte, then shift to
            // the left by the necessary amount to start at correct bit index.
            final byte startByte = packedBuffer[ startByteIndex ];
            final byte maskedStartByte = ( byte ) ( startByte & notMask[ startBit ] );

            // Store the masked result as the first byte in the long to return.
            longValue = toCorrectedLongValue( maskedStartByte );

            // Shift the value to the left, still safely within a long.
            longValue <<= ( numberOfPackedBits - ( 8 - startBit ) );

            // Compute the number of bytes covered by the packed buffer, in
            // terms of the number of requested bits, and use this to determine
            // the initial bit index for the first full byte in the buffer.
            int bitIndex = endByteIndex - startByteIndex - 1;

            // Declare a current byte index outside of the loop, so that we can
            // compare it as a final step to see if the end byte has been fully
            // accounted for or if we still need to do some final bit shifting.
            int byteIndex = startByteIndex + 1;

            // Loop through each byte to add to the unpacked long return value,
            // decrementing the bit index from right to left (BIG ENDIAN order).
            while ( bitIndex > 0 ) {
                // Decrement the bit index before using it.
                bitIndex--;

                // Get the next 8 bits from the packed buffer, via left-shift.
                final byte nextByte = packedBuffer[ byteIndex ];
                long nextByteCorrected = toCorrectedLongValue( nextByte );

                // Shift the value to the left, still safely within a long.
                nextByteCorrected <<= ( ( bitIndex << 3 ) + endBit );

                // Add the shifted result as the next byte in the long to
                // return.
                longValue += nextByteCorrected;

                // Increment to the next byte. Safer than auto-increment, which
                // can cause bugs if new code references the buffer.
                byteIndex++;
            }

            // Check to see if the end byte has already been fully consumed, or
            // if we need to shift some leftover bits to complete the unpacking.
            if ( byteIndex < packedBuffer.length ) {
                // For the last byte, we mask anything after the end bit, then
                // shift to the right by (8 - endBit) bits.
                final byte endByte = packedBuffer[ byteIndex ];
                final byte maskedEndByte = ( byte ) ( endByte & mask[ endBit ] );
                long endByteCorrected = toCorrectedLongValue( maskedEndByte );

                // Shift the value to the right, still safely within a long.
                endByteCorrected >>>= ( 8 - endBit );

                // Add the shifted result as the last byte in the long to
                // return.
                longValue += endByteCorrected;
            }
        }

        return longValue;
    }

    /**
     * Returns a long corresponding to the original byte value as though it is
     * unsigned. As bytes (and all data types except chars) have a sign bit in
     * Java, we otherwise get incorrect values when bit shifting across byte
     * boundaries in a larger data type such as an int or a long. This method
     * applies a foolproof approach using bitwise binary logic to copy values.
     * 
     * @param byteValue
     *            The byte to promote to a long without a sign bit set
     * @return A long value with the eighth bit of the original
     *         {@code byteValue} preserved as a non-sign bit in the new long
     */
    public static long toCorrectedLongValue( final byte byteValue ) {
        long returnValue = 0L;

        // Loop through each bit index and transfer the bit value, including
        // the sign bit as a binary value for that bit index rather than an
        // indicator for negative vs. positive total value.
        for ( int i = 0; i < 8; i++ ) {
            final long val = ( long ) StrictMath.pow( 2, i );
            if ( ( byteValue & val ) > 0 ) {
                returnValue += val;
            }
        }

        return returnValue;
    }
}
