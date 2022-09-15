package com.android.cerekartvendorapp.views.coupons

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.android.cerekartvendorapp.R
import com.android.cerekartvendorapp.adapters.ViewGalleryAdapter
import com.android.cerekartvendorapp.callbacks.AdapterItemClickCallback
import com.android.cerekartvendorapp.customview.ChooseApplicableOptionSheet
import com.android.cerekartvendorapp.databinding.FragmentCouponDealBinding
import com.android.cerekartvendorapp.databinding.FragmentViewGalleryBinding
import com.android.cerekartvendorapp.views.base.BaseFragment


class CouponsDealsFragment : BaseFragment<FragmentCouponDealBinding>(), AdapterItemClickCallback,
    View.OnClickListener, ChooseApplicableOptionSheet.PermissionSelectDialogCallBack {
    private lateinit var mAdapter: ViewGalleryAdapter
    private lateinit var mBinding: FragmentCouponDealBinding
    private var  pos =-1

    override fun getLayoutId(): Int {
        return R.layout.fragment_coupon_deal
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = getViewDataBinding()
        setClickListeners()
        initRecyclerView()
    }

    private fun setClickListeners() {
        mBinding.ivFilter.setOnClickListener(this)
    }

    private fun initRecyclerView() {
        mAdapter = ViewGalleryAdapter(this)
        mBinding.rvCoupons.apply {
            layoutManager = GridLayoutManager(requireActivity(), 2)

        }
        mBinding.rvCoupons.adapter = mAdapter

    }

    override fun onItemSelect(pos: Int) {

    }

    override fun onClick(p0: View?) {
        when (p0) {
            mBinding.ivFilter -> {
                ChooseApplicableOptionSheet(requireActivity(),pos,this).show()
            }
        }
    }

    override fun onSelect(pos: Int) {
        this.pos=pos
    }

}