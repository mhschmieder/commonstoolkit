/**
 * MIT License
 *
 * Copyright (c) 2023, 2025 Mark Schmieder
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
 * An interface to use with enums and other objects that don't have dedicated
 * handlers for what to present in List Cells, Radio Buttons and other GUI 
 * controls, such as those found in the drop down List View of a Combo Box or in
 * their associated Text Field showing the current list selection.
 * <p>
 * Such labels also serve as useful values to save using the Java Preferences API,
 * for session recall of preferred list selections and grouped control selections.
 * <p>
 * As the labels are meant to be assigned to a private field at construction time
 * of each enum constant, the mapper from enum to label is named as a direct field
 * accessor vs. "getLabel()", in line with enum methods such as "ordinal()".
 * <p>
 * To get an enum from a label, only a static method is possible, and though we
 * could overload "valueOf()", it is safer to explicitly call it "valueOfLabel()"
 * in case the Java language ever needs to add its own String-parameter overload.
 * <p>
 * It is not possible to declare a static method in an interface unless its
 * generic type is also static, so the "valueOfLabel() method can have a complete
 * mapping implementation of all enum constants but must be called from an enum
 * instantiation. This is best done by defining a local variable of the enum type
 * and then calling "valueOfLabel" to potentially reassign from the default value.
 * <p>
 * This interface is templated with a Generic argument so that the associated enum
 * can pass this interface into common implementations of listener factories and
 * other such features where enums being final classes cause programming issues.
 * One such example is a gatherer of an enum's complete list of values as labels
 * to feed to a Combo Box, or to pass to a factory method that generates listeners.
 * <p>
 * This is published to the commons toolkit as it isn't GUI specific or dependent
 * on a specific toolkit, and otherwise could not be applied to data model enums.
 * <p>
 * Although oriented towards the needs of mapping between enums and labels, this
 * interface can be applied to other Java objects as well.
 * 
 * @param <T> The object or enum type that implements this interface
 */
public interface Labeled< T > {
    
    /**
     * Returns the string value of the label assigned to this object.
     * 
     * @return the string value of the label assigned to this object
     */
    public String label();
    
    
    /**
     * Returns the object assigned to the provided label.
     * <p>
     * Usually this object will be a specific enum constant.
     * 
     * @param text the label to match to an assigned object or enum
     * @return the object assigned to the provided label
     */
    public T valueOfLabel( String text );
}
