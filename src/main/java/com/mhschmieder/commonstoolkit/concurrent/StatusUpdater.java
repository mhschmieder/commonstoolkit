/*
 * MIT License
 *
 * Copyright (c) 2025 Mark Schmieder
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
package com.mhschmieder.commonstoolkit.concurrent;

/**
 * Contracts implementers to provide status update methods, which allows for
 * concurrent task oriented API's to avoid coupling to simpler low-level
 * libraries or unwanted dependency on GUI modules that host a Progress Monitor.
 */
public interface StatusUpdater {

    /**
     * Updates the status, usually of a concurrent task on another thread.
     * <p>
     * Implementers should add this interface to classes that deal with Tasks.
     * <p>
     * As one example, in the JavaFX Service API context, an override would call
     * updateMessage() if the message is null and non-empty, and then it would
     * call updateProgress() with the work done and the total work involved.
     * <p>
     * As total work may be dynamically modified in some calling contexts, in
     * particular when Machine Learning algorithms are in play, it is necessary
     * to provide this parameter on each call even if it never changes.
     * <p>
     * Some downstream use may need to cast longs to doubles, if storing work
     * done as a ratio of the total number of tasks.
     *
     * @param workDone The amount of work done, unitless in {0..totalWork} range
     * @param totalWork The total amount of work expected, unitless
     * @param message An optional message to update related to current status
     */
    void updateStatus( final long workDone,
                       final long totalWork,
                       final String message );
}
