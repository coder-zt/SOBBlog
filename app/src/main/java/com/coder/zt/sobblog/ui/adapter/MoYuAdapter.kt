package com.coder.zt.sobblog.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.RvMoyuBinding
import com.coder.zt.sobblog.model.moyu.MiniFeed

class MoYuAdapter: RecyclerView.Adapter<MoYuAdapter.ItemView>() {

    val mData by lazy {
        mutableListOf<MiniFeed>()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemView {
        val inflate = DataBindingUtil.inflate<RvMoyuBinding>(
            LayoutInflater.from(parent.context),
            R.layout.rv_moyu,
            parent,
            false
        )
        return  ItemView(inflate)
    }

    override fun onBindViewHolder(holder: ItemView, position: Int) {
        holder.setData(mData[position])
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    fun setData(data:List<MiniFeed>){
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    class ItemView(val inflate:RvMoyuBinding) :RecyclerView.ViewHolder(inflate.root){

        fun setData(miniFeed: MiniFeed) {
            inflate.data = miniFeed
        }

    }
}