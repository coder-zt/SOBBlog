package com.coder.zt.sobblog.ui.adapter

import android.util.Log
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
    private var mInteractInfo:InteractInfo? = null

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
                holder.setMessageNumber(mInteractInfo?.getMsgCount()?:0)
            }
            holder.setData(menuList[i]){

                if(it.grade == ShowType.First){
                    when(it.id){
                        1-> switchChildMenu(it, 10, position, holder)
                        2-> switchChildMenu(it, 20, position, holder)
                        3-> callback(it)
                    }
                }
            }
        }else if(holder is ChildView){
            holder.setData(menuList[i]){
                callback(it)
            }
            var messageCount = 0
            when(menuList[i].menuName){
                "回复我的"-> messageCount = mInteractInfo?.atMsgCount?:0
                "给我点赞"-> messageCount = mInteractInfo?.thumbUpMsgCount?:0
                "文章评论"-> messageCount = mInteractInfo?.articleMsgCount?:0
                "动态评论"-> messageCount = mInteractInfo?.momentCommentCount?:0
                "问题回答"-> messageCount = mInteractInfo?.wendaMsgCount?:0
                "系统通知"-> messageCount = mInteractInfo?.systemMsgCount?:0
            }
            holder.setMessageNumber(messageCount)
        }

    }

    private fun switchChildMenu(
        it: MenuItemData,
        minId: Int,
        position: Int,
        view: ItemView
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
                        view.setMessageShow(true)
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
                        view.setMessageShow(false)
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

    fun setInteractInfo(it: InteractInfo) {
        mInteractInfo = it
        interactItem?.setMessageNumber(it.getMsgCount())
    }


    class ItemView(val inflate:RvMineFirstMenuBinding):RecyclerView.ViewHolder(inflate.root) {

        private var meunData:MenuItemData? = null
        fun setData(data: MenuItemData, callback:(title:MenuItemData)->Unit) {
            inflate.data = data
            inflate.ivIcon.setImageResource(data.menuIcon)
            inflate.root.setOnClickListener {
                callback(data)
            }
            meunData = data
        }

        fun setMessageNumber(value:Int){
            var show = true
            meunData?.let {
                show = it.expand
            }
            if(value == 0 || show){
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

        fun setMessageShow(show:Boolean){
            Log.d(TAG, "setMessageShow: ")

            if(show && inflate.tvMsgNum.text != "0"){
                inflate.tvMsgNum.visibility = View.VISIBLE
            }else{
                inflate.tvMsgNum.visibility = View.GONE
            }
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
