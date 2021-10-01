/*
 * MIT License
 * Copyright (c) 2020, 2021 Mark Schmieder
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * This file is part of the IoToolkit Library
 * You should have received a copy of the MIT License along with the
 * IoToolkit Library. If not, see <https://opensource.org/licenses/MIT>.
 * Project: https://github.com/mhschmieder/iotoolkit
 */
package com.mhschmieder.iotoolkit.io;

public enum FileStatus {
    // NOTE: NOT_OPENED and NOT_SAVED are used to replace the original
    // file status in order to avoid secondary warning dialogs if an
    // immediate warning is needed under certain error conditions.
    CREATED,
    OUT_OF_MEMORY_ERROR,
    GRAPHICS_READ_ERROR,
    GRAPHICS_WRITE_ERROR,
    READ_ERROR,
    WRITE_ERROR,
    CANCELED,
    CLIENT_INCOMPATIBLE,
    NOT_OPENED,
    OPENED,
    OPENED_FOR_RENAME,
    LOADED,
    NOT_SAVED,
    SAVED,
    IMPORTED,
    EXPORTED

}