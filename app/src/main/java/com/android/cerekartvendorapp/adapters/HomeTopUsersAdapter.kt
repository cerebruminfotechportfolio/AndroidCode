package com.android.cerekartvendorapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.cerekartvendorapp.BR
import com.android.cerekartvendorapp.R
import com.android.cerekartvendorapp.databinding.ItemRowTopUserBinding
import com.android.cerekartvendorapp.modal.UserTypeModal


class HomeTopUsersAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var dataList = ArrayList<UserTypeModal>()
    private var rec: RecyclerView? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_row_top_user, parent, false
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

    inner class ViewHolder(val binding: ItemRowTopUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: UserTypeModal) {
            binding.setVariable(BR.model, data)
            binding.executePendingBindings()

        }

    }
}