/**
 * MIT License
 *
 * Copyright (c) 2020, 2022 Mark Schmieder
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
package com.mhschmieder.commonstoolkit.html;

import com.mhschmieder.commonstoolkit.lang.StringUtilities;

/**
 * {@code HtmlUtilities} is a static utilities class for common HTML
 * functionality that at least wasn't part of Core Java at the time this library
 * was written.
 */
public class HtmlUtilities {

    /**
     * The default constructor is disabled, as this is a static utilities class
     */
    private HtmlUtilities() {}

    // This is a utility method to strip typical HTML formatting from labels.
    @SuppressWarnings("nls")
    public static String stripHtmlFormatting( final String formattedString ) {
        if ( formattedString == null ) {
            return null;
        }

        String unformattedString = formattedString;
        unformattedString = StringUtilities.replace( unformattedString, "<html>", "" );
        unformattedString = StringUtilities.replace( unformattedString, "</html>", "" );
        unformattedString = StringUtilities.replace( unformattedString, "<br>", ". " );
        return unformattedString;
    }

    @SuppressWarnings("nls")
    public static StringBuilder getHtmlTableColumnHeader( final String tableColumnHeader ) {
        final StringBuilder htmlTableColumnHeader = new StringBuilder( "<th><b><i>" );
        htmlTableColumnHeader.append( tableColumnHeader );
        htmlTableColumnHeader.append( "</i></b></th>" );
        return htmlTableColumnHeader;
    }

    @SuppressWarnings("nls")
    public static StringBuilder getHtmlImageHeader( final String imageLabel ) {
        final StringBuilder imageHeader = new StringBuilder( "<p /><h1 align=\"center\">" );
        imageHeader.append( imageLabel );
        imageHeader.append( "</h1>" );
        return imageHeader;
    }

}
