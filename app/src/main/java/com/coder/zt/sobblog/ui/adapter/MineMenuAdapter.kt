package com.coder.zt.sobblog.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.RvMineFirstMenuBinding
import com.coder.zt.sobblog.databinding.RvMineSecondMenuBinding
import com.coder.zt.sobblog.model.user.InteractInfo

class MineMenuAdapter(private val menuList: List<MenuItemData>, val callback:(title:MenuItemData)->Unit):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object{
        private const val TAG = "PopListAdapter"
    }

    private  var interactItem:ItemView? = null
    private var showType = ShowType.First
//    private val showList = mutableListOf<MenuItemData>()
    enum class ShowType{
        First,
        FirstFirst,
        FirstSecond,
    }
    init{
//        initShowList()
    }

//    private fun initShowList() {
//        showList.clear()
//        for (menuItemData in menuList) {
//            if (menuItemData.grade == ShowType.First) {
//                showList.add(menuItemData)
//            }
//        }
//        notifyDataSetChanged()
//        Log.d(TAG, "initShowList: $showList.size")
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            0->{
                val inflate = DataBindingUtil.inflate<RvMineFirstMenuBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.rv_mine_first_menu,
                    parent,
                    false
                )
                ItemView(inflate)
            }
            1 ->{
                val inflate = DataBindingUtil.inflate<RvMineSecondMenuBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.rv_mine_second_menu,
                    parent,
                    false
                )
                ChildView(inflate)
            }
            else->{
                val inflate = DataBindingUtil.inflate<RvMineSecondMenuBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.rv_mine_second_menu,
                    parent,
                    false
                )
                ChildView(inflate)
            }

        }

    }

    override fun getItemViewType(position: Int): Int {
        var i = -1
        var p = position
        for (menuItemData in menuList) {
            i++
            if (menuItemData.show) {
                menuItemData.position = position - p
                p--
            }
            if(p == -1){
                break
            }
        }
        return if(menuList[i].id < 10){
            0
        }else{
            1
        }

    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var i = -1
        var p = position
        for (menuItemData in menuList) {
            i++
            if (menuItemData.show) {
                menuItemData.position = position - p
                p--
            }
            if(p == -1){
                break
            }
        }
        if(holder is ItemView){
            if(menuList[i].menuName == "互动管理"){
                interactItem = holder
            }
            holder.setData(menuList[i]){
                callback(it)
                if(it.grade == ShowType.First){
                    when(it.id){
                        1->{
                            switchChildMenu(it, 10, position)
                        }
                        2->{
                            switchChildMenu(it, 20, position)
                        }
                    }
                }
            }
        }else if(holder is ChildView){
            holder.setData(menuList[i]){
                callback(it)
                if(it.grade == ShowType.First){
                    when(it.id){
                        1->{
                            switchChildMenu(it, 10, position)
                        }
                        2->{
                            switchChildMenu(it, 20, position)
                        }
                    }
                }
            }
        }

    }

    private fun switchChildMenu(
        it: MenuItemData,
        minId: Int,
        position: Int
    ) {
        if (it.expand) {
            it.expand = false
            for (menuItemData in menuList) {
                when (showType) {
                    ShowType.FirstSecond -> {
                        if (menuItemData.grade == ShowType.FirstSecond && menuItemData.id > minId && menuItemData.id < minId +10) {
                            menuItemData.show = false
                            notifyItemRemoved(position + 1)
                        }
                    }
                }
            }
        } else {
            showType = ShowType.FirstSecond
            it.expand = true
            var i = 1
            for (menuItemData in menuList) {
                when (showType) {
                    ShowType.FirstSecond -> {
                        if (menuItemData.grade == ShowType.FirstSecond && menuItemData.id > minId && menuItemData.id < minId +10) {
                            menuItemData.show = true
                            notifyItemInserted(position + i)
                            i++
                        }
                    }

                }
            }
        }
    }

    override fun getItemCount(): Int {
        var i = 0
        for (menuItemData in menuList) {
            if(menuItemData.show){
                i ++
            }
        }
        return i
    }

    var messageCount = 0
    fun setInteractInfo(it: InteractInfo) {
        if (interactItem == null) {
            messageCount = it.getMsgCount()
            return
        }
        interactItem?.setMessageNumber(it.getMsgCount())
    }


    class ItemView(val inflate:RvMineFirstMenuBinding):RecyclerView.ViewHolder(inflate.root) {
        fun setData(data: MenuItemData, callback:(title:MenuItemData)->Unit) {
            inflate.data = data
            inflate.ivIcon.setImageResource(data.menuIcon)
            inflate.root.setOnClickListener {
                callback(data)
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

    class ChildView(val inflate: RvMineSecondMenuBinding):RecyclerView.ViewHolder(inflate.root) {
        fun setData(data: MenuItemData, callback:(title:MenuItemData)->Unit) {
            inflate.data = data
            inflate.ivIcon.setImageResource(data.menuIcon)
            inflate.root.setOnClickListener {
                callback(data)
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

//我的页面菜单的数据
    data class MenuItemData(val id:Int,
                            val menuName:String,
                            val menuIcon:Int,
                            var position:Int,
                            val grade:ShowType,
                            var show:Boolean,
                            var expand:Boolean)
}
