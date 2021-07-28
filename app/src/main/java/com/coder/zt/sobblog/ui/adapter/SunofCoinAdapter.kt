package com.coder.zt.sobblog.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.RvArticleInfoBinding
import com.coder.zt.sobblog.databinding.RvSunofCoinBinding
import com.coder.zt.sobblog.model.user.SunofCoinInfo

/**
 * sunof币变化表的适配器
 */
class SunofCoinAdapter : RecyclerView.Adapter<SunofCoinAdapter.ItemView>() {

    private val mData:MutableList<SunofCoinInfo> = mutableListOf()
    class ItemView(val databinding:RvSunofCoinBinding):RecyclerView.ViewHolder(databinding.root) {
        fun setData(sunofCoinInfo: SunofCoinInfo) {
            databinding.data = sunofCoinInfo
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemView {
        val inflate = DataBindingUtil.inflate<RvSunofCoinBinding>(
            LayoutInflater.from(parent.context),
            R.layout.rv_sunof_coin,
            parent,
            false
        )
        return ItemView(inflate)
    }

    override fun onBindViewHolder(holder: ItemView, position: Int) {
        holder.setData(mData[position])
    }

    override fun getItemCount() = mData.size

    fun setData(data:List<SunofCoinInfo>, loadMore:Boolean){
        if(!loadMore){
            mData.clear()
        }
        if(data.size>0){
            mData.addAll(data)
        }
        notifyDataSetChanged()
    }


    fun addData(data:List<SunofCoinInfo>){
        if(data.size>0){
            mData.addAll(data)
        }
        notifyDataSetChanged()
    }

}
