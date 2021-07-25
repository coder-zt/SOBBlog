package com.coder.zt.sobblog.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.RvMiniMenuBinding
import com.coder.zt.sobblog.model.user.InteractInfo

class MineMenuAdapter(val menuList: List<androidx.core.util.Pair<Int, String>>, val callback:(title:String)->Unit):RecyclerView.Adapter<MineMenuAdapter.ItemView>() {

    companion object{
        private const val TAG = "PopListAdapter"
    }

    private lateinit var interactItem:ItemView;

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
        if(menuList[position].second == "互动管理"){
            interactItem = holder
        }
        holder.setData(menuList[position], callback)
    }

    override fun getItemCount() = menuList.size

    fun setInteractInfo(it: InteractInfo) {

        interactItem.setMessageNumber(it.getMsgCount())
    }


    class ItemView(val inflate:RvMiniMenuBinding):RecyclerView.ViewHolder(inflate.root) {
        fun setData(data: androidx.core.util.Pair<Int, String>, callback:(title:String)->Unit) {
            inflate.data = data
            inflate.ivIcon.setImageResource(data.first)
            inflate.root.setOnClickListener {
                callback(data.second)
            }
        }

        fun setMessageNumber(value:Int){
            if(value == 0){
                inflate.tvMsgNum.visibility = View.GONE
                return
            }
            val msg = if(value >= 100){
                "99+"
            }else{
                "$value"
            }
            inflate.tvMsgNum.visibility = View.VISIBLE
            inflate.tvMsgNum.text = msg
        }
    }

}
