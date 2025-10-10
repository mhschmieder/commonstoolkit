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
package com.mhschmieder.commonstoolkit.lang;

import com.mhschmieder.commonstoolkit.text.NumberFormatUtilities;
import com.mhschmieder.commonstoolkit.text.TextUtilities;
import com.mhschmieder.commonstoolkit.util.ClientProperties;

import java.text.NumberFormat;
import java.util.Collection;
import java.util.Locale;

/**
 * A fairly complete set of methods for checking, getting, or enforcing (and
 * otherwise managing) unique labels in a collection of {@code LabeledlObject}.
 * <p>
 * For most clients, the most common entry points will be the uniquefyLabel()
 * methods. Four variants are provided, for flexibility in integration. These
 * methods perform an in-place modification of a label, if non-unique.
 * <p>
 * The general approach is to append an underscore and a multi-digit integer.
 * This is partially accomplished by providing a helper method in TextUtilities
 * for creating a {@link NumberFormat} object, and another for appending an int.
 */
public class LabeledObjectManager {

    /**
     * The default constructor is disabled, as this is a static utilities class.
     */
    private LabeledObjectManager() {}


    // Find out if the candidate label is unique.
    public static boolean isLabelUnique( final Collection< ? extends LabeledObject > labeledObjects,
                                         final String labelCandidate ) {
        // Check whether the supplied label candidate is unique within the 
        // context of its type-specific collection.
        // NOTE: The context of invocation isn't thread-safe and is highly
        //  re-entrant, so avoid parallel streams here to avoid freeze-ups.
        final boolean labelNotUnique = labeledObjects.stream().anyMatch( labeledObject -> {
             final String label = labeledObject.getLabel();
            return ( label.equals( labelCandidate ) );
        } );

        return !labelNotUnique;
    }
    
    // Get the corrected label for a new Labeled Object in the collection.
    // NOTE: The separator can be a blank, or an empty string.
    // NOTE: This should only be called by a GUI control when user edits
    //  are saved, to ensure that a bank string is replaced by a default.
    public static String getCorrectedLabel( final Collection< ? extends LabeledObject > labeledObjects,
                                            final String labelCandidate,
                                            final String labelDefault,
                                            final String separator ) {
        // If a blank or null label was provided, replace it with a default.
        return ( ( labelCandidate == null ) || labelCandidate.trim().isEmpty() )
                ? getNewLabelDefault( labeledObjects, labelDefault, separator )
                : labelCandidate;
    }

    // Get the corrected label for a new Labeled Object in the collection.
    // NOTE: The separator can be a blank, or an empty string.
    // NOTE: This should only be called by a GUI control when user edits
    //  are saved, to ensure that a bank string is replaced by a default.
    // NOTE: Some object types, such as Layers, start at zero vs. one.
    public static String getCorrectedLabel( final Collection< ? extends LabeledObject > labeledObjects,
                                            final String labelCandidate,
                                            final String labelDefault,
                                            final String separator,
                                            final boolean startsAtZero ) {
        // If a blank or null label was provided, replace it with a default.
        return ( ( labelCandidate == null ) || labelCandidate.trim().isEmpty() )
                ? getNewLabelDefault( labeledObjects, 
                                      labelDefault, 
                                      separator, 
                                      startsAtZero )
                : labelCandidate;
    }

    // Get the default label for a new Labeled Object in the collection.
    // NOTE: The separator can be a blank, or an empty string.
    public static String getNewLabelDefault( final Collection< ? extends LabeledObject > labeledObjects,
                                             final String labelDefault,
                                             final String separator ) {
        return getNewLabelDefault( labeledObjects,
                                   labelDefault,
                                   separator,
                                   false );
    }


    // Get the default label for a new Labeled Object in the collection.
    // NOTE: The separator can be a blank, or an empty string.
    // NOTE: Some object types, such as Layers, start at zero vs. one.
    public static String getNewLabelDefault( final Collection< ? extends LabeledObject > labeledObjects,
                                             final String labelDefault,
                                             final String separator,
                                             final boolean startsAtZero ) {
        // Bump beyond the current count, as the new Labeled Object hasn't
        // been added to the collection yet, so isn't part of the count.
        int newLabeledObjectNumber = labeledObjects.size();
        if ( !startsAtZero ) {
            newLabeledObjectNumber += 1;
        }
        return getNextAvailableLabel( labeledObjects,
                                      labelDefault,
                                      separator,
                                      newLabeledObjectNumber );
    }
    // Get the first available label from the base number.
    // NOTE: The separator can be a blank, or an empty string.
    public static String getFirstAvailableLabel( final Collection< ? extends LabeledObject > labeledObjects,
                                                 final String labelDefault,
                                                 final String separator ) {
        return getNextAvailableLabel( labeledObjects, labelDefault, separator, 1 );
    }

