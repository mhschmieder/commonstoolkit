/*
 * MIT License
 *
 * Copyright (c) 2020, 2025, Mark Schmieder. All rights reserved.
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
 * This file is part of the jcommons Library
 *
 * You should have received a copy of the MIT License along with the jcommons
 * Library. If not, see <https://opensource.org/licenses/MIT>.
 *
 * Project: https://github.com/mhschmieder/jcommons
 */
package com.mhschmieder.jcommons.util;

import java.time.LocalDate;

/**
 * Non-observable data model for non-GUI elements associated with Project
 * Properties, when we don't want to bring along JavaFX for its Observables.
 */
public class ProjectProperties {

    // Declare default constants, where appropriate, for all fields.
    public static final String DEFAULT_PROJECT_NAME = "";
    public static final String DEFAULT_PROJECT_TYPE = "";
    public static final String DEFAULT_PROJECT_LOCATION = "";
    public static final String DEFAULT_PROJECT_AUTHOR = "";
    public static final LocalDate DEFAULT_PROJECT_DATE = LocalDate.now();
    public static final String DEFAULT_PROJECT_NOTES = "";

    /**
     * The Project Name is usually derived from filename but can be different.
     */
    protected String projectName;

    /**
     * The Project Type, or Scenario Type in some contexts.
     */
    protected String projectType;

    /**
     * A descriptive location; could be a Venue Name in some contexts.
     */
    protected String projectLocation;

    /**
     * Name of the primary author for the projec, or its most recent revision.
     */
    protected String projectAuthor;

    /**
     * The date that the project was created or last edited, in local time zone.
     */
    protected LocalDate projectDate;

    /**
     * Notes for the project; unrestricted in word count.
     */
    protected String projectNotes;

    /**
     * Makes a {@code ProjectProperties} instance using default values.
     * <p>
     * This is the default constructor; it sets all instance variables to
     * default values, initializing anything that requires memory allocation.
     */
    public ProjectProperties() {
        this(
                DEFAULT_PROJECT_NAME,
                DEFAULT_PROJECT_TYPE,
                DEFAULT_PROJECT_LOCATION,
                DEFAULT_PROJECT_AUTHOR,
                DEFAULT_PROJECT_DATE,
                DEFAULT_PROJECT_NOTES );
    }

    /*
     * This is the fully qualified constructor.
     */
    public ProjectProperties( final String pProjectName,
                              final String pProjectType,
                              final String pProjectLocation,
                              final String pProjectAuthor,
                              final LocalDate pProjectDate,
                              final String pProjectNotes ) {
        projectName = pProjectName;
        projectType = pProjectType;
        projectLocation = pProjectLocation;
        projectAuthor = pProjectAuthor;
        projectDate = pProjectDate;
        projectNotes = pProjectNotes;
    }

    /**
     * Makes a copy of the referenced {@code projectProperties} object.
     * <p>
     * This is the copy constructor, and is offered in place of clone() to
     * guarantee that the source object is never modified by the new target
     * object created here.
     *
     * @param pProjectProperties
     *            The Project Properties reference for the copy
     */
    public ProjectProperties( final ProjectProperties pProjectProperties ) {
        this(
                pProjectProperties.getProjectName(),
                pProjectProperties.getProjectType(),
                pProjectProperties.getProjectLocation(),
                pProjectProperties.getProjectAuthor(),
                pProjectProperties.getProjectDate(),
                pProjectProperties.getProjectNotes() );
    }

    // NOTE: Cloning is disabled as it is dangerous; use the copy constructor
    //  instead.
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    /**
     * Resets all fields to their default values, which are blank.
     * <p>
     * Serves as a default pseudo-constructor.
     */
    public void reset() {
        setProjectProperties(
                DEFAULT_PROJECT_NAME,
                DEFAULT_PROJECT_TYPE,
                DEFAULT_PROJECT_LOCATION,
                DEFAULT_PROJECT_AUTHOR,
                DEFAULT_PROJECT_DATE,
                DEFAULT_PROJECT_NOTES );
    }

    /*
     * Fully qualified pseudo-constructor.
     */
    public void setProjectProperties( final String pProjectName,
                                      final String pProjectType,
                                      final String pProjectLocation,
                                      final String pProjectAuthor,
                                      final LocalDate pProjectDate,
                                      final String pProjectNotes ) {
        setProjectName( pProjectName );
        setProjectType( pProjectType );
        setProjectLocation( pProjectLocation );
        setProjectAuthor( pProjectAuthor );
        setProjectDate( pProjectDate );

        if ( pProjectNotes != null ) {
            setProjectNotes( pProjectNotes );
        }
    }

    /**
     * Sets all fields to match the values in the referenced instance.
     * <p>
     * This serves as a copy pseudo-constructor.
     *
     * @param pProjectProperties
     *            The Project Properties reference for the copy
     */
    public void setProjectProperties(
            final ProjectProperties pProjectProperties ) {
        setProjectProperties(
                pProjectProperties.getProjectName(),
                pProjectProperties.getProjectType(),
                pProjectProperties.getProjectLocation(),
                pProjectProperties.getProjectAuthor(),
                pProjectProperties.getProjectDate(),
                pProjectProperties.getProjectNotes() );
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName( final String pProjectName ) {
        projectName = pProjectName;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType( final String pProjectType ) {
        projectType = pProjectType;
    }

    public String getProjectLocation() {
        return projectLocation;
    }

    public void setProjectLocation( final String pProjectLocation ) {
        projectLocation = pProjectLocation;
    }

    public String getProjectAuthor() {
        return projectAuthor;
    }

    public void setProjectAuthor( final String pProjectAuthor ) {
        projectAuthor = pProjectAuthor;
    }

    public LocalDate getProjectDate() {
        return projectDate;
    }

    public void setProjectDate( final LocalDate pProjectDate ) {
        projectDate = pProjectDate;
    }

    public String getProjectNotes() {
        return projectNotes;
    }

    public void setProjectNotes( final String pProjectNotes ) {
        projectNotes = pProjectNotes;
    }
}
