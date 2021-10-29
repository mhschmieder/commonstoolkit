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

import java.text.NumberFormat;

import com.mhschmieder.commonstoolkit.math.MathConstants;
import com.mhschmieder.commonstoolkit.text.NumberFormatUtilities;

/**
 * Utility methods for physics; primarily consisting of angle methodologies.
 */
public final class PhysicsUtilities {

    /**
     * The default constructor is disabled, as this is a static utilities class.
     */
    private PhysicsUtilities() {}

    public static String formatAngle( final double angle,
                                      final NumberFormat angleFormat,
                                      final AngleUnit angleUnit ) {
        final String formattedAngle = NumberFormatUtilities.formatDouble( angle, angleFormat )
                + angleUnit.toPresentationString();

        return formattedAngle;
    }

    /**
     * Normalize a given angle, accounting for the counter-clockwise convention
     * for positive angles and normalizing to the ( -180, +180 ) range.
     *
     * @param angleRadians
     *            A given angle in radians
     * @return The angle normalized to the ( -180, +180 ) range
     */
    public static double getNormalizedAngleRadians( final double angleRadians ) {
        double normalizedAngleRadians = angleRadians;

        if ( ( normalizedAngleRadians + Math.PI ) < 0d ) {
            normalizedAngleRadians += MathConstants.TWO_PI;
        }

        if ( ( normalizedAngleRadians - Math.PI ) > 0d ) {
            normalizedAngleRadians -= MathConstants.TWO_PI;
        }

        return normalizedAngleRadians;
    }

    public static double parseAngle( final String formattedAngle,
                                     final NumberFormat angleFormat,
                                     final AngleUnit angleUnit ) {
        try {
            // Fetch the presentation string representation for Angle Unit.
            final String angleUnitString = angleUnit.toPresentationString();

            // Make sure to strip the Angle Unit before converting to a double.
            final int angleUnitIndex = formattedAngle.indexOf( angleUnitString );
            final String strippedFormattedAngle = ( angleUnitIndex > 0 )
                ? formattedAngle.substring( 0, angleUnitIndex )
                : formattedAngle;
            final double angle = NumberFormatUtilities.parseDouble( strippedFormattedAngle,
                                                                    angleFormat );

            return angle;
        }
        catch ( final IndexOutOfBoundsException | NullPointerException e ) {
            e.printStackTrace();

            final double angle = NumberFormatUtilities.parseDouble( formattedAngle, angleFormat );

            return angle;
        }
    }

}