    // Get the next available label from the current number.
    // NOTE: The separator can be a blank, or an empty string.
    public static String getNextAvailableLabel( final Collection< ? extends LabeledObject > labeledObjects,
                                                final String labelDefault,
                                                final String separator,
                                                final int labeledObjectNumber ) {
        // Recursively search for (and enforce) name-uniqueness of the next
        // label using the current number as the basis.
        String nextAvailableLabel = labelDefault + separator
                + Integer.toString( labeledObjectNumber );
        for ( final LabeledObject labeledObject : labeledObjects ) {
             final String objectLabel = labeledObject.getLabel();
            if ( nextAvailableLabel.equals( objectLabel ) ) {
                // If the proposed label is not unique in the collection, bump
                // the Labeled Object Number recursively until unique.
                nextAvailableLabel = getNextAvailableLabel( labeledObjects,
                                                            labelDefault,
                                                            separator,
                                                            labeledObjectNumber + 1 );
                break;
            }
        }

        return nextAvailableLabel;
    }

    // NOTE: The separator can be a blank, or an empty string.
    public static String getUniqueLabel( final Collection< ? extends LabeledObject > labeledObjects,
                                         final String labelCandidate,
                                         final String labelDefault,
                                         final String separator,
                                         final boolean insertMode ) {
        return getUniqueLabel( labeledObjects,
                               labelCandidate,
                               labelDefault,
                               separator,
                               Locale.getDefault(),
                               insertMode );
    }

    // NOTE: The separator can be a blank, or an empty string.
    public static String getUniqueLabel( final Collection< ? extends LabeledObject > labeledObjects,
                                         final String labelCandidate,
                                         final String labelDefault,
                                         final String separator,
                                         final ClientProperties clientProperties,
                                         final boolean insertMode ) {
         return getUniqueLabel( labeledObjects,
                               labelCandidate,
                               labelDefault,
                               separator,
                               clientProperties.locale,
                               insertMode );
    }

    // NOTE: The separator can be a blank, or an empty string.
    public static String getUniqueLabel( final Collection< ? extends LabeledObject > labeledObjects,
                                         final String labelCandidate,
                                         final String labelDefault,
                                         final String separator,
                                         final Locale locale,
                                         final boolean insertMode ) {
        final NumberFormat uniquefierNumberFormat = NumberFormatUtilities
                .getUniquefierNumberFormat( locale );
        
        return getUniqueLabel( labeledObjects,
                               labelCandidate,
                               labelDefault,
                               separator,
                               uniquefierNumberFormat,
                               insertMode );
    }

    // NOTE: The separator can be a blank, or an empty string.
    public static String getUniqueLabel( final Collection< ? extends LabeledObject > labeledObjects,
                                         final String labelCandidate,
                                         final String labelDefault,
                                         final String separator,
                                         final NumberFormat uniquefierNumberFormat,
                                         final boolean insertMode ) {
        // Ensure label uniqueness in case it's the same as the last object
        // edited or inserted (e.g. no user edits), by bumping if non-unique.
        // NOTE: We must ensure an initial Insert is uniquefied vs. bumped.
        final String newLabelDefault = getNewLabelDefault( labeledObjects, 
                                                           labelDefault, 
                                                           separator );
        return insertMode
            ? getUniqueLabel( labeledObjects, 
                              labelCandidate, 
                              newLabelDefault, 
                              null, 
                              uniquefierNumberFormat )
            : isLabelUnique( labeledObjects, labelCandidate )
                ? labelCandidate
                : newLabelDefault;
    }

