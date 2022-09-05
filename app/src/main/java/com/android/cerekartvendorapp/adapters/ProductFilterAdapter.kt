package com.android.cerekartvendorapp.adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.cerekartvendorapp.BR
import com.android.cerekartvendorapp.R
import com.android.cerekartvendorapp.databinding.RowItemFilterTagBinding
import com.android.cerekartvendorapp.modal.UserTypeModal


class ProductFilterAdapter(
    var context: Context,
    private var listenr: ClickListener?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var dataList = ArrayList<UserTypeModal>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.row_item_filter_tag, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                holder.binding.rlMainRecord.setOnClickListener {
                    listenr?.onTagClick(dataList.get(position).email)
                }

                holder.bind(dataList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setData(emailList: ArrayList<UserTypeModal>) {
        this.dataList = emailList
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: RowItemFilterTagBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: UserTypeModal) {
            binding.setVariable(BR.model, data)
            binding.executePendingBindings()
            setBackground(data, binding)
        }

    }

    interface ClickListener {
        fun onTagClick(item: String)
    }

    fun getSelectedData(): ArrayList<String> {
        val tagList = ArrayList<String>()
        for (data in dataList) {
            if (data.isSelected)
                tagList.add(data.email)
        }
        return tagList
    }

    private fun setBackground(data: UserTypeModal, binding: RowItemFilterTagBinding) {
        if (data.isSelected) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                binding.tvTag.typeface = context.resources.getFont(R.font.poppins_semibold)
            }
            binding.rlMainRecord.setBackgroundResource(R.drawable.bg_btn_dark_blue)
            binding.rlMainRecord.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.color_1A00DFB7
                )
            )
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                binding.tvTag.typeface = context.resources.getFont(R.font.poppins_regular)
            }
            binding.rlMainRecord.setBackgroundResource(R.drawable.bg_btn_dark_blue)
            binding.rlMainRecord.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        }
    }
}