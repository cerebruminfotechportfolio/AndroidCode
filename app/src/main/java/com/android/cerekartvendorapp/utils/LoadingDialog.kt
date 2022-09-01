package com.android.cerekartvendorapp.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import com.android.cerekartvendorapp.R

/**
 * This dialog is used as a progress dialog throught the application
 *
 *
 * @param context activity's context
 */
class LoadingDialog(context: Context) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.layout_progress_dialog)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
    }

}