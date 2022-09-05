package com.android.cerekartvendorapp.views.products

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.android.cerekartvendorapp.R
import com.android.cerekartvendorapp.constants.PermissionConstants
import com.android.cerekartvendorapp.customview.ChooseCameraGalleryBottomSheet
import com.android.cerekartvendorapp.databinding.ActivityAddSubCategoryBinding
import com.android.cerekartvendorapp.permissionhelper.PermissionHelper
import com.android.cerekartvendorapp.utils.UtilsFunctions
import com.android.cerekartvendorapp.viewmodel.ForgotPasswordViewModel
import com.android.cerekartvendorapp.views.base.BaseActivity
import com.bumptech.glide.Glide
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener


class AddSubCategoryActivity : BaseActivity<ActivityAddSubCategoryBinding>(), View.OnClickListener,
    PermissionHelper.IGetPermissionListener,
    ChooseCameraGalleryBottomSheet.PermissionSelectDialogCallBack {
    private var captureFileUri: Uri? = null
    private lateinit var mBinding: ActivityAddSubCategoryBinding
    private val mViewModel by lazy { ViewModelProvider(this)[ForgotPasswordViewModel::class.java] }
    private var i = 1
    private lateinit var permissionHelper: PermissionHelper

    private var permissions = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE

    )
    private var permissionCamera = arrayOf(
        Manifest.permission.CAMERA
    )

    override fun getLayoutId(): Int {
        return R.layout.activity_add_sub_category
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = getViewDataBinding()
        setUpData()
        setClickListeners()
        setObserver()
    }

    private fun setUpData() {
        permissionHelper = PermissionHelper()
        mBinding.topView.imgeNavigation.setImageResource(R.drawable.ic_profile_back)
    }

    private fun addNewView() {
        val inflater = LayoutInflater.from(this).inflate(R.layout.layout_dynamic_edittext, null)
        mBinding.parentLinearLayout.addView(inflater)
    }

    fun onDelete(view: View) {
        mBinding.parentLinearLayout.removeView(view.parent as View)
        i--
    }

    private fun setObserver() {
        setBaseViewModel(mViewModel)

        mViewModel.getValidationLiveData().observe(this, {
            showToastShort(it.message)
        })
        mViewModel.tempResponse.observe(this, {
            showToastShort("Success")
        })
    }

    private fun setClickListeners() {
        mBinding.topView.imgeNavigation.setOnClickListener(this)
        mBinding.tvAddMore.setOnClickListener(this)
        mBinding.tvUploadImage.setOnClickListener(this)
        mBinding.linColour.setOnClickListener(this)
        permissionHelper.setListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0) {
            mBinding.topView.imgeNavigation -> {
                finish()
            }
            mBinding.linColour -> {
                selectColour()
            }
            mBinding.tvAddMore -> {
                if (i == 8)
                    showToastShort(getString(R.string.max_cat_allowed))
                else {
                    addNewView()
                    i++
                }
            }
            mBinding.tvUploadImage -> {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                    if (permissionHelper.hasPermission(
                            this,
                            permissions,
                            PermissionConstants.REQ_CAMERA
                        )
                    ) {
                        ChooseCameraGalleryBottomSheet(this, this).show()

                    }

                } else {
                    if (permissionHelper.hasPermission(
                            this,
                            permissionCamera,
                            PermissionConstants.REQ_CAMERA
                        )
                    )
                        ChooseCameraGalleryBottomSheet(this, this).show()


                }
            }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                PermissionConstants.REQ_GALLERY -> {
                    if (data != null && data.data != null) {
                        val imageurl: Uri = data.data!!

                        cropImage.launch(
                            options(imageurl) {
                                setGuidelines(CropImageView.Guidelines.ON)
                            }
                        )

                    }

                }
                PermissionConstants.REQ_CAMERA -> {
                    if (captureFileUri != null) {
                        cropImage.launch(
                            options(captureFileUri) {
                                setGuidelines(CropImageView.Guidelines.ON)
                            }
                        )


                    }
                }
            }
        }
    }

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            val imageurl = result.uriContent
            imageurl?.let {
                val path = result.getUriFilePath(this, true)
                mBinding.tvUploadImage.visibility = View.GONE
                mBinding.ivCat.visibility = View.VISIBLE
                Glide.with(this).load(path).into(mBinding.ivCat)
            }

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionHelper.setPermissionResult(requestCode, permissions, grantResults)
    }

    override fun permissionGiven(requestCode: Int) {
        when (requestCode) {
            PermissionConstants.REQ_CAMERA ->
                ChooseCameraGalleryBottomSheet(this, this).show()

        }
    }

    override fun permissionCancel(requestCode: Int) {
    }

    override fun onSelect(pos: Int) {
        if (pos == 0)
            UtilsFunctions.onGalleryJustImageChoose(this)
        else {
            captureFileUri = UtilsFunctions.captureFile(this)
            captureFileUri?.let { UtilsFunctions.openCamera(this, it) }
        }
    }

    private fun selectColour() {
        ColorPickerDialog.Builder(this)
            .setTitle("ColorPicker Dialog")
            .setPreferenceName("MyColorPickerDialog")
            .setPositiveButton(getString(R.string.ok),
                ColorEnvelopeListener { envelope, fromUser ->
                    mBinding.tvColorSelected.setBackgroundColor(envelope.color)
                })
            .setNegativeButton(
                getString(R.string.cancel)
            ) { dialogInterface, i -> dialogInterface.dismiss() }
            .attachAlphaSlideBar(true) // the default value is true.
            .attachBrightnessSlideBar(true) // the default value is true.
            .setBottomSpace(12) // set a bottom space between the last slidebar and buttons.
            .show()
    }
}