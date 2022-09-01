package com.android.cerekartvendorapp.customview
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.android.cerekartvendorapp.R
import com.android.cerekartvendorapp.databinding.LayoutBottomSheetChooseGalleryBinding
import com.google.android.material.bottomsheet.BottomSheetDialog


class ChooseCameraGalleryBottomSheet(
    context: Context,
    var mCameraDialogCallBack: PermissionSelectDialogCallBack
) :
    BottomSheetDialog(context, R.style.CustomBottomSheetDialogTheme), View.OnClickListener {
    lateinit var mBinding: LayoutBottomSheetChooseGalleryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.layout_bottom_sheet_choose_gallery,
            null,
            false
        )
        setContentView(mBinding.root)
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        setListeners()
    }

    private fun setListeners() {
        mBinding.tvGallery.setOnClickListener(this)
        mBinding.tvCam.setOnClickListener(this)

    }


    override fun onClick(v: View?) {
        dismiss()
        when (v) {
            mBinding.tvGallery -> {
                mCameraDialogCallBack.onSelect(0)

            }
            mBinding.tvCam -> {
                mCameraDialogCallBack.onSelect(1)

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