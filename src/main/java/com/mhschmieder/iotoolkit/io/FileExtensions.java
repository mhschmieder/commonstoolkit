/**
 * MIT License
 *
 * Copyright (c) 2020, 2021 Mark Schmieder
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
 * This file is part of the IoToolkit Library
 *
 * You should have received a copy of the MIT License along with the
 * GraphicsToolkit Library. If not, see <https://opensource.org/licenses/MIT>.
 *
 * Project: https://github.com/mhschmieder/iotoolkit
 */
package com.mhschmieder.iotoolkit.io;

/**
 * {@code FileExtensions} is a static constants class for common file
 * extensions.
 */
public final class FileExtensions {

    /**
     * The default constructor is disabled, as this is a static constants class.
     */
    private FileExtensions() {}

    // Declare constants for file type descriptions and extensions.
    public static final String                            BMP_DESCRIPTION             = "BMP";                 //$NON-NLS-1$
    @SuppressWarnings("nls") public static final String[] BMP_EXTENSIONS              =
                                                                         new String[] {
                                                                                        "*.bmp",
                                                                                        "*.dib" };

    public static final String                            CSV_DESCRIPTION             = "CSV";                 //$NON-NLS-1$
    @SuppressWarnings("nls") public static final String   CSV_EXTENSIONS              = "*.csv";

    public static final String                            DXF_DESCRIPTION             =
                                                                          "AutoCAD DXF";                       //$NON-NLS-1$
    @SuppressWarnings("nls") public static final String   DXF_EXTENSIONS              = "*.dxf";

    public static final String                            EPS_DESCRIPTION             =
                                                                          "Encapsulated PostScript";           //$NON-NLS-1$
    @SuppressWarnings("nls") public static final String[] EPS_EXTENSIONS              =
                                                                         new String[] {
                                                                                        "*.eps",
                                                                                        "*.epsf" };

    public static final String                            FPX_DESCRIPTION             =
                                                                          "FlashPix Bitmap";                   //$NON-NLS-1$
    @SuppressWarnings("nls") public static final String   FPX_EXTENSIONS              = "*.fpx";

    public static final String                            GIF_DESCRIPTION             = "GIF";                 //$NON-NLS-1$
    @SuppressWarnings("nls") public static final String   GIF_EXTENSIONS              = "*.gif";

    public static final String                            HTML_DESCRIPTION            = "HTML";                //$NON-NLS-1$
    @SuppressWarnings("nls") public static final String[] HTML_EXTENSIONS             =
                                                                          new String[] {
                                                                                         "*.html",
                                                                                         "*.htm" };

    // :NOTE: TIFF requires either ImageIO-Ext or JAI 1.1.3 JAR's. Both are
    // quite large (especially the former, which also has JNI support that might
    // not include the Mac, but otherwise is a more direct analog to how we do
    // other formats currently vs. the different JAI approach). Better to wait
    // until the switch to JavaFX, which has its own imaging API's.
    // :NOTE: WBMP isn't necessary anymore as most people's wireless devices can
    // now handle the bandwidth of full color images.
    // :NOTE: PostScript is now commented out due to the export method not
    // having a way to run properly on a Swing thread inside a JavaFX thread and
    // return status as well as have the action be guaranteed to have completed
    // the file write before the attempt to copy the temp file checks the file
    // length to make sure it isn't zero bytes.
    public static final String                            IMAGE_GRAPHICS_DESCRIPTION  =
                                                                                     "Image Graphics Files";   //$NON-NLS-1$
    @SuppressWarnings("nls") public static final String[] IMAGE_GRAPHICS_EXTENSIONS   =
                                                                                    new String[] {
                                                                                                   "*.png",
                                                                                                   // "*.pnm",
                                                                                                   "*.gif",
                                                                                                   "*.jpg",
                                                                                                   "*.jpeg",
                                                                                                   "*.jpe",
                                                                                                   "*.tiff",
                                                                                                   "*.tif",
                                                                                                   // "*.ps",
                                                                                                   // "*.wbm",
                                                                                                   // "*.wbmp",
                                                                                                   "*.bmp",
                                                                                                   "*.dib" };

    public static final String                            JAR_DESCRIPTION             = "JAR";                 //$NON-NLS-1$
    @SuppressWarnings("nls") public static final String   JAR_EXTENSIONS              = "*.jar";

    public static final String                            JPEG_DESCRIPTION            = "JPEG";                //$NON-NLS-1$
    @SuppressWarnings("nls") public static final String[] JPEG_EXTENSIONS             =
                                                                          new String[] {
                                                                                         "*.jpg",
                                                                                         "*.jpeg",
                                                                                         "*.jpe" };

    public static final String                            LOG_DESCRIPTION             = "Log File";            //$NON-NLS-1$
    @SuppressWarnings("nls") public static final String   LOG_EXTENSIONS              = "*.log";

