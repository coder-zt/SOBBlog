package com.coder.zt.sobblog.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.RvMiniMenuBinding

class MiniMuenAdapter(val menuList: List<androidx.core.util.Pair<Int, String>>, val callback:()->Unit):RecyclerView.Adapter<MiniMuenAdapter.ItemView>() {

    companion object{
        private const val TAG = "PopListAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemView {
        val inflate = DataBindingUtil.inflate<RvMiniMenuBinding>(
            LayoutInflater.from(parent.context),
            R.layout.rv_mini_menu,
            parent,
            false
        )
        return ItemView(inflate)
    }

    override fun onBindViewHolder(holder: ItemView, position: Int) {
        holder.setData(menuList[position], callback)
    }

    override fun getItemCount() = menuList.size


    class ItemView(val inflate:RvMiniMenuBinding):RecyclerView.ViewHolder(inflate.root) {
        fun setData(data: androidx.core.util.Pair<Int, String>, callback:()->Unit) {
            inflate.data = data
            inflate.ivIcon.setImageResource(data.first)
        }
    }

}
