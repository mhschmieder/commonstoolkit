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
package com.mhschmieder.commonstoolkit.math;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 * Utility methods for math; primarily consisting of basic entity methods on
 * points and lines.
 */
public class MathUtilities {

    /**
     * The default constructor is disabled, as this is a static utilities class.
     */
    private MathUtilities() {}

    public static Vector2D copyPoint2D( final Vector2D point2D ) {
        final Vector2D copiedPoint2D = new Vector2D( point2D.getX(), point2D.getY() );
        return copiedPoint2D;
    }

    public static Vector3D copyPoint3D( final Vector3D point3D ) {
        final Vector3D copiedPoint3D =
                                     new Vector3D( point3D.getX(), point3D.getY(), point3D.getZ() );
        return copiedPoint3D;
    }

    public static Vector3D exchangeCoordinates( final Vector3D point3D,
                                                final OrthogonalAxes orthogonalAxes ) {
        switch ( orthogonalAxes ) {
        case XY:
            return new Vector3D( point3D.getY(), point3D.getX(), point3D.getZ() );
        case XZ:
            return new Vector3D( point3D.getZ(), point3D.getY(), point3D.getX() );
        case YZ:
            return new Vector3D( point3D.getX(), point3D.getZ(), point3D.getY() );
        default:
            return Vector3D.ZERO;
        }
    }

    /**
     * Returns the distance between two points.
     *
     * @param x1
     *            the X coordinate of the first specified point
     * @param y1
     *            the Y coordinate of the first specified point
     * @param x2
     *            the X coordinate of the second specified point
     * @param y2
     *            the Y coordinate of the second specified point
     * @return the distance between the two sets of specified coordinates.
     * @since 1.2
     */
    public static double distance( final double x1,
                                   final double y1,
                                   final double x2,
                                   final double y2 ) {
        final double x1Adjusted = x1 - x2;
        final double y1Adjusted = y1 - y2;
        return Math.hypot( x1Adjusted, y1Adjusted );
    }

    /**
     * Returns the distance from this <code>Point2D</code> to a specified point.
     *
     * @param pt
     *            The point from which to measure distance
     * @param px
     *            the X coordinate of the specified point to be measured against
     *            this <code>Point2D</code>
     * @param py
     *            the Y coordinate of the specified point to be measured against
     *            this <code>Point2D</code>
     * @return the distance between this <code>Point2D</code> and a specified
     *         point.
     * @since 1.2
     */
    public static double distance( final Vector2D pt, final double px, final double py ) {
        final double pxAdjusted = px - pt.getX();
        final double pyAdjusted = py - pt.getY();
        return Math.hypot( pxAdjusted, pyAdjusted );
    }

    /**
     * Returns the square of the distance between two points.
     *
     * @param x1
     *            the X coordinate of the first specified point
     * @param y1
     *            the Y coordinate of the first specified point
     * @param x2
     *            the X coordinate of the second specified point
     * @param y2
     *            the Y coordinate of the second specified point
     * @return the square of the distance between the two sets of specified
     *         coordinates.
     * @since 1.2
     */
    public static double distanceSq( final double x1,
                                     final double y1,
                                     final double x2,
                                     final double y2 ) {
        final double x1Adjusted = x1 - x2;
        final double y1Adjusted = y1 - y2;
        return ( ( x1Adjusted * x1Adjusted ) + ( y1Adjusted * y1Adjusted ) );
    }

    /**
     * Returns the square of the distance from this <code>Point2D</code> to a
     * specified point.
     *
     * @param point
     *            The point from which to measure distance
     * @param px
     *            the X coordinate of the specified point to be measured against
     *            this <code>Point2D</code>
     * @param py
     *            the Y coordinate of the specified point to be measured against
     *            this <code>Point2D</code>
     * @return the square of the distance between this <code>Point2D</code> and
     *         the specified point.
     * @since 1.2
     */
    public static double distanceSq( final Vector2D point, final double px, final double py ) {
        final double pxAdjusted = px - point.getX();
        final double pyAdjusted = py - point.getY();
        return ( ( pxAdjusted * pxAdjusted ) + ( pyAdjusted * pyAdjusted ) );
    }

    /**
     * Returns the square of the distance from one <code>Point2D</code> to
     * another specified <code>Point2D</code>.
     *
     * @param pt1
     *            The reference point to use for measuring another point
     * @param pt2
     *            The specified point to be measured against the reference point
     * @return the square of the distance between this <code>Point2D</code> to a
     *         specified <code>Point2D</code>.
     * @since 1.2
     */
    public static double distanceSq( final Vector2D pt1, final Vector2D pt2 ) {
        final double px = pt2.getX() - pt1.getX();
        final double py = pt2.getY() - pt1.getY();
        return ( ( px * px ) + ( py * py ) );
    }

}
