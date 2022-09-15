package com.android.cerekartvendorapp.views.products

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.cerekartvendorapp.R
import com.android.cerekartvendorapp.adapters.ProductCatalogueListAdapter
import com.android.cerekartvendorapp.callbacks.AdapterItemClickCallback
import com.android.cerekartvendorapp.callbacks.DialogCallback
import com.android.cerekartvendorapp.callbacks.PopupMenuClick
import com.android.cerekartvendorapp.callbacks.TextWatcherCallback
import com.android.cerekartvendorapp.constants.IntentConstant
import com.android.cerekartvendorapp.customview.EditTextWatcher
import com.android.cerekartvendorapp.databinding.FragmentProductCatalougeListingBinding
import com.android.cerekartvendorapp.utils.DialogUtils
import com.android.cerekartvendorapp.viewmodel.ForgotPasswordViewModel
import com.android.cerekartvendorapp.views.base.BaseFragment


class ProductCatalougeListingFragment : BaseFragment<FragmentProductCatalougeListingBinding>(),
    PopupMenuClick, AdapterItemClickCallback {
    private lateinit var mAdapter: ProductCatalogueListAdapter
    private var searchKey: String? = null
    private lateinit var mBinding: FragmentProductCatalougeListingBinding
    private val mViewModel by lazy { ViewModelProvider(this)[ForgotPasswordViewModel::class.java] }

    override fun getLayoutId(): Int {
        return R.layout.fragment_product_catalouge_listing
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = getViewDataBinding()
        setClickListeners()
        setUpData()
        setObserver()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        mAdapter = ProductCatalogueListAdapter(this, this)
        val layoutRecManager = LinearLayoutManager(requireActivity())
        mBinding.rvproduct.apply {
            layoutManager = layoutRecManager
            adapter = mAdapter
        }
    }

    private fun setUpData() {
        mBinding.topView.main.visibility = View.GONE

    }


    private fun setObserver() {
        setFragmentBaseViewModel(mViewModel)
    }

    private fun setClickListeners() {
        mBinding.edtSearch.addTextChangedListener(EditTextWatcher(object : TextWatcherCallback {
            override fun onTextChanged(p0: CharSequence?) {
                if (p0.toString().length >= 3) {
                    searchKey = p0.toString()
                    resetData()
                } else if (p0.toString().length == 0) {
                    searchKey = null
                    resetData()

                }


            }

        }))
    }

    private fun resetData() {
        /*mAdapter.dataList.clear()
        mAdapter.notifyDataSetChanged()
        hitApi()*/
    }


    override fun onSelect(pos: Int, itemPos: Int) {
        when (pos) {
            2 -> startActivity(Intent(requireActivity(), AddSubCategoryActivity::class.java))
            3 -> {
                DialogUtils.setTaxConfirmationDialog(requireActivity(), object : DialogCallback {
                    override fun onPositiveClick(reason: String) {

                    }

                })
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            if (itemPos == mAdapter.itemCount - 1) {
                mBinding.rvproduct.setPadding(0, 0, 0, 60)
                mBinding.rvproduct.scrollToPosition(mAdapter.itemCount - 1)
            }
        }, 300)
    }

    override fun onItemSelect(pos: Int) {
        val intent = Intent(requireActivity(), SubCategoryListingActivity::class.java)
        intent.apply {
            putExtra(IntentConstant.key_cat_id, "")
            putExtra(IntentConstant.key_cat_name, "")
            putExtra(IntentConstant.key_stock_avail, true)
            startActivity(intent)
        }
    }
}