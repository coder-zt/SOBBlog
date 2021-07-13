package com.coder.zt.sobblog.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.ActivityArticleDetailBinding
import com.coder.zt.sobblog.ui.adapter.ArticleCommentAdapter
import com.coder.zt.sobblog.ui.adapter.RewardAdapter
import com.coder.zt.sobblog.ui.base.BaseActivity
import com.coder.zt.sobblog.utils.ScreenUtils
import com.coder.zt.sobblog.viewmodel.ArticleDetailViewModel
import java.io.*

class ArticleDetailActivity:BaseActivity<ActivityArticleDetailBinding>(){

companion object{
    private const val TAG = "ArticleDetailActivity"
}

    val viewModel: ArticleDetailViewModel by lazy {
        ViewModelProvider(this).get(ArticleDetailViewModel::class.java)
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

    private fun initView() {
        dataBinding.wvArticle.setWebViewScrollView(dataBinding.wbsv)
        dataBinding.rvReward.adapter = rewardAdapter
        dataBinding.rvComment.adapter = commentAdapter
        dataBinding.rvComment.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                Log.d(TAG, "onScrollStateChanged: $newState")
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                Log.d(TAG, "onScrolled: $dx")
            }
        })
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
            for (rewardUserInfo in it) {
                Log.d(TAG, "initData: $rewardUserInfo")
            }
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

