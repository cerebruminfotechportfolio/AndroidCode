package com.android.cerekartvendorapp.views.products

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.cerekartvendorapp.R
import com.android.cerekartvendorapp.adapters.AllProductListAdapter
import com.android.cerekartvendorapp.adapters.ProductFilterAdapter
import com.android.cerekartvendorapp.callbacks.AdapterItemClickCallback
import com.android.cerekartvendorapp.callbacks.PopupMenuClick
import com.android.cerekartvendorapp.callbacks.TextWatcherCallback
import com.android.cerekartvendorapp.constants.IntentConstant
import com.android.cerekartvendorapp.customview.ChooseFilterBottomSheet
import com.android.cerekartvendorapp.customview.EditTextWatcher
import com.android.cerekartvendorapp.databinding.ActivityProductCatalougeListingBinding
import com.android.cerekartvendorapp.viewmodel.ForgotPasswordViewModel
import com.android.cerekartvendorapp.views.base.BaseActivity


class AllProductListingActivity : BaseActivity<ActivityProductCatalougeListingBinding>(),
    View.OnClickListener,
    PopupMenuClick, AdapterItemClickCallback, ProductFilterAdapter.ClickListener {
    private  var itemSort: String=""
    private lateinit var mAdapter: AllProductListAdapter
    private var searchKey: String? = null
    private var itemClick = -1
    private lateinit var mBinding: ActivityProductCatalougeListingBinding
    private val mViewModel by lazy { ViewModelProvider(this)[ForgotPasswordViewModel::class.java] }
    private lateinit var sheet: ChooseFilterBottomSheet

    override fun getLayoutId(): Int {
        return R.layout.activity_product_catalouge_listing
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = getViewDataBinding()
        getIntentData()
        setClickListeners()
        setUpData()
        setObserver()
        initRecyclerView()
    }

    private fun getIntentData() {
        intent?.getStringExtra(IntentConstant.key_cat_name)
        intent?.getStringExtra(IntentConstant.key_cat_name)
    }

    private fun initRecyclerView() {
        mAdapter = AllProductListAdapter(this, this)
        val layoutRecManager = LinearLayoutManager(this)
        mBinding.rvproduct.apply {
            layoutManager = layoutRecManager
            adapter = mAdapter
        }
    }

    private fun setUpData() {
        mBinding.topView.imgeNavigation.setImageResource(R.drawable.ic_profile_back)
        mBinding.tvProducts.setText(getString(R.string.all_products))
        mBinding.ivAdd.visibility = View.VISIBLE
        mBinding.linFilter.visibility = View.VISIBLE
        mBinding.tvCategories.visibility = View.GONE
    }


    private fun setObserver() {
        setBaseViewModel(mViewModel)
    }

    private fun setClickListeners() {
        mBinding.topView.imgeNavigation.setOnClickListener(this)
        mBinding.tvSort.setOnClickListener(this)
        mBinding.tvItem.setOnClickListener(this)
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

    override fun onClick(p0: View?) {
        val dataList = ArrayList<String>()
        when (p0) {
            mBinding.topView.imgeNavigation -> {
                finish()
            }
            mBinding.tvSort -> {
                itemClick = 0
                val list = resources.getStringArray(R.array.filter_product_sort).toList()
                dataList.addAll(list)
                sheet = ChooseFilterBottomSheet(this, this, itemSort, dataList,itemClick)
                sheet.show()

            }
            mBinding.tvItem->
            {
                itemClick = 1
                val list = resources.getStringArray(R.array.filter_product_item_type).toList()
                dataList.addAll(list)
                sheet = ChooseFilterBottomSheet(this, this, itemSort, dataList,itemClick)
                sheet.show()
            }

        }

    }

    override fun onSelect(pos: Int, itemPos: Int) {
        when (pos) {
            2 -> startActivity(Intent(this, AddSubCategoryActivity::class.java))
        }
        Handler(Looper.getMainLooper()).postDelayed({
            if (itemPos == mAdapter.itemCount - 1) {
                mBinding.rvproduct.setPadding(0, 0, 0, 60)
                mBinding.rvproduct.scrollToPosition(mAdapter.itemCount - 1)
            }
        }, 300)
    }

    override fun onItemSelect(pos: Int) {
        val intent = Intent(this, SubCategoryProductListingActivity::class.java)
        intent.apply {
            putExtra(IntentConstant.key_cat_id, "")
            putExtra(IntentConstant.key_cat_name, "")
            // startActivity(intent)
        }
    }

    override fun onTagClick(item: String) {
        sheet.dismiss()
        when (itemClick) {
            0 -> {
                mBinding.tvSort.text = item
                itemSort = item

            }
            1 -> {
                mBinding.tvItem.text = item
                itemSort = item

            }
        }

    }
}