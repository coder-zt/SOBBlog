package com.coder.zt.sobblog.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.MoyoTopViewBinding
import com.coder.zt.sobblog.databinding.RvMoyuBinding
import com.coder.zt.sobblog.model.moyu.MiniFeed

/**
 * 摸鱼板块的内容
 *
 * 请求数据与ui不符文件解决：
 * 在客户端网络模块请求将所有数据请求再以特定的格式返回，加载出错的数据表示加载出错，并提供重新请求数据的点击事件
 */
class MoYuAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object{
        private const val TAG = "MoYuAdapter"
    }

    private val TOP_VIEW:Int = 0
    private val CONTENT_VIEW:Int = 1
    private val listener:()->Unit = {
        checkChildrenState()
    }
    private val contentViewSet:MutableSet<ContentView> = mutableSetOf()
    val mData by lazy {
        mutableListOf<MiniFeed>()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d(TAG, "onCreateViewHolder: $viewType")
        return when(viewType){
            TOP_VIEW->{
                val inflate = DataBindingUtil.inflate<MoyoTopViewBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.moyo_top_view,
                    parent,
                    false
                )
                TopView(inflate)
            }
            else->{
                val inflate = DataBindingUtil.inflate<RvMoyuBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.rv_moyu,
                    parent,
                    false
                )
                ContentView(inflate)
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(position >0){
            val contentItem = holder as ContentView
            contentItem.setData(mData[position-1], position, listener)
            contentViewSet.add(contentItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        Log.d(TAG, "getItemViewType: $position")
        return if(position == 0){
            TOP_VIEW
        }else{
            CONTENT_VIEW
        }
    }
    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount: ${mData.size}")
        return mData.size + 1
    }

    fun setData(data:List<MiniFeed>){
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    fun checkChildrenState() {
        for (contentView in contentViewSet) {
            contentView.checkShowExpansion()
        }
    }

    class ContentView(val inflate:RvMoyuBinding) :RecyclerView.ViewHolder(inflate.root){

        private var showExpansion = false
        fun setData(miniFeed: MiniFeed, position: Int,listener:()->Unit) {
            inflate.data = miniFeed
            val picSize = miniFeed.images.size
            inflate.doBtn.setOnClickListener(object:View.OnClickListener{
                override fun onClick(v: View?) {
                    inflate.doEll.apply {
                        listener.invoke()
                        showExpansion = switchShowState()
                    }
                }
            })
            Log.d(TAG, "setData:  $position ---> $picSize")
            if(picSize == 0){
                inflate.recyclerView.visibility = View.GONE
                return
            }else{
                inflate.recyclerView.visibility = View.VISIBLE
            }
            val span = when {
                picSize > 4 -> 3
                picSize > 1 -> 2
                else -> 1
            }
            inflate.recyclerView.layoutManager =
                GridLayoutManager(inflate.root.context, span)
            inflate.recyclerView.adapter = GridImagesAdapter(picSize, miniFeed.images)
        }

        fun checkShowExpansion(){
            if (showExpansion) {
                inflate.doEll.apply {
                    showExpansion = switchShowState()
                }
            }
        }

    }

    class TopView(val inflate:MoyoTopViewBinding) :RecyclerView.ViewHolder(inflate.root){

        fun setData() {

        }

    }
}