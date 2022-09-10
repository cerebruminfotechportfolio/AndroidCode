package com.android.cerekartvendorapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.cerekartvendorapp.R
import com.android.cerekartvendorapp.callbacks.AdapterItemClickCallback
import com.android.cerekartvendorapp.databinding.RowItemGalleryBinding


class ViewGalleryAdapter(private val callback: AdapterItemClickCallback) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    //var dataList = ArrayList<Images>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.row_item_gallery, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                holder.binding.ivPhotos.setOnClickListener{
                    callback.onItemSelect(position)
                }

               // holder.bind(dataList[position])
            }
        }
    }

    override fun getItemCount(): Int {
       /* return dataList.size*/
        return 20
    }





    inner class ViewHolder(val binding: RowItemGalleryBinding) :
        RecyclerView.ViewHolder(binding.root) {
       /* fun bind(data: Images) {
            binding.setVariable(BR.model, data)
            binding.executePendingBindings()
        }*/

    }

}