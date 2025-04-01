/**
 * MIT License
 *
 * Copyright (c) 2025 Mark Schmieder
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

import java.util.Objects;

/**
 * Helper methods for working with enums that implement specific interfaces, 
 * using generics to allow for global implementations that eliminate the need
 * for copy/paste of identical code for each enum class that implements the
 * interface in question.
 */
public final class EnumUtilities {


    /**
     * The default constructor is disabled, as this is a static utility class.
     */
    private EnumUtilities() {}
    
    public static Labeled< ? > getLabeledEnumFromLabel( final String text,
                                                        final Labeled< ? >[] values ) {
        // Exit early if there's nothing to compare.
        if ( text == null ) {
            return null;
        }
   
        // NOTE: This is a fail-safe that should never return as-is vs. throwing
        //  an exception if no valid Labeled enum is found or there's an error.
        Labeled< ? > labeledEnum = null;
   
        boolean foundLabeled = false;
        try {
            for ( final Labeled< ? > labeled : values ) {
                // Trim leading spaces, as sometimes labels are used to pair
                // with other strings (such as a measurement unit following a
                // value) but when selecting in a drop-list, these are stripped.
                // Usually, values() returns with leading spaces and the passed
                // text value is trimmed and comes from a GUI drop-list.
                if ( Objects.equals( labeled.label().trim(), 
                                     text.trim() ) ) {
                    labeledEnum = labeled;
                    foundLabeled = true;
                    break;
                }
            }
        }
        catch ( final Exception e ) {
            // Nothing to do here; error handling is subsumed below.
        }
   
        if ( !foundLabeled ) {
            // TODO: Pass the enum's classname to insert in this string, but
            //  only if getClass().getCanonicalName() doesn't return "Enum".
            final String message = "Unexpected value for Labeled enum: " + text;
            throw new IllegalArgumentException( message );
        }

        return labeledEnum;
    }
    
    public static Abbreviated< ? > getAbbreviatedEnumFromAbbreviation( 
            final String text,
            final Abbreviated< ? >[] values ) {
        // Exit early if there's nothing to compare.
        if ( text == null ) {
            return null;
        }
   
        // NOTE: This is a fail-safe that should never return as-is vs. throwing
        //  an exception if no valid Abbreviated enum is found or there's an error.
        Abbreviated< ? > abbreviatedEnum = null;
        
        boolean foundAbbreviated = false;
        try {
            for ( final Abbreviated< ? > abbreviated : values ) {
                // Trim leading spaces, as sometimes labels are used to pair
                // with other strings (such as a measurement unit following a
                // value) but when selecting in a drop-list, these are stripped.
                // Usually, values() returns with leading spaces and the passed
                // text value is trimmed and comes from a GUI drop-list.
                if ( Objects.equals( abbreviated.abbreviation().trim(), 
                                     text.trim() ) ) {
                    abbreviatedEnum = abbreviated;
                    foundAbbreviated = true;
                    break;
                }
            }
        }
        catch ( final Exception e ) {
            // Nothing to do here; error handling is subsumed below.
        }
        
        if ( !foundAbbreviated ) {
            // TODO: Pass the enum's classname to insert in this string, but
            //  only if getClass().getCanonicalName() doesn't return "Enum".
            final String message = "Unexpected value for Abbreviated enum: " + text;
            throw new IllegalArgumentException( message );
        }

        return abbreviatedEnum;
    }
    
    public static Indexed< ? > getIndexedEnumFromIndex( 
            final int index,
            final Indexed< ? >[] values ) {
        // NOTE: This is a fail-safe that should never return as-is vs. throwing
        //  an exception if no valid Indexed enum is found or there's an error.
        Indexed< ? > indexedEnum = null;
        
        boolean foundIndexed = false;
        try {
            for ( final Indexed< ? > indexed : values ) {
                if ( indexed.index() == index ) {
                    indexedEnum = indexed;
                    foundIndexed = true;
                    break;
                }
            }
        }
        catch ( final Exception e ) {
            // Nothing to do here; error handling is subsumed below.
        }
        
        if ( !foundIndexed ) {
            // TODO: Pass the enum's classname to insert in this string, but
            //  only if getClass().getCanonicalName() doesn't return "Enum".
            final String message = "Unexpected value for Indexed enum: " + index;
            throw new IllegalArgumentException( message );
        }

        return indexedEnum;
    }
}
