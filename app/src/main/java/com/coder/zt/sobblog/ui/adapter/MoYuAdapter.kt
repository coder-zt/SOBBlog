package com.coder.zt.sobblog.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.MoyoTopViewBinding
import com.coder.zt.sobblog.databinding.RvMoyuBinding
import com.coder.zt.sobblog.model.datamanager.UserDataMan
import com.coder.zt.sobblog.model.moyu.MiniFeed
import com.coder.zt.sobblog.model.moyu.MoYuDataDisplay
import com.coder.zt.sobblog.utils.ScreenUtils
import com.coder.zt.sobblog.utils.ToastUtils

/**
 * 摸鱼板块的内容
 *
 * 请求数据与ui不符文件解决：
 * 在客户端网络模块请求将所有数据请求再以特定的格式返回，加载出错的数据表示加载出错，并提供重新请求数据的点击事件
 */
class MoYuAdapter(val callback:(code:DO_TYPE, data:Any) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object{
        private const val TAG = "MoYuAdapter"
    }
    enum class DO_TYPE{
        REPLY,
        COMMENT,
        THUMB_UP
    }
    private val TOP_VIEW:Int = 0
    private val CONTENT_VIEW:Int = 1
    private val listener:()->Unit = {
        checkChildrenState()
    }
    private val contentViewSet:MutableSet<ContentView> = mutableSetOf()
    val mData by lazy {
        mutableListOf<MoYuDataDisplay.MiniFeed>()
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
            contentItem.setData(mData[position-1], position, listener,callback)
            contentViewSet.add(contentItem)
        }else{
            val topItem = holder as TopView
            topItem.setData()
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

    fun setData(data:List<MoYuDataDisplay.MiniFeed>){
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
        fun setData(miniFeed: MoYuDataDisplay.MiniFeed, position: Int,listener:()->Unit,callback:(code:DO_TYPE, data:Any) -> Unit) {
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
            val userId = UserDataMan.getUserInfo()?.id
            Log.d(TAG, "setData: $userId")
            if (miniFeed.thumbUpList.contains(userId)) {
                inflate.tvThumb.text = "已赞"
            }else{
                inflate.tvThumb.text = "赞"
            }
            inflate.thumbBtnLl.setOnClickListener {
                if(inflate.tvThumb.text == "已赞"){
                    ToastUtils.showError("渣男！你是想取消点赞吗？")
                }else{
                    callback.invoke(DO_TYPE.THUMB_UP, miniFeed.id)
                }
            }
            inflate.commentBtnLl.setOnClickListener {
                callback.invoke(DO_TYPE.COMMENT, miniFeed.id)
            }
            Log.d(TAG, "setData:  $position ---> $picSize")
            //展示评论数据
            if(miniFeed.commentCount == 0 && miniFeed.thumbUpCount == 0){
                inflate.rvComment.visibility = View.GONE
                return
            }else{
                inflate.rvComment.visibility = View.VISIBLE
                inflate.rvComment.adapter = MYCommentAdapter(miniFeed.thumbUpCount, miniFeed.comment)
            }
            // 设置动态图片显示的样式
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
            val userInfo = UserDataMan.getUserInfo()
            if (userInfo != null) {
                inflate.usernameTv.text = userInfo.nickname
                val corners = RoundedCorners(ScreenUtils.dp2px(12))
                val override = RequestOptions.bitmapTransform(corners)
                //.override(ScreenUtils.dp2px(30f), ScreenUtils.dp2px(30f));
                Glide.with(inflate.avatar).load(userInfo.avatar).apply(override).into(inflate.avatar)
            }
        }

    }

}