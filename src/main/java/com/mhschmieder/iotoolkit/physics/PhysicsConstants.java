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
package com.mhschmieder.iotoolkit.physics;

/**
 * Container for physical constants.
 */
public final class PhysicsConstants {

    // :NOTE: The constructor is disabled, since this is a static utilities
    // class.
    private PhysicsConstants() {}

    // //////////////////////////////////////////////////////////////////////////
    // The following are taken from ANSI standard S1.26-1995
    // "Method for Calculation of the absorption of sound by the atmosphere".

    // Reference speed of sound in meters per second, at 0% humidity and 20C.
    public static final double SPEED_OF_SOUND_DRY_AIR                            = 343.2;

    // Reference speed of sound in meters per second, at 50% humidity and 20C.
    public static final double SPEED_OF_SOUND_NORMAL_AIR                         = 343.8332d;

    // Break-off points for what is considered low vs. high altitude.
    public static final int    ALTITUDE_LOW_METERS                               = 800;
    public static final int    ALTITUDE_HIGH_METERS                              = 2200;

    // Pressure extremes in pascals.
    public static final double PRESSURE_MINIMUM_PA                               = 50000d;
    public static final double PRESSURE_MAXIMUM_PA                               = 111000d;

    // Altitude-specific pressure in pascals.
    public static final double PRESSURE_LOW_ALTITUDE_PA                          = 101325d;
    public static final double PRESSURE_MEDIUM_ALTITUDE_PA                       = 87646d;
    public static final double PRESSURE_HIGH_ALTITUDE_PA                         = 75629d;

    // Reference pressure (one atmosphere at mean sea level) in pascals.
    public static final double PRESSURE_REFERENCE_PA                             =
                                                     PRESSURE_LOW_ALTITUDE_PA;

    // Triple point pressure of water in pascals
    public static final double WATER_TRIPLE_POINT_PRESSURE_PA                    = 611.657d;

    // Triple point temperature of specially purified water, in degrees Kelvin.
    // :NOTE: Used in the conversion from Relative to Molar Humidity; but not to
    // convert temperature from degrees Celsius to degrees Kelvin, as it
    // corresponds to 0.01 degrees Celsius vs. Absolute Zero.
    public static final double WATER_TRIPLE_POINT_TEMPERATURE_K                  = 273.16d;

    // Room temperature in Kelvin
    public static final double ROOM_TEMPERATURE_K                                = 293.15d;

    // Room temperature in degrees Celsius.
    public static final double ROOM_TEMPERATURE_C                                = UnitConversion
            .convertTemperature( ROOM_TEMPERATURE_K,
                                 TemperatureUnit.KELVIN,
                                 TemperatureUnit.CELSIUS );

    // Temperature extrema in Kelvin.
    public static final double TEMPERATURE_MINIMUM_K                             = 233.15d;
    public static final double TEMPERATURE_MAXIMUM_K                             = 348.15d;

    // Temperature extrema in degrees Celsius.
    public static final double TEMPERATURE_MINIMUM_C                             = UnitConversion
            .convertTemperature( TEMPERATURE_MINIMUM_K,
                                 TemperatureUnit.KELVIN,
                                 TemperatureUnit.CELSIUS );
    public static final double TEMPERATURE_MAXIMUM_C                             = UnitConversion
            .convertTemperature( TEMPERATURE_MAXIMUM_K,
                                 TemperatureUnit.KELVIN,
                                 TemperatureUnit.CELSIUS );

    // Relative Humidity extrema in percentiles.
    public static final double HUMIDITY_MINIMUM_RELATIVE                         = 0d;
    public static final double HUMIDITY_MAXIMUM_RELATIVE                         = 100d;

    // //////////////////////////////////////////////////////////////////////////
    // The following is taken from ANSI standard S1.4-1983 Specification for
    // Sound Level Meters.

    // Reference minimum audible sound pressure in pascals used for calculating
    // SPL.
    public static final double PRESSURE_MINIMUM_AUDIBLE_PA                       = 20E-6;

    // //////////////////////////////////////////////////////////////////////////
    // The following are taken from "Fundamentals of Acoustics" 4th Ed. Kinsler
    // and Frey, John Wiley and Sons.

    // Density of Air at 20 degrees Celsius in kilograms per cubic meter.
    public static final double AIR_DENSITY_20C_REFERENCE                         = 1.21d;

    // Reference minimum audible sound intensity in Watts per meter^2 used for
    // calculating SPL.
    public static final double INTENSITY_MINIMUM_AUDIBLE                         = 1E-12;

    // Acceleration of Gravity is important for Mass/Weight conversions, and is
    // constant on the surface of the earth. Measured in meters / seconds**2.
    public static final double ACCELERATION_OF_GRAVITY_METERS_PER_SECOND_SQUARED = 9.807d;

}
