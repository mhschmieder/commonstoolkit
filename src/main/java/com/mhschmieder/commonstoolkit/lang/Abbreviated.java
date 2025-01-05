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

/**
 * An interface to use with enums and other objects that need abbreviated versions
 * of their name or label, an obvious case being abbreviated versions of units.
 * <p>
 * The abbreviations do not have to be different from the names or labels, in the
 * enum context, as each enum constant is different and not all necessarily need
 * to be abbreviated, but providing common API allows for more programming styles.
 * <p>
 * As the abbreviations are meant to be assigned to a private field at construction
 * time of each enum constant, the mapper from enum to abbreviation is named as a
 * direct field accessor vs. "getAbbreviation()", in line with enum methods such as
 * "ordinal()" and "name()".
 * <p>
 * To get an enum from an abbreviation, only a static method is possible, and though
 * we could overload "valueOf()", it is safer to call it "valueOfAbbreviation()" in
 * case the Java language ever needs to add its own String-parameter overload.
 * <p>
 * It is not possible to declare a static method in an interface unless its
 * generic type is also static, so the "valueOfAbbreviation() method can have a 
 * complete mapping implementation of all enum constants but must be called from an
 * enum instantiation. This is best done by defining a local variable of the enum
 * type and then calling "valueOfAbbreviation" to potentially reassign from the
 * default value.
 * <p>
 * This interface is templated with a Generic argument so that the associated enum
 * can pass this interface into common implementations of listener factories and
 * other such features where enums being final classes cause programming issues.
 * One such example is a gatherer of an enum's complete list of abbreviated values
 * to append measurement units to the end of typed values in Combo Boxes.
 * <p>
 * This is published to the commons toolkit as it isn't GUI specific or dependent
 * on a specific toolkit, and otherwise could not be applied to data model enums.
 * <p>
 * Although oriented towards the needs of mapping between enums and abbreviated
 * labels, this interface can be applied to other Java objects as well.
 * 
 * @param <T> The object or enum type that implements this interface
 */
public interface Abbreviated< T > {
    
    /**
     * Returns the String value of the abbreviation assigned to this object.
     * 
     * @return the String value of the abbreviation assigned to this object
     */
    public String abbreviation();
    
    /**
     * Returns the object assigned to the provided abbreviated String.
     * <p>
     * Usually this object will be a specific enum constant.
     * 
     * @param abbreviatedLabel the abbreviated label to match to an assigned
     *        object or enum
     * @return the object assigned to the provided abbreviated label
     */
    public T valueOfAbbreviation( String abbreviatedLabel );
}
