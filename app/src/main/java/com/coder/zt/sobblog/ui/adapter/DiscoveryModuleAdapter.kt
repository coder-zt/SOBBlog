package com.coder.zt.sobblog.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.RvDiscoveryItemBinding

class DiscoveryModuleAdapter(val callback:(i:ItemType)->Unit): RecyclerView.Adapter<DiscoveryModuleAdapter.ItemView>() {

    enum class ItemType{
        TYPE_MOYU,
        TYPE_QUESTION
    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ):ItemView {
        val inflate = DataBindingUtil.inflate<RvDiscoveryItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.rv_discovery_item, parent, false
        )

        return ItemView(inflate, callback)
    }



    override fun onBindViewHolder(holder: ItemView, position: Int) {
        when(position){
            0 ->{
                holder.setData(ItemType.TYPE_MOYU)
            }
            1 ->{
                holder.setData(ItemType.TYPE_QUESTION)
            }
        }
    }

    override fun getItemCount(): Int {
        return 2
    }

    class ItemView(val dataBinding:RvDiscoveryItemBinding,val callback:(i:ItemType)->Unit):RecyclerView.ViewHolder(dataBinding.root) {

        init {

        }

        fun setData(type:ItemType){
            when(type){
                ItemType.TYPE_MOYU ->{
                    dataBinding.tvTypeBtn.text = "鱼塘"
                }
                ItemType.TYPE_QUESTION ->{
                    dataBinding.tvTypeBtn.text = "问答"
                }
            }
            dataBinding.tvTypeBtn.setOnClickListener {
                callback.invoke(type)
            }
        }
    }
}