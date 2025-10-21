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

import com.mhschmieder.jcommons.lang.EnumUtilities;
import com.mhschmieder.jcommons.lang.Labeled;

import java.nio.ByteOrder;

/**
 * An enumeration of Endianness that includes a deferral of setting it by
 * platform on an as-needed basis. This enumeration includes mappers to other
 * Endianness schemes, such as the one in Java NIO ByteOrder.
 * <p>
 * TODO: Check whether this is redundant with anything in Apache Commons Lang.
 */
public enum Endianness implements Labeled< Endianness > {
    LITTLE_ENDIAN( "Little Endian" ),
    BIG_ENDIAN( "Big Endian" ),
    MATCH_PLATFORM( "Match Platform (OS/CPU)" );

    private String label;

    Endianness( final String pLabel ) {
        label = pLabel;
    }

    @Override
    public String label() {
        return label;
    }

    @Override
    public Endianness valueOfLabel( final String text ) {
        return ( Endianness ) EnumUtilities.getLabeledEnumFromLabel(
                text, values() );
    }

    public static Endianness defaultValue() {
        return MATCH_PLATFORM;
    }

    public static ByteOrder toByteOrder( final Endianness endianness ) {
        ByteOrder byteOrder = null;

        switch ( endianness ) {
            case LITTLE_ENDIAN:
                byteOrder = ByteOrder.LITTLE_ENDIAN;
                break;
            case BIG_ENDIAN:
                byteOrder = ByteOrder.BIG_ENDIAN;
                break;
            case MATCH_PLATFORM:
                byteOrder = ByteOrder.nativeOrder();
                break;
            default:
                final String errorMessage = "Unexpected Endianness: "
                        + endianness;
                throw new IllegalArgumentException( errorMessage );
        }

        return byteOrder;
    }
    
    public static String toByteOrderString( final Endianness endianness ) {
        final ByteOrder byteOrder = toByteOrder( endianness );
        return ( byteOrder != null ) ? byteOrder.toString() : null;
    }

    public ByteOrder toByteOrder() {
        return toByteOrder( this );
    }
    
    public String toByteOrderString() {
        return toByteOrderString( this );
    }
}
