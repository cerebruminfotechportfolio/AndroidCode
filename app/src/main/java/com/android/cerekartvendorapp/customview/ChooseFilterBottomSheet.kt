package com.android.cerekartvendorapp.customview

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.cerekartvendorapp.R
import com.android.cerekartvendorapp.adapters.ProductFilterAdapter
import com.android.cerekartvendorapp.databinding.LayoutBottomSheetFilterBinding
import com.android.cerekartvendorapp.modal.UserTypeModal
import com.google.android.material.bottomsheet.BottomSheetDialog

import kotlin.collections.ArrayList


class ChooseFilterBottomSheet(
    context: Context,
    private var listener: ProductFilterAdapter.ClickListener?,
    private var tag: String, private var dataList: ArrayList<String>, private val itemPos: Int
) :
    BottomSheetDialog(context, R.style.CustomBottomSheetDialogTheme), View.OnClickListener {
    private lateinit var mAdapter: ProductFilterAdapter
    lateinit var mBinding: LayoutBottomSheetFilterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.layout_bottom_sheet_filter,
            null,
            false
        )
        setContentView(mBinding.root)
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        setListeners()
    }

    private fun setListeners() {
        if (itemPos == 1)
            mBinding.tvTag.setText(context.getString(R.string.item_type))
        mAdapter = ProductFilterAdapter(context, listener)
        val layoutRecManager = LinearLayoutManager(context)
        mBinding.rvFilter.layoutManager = layoutRecManager
        mBinding.rvFilter.adapter = mAdapter
        mBinding.rvFilter.isNestedScrollingEnabled = true
        val tagList = ArrayList<UserTypeModal>()
        for (item in dataList) {
            val data = UserTypeModal(item, "")
            if (item == tag) {
                data.isSelected = true
            }

            tagList.add(data)
        }
        mAdapter.setData(tagList)
        mBinding.ivCross.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        dismiss()
        when (v) {
            mBinding.ivCross -> dismiss()

        }
    }

    /**
     * Callback that types of events emit by the interface
     *
     */
    interface TagSelectDialogCallBack {
        fun onSelect(tags: ArrayList<String>)

    }

}