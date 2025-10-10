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
package com.mhschmieder.commonstoolkit.io;

import com.mhschmieder.commonstoolkit.lang.StringConstants;

import java.io.PrintWriter;

/**
 * Facilities for writing padded numbers to character streams via PrintWriter.
 * <p>
 * Many file formats require specific padding of numbers, so this serves those
 * formats, which are plentiful in the scientific domain due to FORTRAN code.
 */
public class PaddedNumberWriter {

    /**
     * The default constructor is disabled, as this is a static utilities class
     */
    public PaddedNumberWriter() {}

    /**
     * Prints formatted integers using the provided padding to add spaces.
     * <p>
     * NOTE: Unlike floating-point numbers, integers are padded in the front.
     * <p>
     * TODO: Once the padding is resolved as a char vs. a String, write a base
     *  method that accepts the padding character as a parameter, and call it.
     *
     * @param printWriter The {@link PrintWriter} to use for buffering the
     *                    formatted numeric output
     * @param padding     The number of spaces to use as padding after the
     *                    string-converted integer
     * @param value       The pre-truncated integer to print
     */
    public static void writePaddedInt( final PrintWriter printWriter,
                                       final int padding,
                                       final int value ) {
        final String intAsString = Integer.toString( value );

        // Add spaces to pad the output. This is usually to achieve alignment.
        // TODO: Review whether printing a character is better than a string,
        //  based on efficiency and whether the same number of bytes is written.
        for ( int i = 0; i < ( padding - intAsString.length() ); i++ ) {
            printWriter.print( StringConstants.SPACE );
        }

        // Print the pre-padded string-converted integer.
        printWriter.print( intAsString );
    }

    /**
     * Prints formatted integers using the provided padding to add zeroes.
     * <p>
     * NOTE: Unlike floating-point numbers, integers are padded in the front.
     * <p>
     * TODO: Once the padding is resolved as a char vs. a String, write a base
     *  method that accepts the padding character as a parameter, and call it.
     *
     * @param printWriter The {@link PrintWriter} to use for buffering the
     *                    formatted numeric output
     * @param padding     The number of spaces to use as padding after the
     *                    string-converted integer
     * @param value       The pre-truncated integer to print
     */
    public static void writeZeroPaddedInt( final PrintWriter printWriter,
                                           final int padding,
                                           final int value ) {
        final String intAsString = Integer.toString( value );

        // Add zeroes to pad the output. This is usually to achieve alignment.
        // TODO: Review whether printing a character is better than a string,
        //  based on efficiency and whether the same number of bytes is written.
        for ( int i = 0; i < ( padding - intAsString.length() ); i++ ) {
            printWriter.print( "0" );
        }

        // Print the pre-padded string-converted integer.
        printWriter.print( intAsString );
    }

    /**
     * Prints formatted single-precision floating point numbers using the
     * provided padding to add spaces.
     * <p>
     * In order to keep this low-level code decoupled from various math
     * utilities, it is up to the caller to pre-truncate or round the number
     * that is passed in, to the required precision.
     * <p>
     * TODO: Once the padding is resolved as a char vs. a String, write a base
     *  method that accepts the padding character as a parameter, and call it.
     *
     * @param printWriter The {@link PrintWriter} to use for buffering the
     *                    formatted numeric output
     * @param padding     The number of spaces to use as padding after the
     *                    string-converted floating-point number
     * @param value       The pre-truncated floating-point number to print
     */
    public static void writePaddedFloat( final PrintWriter printWriter,
                                         final int padding,
                                         final float value ) {
        final String floatAsString = Float.toString( value );

        // Print the string-converted floating-point number.
        printWriter.print( floatAsString );

        // Add spaces to pad the output. This is usually to achieve alignment.
        // TODO: Review whether printing a character is better than a string,
        //  based on efficiency and whether the same number of bytes is written.
        for ( int i = 0; i < ( padding - floatAsString.length() ); i++ ) {
            printWriter.print( StringConstants.SPACE );
        }
    }

    /**
     * Prints formatted double-precision floating point numbers using the
     * provided padding to add spaces.
     * <p>
     * In order to keep this low-level code decoupled from various math
     * utilities, it is up to the caller to pre-truncate or round the number
     * that is passed in, to the required precision.
     * <p>
     * TODO: Once the padding is resolved as a char vs. a String, write a base
     *  method that accepts the padding character as a parameter, and call it.
     *
     * @param printWriter The {@link PrintWriter} to use for buffering the
     *                    formatted numeric output
     * @param padding     The number of spaces to use as padding after the
     *                    string-converted floating-point number
     * @param value       The pre-truncated floating-point number to print
     */
    public static void writePaddedDouble( final PrintWriter printWriter,
                                          final int padding,
                                          final double value ) {
        final String doubleAsString = Double.toString( value );

        // Print the string-converted floating-point number.
        printWriter.print( doubleAsString );

        // Add spaces to pad the output. This is usually to achieve alignment.
        // TODO: Review whether printing a character is better than a string,
        //  based on efficiency and whether the same number of bytes is written.
        for ( int i = 0; i < ( padding - doubleAsString.length() ); i++ ) {
            printWriter.print( StringConstants.SPACE );
        }
    }
}
