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

// TODO: Switch octave range to either an enumeration with supporting transforms
// to presentation strings and lookup indices or to a direct lookup index.
public final class FrequencyRange {

    public static final String RELATIVE_BANDWIDTH_DEFAULT       = RelativeBandwidth.defaultValue()
            .toPresentationString();
    public static final String OCTAVE_RANGE_WIDE_DEFAULT        = "20 Hz To 20 kHz";              //$NON-NLS-1$
    public static final String OCTAVE_RANGE_NARROW_DEFAULT      = "80 Hz To 160 Hz";              //$NON-NLS-1$
    public static final String CENTER_FREQUENCY_DISPLAY_DEFAULT = "4 kHz";                        //$NON-NLS-1$

    // Determine the nominal default Center Frequency based on Octave Range.
    @SuppressWarnings("nls")
    public static double getNominalCenterFrequencyDefaultForOctaveRange( final String sOctaveRange,
                                                                         final boolean narrowBand ) {
        switch ( sOctaveRange ) {
        case FrequencyRange.OCTAVE_RANGE_WIDE_DEFAULT:
            return 4000d;
        case "10 Hz to 20 Hz":
            return narrowBand ? 15.6 : 16d;
        case "20 Hz to 40 Hz":
            return narrowBand ? 31.2 : 31.5d;
        case "40 Hz to 80 Hz":
            return narrowBand ? 62.5 : 63d;
        case "80 Hz to 160 Hz":
            return 125d;
        case "160 Hz to 315 Hz":
            return 250d;
        case "315 Hz to 630 Hz":
            return 500d;
        case "630 Hz to 1.25 kHz":
            return 1000d;
        case "1.25 kHz to 2.5 kHz":
            return 2000d;
        case "2.5 kHz to 5 kHz":
            return 4000d;
        case "5 kHz to 10 kHz":
            return 8000d;
        case "10 kHz to 20 kHz":
            return 16000d;
        default:
            return 4000d;
        }
    }

    // Determine the nominal default Octave Range based on Center Frequency.
    @SuppressWarnings("nls")
    public static String getNominalOctaveRangeDefaultForCenterFrequency( final double centerFrequency ) {
        // NOTE: We account for current offsets of ranges.
        if ( centerFrequency < 19d ) {
            return "10 Hz to 20 Hz";
        }
        else if ( centerFrequency < 39d ) {
            return "20 Hz to 40 Hz";
        }
        else if ( centerFrequency < 78d ) {
            return "40 Hz to 80 Hz";
        }
        else if ( centerFrequency < 156d ) {
            return "80 Hz to 160 Hz";
        }
        else if ( centerFrequency < 312d ) {
            return "160 Hz to 315 Hz";
        }
        else if ( centerFrequency < 624d ) {
            return "315 Hz to 630 Hz";
        }
        else if ( centerFrequency < 1248d ) {
            return "630 Hz to 1.25 kHz";
        }
        else if ( centerFrequency < 2496d ) {
            return "1.25 kHz to 2.5 kHz";
        }
        else if ( centerFrequency < 4992d ) {
            return "2.5 kHz to 5 kHz";
        }
        else if ( centerFrequency < 9986d ) {
            return "5 kHz to 10 kHz";
        }
        else {
            return "10 kHz to 20 kHz";
        }
    }

