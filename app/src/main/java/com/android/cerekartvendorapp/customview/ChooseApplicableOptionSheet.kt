package com.android.cerekartvendorapp.customview

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.android.cerekartvendorapp.R
import com.android.cerekartvendorapp.databinding.LayoutBottomSheetApplicableStatusBinding
import com.google.android.material.bottomsheet.BottomSheetDialog


class ChooseApplicableOptionSheet(
    context: Context, val pos: Int,
    var mCameraDialogCallBack: PermissionSelectDialogCallBack
) :
    BottomSheetDialog(context, R.style.CustomBottomSheetDialogTheme), View.OnClickListener {
    lateinit var mBinding: LayoutBottomSheetApplicableStatusBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.layout_bottom_sheet_applicable_status,
            null,
            false
        )
        setContentView(mBinding.root)
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        setListeners()
    }

    private fun setListeners() {
        mBinding.tvAll.setOnClickListener(this)
        mBinding.tvNotApplied.setOnClickListener(this)
        mBinding.tvApplied.setOnClickListener(this)
        when (pos) {
            0 -> mBinding.tvAll.setCompoundDrawablesRelativeWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_nav_orders,
                0
            )
            1 -> mBinding.tvApplied.setCompoundDrawablesRelativeWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_nav_orders,
                0
            )
            2 -> mBinding.tvNotApplied.setCompoundDrawablesRelativeWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_nav_orders,
                0
            )

        }

    }


    override fun onClick(v: View?) {
        dismiss()
        when (v) {
            mBinding.tvAll -> {
                mCameraDialogCallBack.onSelect(0)
                dismiss()
            }
            mBinding.tvApplied -> {
                mCameraDialogCallBack.onSelect(1)
                dismiss()
            }
            mBinding.tvNotApplied -> {
                mCameraDialogCallBack.onSelect(2)
                dismiss()

            }

        }
    }

    /**
     * Callback that types of events emit by the interface
     *
     */
    interface PermissionSelectDialogCallBack {
        fun onSelect(pos: Int)
    }
}