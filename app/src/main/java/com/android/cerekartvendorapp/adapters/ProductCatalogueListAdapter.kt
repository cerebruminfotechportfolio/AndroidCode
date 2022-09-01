package com.android.cerekartvendorapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.cerekartvendorapp.BR
import com.android.cerekartvendorapp.R
import com.android.cerekartvendorapp.callbacks.PopupMenuClick
import com.android.cerekartvendorapp.databinding.ItemRowProductCatalougeBinding
import com.android.cerekartvendorapp.modal.UserTypeModal
import com.android.cerekartvendorapp.utils.UtilsFunctions


class ProductCatalogueListAdapter(val callback: PopupMenuClick) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var dataList = ArrayList<UserTypeModal>()
    private var rec: RecyclerView? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_row_product_catalouge, parent, false
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
                holder.binding.ivsetting.tag = position
                holder.binding.ivsetting.setOnClickListener {
                    val tagPos = it.tag as Int
                    if (position == itemCount - 1) {
                        rec?.setPadding(0, 0, 0, 500)
                        rec?.scrollToPosition(itemCount - 1)
                    } else
                        rec?.setPadding(0, 0, 0, 60)
                    UtilsFunctions.showMenu(holder.binding.ivsetting, tagPos, callback)
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

    inner class ViewHolder(val binding: ItemRowProductCatalougeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: UserTypeModal) {
            binding.setVariable(BR.model, data)
            binding.executePendingBindings()

        }

    }
}