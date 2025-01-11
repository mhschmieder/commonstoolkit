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
 * This file is part of the CommonsToolkit Library
 *
 * You should have received a copy of the MIT License along with the
 * CommonsToolkit Library. If not, see <https://opensource.org/licenses/MIT>.
 *
 * Project: https://github.com/mhschmieder/commonstoolkit
 */
package com.mhschmieder.commonstoolkit.io;

/**
 * Enumeration of common file modes when handling File Menu actions. The "other"
 * instance marks the mode as needing extension by downstream clients, as we
 * can't inherit enums so we must supplement with an app-specific enum or class.
 * <p>
 * NOTE: Raster Graphics and Vector Graphics are given separate instances for
 *  Import and Export primarily because PDF files can be used as either category.
 *  There is also some overlap with CAD, but such files may also contain details
 *  from modeling and manufacturing realms that require domain-specific parsing.
 * <p>
 * NOTE: The Table Data category is for CSV as it isn't as detailed or flexible
 *  as Spreadsheet Data and is likely to be used in a narrow scope vs. app-level.
 * <p>
 * TODO: Flesh out the general File Menu and sub-menus to capture all enums.
 */
public enum FileMode {
    NEW, 
    OPEN,
    IMPORT_TEXT_DATA,
    IMPORT_TABLE_DATA,
    IMPORT_SPREADSHEET_DATA,
    IMPORT_BINARY_DATA,
    IMPORT_IMAGE_DATA,
    IMPORT_RASTER_GRAPHICS, 
    IMPORT_VECTOR_GRAPHICS,
    IMPORT_RENDERED_GRAPHICS,
    IMPORT_CAD,
    LOAD, 
    CLOSE, 
    SAVE, 
    SAVE_CONVERTED, 
    SAVE_LOG,
    SAVE_REPORT,
    SAVE_SERVER_REQUEST,
    SAVE_SERVER_RESPONSE,
    EXPORT_TEXT_DATA,
    EXPORT_TABLE_DATA,
    EXPORT_SPREADSHEET_DATA,
    EXPORT_BINARY_DATA,
    EXPORT_IMAGE_DATA,
    EXPORT_RASTER_GRAPHICS,
    EXPORT_VECTOR_GRAPHICS,
    EXPORT_RENDERED_GRAPHICS,
    EXPORT_CAD,
    OTHER
}
