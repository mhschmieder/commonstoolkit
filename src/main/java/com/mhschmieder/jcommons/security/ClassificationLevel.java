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
package com.mhschmieder.jcommons.security;

import com.mhschmieder.jcommons.lang.Abbreviated;
import com.mhschmieder.jcommons.lang.EnumUtilities;
import com.mhschmieder.jcommons.lang.Labeled;

/**
 * An enumeration of current US government classification levels, which were
 * modified starting around 2017 or thereabouts.
 * <p>
 * NOTE: Until hearing otherwise, the verbose "Controlled Unclassified
 *  Information" classification level is only presented by its "CUI" acronym.
 * <p>
 * TODO: Carefully vet the current abbreviations and re-review CUI for any
 *  preferred unabbreviated forms, as well as case-sensitivity of all terms.
 */
public enum ClassificationLevel implements Labeled< ClassificationLevel >, 
        Abbreviated< ClassificationLevel > {
    UNCLASSIFIED( "Unclassified", "U" ), 
    CUI( "CUI", "CUI" ), 
    CONFIDENTIAL( "Confidential", "C" ), 
    SECRET( "Secret", "S" ), 
    TOP_SECRET( "Top Secret", "TS" );
    
    private String label;
    private String abbreviation;
    
    ClassificationLevel( final String pLabel,
                         final String pAbbreviation ) {
        label = pLabel;
        abbreviation = pAbbreviation;
    }

    @Override 
    public final String label() {
        return label;
    }

    @Override
    public ClassificationLevel valueOfLabel( final String text ) {
        return ( ClassificationLevel ) EnumUtilities.getLabeledEnumFromLabel( 
            text, values() );
    }

    @Override 
    public final String abbreviation() {
        return abbreviation;
    }

    @Override
    public ClassificationLevel valueOfAbbreviation( final String abbreviatedText ) {
        return ( ClassificationLevel ) EnumUtilities.getAbbreviatedEnumFromAbbreviation( 
            abbreviatedText, values() );
    }

    public static ClassificationLevel defaultValue() {
        return UNCLASSIFIED;
    }

    @Override
    public String toString() {
        // NOTE: This override takes care of displaying the current choice in
        //  its custom label form when a Combo Box is hosted by a Table Cell.
        return label();
    }
}
