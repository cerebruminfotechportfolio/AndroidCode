package com.android.cerekartvendorapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.cerekartvendorapp.BR
import com.android.cerekartvendorapp.R
import com.android.cerekartvendorapp.callbacks.AdapterItemClickCallback
import com.android.cerekartvendorapp.callbacks.PopupMenuClick
import com.android.cerekartvendorapp.databinding.ItemRowProductsBinding
import com.android.cerekartvendorapp.modal.UserTypeModal


class AllProductListAdapter(
    val callback: PopupMenuClick,
    val adapterItemClickCallback: AdapterItemClickCallback
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var dataList = ArrayList<UserTypeModal>()
    private var rec: RecyclerView? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_row_products, parent, false
            )
        )
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        rec = recyclerView

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                holder.binding.mainItem.setOnClickListener {
                    adapterItemClickCallback.onItemSelect(position)
                }

                // holder.bind(dataList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return 10
        /* return dataList.size*/

    }


    fun setData(emailList: ArrayList<UserTypeModal>) {
        this.dataList = emailList
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemRowProductsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: UserTypeModal) {
            binding.setVariable(BR.model, data)
            binding.executePendingBindings()

        }

    }
}