    public static final String                            PDF_DESCRIPTION             = "Adobe PDF";           //$NON-NLS-1$
    @SuppressWarnings("nls") public static final String   PDF_EXTENSIONS              = "*.pdf";

    public static final String                            PNG_DESCRIPTION             = "PNG";                 //$NON-NLS-1$
    @SuppressWarnings("nls") public static final String   PNG_EXTENSIONS              = "*.png";

    public static final String                            PNM_DESCRIPTION             = "PNM";                 //$NON-NLS-1$
    @SuppressWarnings("nls") public static final String   PNM_EXTENSIONS              = "*.pnm";

    public static final String                            PPT_DESCRIPTION             =
                                                                          "Presentation";                      //$NON-NLS-1$
    @SuppressWarnings("nls") public static final String   PPT_EXTENSIONS              = "*.ppt";

    public static final String                            PPTX_DESCRIPTION            =
                                                                           "Presentation";                     //$NON-NLS-1$
    @SuppressWarnings("nls") public static final String   PPTX_EXTENSIONS             = "*.pptx";

    public static final String                            PRESENTATION_DESCRIPTION    =
                                                                                   "Presentation Files";       //$NON-NLS-1$
    @SuppressWarnings("nls") public static final String[] PRESENTATION_EXTENSIONS     =
                                                                                  new String[] {
                                                                                                 "*.pptx",
                                                                                                 "*.ppts" };

    public static final String                            PS_DESCRIPTION              =
                                                                         "PostScript";                         //$NON-NLS-1$
    @SuppressWarnings("nls") public static final String   PS_EXTENSIONS               = "*.ps";

    public static final String                            RASTER_IMAGE_DESCRIPTION    =
                                                                                   "Raster Image Files";       //$NON-NLS-1$
    @SuppressWarnings("nls") public static final String[] RASTER_IMAGE_EXTENSIONS     =
                                                                                  new String[] {
                                                                                                 "*.png",
                                                                                                 "*.gif",
                                                                                                 "*.jpg",
                                                                                                 "*.jpeg",
                                                                                                 "*.jpe" };

    public static final String                            SPREADSHEET_DESCRIPTION     =
                                                                                  "Spreadsheet Files";         //$NON-NLS-1$
    @SuppressWarnings("nls") public static final String[] SPREADSHEET_EXTENSIONS      =
                                                                                 new String[] {
                                                                                                "*.xlsx",
                                                                                                "*.xls" };

    public static final String                            SVG_DESCRIPTION             = "SVG";                 //$NON-NLS-1$
    @SuppressWarnings("nls") public static final String   SVG_EXTENSIONS              = "*.svg";

    public static final String                            TIFF_DESCRIPTION            = "TIFF";                //$NON-NLS-1$
    @SuppressWarnings("nls") public static final String[] TIFF_EXTENSIONS             =
                                                                          new String[] {
                                                                                         "*.tiff",
                                                                                         "*.tif" };

    public static final String                            TXT_DESCRIPTION             = "Text File";           //$NON-NLS-1$
    @SuppressWarnings("nls") public static final String   TXT_EXTENSIONS              = "*.txt";

    public static final String                            VECTOR_GRAPHICS_DESCRIPTION =
                                                                                      "Vector Graphics Files"; //$NON-NLS-1$
    @SuppressWarnings("nls") public static final String[] VECTOR_GRAPHICS_EXTENSIONS  =
                                                                                     new String[] {
                                                                                                    "*.eps",
                                                                                                    "*.epsf",
                                                                                                    "*.pdf",
                                                                                                    "*.svg" };

    public static final String                            WBMP_DESCRIPTION            =
                                                                           "Wireless Bitmap";                  //$NON-NLS-1$
    @SuppressWarnings("nls") public static final String[] WBMP_EXTENSIONS             =
                                                                          new String[] {
                                                                                         "*.wbm",
                                                                                         "*.wbmp" };

    public static final String                            XLS_DESCRIPTION             =
                                                                          "Excel 97-2003 Workbook";            //$NON-NLS-1$
    @SuppressWarnings("nls") public static final String   XLS_EXTENSIONS              = "*.xls";

    public static final String                            XLSX_DESCRIPTION            =
                                                                           "Office Open XML";                  //$NON-NLS-1$
    @SuppressWarnings("nls") public static final String   XLSX_EXTENSIONS             = "*.xlsx";

    public static final String                            XML_DESCRIPTION             = "XML";                 //$NON-NLS-1$
    @SuppressWarnings("nls") public static final String   XML_EXTENSIONS              = "*.xml";

    public static final String                            ZIP_DESCRIPTION             = "ZIP";                 //$NON-NLS-1$
    @SuppressWarnings("nls") public static final String   ZIP_EXTENSIONS              = "*.zip";

}
