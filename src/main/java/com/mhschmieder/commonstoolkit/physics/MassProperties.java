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
package com.mhschmieder.commonstoolkit.physics;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import com.mhschmieder.commonstoolkit.math.MathUtilities;

/**
 * The coordinate/axis convention is the same as elsewhere in rigging:
 * <p>
 * x-axis is positive in front and negative in rear
 * <p>
 * y-axis goes from side to side and is rarely if ever used for COG
 * <p>
 * z-axis is positive on top and negative on bottom
 * <p>
 * Additionally, this object is GC-centric, for placement-neutrality.
 */
public final class MassProperties implements MassComputable {

    private static final double   WEIGHT_DEFAULT_KG                 = 0.0d;
    private static final Vector3D COG_IN_OBJECT_COORDINATES_DEFAULT = Vector3D.ZERO;
    private static final boolean  COG_VALID_DEFAULT                 = false;

    private double                _weightKg                         = WEIGHT_DEFAULT_KG;
    private Vector3D              _cogInObjectCoordinates           =
                                                          COG_IN_OBJECT_COORDINATES_DEFAULT;
    private boolean               _cogValid                         = COG_VALID_DEFAULT;

    // Default constructor.
    public MassProperties() {
        this( WEIGHT_DEFAULT_KG, COG_IN_OBJECT_COORDINATES_DEFAULT, COG_VALID_DEFAULT );
    }

    // Partially qualified constructor.
    public MassProperties( final double weightKg, final boolean cogValid ) {
        this( weightKg, COG_IN_OBJECT_COORDINATES_DEFAULT, cogValid );
    }

    // Fully qualified constructor.
    public MassProperties( final double weightKg,
                           final Vector3D cogInObjectCoordinates,
                           final boolean cogValid ) {
        _weightKg = weightKg;
        _cogInObjectCoordinates = MathUtilities.copyPoint3D( cogInObjectCoordinates );
        _cogValid = cogValid;
    }

    @Override
    public Vector3D getCogInObjectCoordinates() {
        return _cogInObjectCoordinates;
    }

    @Override
    public double getWeightKg() {
        return _weightKg;
    }

    @Override
    public boolean isCogValid() {
        return _cogValid;
    }

}
