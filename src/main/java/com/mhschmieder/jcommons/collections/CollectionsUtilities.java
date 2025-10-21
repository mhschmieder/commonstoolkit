/**
 * MIT License
 *
 * Copyright (c) 2020, 2023 Mark Schmieder
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
package com.mhschmieder.jcommons.collections;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * {@code CollectionsUtilities} is a static utilities class for common tools
 * that interact with the Java Collections Framework.
 */
public final class CollectionsUtilities {

    /**
     * The default constructor is disabled, as this is a static utilities class.
     */
    private CollectionsUtilities() {}

    /**
     * Returns the current physical capacity of the supplied {@link ArrayList}.
     * 
     * @param arrayList The {@link ArrayList} whose capacity will be queried
     * @return The current physical capacity of the supplied {@link ArrayList}
     * @throws Exception Thrown if the {@code elementData} field does not exist
     */
    public static int getPhysicalCapacity( final ArrayList arrayList ) 
        throws Exception {
        // Starting with Java 8, new collections have capacity of zero, so treat
        // invalid references the same way. The first element added, changes the
        // ArrayList size to its default size or its specified initial capacity.
        if ( arrayList == null ) {
            return 0;
        }
        
        final Field field = ArrayList.class.getDeclaredField( "elementData" );
        field.setAccessible(  true  );
        
        return ( ( Object[] ) field.get(  arrayList  ) ).length;
    }
}
