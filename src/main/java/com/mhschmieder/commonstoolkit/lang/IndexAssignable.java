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
 * An interface to use with enums for features in libraries that predate Java 1.5
 * and thus took other approaches to guaranteeing locally-unique lookup values
 * for sets of related constants. In many cases, the legacy implementations are
 * baked into application project file formats and require integer index mapping.
 * <p>
 * As the indices are meant to be assigned to a private field at construction time
 * of each enum constant, the mapper from enum to index is named as a direct field
 * accessor vs. "getIndex()", in line with enum methods such as "ordinal()".
 * <p>
 * To get an enum from an index, only a static method is possible, and though we
 * could overload "valueOf()", it is safer to explicitly call it "valueOfIndex()"
 * in case the Java language ever needs to add its own int-parameter overload.
 * <p>
 * It is not possible to declare a static method in an interface unless its
 * generic type is also static, so the "valueOfIndex() method can have a complete
 * mapping implementation of all enum constants but must be called from an enum
 * instantiation. This is best done by defining a local variable of the enum type
 * and then calling "valueOfIndex" to potentially reassign from the default value.
 * <p>
 * This interface is templated with a Generic argument so that the associated enum
 * can pass this interface into common implementations of listener factories and
 * other such features where enums being final classes cause programming issues.
 * <p>
 * Although oriented towards the needs of mapping between legacy integer values
 * and modern enums, this interface can be applied to other Java objects as well.
 * 
 * @param <T> The object or enum type that implements this interface
 */
public interface IndexAssignable< T > {
    
    /**
     * Returns the integer value of the index assigned to this object.
     * 
     * @return the integer value of the index assigned to this object
     */
    public int index();
    
    /**
     * Returns the object assigned to the provided index.
     * <p>
     * Usually this object will be a specific enum constant.
     * 
     * @param index the index to match to an assigned object or enum
     * @return the object assigned to the provided index
     */
    public T valueOfIndex( int index );
}
