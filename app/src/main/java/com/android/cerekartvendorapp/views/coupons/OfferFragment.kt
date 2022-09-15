package com.android.cerekartvendorapp.views.coupons

import android.os.Bundle
import android.view.View
import com.android.cerekartvendorapp.R
import com.android.cerekartvendorapp.databinding.FragmentCouponDealBinding
import com.android.cerekartvendorapp.databinding.FragmentOffersBinding
import com.android.cerekartvendorapp.views.base.BaseFragment
import com.android.cerekartvendorapp.views.products.ProductsFragment
import com.example.moodtrack.igethappy.adapters.CustomFragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class OfferFragment : BaseFragment<FragmentOffersBinding>(){
    private lateinit var mBinding: FragmentOffersBinding

    override fun getLayoutId(): Int {
        return R.layout.fragment_offers
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = getViewDataBinding()
        setUpPagerAdapter()
    }


    private fun setUpPagerAdapter() {
        val adapter = CustomFragmentPagerAdapter(requireActivity())
        adapter.add(CouponsDealsFragment(), "Deals")
        adapter.add(CouponsDealsFragment(), "Coupons")
        mBinding.vpCoupons.adapter = adapter
        mBinding.vpCoupons.setCurrentItem(0)
        TabLayoutMediator( mBinding.tabsContainer, mBinding.vpCoupons) { tab, position ->
            tab.text = adapter.getPageTitle(position)
        }.attach()
        mBinding.tabsContainer.tabMode = TabLayout.MODE_FIXED

    }

}