    // Get a unique label from the candidate label.
    // NOTE: The default label is only used when the edited label is blank.
    public static String getUniqueLabel( final Collection< ? extends LabeledObject > labeledObjects,
                                         final String labelCandidate,
                                         final String labelDefault,
                                         final String labelToExclude,
                                         final NumberFormat uniquefierNumberFormat ) {
        // Recursively search for (and enforce) name-uniqueness of the label
        // candidate, leaving unadorned if possible. If no label candidate exists, 
        // start with a default label.
        return ( ( labelCandidate == null ) || labelCandidate.trim().isEmpty() )
                ? getUniqueLabel( labeledObjects,
                                  labelDefault,
                                  labelToExclude,
                                  uniquefierNumberFormat )
                : getUniqueLabel( labeledObjects,
                                  labelCandidate,
                                  labelToExclude,
                                  uniquefierNumberFormat );
    }

    public static String getUniqueLabel( final Collection< ? extends LabeledObject > labeledObjects,
                                         final String labelCandidate,
                                         final String labelToExclude,
                                         final NumberFormat uniquefierNumberFormat ) {
        // Only adorn the label candidate if it is non-unique.
        final int uniquefierNumber = 0;
        return getUniqueLabel( labeledObjects,
                               labelCandidate,
                               labelToExclude,
                               uniquefierNumber,
                               uniquefierNumberFormat );
    }

    public static String getUniqueLabel( final Collection< ? extends LabeledObject > labeledObjects,
                                         final String labelCandidate,
                                         final String labelToExclude,
                                         final int uniquefierNumber,
                                         final NumberFormat uniquefierNumberFormat ) {
        // Recursively search for (and enforce) uniqueness of the supplied
        // label candidate and uniquefier number.
        // NOTE: We loop from the start of the collection, to allow reuse of 
        //  deleted names and to minimize or eliminate holes in the numbering.
       final String uniquefierAppendix = TextUtilities
                .getUniquefierAppendix( uniquefierNumber, uniquefierNumberFormat );
        String uniqueLabel = labelCandidate + uniquefierAppendix;
        for ( final LabeledObject labeledObject : labeledObjects ) {
            final String label = labeledObject.getLabel();
            if ( !label.equals( labelToExclude )
                    && label.equals( uniqueLabel ) ) {
                // Recursively guarantee the appendix-adjusted label is also
                // unique, using a hopefully-unique number as the appendix.
                uniqueLabel = getUniqueLabel( labeledObjects,
                                              labelCandidate,
                                              labelToExclude,
                                              uniquefierNumber + 1,
                                              uniquefierNumberFormat );
                break;
            }
        }

        return uniqueLabel;
    }
    
    // NOTE: The separator can be a blank, or an empty string.
    public static void uniquefyLabel( final Collection< ? extends LabeledObject > labeledObjects,
                                      final LabeledObject labeledObject,
                                      final String labelDefault,
                                      final String separator ) {
        uniquefyLabel( labeledObjects,
                       labeledObject,
                       labelDefault,
                       separator,
                       Locale.getDefault() );
    }
   
    // NOTE: The separator can be a blank, or an empty string.
    public static void uniquefyLabel( final Collection< ? extends LabeledObject > labeledObjects,
                                      final LabeledObject labeledObject,
                                      final String labelDefault,
                                      final String separator,
                                      final ClientProperties clientProperties ) {
        uniquefyLabel( labeledObjects,
                       labeledObject,
                       labelDefault,
                       separator,
                       clientProperties.locale );
    }
    
    // NOTE: The separator can be a blank, or an empty string.
    public static void uniquefyLabel( final Collection< ? extends LabeledObject > labeledObjects,
                                      final LabeledObject labeledObject,
                                      final String labelDefault,
                                      final String separator,
                                      final Locale locale ) {
        final NumberFormat uniquefierNumberFormat = NumberFormatUtilities
                .getUniquefierNumberFormat( locale );
        
        uniquefyLabel( labeledObjects,
                       labeledObject,
                       labelDefault,
                       separator,
                       uniquefierNumberFormat );
    }
   
    // NOTE: The separator can be a blank, or an empty string.
    public static void uniquefyLabel( final Collection< ? extends LabeledObject > labeledObjects,
                                      final LabeledObject labeledObject,
                                      final String labelDefault,
                                      final String separator,
                                      final NumberFormat uniquefierNumberFormat ) {
        final String labelCandidate = labeledObject.getLabel();
        final String uniqueLabel = getUniqueLabel( labeledObjects,
                                                   labelCandidate,
                                                   labelDefault,
                                                   separator,
                                                   uniquefierNumberFormat,
                                                   false );
        labeledObject.setLabel( uniqueLabel );
    }
}
