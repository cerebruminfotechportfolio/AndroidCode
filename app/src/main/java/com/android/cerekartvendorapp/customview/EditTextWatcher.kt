package com.android.cerekartvendorapp.customview

import android.text.Editable
import android.text.TextWatcher
import com.android.cerekartvendorapp.callbacks.TextWatcherCallback

class EditTextWatcher(val callback:TextWatcherCallback) : TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
   // no action perfomred
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        callback.onTextChanged(p0)

    }

    override fun afterTextChanged(p0: Editable?) {
        // no action perfomred
    }
}