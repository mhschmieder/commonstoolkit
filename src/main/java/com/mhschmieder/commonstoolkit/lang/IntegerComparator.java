/**
 * MIT License
 *
 * Copyright (c) 2023 Mark Schmieder
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

import java.io.Serializable;
import java.util.Comparator;

/**
 * Integer based {@link Comparator} meant for use by Collections.sort() (e.g.).
 * <p>
 * In other words, a sorting comparator class that sorts by integer value.
 */
public class IntegerComparator implements Comparator< Integer >, Serializable {
    /**
     * Unique Serial Version ID for this class, to avoid class loader conflicts.
     */
    private static final long serialVersionUID = 1L;

    public IntegerComparator() {
        // NOTE: No data members or superclass, so nothing to do here.
    }

    /**
     * Returns an integer corresponding to the comparisons results of two
     * integers.
     * 
     * @param integer1 The first integer to compare, as the comparison source
     * @param integer2 The second integer to compare, as the comparison target
     * @return A negative integer if the first argument is less than the second
     * argument; zero if equal to; or a positive integer if greater than
     */
    @Override
    public int compare( final Integer integer1, final Integer integer2 ) {
        return integer1.compareTo(  integer2  );
    }
}
