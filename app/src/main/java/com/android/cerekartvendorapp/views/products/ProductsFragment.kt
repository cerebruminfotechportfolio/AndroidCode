package com.android.cerekartvendorapp.views.products

import android.os.Bundle
import android.view.View
import com.android.cerekartvendorapp.R
import com.android.cerekartvendorapp.databinding.FragmentProductBinding
import com.android.cerekartvendorapp.views.base.BaseFragment
import com.android.cerekartvendorapp.views.gallery.ViewGalleryFragment
import com.android.cerekartvendorapp.views.home.LandingMainActivity
import com.example.moodtrack.igethappy.adapters.CustomFragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ProductsFragment : BaseFragment<FragmentProductBinding>(){
    private lateinit var mBinding: FragmentProductBinding

    override fun getLayoutId(): Int {
        return R.layout.fragment_product
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = getViewDataBinding()
        setUpPagerAdapter()
        setUpData()
    }

    private fun setUpData() {
        ( activity as LandingMainActivity).setUpToolbar(getString(R.string.products))
    }


    private fun setUpPagerAdapter() {
        val adapter = CustomFragmentPagerAdapter(requireActivity())
        adapter.add(ProductCatalougeListingFragment(), "Products Catalouge")
        adapter.add(AllProductListingFragment(), "All Products")
        adapter.add(ViewGalleryFragment(), "Add Ons")
        mBinding.vpProduct.adapter = adapter
        mBinding.vpProduct.setCurrentItem(0)
        TabLayoutMediator(mBinding.tabsContainer, mBinding.vpProduct) { tab, position ->
            tab.text = adapter.getPageTitle(position)
        }.attach()
        mBinding.tabsContainer.tabMode = TabLayout.MODE_FIXED

    }



}