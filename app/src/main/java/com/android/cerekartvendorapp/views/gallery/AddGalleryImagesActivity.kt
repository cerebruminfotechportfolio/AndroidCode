package com.android.cerekartvendorapp.views.gallery

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.android.cerekartvendorapp.R
import com.android.cerekartvendorapp.constants.PermissionConstants
import com.android.cerekartvendorapp.customview.ChooseCameraGalleryBottomSheet
import com.android.cerekartvendorapp.databinding.ActivityAddGalleryImagesBinding
import com.android.cerekartvendorapp.databinding.ActivityAddSubCategoryBinding
import com.android.cerekartvendorapp.permissionhelper.PermissionHelper
import com.android.cerekartvendorapp.utils.FileUtils
import com.android.cerekartvendorapp.utils.UtilsFunctions
import com.android.cerekartvendorapp.viewmodel.ForgotPasswordViewModel
import com.android.cerekartvendorapp.views.base.BaseActivity
import com.bumptech.glide.Glide
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener


class AddGalleryImagesActivity : BaseActivity<ActivityAddGalleryImagesBinding>(),
    View.OnClickListener,
    PermissionHelper.IGetPermissionListener,
    ChooseCameraGalleryBottomSheet.PermissionSelectDialogCallBack {
    private var captureFileUri: Uri? = null
    private lateinit var mBinding: ActivityAddGalleryImagesBinding
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
        return R.layout.activity_add_gallery_images
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
        val list = resources.getStringArray(R.array.media_type).toList()
        val adapter = ArrayAdapter(this, R.layout.layout_spinner_item, list)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mBinding.spinnerMediaType.adapter = adapter
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
        mBinding.tvUploadImage.setOnClickListener(this)
        permissionHelper.setListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0) {
            mBinding.topView.imgeNavigation -> {
                finish()
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

                PermissionConstants.REQ_VIDEO -> {
                    if (data != null && data.data != null) {
                        val imageurl: Uri = data.data!!
                        val path = FileUtils.getRealPathFromURI(this, imageurl)
                        path?.let {

                             Glide.with(this).load(imageurl).thumbnail(0.1f)
                                 .into(mBinding.ivCat)
                                mBinding.tvUploadImage.visibility = View.GONE
                                mBinding.ivCat.visibility = View.VISIBLE

                        }

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
        if (pos == 0) {
            if (mBinding.spinnerMediaType.selectedItemPosition == 0)
                UtilsFunctions.onGalleryJustImageChoose(this)
            else
                UtilsFunctions.onGalleryJustVideoChoose(this)

        } else {
            captureFileUri = UtilsFunctions.captureFile(this)
            captureFileUri?.let { UtilsFunctions.openCamera(this, it) }
        }
    }


}