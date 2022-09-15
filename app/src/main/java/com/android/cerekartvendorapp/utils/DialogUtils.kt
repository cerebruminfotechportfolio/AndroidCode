package com.android.cerekartvendorapp.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.core.content.contentValuesOf
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.android.cerekartvendorapp.R
import com.android.cerekartvendorapp.callbacks.DialogCallback

object DialogUtils {

    fun setTaxConfirmationDialog(
        mContext: Activity,
        mInterface: DialogCallback

    ): Dialog {
        val dialogView = Dialog(mContext)
        dialogView.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(mContext),
                R.layout.dialog_apply_tax,
                null,
                false
            )

        dialogView.setContentView(binding.root)
        dialogView.setCancelable(false)
        dialogView.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        dialogView.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val tvSave = dialogView.findViewById<TextView>(R.id.btn_save)
        val tvCancel = dialogView.findViewById<TextView>(R.id.btn_cancel)
        val spinnerTax = dialogView.findViewById<Spinner>(R.id.spinner_tax)
        val list = mContext.resources.getStringArray(R.array.product_tax).toList()
        val adapter = ArrayAdapter(mContext, R.layout.layout_spinner_item_tax, list)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTax.adapter = adapter
        tvCancel.setOnClickListener {
            dialogView.dismiss()
        }
        tvSave.setOnClickListener {

            dialogView.dismiss()
        }



        dialogView.show()

        return dialogView

    }

}