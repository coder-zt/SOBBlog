package com.coder.zt.sobblog.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.ActivityArticleDetailBinding
import com.coder.zt.sobblog.ui.adapter.ArticleCommentAdapter
import com.coder.zt.sobblog.ui.adapter.RewardAdapter
import com.coder.zt.sobblog.ui.base.BaseActivity
import com.coder.zt.sobblog.utils.AppRouter
import com.coder.zt.sobblog.utils.ScreenUtils
import com.coder.zt.sobblog.utils.ToastUtils
import com.coder.zt.sobblog.viewmodel.ArticleViewModel

class ArticleDetailActivity:BaseActivity<ActivityArticleDetailBinding>(){

companion object{
    private const val TAG = "ArticleDetailActivity"
}

    val viewModel: ArticleViewModel by lazy {
        ViewModelProvider(this).get(ArticleViewModel::class.java)
    }

    private val rewardAdapter: RewardAdapter by lazy {
        RewardAdapter()
    }

    private val commentAdapter: ArticleCommentAdapter by lazy {
        ArticleCommentAdapter()
    }

    private lateinit var articleId:String

    override fun getLayoutId() = R.layout.activity_article_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        dataBinding.wvArticle.setWebViewScrollView(dataBinding.wbsv)
        dataBinding.rvReward.adapter = rewardAdapter
        dataBinding.rvComment.adapter = commentAdapter
        dataBinding.rvComment.adapter = commentAdapter
        dataBinding.wbsv.setOverScrollListener {
            dataBinding.rvComment.isNestedScrollingEnabled = it
        }
    }

    private fun initData() {
        viewModel.articleDetail.observe(this) {
            dataBinding.data = it
            val corners = RoundedCorners(ScreenUtils.dp2px(24))
            val override = RequestOptions.bitmapTransform(corners)
            Glide.with(this).load(it.avatar).apply(override).into(dataBinding.userAvatar)
            Glide.with(this).load(it.covers[0]).into(dataBinding.articleCover)
            Glide.with(this).load(it.avatar).into(dataBinding.userAvatar)
            val articleContent = it.content
            dataBinding.wvArticle.loadArticle(articleContent)
        }
        viewModel.rewardInfo.observe(this){
            rewardAdapter.setData(it)
        }
        viewModel.commentInfo.observe(this){
            commentAdapter.setData(it)
        }
        articleId = intent.getStringExtra(AppRouter.param_id)?:""
        loadArticle(articleId)
    }





    @SuppressLint("SetJavaScriptEnabled")
    override fun onResume() {
        super.onResume()
    }

    private fun loadArticle(articleId:String) {
        Log.d(TAG, "loadArticle: $articleId")
        if (articleId.isEmpty()) {
            ToastUtils.showError("找不到该文章信息")
            return
        }
        viewModel.getArticleDetail(articleId)
        viewModel.getArticleReward(articleId)
        viewModel.getArticleComment(articleId)
    }
}

