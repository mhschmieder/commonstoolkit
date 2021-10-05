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
 * This file is part of the CommonsToolkit Library
 *
 * You should have received a copy of the MIT License along with the
 * CommonsToolkit Library. If not, see <https://opensource.org/licenses/MIT>.
 *
 * Project: https://github.com/mhschmieder/commonstoolkit
 */
package com.mhschmieder.commonstoolkit.physics;

import java.util.Locale;

public enum WeightUnit {
    METRIC_TONS, KILOGRAMS, GRAMS, POUNDS, OUNCES;

    public static WeightUnit abbreviatedValueOf( final String abbreviatedWeightUnit ) {
        return ( " mt".equalsIgnoreCase( abbreviatedWeightUnit ) ) //$NON-NLS-1$
            ? METRIC_TONS
            : ( " kg".equalsIgnoreCase( abbreviatedWeightUnit ) ) //$NON-NLS-1$
                ? KILOGRAMS
                : ( " g".equalsIgnoreCase( abbreviatedWeightUnit ) ) //$NON-NLS-1$
                    ? GRAMS
                    : ( " lbs".equalsIgnoreCase( abbreviatedWeightUnit ) ) //$NON-NLS-1$
                        ? POUNDS
                        : ( " oz".equalsIgnoreCase( abbreviatedWeightUnit ) ) //$NON-NLS-1$
                            ? OUNCES
                            : defaultValue();
    }

    public static WeightUnit canonicalValueOf( final String canonicalWeightUnit ) {
        return ( canonicalWeightUnit != null )
            ? valueOf( canonicalWeightUnit.toUpperCase( Locale.ENGLISH ).replaceAll( " ", "_" ) ) //$NON-NLS-1$ //$NON-NLS-2$
            : defaultValue();
    }

    public static WeightUnit defaultValue() {
        return KILOGRAMS;
    }

    public final String toAbbreviatedString() {
        switch ( this ) {
        case METRIC_TONS:
            return " mt"; //$NON-NLS-1$
        case KILOGRAMS:
            return " kg"; //$NON-NLS-1$
        case GRAMS:
            return " g"; //$NON-NLS-1$
        case POUNDS:
            return " lbs"; //$NON-NLS-1$
        case OUNCES:
            return " oz"; //$NON-NLS-1$
        default:
            final String errMessage = "Unexpected WeightUnit " + this; //$NON-NLS-1$
            throw new IllegalArgumentException( errMessage );
        }
    }

    public final String toCanonicalString() {
        return toString().toLowerCase( Locale.ENGLISH );
    }

    public String toPresentationString() {
        return toAbbreviatedString();
    }

    @Override
    public final String toString() {
        // :NOTE: As of Java 6, enums include the underscore in their string
        // representation, which is a problem for backward-compatibility with
        // XML parsers. Thus, we need to strip the underscores and replace them
        // with spaces, to behave like Java 5.
        final String value = super.toString();
        return value.replaceAll( "_", " " ); //$NON-NLS-1$ //$NON-NLS-2$
    }

}
