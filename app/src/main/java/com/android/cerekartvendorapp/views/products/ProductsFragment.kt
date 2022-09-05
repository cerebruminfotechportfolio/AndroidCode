package com.android.cerekartvendorapp.views.products

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.android.cerekartvendorapp.R
import com.android.cerekartvendorapp.databinding.FragmentProductBinding
import com.android.cerekartvendorapp.views.base.BaseFragment

class ProductsFragment : BaseFragment<FragmentProductBinding>(), View.OnClickListener{
    private lateinit var mBinding: FragmentProductBinding

    override fun getLayoutId(): Int {
        return R.layout.fragment_product
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = getViewDataBinding()
        setClickListeners()
    }


    private fun setClickListeners() {
        mBinding.tvAddOns.setOnClickListener(this)
        mBinding.tvProductsCatalouge.setOnClickListener(this)
        mBinding.tvAllProducts.setOnClickListener(this)
    }


    override fun onClick(p0: View?) {
        when (p0) {
            mBinding.tvProductsCatalouge -> {
                startActivity(Intent(requireActivity(),ProductCatalugeListingActivity::class.java))
            }
            mBinding.tvAllProducts -> {
                startActivity(Intent(requireActivity(),AllProductListingActivity::class.java))
            }

        }
    }
}