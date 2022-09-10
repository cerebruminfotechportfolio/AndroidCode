package com.android.cerekartvendorapp.views.gallery

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.android.cerekartvendorapp.R
import com.android.cerekartvendorapp.adapters.ViewGalleryAdapter
import com.android.cerekartvendorapp.callbacks.AdapterItemClickCallback
import com.android.cerekartvendorapp.databinding.FragmentViewGalleryBinding
import com.android.cerekartvendorapp.views.base.BaseFragment


class ViewGalleryFragment : BaseFragment<FragmentViewGalleryBinding>(), AdapterItemClickCallback {
    private lateinit var mAdapter: ViewGalleryAdapter
    private lateinit var mBinding: FragmentViewGalleryBinding

    override fun getLayoutId(): Int {
        return R.layout.fragment_view_gallery
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = getViewDataBinding()
        setClickListeners()
        initRecyclerView()
    }

    private fun setClickListeners() {

    }
    private fun initRecyclerView() {
        mAdapter = ViewGalleryAdapter(this)
        mBinding.rvGallery.apply {
            layoutManager = GridLayoutManager(requireActivity(), 2)

        }
        mBinding.rvGallery.adapter = mAdapter

    }

    override fun onItemSelect(pos: Int) {

    }

}