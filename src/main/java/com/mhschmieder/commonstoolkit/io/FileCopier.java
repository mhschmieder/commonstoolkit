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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

import org.apache.commons.io.FileUtils;

/**
 * Facilities for copying files safely.
 */
public class FileCopier {

    /**
     * The default constructor is disabled, as this is a static utilities class
     */
    public FileCopier() {}
    
    /*
     * fileCopy
     *
     * Copies the first file to the second file.
     */
    public static boolean fileCopy( final String inputFilename,
                                    final String outputFilename ) {
        try {
            final File inFile = new File( inputFilename );
            final File outFile = new File( outputFilename );
            FileUtils.copyFile( inFile, outFile );
        }
        catch ( final Exception e ) {
            e.printStackTrace();
            System.out.println(
                    "Could Not Copy File "
                    + inputFilename
                    + " to "
                    + outputFilename );
            return false;
        }

        return true;
    }

    // Copy a file using streams (the oldest approach, but sometimes fastest).
    // NOTE: There is no need to provide a wrapper for Java NIO Files.copy() as
    //  an additional option, as Apache Commons IO FileUtils.copyFile() does that.
    public static long copyFileUsingStreams( final File source, 
                                             final File dest ) {
        long totalNumberOfBytesRead = 0L;
    
        try ( final InputStream is = new FileInputStream( source );
                final OutputStream os = new FileOutputStream( dest ) ) {
            totalNumberOfBytesRead = FileCopier.copyFileStream( is, os );
        }
        catch ( final Exception e ) {
            e.printStackTrace();
        }
    
        return totalNumberOfBytesRead;
    }

    // Copy a file stream using traditional Java IO.
    public static long copyFileStream( final InputStream is, 
                                       final OutputStream os ) {
        long totalNumberOfBytesRead = 0L;
    
        try {
            byte[] buffer = new byte[ 8192 ];
            int numberOfBytesRead;
            while ( ( numberOfBytesRead = is.read( buffer ) ) > 0 ) {
                os.write( buffer, 0, numberOfBytesRead );
                totalNumberOfBytesRead += numberOfBytesRead;
            }
        }
        catch ( final Exception e ) {
            e.printStackTrace();
        }
    
        return totalNumberOfBytesRead;
    }

    // Copy a file using channels (a somewhat newer approach, sometimes
    // fastest).
    // NOTE: There is no need to provide a wrapper for Java NIO Files.copy() as
    //  an additional option, as Apache Commons IO FileUtils.copyFile() does that.
    public static long copyFileUsingChannels( final File source, 
                                              final File dest ) {
        long totalNumberOfBytesRead = 0L;
    
        try ( final FileInputStream sourceStream = new FileInputStream( source );
                final FileChannel sourceChannel = sourceStream.getChannel();
                final FileOutputStream destStream = new FileOutputStream( dest );
                final FileChannel destChannel = destStream.getChannel() ) {
            totalNumberOfBytesRead = destChannel
                    .transferFrom( sourceChannel, 0L, sourceChannel.size() );
        }
        catch ( final Exception e ) {
            e.printStackTrace();
        }
    
        return totalNumberOfBytesRead;
    }
}
