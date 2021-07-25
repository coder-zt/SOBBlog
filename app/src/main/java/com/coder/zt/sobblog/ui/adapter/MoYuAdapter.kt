package com.coder.zt.sobblog.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.coder.zt.sobblog.model.moyu.MYComment
import com.coder.zt.sobblog.model.moyu.MiniFeed
import com.coder.zt.sobblog.utils.ScreenUtils

/**
 * 摸鱼板块的内容
 *
 * 请求数据与ui不符文件解决：
 * 在客户端网络模块请求将所有数据请求再以特定的格式返回，加载出错的数据表示加载出错，并提供重新请求数据的点击事件
 */
class MoYuAdapter(val callback:(code:DOTYPE, data:Any) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object{
        private const val TAG = "MoYuAdapter"
    }

    enum class  DOTYPE{
        REPLY,
        COMMENT,
        GET_COMMENT,
        THUMB_UP,
        SHARE_LINK
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
    private lateinit var topView:TopView


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
            topView = topItem
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
        if (data.isNotEmpty()) {
            mData.addAll(data)
        }
        notifyDataSetChanged()
    }

    fun addData(data:List<MiniFeed>){
        if (data.isNotEmpty()) {
            Log.d(TAG, "addData: 获取数据添加 ${data.size}")
            mData.addAll(data)
        }
        notifyDataSetChanged()
    }

    fun checkChildrenState() {
        for (contentView in contentViewSet) {
            contentView.checkShowExpansion()
        }
    }

    fun setComment(it: List<MYComment>) {
        //
        for (contentView in contentViewSet) {
            contentView.setComment(it)
        }
    }

    fun updateThumbUp(first: String) {
        for ((i, mDatum) in mData.withIndex()) {
            if(mDatum.id == first){
                Log.d(TAG, "updateThumbUp: 点赞后更新数据")
                mDatum.thumbUpCount++
                val userId = UserDataMan.getUserInfo()?.id
                mDatum.thumbUpList.add(userId?:"")
                notifyDataSetChanged()
            }
        }

    }

    fun setTopWarpUrl(it: String?) {
        Glide.with(topView.inflate.themeIv.context).load(it).into(topView.inflate.themeIv)
    }

    class ContentView(val inflate:RvMoyuBinding) :RecyclerView.ViewHolder(inflate.root){

        private var showExpansion = false
        private var requestComment = false
        private lateinit var adapter:ArticleCommentAdapter
        fun setData(miniFeed: MiniFeed, position: Int,listener:()->Unit,callback:(code:DOTYPE, data:Any) -> Unit) {
            requestComment = false
            showExpansion = false
            inflate.rvComment.visibility = View.GONE
            inflate.triangleView.visibility = View.GONE
            inflate.data = miniFeed
            //展示评论、点赞数量
            val userId = UserDataMan.getUserInfo()?.id
            if(miniFeed.thumbUpList.contains(userId)){
                inflate.zanIv.setImageResource(R.mipmap.ic_liked_blue)
            }else{
                inflate.zanIv.setImageResource(R.mipmap.zan_grey_feidian3)
            }
            //话题
            if (miniFeed.topicName.isNullOrEmpty()) {
                inflate.topicTv.visibility = View.GONE
            }else{
                inflate.topicTv.visibility = View.VISIBLE
            }

            setListener(callback, miniFeed)
            // 设置动态图片显示的样式
            val picSize = miniFeed.images.size
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

        private fun setListener(
            callback: (code: DOTYPE, data: Any) -> Unit,
            miniFeed: MiniFeed
        ) {
            //设置点击事件
            //点赞
            inflate.zanLl.setOnClickListener {
                callback.invoke(DOTYPE.THUMB_UP, miniFeed.id)
            }
            //点击评论（显示与隐藏）
            inflate.commentLl.setOnClickListener {
                if(!showExpansion){
                    inflate.rvComment.visibility = View.VISIBLE
                    inflate.triangleView.visibility = View.VISIBLE
                    adapter = ArticleCommentAdapter(miniFeed.id,callback)
                    inflate.rvComment.adapter = adapter
                    showExpansion = true
                    //获取评论
                    if(miniFeed.commentCount > 0){
                        requestComment = true
                        callback.invoke(DOTYPE.GET_COMMENT, miniFeed.id)
                    }
                }else{
                    inflate.rvComment.visibility = View.GONE
                    inflate.triangleView.visibility = View.GONE
                    showExpansion = false
                    requestComment = false
                }

            }
            //点击分享的链接
            inflate.rlLinkContainer.setOnClickListener{
                callback(DOTYPE.SHARE_LINK, miniFeed.linkUrl)
            }
        }

        /**
         * 检查该条动态的评论是否已经展开
         */
        fun checkShowExpansion(){
            if (showExpansion) {
                inflate.rvComment.visibility = View.GONE
                inflate.triangleView.visibility = View.GONE
                showExpansion = false
            }
        }

        /**
         * 设置该条动态的评论
         */
        fun setComment(it: List<MYComment>) {
            if (!it.isNullOrEmpty()) {
                if (inflate.data?.id == it[0].momentId && requestComment) {
                    adapter.setMYData(it)
                    requestComment = false
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