    // Determine if the Center Frequency is within the given Octave Range.
    @SuppressWarnings("nls")
    public static boolean isCenterFrequencyInOctaveRange( final String sOctaveRange,
                                                          final double centerFrequency ) {
        // NOTE: We have to special-case for the first and last Narrow Band
        // Octave Ranges, as they extend beyond the single Wide Band Octave
        // Range and thus should cue a "closest match" vs. a default.
        switch ( sOctaveRange ) {
        case FrequencyRange.OCTAVE_RANGE_WIDE_DEFAULT:
            return true;
        case "10 Hz to 20 Hz":
            return centerFrequency < 19d;
        case "20 Hz to 40 Hz":
            return ( centerFrequency >= 19d ) && ( centerFrequency < 39d );
        case "40 Hz to 80 Hz":
            return ( centerFrequency >= 39d ) && ( centerFrequency < 78d );
        case "80 Hz to 160 Hz":
            return ( centerFrequency >= 78d ) && ( centerFrequency < 156d );
        case "160 Hz to 315 Hz":
            return ( centerFrequency >= 156d ) && ( centerFrequency < 312d );
        case "315 Hz to 630 Hz":
            return ( centerFrequency >= 312d ) && ( centerFrequency < 624d );
        case "630 Hz to 1.25 kHz":
            return ( centerFrequency >= 624d ) && ( centerFrequency < 1248d );
        case "1.25 kHz to 2.5 kHz":
            return ( centerFrequency >= 1248d ) && ( centerFrequency < 2496d );
        case "2.5 kHz to 5 kHz":
            return ( centerFrequency >= 2496d ) && ( centerFrequency < 4992d );
        case "5 kHz to 10 kHz":
            return ( centerFrequency >= 4992d ) && ( centerFrequency < 9986d );
        case "10 kHz to 20 kHz":
            return centerFrequency >= 9986d;
        default:
            return false;
        }
    }

    private RelativeBandwidth relativeBandwidth;
    private String            octaveRange;
    private double            centerFrequency;

    // This is the default constructor; it sets all instance variables to
    // default values.
    public FrequencyRange() {
        this( RelativeBandwidth.defaultValue(), OCTAVE_RANGE_WIDE_DEFAULT, 4000.0d );
    }

    // This is the copy constructor, and is offered in place of clone() to
    // guarantee that the source object is never modified by the new target
    // object created here.
    public FrequencyRange( final FrequencyRange frequencyRange ) {
        this( frequencyRange.getRelativeBandwidth(),
              frequencyRange.getOctaveRange(),
              frequencyRange.getCenterFrequency() );
    }

    // This is the preferred constructor, when all initialization values are
    // known.
    public FrequencyRange( final RelativeBandwidth pRelativeBandwidth,
                           final String pOctaveRange,
                           final double pCenterFrequency ) {
        relativeBandwidth = pRelativeBandwidth;
        octaveRange = pOctaveRange;
        centerFrequency = pCenterFrequency;
    }

    // NOTE: Cloning is disabled as it is dangerous; use the copy constructor
    // instead.
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    // Default pseudo-constructor.
    void reset() {
        setRelativeBandwidth( RelativeBandwidth.defaultValue() );

        final String octaveRangeDefault = OCTAVE_RANGE_WIDE_DEFAULT;
        setOctaveRange( octaveRangeDefault );

        final double centerFrequencyDefault =
                                            getNominalCenterFrequencyDefaultForOctaveRange( octaveRangeDefault,
                                                                                            false );
        setCenterFrequency( centerFrequencyDefault );
    }

    // Fully qualified pseudo-constructor.
    protected void setFrequencyRange( final RelativeBandwidth pRelativeBandwidth,
                                      final String pOctaveRange,
                                      final double pCenterFrequency ) {
        setRelativeBandwidth( pRelativeBandwidth );
        setOctaveRange( pOctaveRange );
        setCenterFrequency( pCenterFrequency );
    }

    // Pseudo-copy constructor
    public void setFrequencyRange( final FrequencyRange frequencyRange ) {
        setFrequencyRange( frequencyRange.getRelativeBandwidth(),
                           frequencyRange.getOctaveRange(),
                           frequencyRange.getCenterFrequency() );
    }

    public void setRelativeBandwidth( final RelativeBandwidth pRelativeBandwidth ) {
        relativeBandwidth = ( pRelativeBandwidth != null )
            ? pRelativeBandwidth
            : RelativeBandwidth.defaultValue();
    }

    public RelativeBandwidth getRelativeBandwidth() {
        return relativeBandwidth;
    }

    public void setOctaveRange( final String pOctaveRange ) {
        octaveRange = ( pOctaveRange != null ) ? pOctaveRange : OCTAVE_RANGE_WIDE_DEFAULT;
    }

    public String getOctaveRange() {
        return octaveRange;
    }

    public void setCenterFrequency( final double pCenterFrequency ) {
        centerFrequency = pCenterFrequency;
    }

    public double getCenterFrequency() {
        return centerFrequency;
    }
}
