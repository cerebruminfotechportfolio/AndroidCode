package com.android.cerekartvendorapp.views.products

import android.os.Bundle
import android.view.View
import com.android.cerekartvendorapp.R
import com.android.cerekartvendorapp.databinding.ActivityAddSubCategoryBinding
import com.android.cerekartvendorapp.databinding.ActivityProductDetailBinding
import com.android.cerekartvendorapp.permissionhelper.PermissionHelper
import com.android.cerekartvendorapp.views.base.BaseActivity

class ProductDetailActivity :BaseActivity<ActivityProductDetailBinding>(), View.OnClickListener {
    private lateinit var mBinding: ActivityProductDetailBinding

    override fun getLayoutId(): Int {
        return R.layout.activity_product_detail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = getViewDataBinding()
        setUpData()
        setClickListeners()
    }

    private fun setClickListeners() {
        mBinding.topView.imgeNavigation.setOnClickListener(this)
    }

    private fun setUpData() {
        mBinding.topView.imgeNavigation.setImageResource(R.drawable.ic_profile_back)
        mBinding.topView.txtTitle.setText(getString(R.string.product_detail))
        mBinding.topView.imgLogo.visibility = View.GONE
    }

    override fun onClick(p0: View?) {
        when (p0) {
            mBinding.topView.imgeNavigation -> {
                finish()
            }
        }
    }
}