// Copyright (c) Akop Karapetyan
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.

package org.akop.ararat.view

import android.os.Build
import android.text.Editable
import android.text.Selection
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.BaseInputConnection


class CrosswordInputConnection(targetView: View) : BaseInputConnection(targetView, false) {

    //var onInputEventListener: OnInputEventListener? = null
    var myEditable: Editable? = null

    override fun sendKeyEvent(event: KeyEvent): Boolean {
        if (event.keyCode == KeyEvent.KEYCODE_DEL) {
            myEditable!!.append(
                EditableAccomodatingLatinIMETypeNullIssues.ONE_UNPROCESSED_CHARACTER)
            Selection.setSelection(myEditable, 1)
        }
        return super.sendKeyEvent(event)
    }

    override fun getEditable(): Editable {
        if (myEditable == null) {
            myEditable = EditableAccomodatingLatinIMETypeNullIssues(
                EditableAccomodatingLatinIMETypeNullIssues.ONE_UNPROCESSED_CHARACTER)
            Selection.setSelection(myEditable, 1)
        } else {
            val myEditableLength = myEditable!!.length
            if (myEditableLength == 0) {
                myEditable!!.append(
                    EditableAccomodatingLatinIMETypeNullIssues.ONE_UNPROCESSED_CHARACTER)
                Selection.setSelection(myEditable, 1)
            }
        }
        return myEditable!!
    }

    override fun deleteSurroundingText(beforeLength: Int, afterLength: Int): Boolean {
        if(beforeLength == 1 && afterLength == 0) {
            // Send Backspace key down and up events to replace the ones omitted
            // by the LatinIME keyboard.
            super.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL))
            super.sendKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DEL))
        } else {
            //Really, I can't see how this would be invoked, given that we're using
            // TYPE_NULL, for non-buggy versions, but in order to limit the impact
            // of this change as much as possible (i.e., to versions at and above 4.0)
            // I am using the original behavior here for non-affected versions.
            super.deleteSurroundingText(beforeLength, afterLength)
        }
        return true
    }
}
