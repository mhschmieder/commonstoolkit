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

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;

public final class NaturalEnvironment {

    // Declare default constants, where appropriate, for all fields.
    public static final double    TEMPERATURE_K_DEFAULT           =
                                                        PhysicsConstants.ROOM_TEMPERATURE_K;
    public static final double    HUMIDITY_RELATIVE_DEFAULT       = 50d;
    public static final double    PRESSURE_PA_DEFAULT             =
                                                      PhysicsConstants.PRESSURE_REFERENCE_PA;
    public static final boolean   AIR_ATTENUATION_APPLIED_DEFAULT = true;

    // Natural Environment is stored in standard scientific units, even though
    // it will most likely be used in domain units associate with air on earth,
    // where the natural limits are well established and inform different units.
    private final DoubleProperty  temperatureK;
    private final DoubleProperty  humidityRelative;
    private final DoubleProperty  pressurePa;
    private final BooleanProperty airAttenuationApplied;

    // NOTE: This field has to follow JavaFX Property Beans conventions.
    public BooleanBinding         naturalEnvironmentChanged;

    /**
     * This is the default constructor; it sets all instance variables to
     * default values, initializing anything that requires memory allocation.
     */
    public NaturalEnvironment() {
        this( TEMPERATURE_K_DEFAULT,
              HUMIDITY_RELATIVE_DEFAULT,
              PRESSURE_PA_DEFAULT,
              AIR_ATTENUATION_APPLIED_DEFAULT );
    }

    /** This is the fully qualified constructor. */
    public NaturalEnvironment( final double pTemperatureK,
                               final double pHumidityRelative,
                               final double pPressurePa,
                               final boolean pAirAttenuationApplied ) {
        temperatureK = new SimpleDoubleProperty( pTemperatureK );
        humidityRelative = new SimpleDoubleProperty( pHumidityRelative );
        pressurePa = new SimpleDoubleProperty( pPressurePa );
        airAttenuationApplied = new SimpleBooleanProperty( pAirAttenuationApplied );

        // Bind all of the properties to the associated dirty flag.
        // NOTE: This is done during initialization, as it is best to make
        // singleton objects and just update their values vs. reconstructing.
        bindProperties();
    }

    /**
     * This is the copy constructor, and is offered in place of clone() to
     * guarantee that the source object is never modified by the new target
     * object created here.
     *
     * @param pNaturalEnvironment
     *            The Natural Environment reference for the copy
     */
    public NaturalEnvironment( final NaturalEnvironment pNaturalEnvironment ) {
        this( pNaturalEnvironment.getTemperatureK(),
              pNaturalEnvironment.getHumidityRelative(),
              pNaturalEnvironment.getPressurePa(),
              pNaturalEnvironment.isAirAttenuationApplied() );
    }

    public BooleanProperty airAttenuationAppliedProperty() {
        return airAttenuationApplied;
    }

    private void bindProperties() {
        // Establish the dirty flag criteria as any assignable value change.
        naturalEnvironmentChanged = new BooleanBinding() {
            {
                // When any of these assignable values change, the
                // naturalEnvironmentChanged Boolean Binding is invalidated and
                // notifies its listeners.
                super.bind( temperatureKProperty(),
                            humidityRelativeProperty(),
                            pressurePaProperty(),
                            airAttenuationAppliedProperty() );
            }

            // Just auto-clear the invalidation by overriding with a status that
            // is affirmative of a change having triggered the call.
            @Override
            protected boolean computeValue() {
                return true;
            }
        };
    }

    // NOTE: Cloning is disabled as it is dangerous; use the copy constructor
    // instead.
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public double getHumidityRelative() {
        return humidityRelative.get();
    }

    public double getPressure( final PressureUnit pPressureUnit ) {
        switch ( pPressureUnit ) {
        case KILOPASCALS:
            return getPressureKpa();
        case PASCALS:
            return getPressurePa();
        case MILLIBARS:
            return getPressureMb();
        case ATMOSPHERES:
            return getPressureAtm();
        default:
            final String errMessage = "Unexpected PressureUnit " //$NON-NLS-1$
                    + pPressureUnit;
            throw new IllegalArgumentException( errMessage );
        }
    }

    public double getPressureAtm() {
        return UnitConversion.pascalsToAtmospheres( pressurePa.get() );
    }

    public double getPressureKpa() {
        return UnitConversion.pascalsToKilopascals( pressurePa.get() );
    }

    public double getPressureMb() {
        return UnitConversion.pascalsToMillibars( pressurePa.get() );
    }

    public double getPressurePa() {
        return pressurePa.get();
    }

    public double getTemperature( final TemperatureUnit pTemperatureUnit ) {
        switch ( pTemperatureUnit ) {
        case KELVIN:
            return getTemperatureK();
        case CELSIUS:
            return getTemperatureC();
        case FAHRENHEIT:
            return getTemperatureF();
        default:
            final String errMessage = "Unexpected TemperatureUnit " //$NON-NLS-1$
                    + pTemperatureUnit;
            throw new IllegalArgumentException( errMessage );
        }
    }

