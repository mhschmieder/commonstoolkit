/**
 * MIT License
 *
 * Copyright (c) 2020, 2024 Mark Schmieder
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
package com.mhschmieder.commonstoolkit.branding;

public class ProductBranding {

    // Declare the fully qualified application name.
    public String applicationName;

    // Declare strings for product name and version, for ongoing reference.
    public String productName;
    public String productVersion;
    public String productVersionProtected;
    public String revisionDate;
    
    // NOTE: A default blanker is provided to discourage using null references.
    public ProductBranding() {
        this( "", "", "", "", "" );
    }

    public ProductBranding( final String pApplicationName,
                            final String pProductName,
                            final String pProductVersion,
                            final String pProductVersionProtected,
                            final String pRevisionDate ) {
        applicationName = pApplicationName;
        productName = pProductName;
        productVersion = pProductVersion;
        productVersionProtected = pProductVersionProtected;
        revisionDate = pRevisionDate;
    }
}
