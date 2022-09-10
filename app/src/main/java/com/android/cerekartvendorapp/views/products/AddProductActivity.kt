package com.android.cerekartvendorapp.views.products

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.android.cerekartvendorapp.R
import com.android.cerekartvendorapp.constants.IntentConstant
import com.android.cerekartvendorapp.constants.PermissionConstants
import com.android.cerekartvendorapp.customview.ChooseCameraGalleryBottomSheet
import com.android.cerekartvendorapp.databinding.ActivityAddProductBinding
import com.android.cerekartvendorapp.permissionhelper.PermissionHelper
import com.android.cerekartvendorapp.utils.UtilsFunctions
import com.android.cerekartvendorapp.viewmodel.AddProductViewModel
import com.android.cerekartvendorapp.viewmodel.ForgotPasswordViewModel
import com.android.cerekartvendorapp.views.base.BaseActivity
import com.bumptech.glide.Glide
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options


class AddProductActivity : BaseActivity<ActivityAddProductBinding>(), View.OnClickListener,
    PermissionHelper.IGetPermissionListener,
    ChooseCameraGalleryBottomSheet.PermissionSelectDialogCallBack {
    private var captureFileUri: Uri? = null
    private lateinit var mBinding: ActivityAddProductBinding
    private val mViewModel by lazy { ViewModelProvider(this)[AddProductViewModel::class.java] }
    private var i = 1
    private lateinit var permissionHelper: PermissionHelper
    private var isStockManagement = false

    private var permissions = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE

    )
    private var permissionCamera = arrayOf(
        Manifest.permission.CAMERA
    )

    override fun getLayoutId(): Int {
        return R.layout.activity_add_product
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = getViewDataBinding()
        mBinding.model = mViewModel
        getIntentData()
        setUpData()
        setClickListeners()
        setObserver()
    }


    private fun getIntentData() {
        intent?.extras?.let {
            isStockManagement = intent.getBooleanExtra(IntentConstant.key_stock_avail, false)
        }

    }

    private fun setUpData() {
        permissionHelper = PermissionHelper()
        mBinding.topView.imgeNavigation.setImageResource(R.drawable.ic_profile_back)
        if (!isStockManagement) {
            mBinding.constStock.visibility = View.GONE
            mBinding.edtProductQuantity.visibility = View.GONE
        }
        val list = resources.getStringArray(R.array.product_tax).toList()
        val listUnit = resources.getStringArray(R.array.product_tax).toList()
        val adapter = ArrayAdapter(this, R.layout.layout_spinner_item, list)
        val adapter1 = ArrayAdapter(this, R.layout.layout_spinner_item, listUnit)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mBinding.spinnerMediaType.adapter = adapter
        mBinding.spinnerUnitType.adapter = adapter1
        mBinding.switchStock?.setOnCheckedChangeListener({ _ , isChecked ->
            if(isChecked)
               mBinding.edtProductQuantity.visibility=View.VISIBLE
            else
                mBinding.edtProductQuantity.visibility=View.GONE

        })

    }

    private fun setObserver() {
        setBaseViewModel(mViewModel)

        mViewModel.getValidationLiveData().observe(this, {
            when (it.type) {
                0 -> {
                    mBinding.edtProductQuantity.setError(it.message)
                    mBinding.edtProductQuantity.requestFocus()
                }
                1 -> {
                    mBinding.edtProductName.setError(it.message)
                    mBinding.edtProductName.requestFocus()
                }
                2 -> showToastShort(it.message)
                3 -> {
                    mBinding.edtDesc.setError(it.message)
                    mBinding.edtDesc.requestFocus()
                }
                4 -> showToastShort(it.message)
                5 -> {
                    mBinding.edtDuration.setError(it.message)
                    mBinding.edtDuration.requestFocus()
                }
                6 -> {
                    mBinding.edtPrice.setError(it.message)
                    mBinding.edtPrice.requestFocus()

                }
            }
        })
        mViewModel.tempResponse.observe(this, {
            showToastShort("Success")
        })
    }

    private fun setClickListeners() {
        mBinding.topView.imgeNavigation.setOnClickListener(this)
        mBinding.tvUploadImage.setOnClickListener(this)
        mBinding.btnSave.setOnClickListener(this)
        permissionHelper.setListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0) {
            mBinding.topView.imgeNavigation -> {
                finish()
            }
            mBinding.btnSave -> {
                mViewModel.isStockManagement = isStockManagement
                if (mBinding.radioOne.isChecked)
                    mViewModel.itemType = 0
                else if (mBinding.radioTwo.isChecked)
                    mViewModel.itemType = 1

                mViewModel.unit = mBinding.spinnerUnitType.selectedItemPosition
                mViewModel.addProduct()
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


}