    public double getTemperatureC() {
        return UnitConversion.kelvinToCelsius( temperatureK.get() );
    }

    public double getTemperatureF() {
        return UnitConversion.kelvinToFahrenheit( temperatureK.get() );
    }

    public double getTemperatureK() {
        return temperatureK.get();
    }

    public DoubleProperty humidityRelativeProperty() {
        return humidityRelative;
    }

    public boolean isAirAttenuationApplied() {
        return airAttenuationApplied.get();
    }

    public DoubleProperty pressurePaProperty() {
        return pressurePa;
    }

    // Default pseudo-constructor.
    public void reset() {
        setNaturalEnvironment( TEMPERATURE_K_DEFAULT,
                               HUMIDITY_RELATIVE_DEFAULT,
                               PRESSURE_PA_DEFAULT,
                               AIR_ATTENUATION_APPLIED_DEFAULT );
    }

    public void setAirAttenuationApplied( final boolean pAirAttenuationApplied ) {
        airAttenuationApplied.set( pAirAttenuationApplied );
    }

    public void setHumidityRelative( final double pHumidityRelative ) {
        humidityRelative.set( pHumidityRelative );
    }

    public void setHumidityRelative( final double pHumidity, final HumidityUnit pHumidityUnit ) {
        // TODO: Implement molar humidity, which requires adding a conversion
        // method to UnitsConversion based on the C++ Physics Library code.
        if ( HumidityUnit.RELATIVE.equals( pHumidityUnit ) ) {
            setHumidityRelative( pHumidity );
        }
    }

    /** Fully qualified pseudo-constructor. */
    public void setNaturalEnvironment( final double pTemperatureK,
                                       final double pHumidityRelative,
                                       final double pPressurePa,
                                       final boolean pAirAttenuationApplied ) {
        setTemperatureK( pTemperatureK );
        setHumidityRelative( pHumidityRelative );
        setPressurePa( pPressurePa );
        setAirAttenuationApplied( pAirAttenuationApplied );
    }

    /** Copy pseudo-constructor. */
    public void setNaturalEnvironment( final NaturalEnvironment pNaturalEnvironment ) {
        setNaturalEnvironment( pNaturalEnvironment.getTemperatureK(),
                               pNaturalEnvironment.getHumidityRelative(),
                               pNaturalEnvironment.getPressurePa(),
                               pNaturalEnvironment.isAirAttenuationApplied() );
    }

    public void setPressure( final double pPressure, final PressureUnit pPressureUnit ) {
        switch ( pPressureUnit ) {
        case KILOPASCALS:
            setPressureKpa( pPressure );
            break;
        case PASCALS:
            setPressurePa( pPressure );
            break;
        case MILLIBARS:
            setPressureMb( pPressure );
            break;
        case ATMOSPHERES:
            setPressureAtm( pPressure );
            break;
        default:
            final String errMessage = "Unexpected PressureUnit " //$NON-NLS-1$
                    + pPressureUnit;
            System.err.println( errMessage );
        }
    }

    public void setPressureAtm( final double pPressureAtm ) {
        pressurePa.set( UnitConversion.atmospheresToPascals( pPressureAtm ) );
    }

    public void setPressureKpa( final double pPressureKpa ) {
        pressurePa.set( UnitConversion.kilopascalsToPascals( pPressureKpa ) );
    }

    public void setPressureMb( final double pPressureMb ) {
        pressurePa.set( UnitConversion.millibarsToPascals( pPressureMb ) );
    }

    public void setPressurePa( final double pPressurePa ) {
        pressurePa.set( pPressurePa );
    }

    public void setTemperature( final double pTemperature,
                                final TemperatureUnit pTemperatureUnit ) {
        switch ( pTemperatureUnit ) {
        case KELVIN:
            setTemperatureK( pTemperature );
            break;
        case CELSIUS:
            setTemperatureC( pTemperature );
            break;
        case FAHRENHEIT:
            setTemperatureF( pTemperature );
            break;
        default:
            final String errMessage = "Unexpected TemperatureUnit " //$NON-NLS-1$
                    + pTemperatureUnit;
            System.err.println( errMessage );
        }
    }

    public void setTemperatureC( final double pTemperatureC ) {
        temperatureK.set( UnitConversion.celsiusToKelvin( pTemperatureC ) );
    }

    public void setTemperatureF( final double pTemperatureF ) {
        temperatureK.set( UnitConversion.fahrenheitToKelvin( pTemperatureF ) );
    }

    public void setTemperatureK( final double pTemperatureK ) {
        temperatureK.set( pTemperatureK );
    }

    public DoubleProperty temperatureKProperty() {
        return temperatureK;
    }

}
