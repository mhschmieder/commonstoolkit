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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Optional;
import java.util.Vector;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.FilenameUtils;

/**
 * {@code CsvUtilities} is a utilities class for handling the CSV format.
 *
 * @version 1.0
 *
 * @author Mark Schmieder
 */
public final class CsvUtilities {

    /**
     * The default constructor is disabled, as this is a static utilities class.
     */
    private CsvUtilities() {}

    @SuppressWarnings("nls")
    public static final boolean convertCsvToStringVector( final File file,
                                                          final Vector< Vector< String > > rows ) {
        final String fileName = file.getName();
        final String fileNameCaseInsensitive = fileName.toLowerCase( Locale.ENGLISH );
        if ( FilenameUtils.isExtension( fileNameCaseInsensitive, "csv" ) ) {
            // Load the project from a CSV file.
            //
            // Chain a BufferedReader to a FileReader, for better performance.
            try ( final FileReader fileReader = new FileReader( file );
                    final BufferedReader bufferedReader = new BufferedReader( fileReader ) ) {
                final boolean fileOpened = loadFromCsv( bufferedReader, rows );
                if ( !fileOpened ) {
                    return false;
                }
            }
            catch ( final IOException ioe ) {
                ioe.printStackTrace();
                return false;
            }
        }
        else if ( FilenameUtils.isExtension( fileNameCaseInsensitive, "zip" ) ) {
            // Load the project from a ZIP file. Send the file vs. a
            // ZipInputStream, due to the need to cycle twice, and due to
            // problems with ZipInputStream.
            final boolean fileOpened = loadFromZip( file, rows );
            if ( !fileOpened ) {
                return false;
            }
        }

        return rows.size() > 0;
    }

    // Load a comma-delimited stream into a data vector.
    public static final boolean loadFromCsv( final BufferedReader bufferedReader,
                                             final Vector< Vector< String > > rows ) {
        Vector< String > columns = new Vector<>();
        final StringBuilder buffer = new StringBuilder();

        // Parse a CSV stream into a vector of vectors.
        try {
            String line = bufferedReader.readLine();
            while ( line != null ) {
                boolean inString = false;

                for ( int i = 0; i < line.length(); i++ ) {
                    final char c = line.charAt( i );
                    if ( ( c == ',' ) && ( !inString ) ) {
                        columns.add( buffer.toString() );
                        buffer.setLength( 0 );
                    }
                    else if ( ( c == '"' ) && ( !inString ) ) {
                        inString = true;
                    }
                    else if ( ( c == '"' ) && ( ( ( i + 1 ) == line.length() )
                            || ( line.charAt( i + 1 ) != '"' ) ) ) {
                        inString = false;
                    }
                    else if ( c == '"' ) {
                        buffer.append( c );

                        // Skip the second string character.
                        i++;
                    }
                    else {
                        buffer.append( c );
                    }
                }

                columns.add( buffer.toString() );
                buffer.setLength( 0 );

                rows.add( columns );
                columns = new Vector<>();

                line = bufferedReader.readLine();
            }
        }
        catch ( final Exception e ) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    // :TODO: Find a way to report errors if not a legitimate ZIP file.
    @SuppressWarnings("nls")
    public static final boolean loadFromZip( final File file,
                                             final Vector< Vector< String > > rows ) {
        try ( final ZipFile zipFile = new ZipFile( file ) ) {
            final Predicate< ZipEntry > isFile = zipEntry -> !zipEntry.isDirectory();
            final Predicate< ZipEntry > isCsv = zipEntry -> FilenameUtils
                    .isExtension( zipEntry.getName().toLowerCase( Locale.ENGLISH ), "csv" );

            final Optional< ? extends ZipEntry > optionalCsvEntry = zipFile.stream()
                    .filter( isFile.and( isCsv ) ).findFirst();

            // There must be a valid CSV entry in order for this ZIP file to be
            // considered valid.
            if ( !optionalCsvEntry.isPresent() ) {
                return false;
            }

            // Read in the XML CSV as an in-memory stream.
            //
            // Using a safe try-with-resources clause, chain a BufferedReader to
            // an InputStreamReader to the InputStream, for better performance.
            boolean fileOpened = false;
            try ( final InputStream inputStream = zipFile.getInputStream( optionalCsvEntry.get() );
                    final InputStreamReader inputStreamReader =
                                                              new InputStreamReader( inputStream );
                    final BufferedReader bufferedReader =
                                                        new BufferedReader( inputStreamReader ) ) {
                fileOpened = loadFromCsv( bufferedReader, rows );
            }

            return fileOpened;
        }
        catch ( final Exception e ) {
            e.printStackTrace();
            return false;
        }
    }

}
