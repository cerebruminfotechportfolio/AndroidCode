package com.android.cerekartvendorapp.views.products

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.cerekartvendorapp.R
import com.android.cerekartvendorapp.adapters.SubCategoryListAdapter
import com.android.cerekartvendorapp.callbacks.AdapterItemClickCallback
import com.android.cerekartvendorapp.callbacks.PopupMenuClick
import com.android.cerekartvendorapp.callbacks.TextWatcherCallback
import com.android.cerekartvendorapp.constants.IntentConstant
import com.android.cerekartvendorapp.customview.EditTextWatcher
import com.android.cerekartvendorapp.databinding.FragmentProductCatalougeListingBinding
import com.android.cerekartvendorapp.viewmodel.ForgotPasswordViewModel
import com.android.cerekartvendorapp.views.base.BaseActivity


class SubCategoryListingActivity : BaseActivity<FragmentProductCatalougeListingBinding>(), View.OnClickListener,
    PopupMenuClick, AdapterItemClickCallback {
    private lateinit var mAdapter: SubCategoryListAdapter
    private  var searchKey: String?=null
    private lateinit var mBinding: FragmentProductCatalougeListingBinding
    private val mViewModel by lazy { ViewModelProvider(this)[ForgotPasswordViewModel::class.java] }
   private  var isStockAvail=false
    override fun getLayoutId(): Int {
        return R.layout.fragment_product_catalouge_listing
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
        isStockAvail=intent?.getBooleanExtra(IntentConstant.key_stock_avail,false)!!
    }

    private fun initRecyclerView() {
        mAdapter = SubCategoryListAdapter(this,this)
        val layoutRecManager = LinearLayoutManager(this)
        mBinding.rvproduct.apply {
            layoutManager = layoutRecManager
            adapter = mAdapter
        }
    }

    private fun setUpData() {
        mBinding.topView.imgeNavigation.setImageResource(R.drawable.ic_profile_back)
        mBinding.topView.imgAdd.setImageResource(R.drawable.ic_add_circle_white)
        mBinding.topView.txtTitle.setText(getString(R.string.food_delivery))
        mBinding.topView.main.visibility=View.VISIBLE
        mBinding.topView.imgLogo.visibility=View.GONE
    }


    private fun setObserver() {
        setBaseViewModel(mViewModel)
    }

    private fun setClickListeners() {
        mBinding.topView.imgeNavigation.setOnClickListener(this)
        mBinding.edtSearch.addTextChangedListener(EditTextWatcher(object :TextWatcherCallback {
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
        when (p0) {
            mBinding.topView.imgeNavigation -> {
                finish()
            }

        }

    }

    override fun onSelect(pos: Int, itemPos: Int) {
       when(pos)
       {
           0-> startActivity(Intent(this,AddProductActivity::class.java).putExtra(IntentConstant.key_stock_avail,isStockAvail))


           2->startActivity(Intent(this,AddSubCategoryActivity::class.java))
       }
        Handler(Looper.getMainLooper()).postDelayed({
            if (itemPos == mAdapter.itemCount - 1) {
                mBinding.rvproduct.setPadding(0, 0, 0, 60)
                mBinding.rvproduct.scrollToPosition(mAdapter.itemCount - 1)
            }
        }, 300)
    }

    override fun onItemSelect(pos: Int) {
       val intent=Intent(this,SubCategoryProductListingActivity::class.java)
        intent.apply {
            putExtra(IntentConstant.key_cat_id,"")
            putExtra(IntentConstant.key_cat_name,"")
            startActivity(intent)
        }
    }
}