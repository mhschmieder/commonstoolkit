/**
 * MIT License
 *
 * Copyright (c) 2024, 2025 Mark Schmieder
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
package com.mhschmieder.jcommons.lang;

/**
 * An enumeration of Delimiter choices used for separating values in a
 * text-based data file. One obvious example of usage is in CSV files, which
 * unfortunately do not always follow the standard, so all common delimiters
 * are listed here, including the potentially OS-specific return sequence.
 */
public enum Delimiter implements Indexed< Delimiter >, Labeled< Delimiter >, 
        Abbreviated< Delimiter > {
    COMMA( 2, "Comma", StringConstants.COMMA ),
    TAB( 0, "Tab", StringConstants.TAB ),
    SPACE( 3, "Space", StringConstants.SPACE ),
    SEMICOLON( 1, "Semicolon", StringConstants.SEMICOLON ),
    RETURN( 4, "Return", StringConstants.CR + StringConstants.LF );

    private int index;
    private String label;
    private String abbreviation;

    Delimiter( final int pIndex,
               final String pLabel,
               final String pAbbreviation ) {
        index =  pIndex;
        label = pLabel;
        abbreviation = pAbbreviation;
    }

    @Override
    public int index() {
        return index;
    }

    @Override
    public Delimiter valueOfIndex( final int pIndex ) {
        return ( Delimiter ) EnumUtilities
                .getIndexedEnumFromIndex( pIndex, values() );
    }

    @Override
    public String label() {
        return label;
    }

    @Override
    public Delimiter valueOfLabel( final String text ) {
        return ( Delimiter ) EnumUtilities.getLabeledEnumFromLabel(
                text, values() );
    }

    @Override
    public String abbreviation() {
        return abbreviation;
    }

    @Override
    public Delimiter valueOfAbbreviation( String abbreviatedLabel ) {
        return ( Delimiter ) EnumUtilities.getAbbreviatedEnumFromAbbreviation(
                abbreviatedLabel, values() );
    }

    /**
     * Returns the default delimiter, which by definition is always the comma.
     *
     * @return The default delimiter.
     */
    public static Delimiter defaultValue() {
        return COMMA;
    }
}
