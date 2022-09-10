package com.android.cerekartvendorapp.views.gallery

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.android.cerekartvendorapp.R
import com.android.cerekartvendorapp.databinding.FragmentGalleryBinding
import com.android.cerekartvendorapp.views.base.BaseFragment
import com.android.cerekartvendorapp.views.products.ProductsFragment
import com.example.moodtrack.igethappy.adapters.CustomFragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class GalleryFragment : BaseFragment<FragmentGalleryBinding>(), View.OnClickListener {
    private lateinit var mBinding: FragmentGalleryBinding

    override fun getLayoutId(): Int {
        return R.layout.fragment_gallery
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = getViewDataBinding()
        setUpPagerAdapter()
        setClickListeners()
    }

    private fun setClickListeners() {
        mBinding.ivAdd.setOnClickListener(this)
    }


    private fun setUpPagerAdapter() {
        val adapter = CustomFragmentPagerAdapter(requireActivity())
        adapter.add(ViewGalleryFragment(), "All")
        adapter.add(ViewGalleryFragment(), "Photos")
        adapter.add(ViewGalleryFragment(), "Videos")
        mBinding.vpCoupons.adapter = adapter
        mBinding.vpCoupons.setCurrentItem(0)
        TabLayoutMediator(mBinding.tabsContainer, mBinding.vpCoupons) { tab, position ->
            tab.text = adapter.getPageTitle(position)
        }.attach()
        mBinding.tabsContainer.tabMode = TabLayout.MODE_FIXED

    }

    override fun onClick(p0: View?) {
        when (p0) {
            mBinding.ivAdd -> {
                startActivity(Intent(requireActivity(),AddGalleryImagesActivity::class.java))
            }
        }
    }

}