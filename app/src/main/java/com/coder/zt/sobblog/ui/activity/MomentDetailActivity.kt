package com.coder.zt.sobblog.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.ActivityMomentDetailBinding
import com.coder.zt.sobblog.model.datamanager.UserDataMan
import com.coder.zt.sobblog.model.moyu.MYComment
import com.coder.zt.sobblog.model.moyu.MiniFeed
import com.coder.zt.sobblog.model.moyu.Moment
import com.coder.zt.sobblog.ui.adapter.ArticleCommentAdapter
import com.coder.zt.sobblog.ui.adapter.GridImagesAdapter
import com.coder.zt.sobblog.ui.adapter.MoYuAdapter
import com.coder.zt.sobblog.ui.base.BaseActivity
import com.coder.zt.sobblog.utils.AppRouter
import com.coder.zt.sobblog.utils.ToastUtils
import com.coder.zt.sobblog.utils.TransUtils
import com.coder.zt.sobblog.viewmodel.MoYuViewModel

class MomentDetailActivity:BaseActivity<ActivityMomentDetailBinding>() {

    override fun getLayoutId() = R.layout.activity_moment_detail

    private lateinit var momentId:String

    private val viewModel: MoYuViewModel by lazy{
        ViewModelProvider(this).get(MoYuViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    private fun initData() {
        momentId = intent.getStringExtra(AppRouter.param_id) ?: ""
        if (momentId.isBlank()) {
            ToastUtils.showError("<・)))><<游走了")
            finish()
        }
        viewModel.momentDetail.observe(this) {
            setData(it) { doType: MoYuAdapter.DOTYPE, any: Any ->
                when (doType) {
                    MoYuAdapter.DOTYPE.THUMB_UP -> {//点赞
                        if (UserDataMan.checkUserLoginState(
                                this,
                                getString(R.string.check_login_thumb_up_tips)
                            )
                        ) {
                            viewModel.thumbUP(any as String)
                        }
                    }
                    MoYuAdapter.DOTYPE.COMMENT -> {//评论动态
                        if (UserDataMan.checkUserLoginState(
                                this,
                                getString(R.string.check_login_comment_tips)
                            )
                        ) {
//                        minifeedIdTemp = any as String//动态id
//                        targetUserId = ""//被评论者的ID
//                        commentIdTemp = ""//评论ID
//                        showCommentInput("")
                        }
                    }
                    MoYuAdapter.DOTYPE.REPLY -> {//回复评论
                        if (UserDataMan.checkUserLoginState(
                                this,
                                getString(R.string.check_login_comment_tips)
                            )
                        ) {
                            UserDataMan.getUserInfo()!!.let {
                                val comment = any as ArticleCommentAdapter.Comment
//                            Log.d(MoYuActivity.TAG, "comment: $comment ")
//                            minifeedIdTemp = comment.objectId//动态ID
//                            targetUserId = comment.userId//被评论者的ID
//                            commentIdTemp = comment._id//评论ID
//                            showCommentInput(comment.userName)
                            }
                        }

                    }
                    MoYuAdapter.DOTYPE.GET_COMMENT -> {//获取该动态的评论
                        viewModel.getMiniFeedComment(any as String)
                    }
                    MoYuAdapter.DOTYPE.SHARE_LINK -> {//点击了分享链接
                        TransUtils.dispatchShareLink(this, any as String)
                    }
                    MoYuAdapter.DOTYPE.PIC_SHOW -> {//展示图片
                        val data = any as Pair<List<String>, Int>
                        val picUrls: List<String> = data.first
                        val pics = ArrayList<String>()
                        pics.addAll(picUrls)
                        AppRouter.toPictureBrowseActivity(this, pics, data.second)
                    }
                }
            }
        }
        viewModel.feedComment.observe(this){
            setComment(it)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMomentDetail(momentId)
    }

    private var showExpansion = false
    private var requestComment = false
    private lateinit var adapter: ArticleCommentAdapter

    fun setData(miniFeed: Moment, callback:(code: MoYuAdapter.DOTYPE, data:Any) -> Unit) {
        requestComment = false
        showExpansion = false
        dataBinding.rvComment.visibility = View.GONE
        dataBinding.triangleView.visibility = View.GONE
        dataBinding.data = miniFeed
        //展示评论、点赞数量
        val userId = UserDataMan.getUserInfo()?.id
        if(miniFeed.thumbUpList.contains(userId)){
            dataBinding.zanIv.setImageResource(R.mipmap.ic_liked_blue)
        }else{
            dataBinding.zanIv.setImageResource(R.mipmap.zan_grey_feidian3)
        }
        //话题
        if (miniFeed.topicName.isNullOrEmpty()) {
            dataBinding.topicTv.visibility = View.GONE
        }else{
            dataBinding.topicTv.visibility = View.VISIBLE
        }

        setListener(callback, miniFeed)
        // 设置动态图片显示的样式
        val picSize = miniFeed.images.size
        if(picSize == 0){
            dataBinding.recyclerView.visibility = View.GONE
            return
        }else{
            dataBinding.recyclerView.visibility = View.VISIBLE
        }
        val span = when {
            picSize > 4 -> 3
            picSize > 1 -> 2
            else -> 1
        }
        dataBinding.recyclerView.layoutManager =
            GridLayoutManager(dataBinding.root.context, span)
        dataBinding.recyclerView.adapter = GridImagesAdapter(picSize, miniFeed.images,callback)


    }

    private fun setListener(
        callback: (code: MoYuAdapter.DOTYPE, data: Any) -> Unit,
        miniFeed: Moment
    ) {
        //设置点击事件
        //点赞
        dataBinding.zanLl.setOnClickListener {
            callback.invoke(MoYuAdapter.DOTYPE.THUMB_UP, miniFeed.id)
        }
        //点击评论（显示与隐藏）
        dataBinding.commentLl.setOnClickListener {
            if(!showExpansion){
                dataBinding.rvComment.visibility = View.VISIBLE
                dataBinding.triangleView.visibility = View.VISIBLE
                adapter = ArticleCommentAdapter(miniFeed.id,callback)
                dataBinding.rvComment.adapter = adapter
                showExpansion = true
                //获取评论
                if(miniFeed.commentCount > 0){
                    requestComment = true
                    callback.invoke(MoYuAdapter.DOTYPE.GET_COMMENT, miniFeed.id)
                }
            }else{
                dataBinding.rvComment.visibility = View.GONE
                dataBinding.triangleView.visibility = View.GONE
                showExpansion = false
                requestComment = false
            }

        }
        //点击分享的链接
        dataBinding.rlLinkContainer.setOnClickListener{
            callback(MoYuAdapter.DOTYPE.SHARE_LINK, miniFeed.linkUrl)
        }
    }

    /**
     * 检查该条动态的评论是否已经展开
     */
    fun checkShowExpansion(){
        if (showExpansion) {
            dataBinding.rvComment.visibility = View.GONE
            dataBinding.triangleView.visibility = View.GONE
            showExpansion = false
        }
    }

    /**
     * 设置该条动态的评论
     */
    fun setComment(it: List<MYComment>) {
        if (!it.isNullOrEmpty()) {
            if (dataBinding.data?.id == it[0].momentId && requestComment) {
                adapter.setMYData(it)
                requestComment = false
            }
        }
    }
}