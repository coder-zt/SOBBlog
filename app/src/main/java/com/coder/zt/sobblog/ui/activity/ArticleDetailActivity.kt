package com.coder.zt.sobblog.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.ActivityArticleDetailBinding
import com.coder.zt.sobblog.ui.adapter.ArticleCommentAdapter
import com.coder.zt.sobblog.ui.adapter.RewardAdapter
import com.coder.zt.sobblog.ui.base.BaseActivity
import com.coder.zt.sobblog.utils.ScreenUtils
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

    override fun getLayoutId(): Int {
        Log.d(TAG, "getLayoutId: ${ScreenUtils.getScreenWidth()}")
        return R.layout.activity_article_detail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }

    var scrollY:Int = 0

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
    }





    @SuppressLint("SetJavaScriptEnabled")
    override fun onResume() {
        super.onResume()
        loadArticle()
    }

    private fun loadArticle() {
        val articleId = "1403262826952323074"
        viewModel.getArticleDetail(articleId)
        viewModel.getArticleReward(articleId)
        viewModel.getArticleComment(articleId)
    }
}

