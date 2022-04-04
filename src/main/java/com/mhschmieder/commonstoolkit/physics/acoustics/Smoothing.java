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
package com.mhschmieder.commonstoolkit.physics.acoustics;

/**
 * Smoothing generally refers to operations over specific Frequency Band Q
 * Factors.
 */
public enum Smoothing {
    NARROW_BAND, SIXTH_OCTAVE_BAND, THIRD_OCTAVE_BAND;

    public static final Smoothing defaultValue() {
        return NARROW_BAND;
    }

    public static final Smoothing fromOctaveDivider( final int octaveDivider ) {
        switch ( octaveDivider ) {
        case 0:
            return NARROW_BAND;
        case 6:
            return SIXTH_OCTAVE_BAND;
        case 3:
            return THIRD_OCTAVE_BAND;
        default:
            return defaultValue();
        }
    }

    public static final int toOctaveDivider( final Smoothing smoothing ) {
        switch ( smoothing ) {
        case NARROW_BAND:
            return 0;
        case SIXTH_OCTAVE_BAND:
            return 6;
        case THIRD_OCTAVE_BAND:
            return 3;
        default:
            final String errMessage = "Unexpected " + smoothing.getClass().getSimpleName() + " " //$NON-NLS-1$ //$NON-NLS-2$
                    + smoothing;
            throw new IllegalArgumentException( errMessage );
        }
    }

    public static final Smoothing fromPresentationString( final String smoothingPresentationString ) {
        switch ( smoothingPresentationString ) {
        case "No Smoothing": //$NON-NLS-1$
            return NARROW_BAND;
        case "1/6 Octave Smoothing": //$NON-NLS-1$ :
            return SIXTH_OCTAVE_BAND;
        case "1/3 Octave Smoothing": //$NON-NLS-1$
            return THIRD_OCTAVE_BAND;
        default:
            return defaultValue();
        }
    }

    public static final String toPresentationString( final Smoothing smoothing ) {
        switch ( smoothing ) {
        case NARROW_BAND:
            return "No Smoothing"; //$NON-NLS-1$
        case SIXTH_OCTAVE_BAND:
            return "1/6 Octave Smoothing"; //$NON-NLS-1$
        case THIRD_OCTAVE_BAND:
            return "1/3 Octave Smoothing"; //$NON-NLS-1$
        default:
            final String errMessage = "Unexpected " + smoothing.getClass().getSimpleName() + " " //$NON-NLS-1$ //$NON-NLS-2$
                    + smoothing;
            throw new IllegalArgumentException( errMessage );
        }
    }

    public final int toOctaveDivider() {
        return toOctaveDivider( this );
    }

    public final String toPresentationString() {
        return toPresentationString( this );
    }

}
