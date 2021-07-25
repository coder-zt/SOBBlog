package com.coder.zt.sobblog.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.ActivityArticleDetailBinding
import com.coder.zt.sobblog.databinding.PopRvCollectBinding
import com.coder.zt.sobblog.databinding.PopRvReawrdBinding
import com.coder.zt.sobblog.model.article.ArticleCollect
import com.coder.zt.sobblog.model.article.ArticleReward
import com.coder.zt.sobblog.model.datamanager.UserDataMan
import com.coder.zt.sobblog.ui.adapter.*
import com.coder.zt.sobblog.ui.base.BaseActivity
import com.coder.zt.sobblog.utils.*
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
    private val callback:(MoYuAdapter.DOTYPE, Any) -> Unit = {doType: MoYuAdapter.DOTYPE, data:Any ->
        when(doType){
            MoYuAdapter.DOTYPE.COMMENT ->{//评论动态
                if(UserDataMan.checkUserLoginState(this, getString(R.string.check_login_comment_tips))){
                }
            }
            MoYuAdapter.DOTYPE.REPLY ->{//回复评论
                if(UserDataMan.checkUserLoginState(this, getString(R.string.check_login_comment_tips))) {

                }

            }
        }
    }

    private lateinit var articleId:String

    private val commentAdapter: ArticleCommentAdapter by lazy {
        ArticleCommentAdapter("", callback)
    }


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
        dataBinding.wbsv.setOverScrollListener {
            dataBinding.rvComment.isNestedScrollingEnabled = it
        }
        dataBinding.llZanContainer.setOnClickListener {
            if(UserDataMan.checkUserLoginState(this, getString(R.string.check_login_thumb_up_tips))) {
                viewModel.articleThumbUp(articleId)
            }
//            dataBinding.wvArticle.loadUrl("javascript:window.androidApi.printPageSource('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
        }
        dataBinding.llCollectContainer.setOnClickListener {
            if(UserDataMan.checkUserLoginState(this, getString(R.string.check_login_collect_tips))) {
                viewModel.getCollect()
            }
        }
        dataBinding.llRewardContainer.setOnClickListener {
            if(UserDataMan.checkUserLoginState(this, getString(R.string.check_login_reward_tips))) {
                val rewardList:List<Int> = listOf<Int>(2,8,16)
                PopWindowUtils.showListData<PopRvReawrdBinding, Int>(R.layout.pop_rv_reawrd,rewardList,this,
                    object:PopListAdapter.ItemsListSetData<PopRvReawrdBinding, Int>{
                        override fun setData(inflate: PopRvReawrdBinding, d: Int) {
                            inflate.data = d
                        }

                        override fun onClick(d: Int) {
                            viewModel.articleReward(ArticleReward(articleId,d))
                        }

                    },false)
            }
        }
        dataBinding.wvArticle.setOpenUrlListener {
            AppRouter.toOutsideWebsite(this, it)
        }
    }


    private fun initData() {
        viewModel.articleDetail.observe(this) {
            dataBinding.data = it
            val corners = RoundedCorners(ScreenUtils.dp2px(24))
            val override = RequestOptions.bitmapTransform(corners)
            Glide.with(this).load(it.avatar).apply(override).into(dataBinding.userAvatar)
            Glide.with(this).load(it.covers[0]).into(dataBinding.articleCover)
            val articleContent = it.content
            dataBinding.wvArticle.loadArticle(articleContent)
            viewModel.articleCheckThumbUp(it.id)
        }
        viewModel.rewardInfo.observe(this){
            rewardAdapter.setData(it)
        }
        viewModel.commentInfo.observe(this){
            commentAdapter.setData(it)
        }
        viewModel.checkThumbUp.observe(this){
            dataBinding.zanTv.text = it.data
            if(it.success){//已经点赞
                dataBinding.zanIv.setImageResource(R.mipmap.ic_liked_blue)
                dataBinding.zanTv.setTextColor(resources.getColor(R.color.theme_blue))
                dataBinding.llZanContainer.setBackgroundResource( R.drawable.bule_boroad_bg)
            }else{//还未点赞
                dataBinding.llZanContainer.setBackgroundResource( R.drawable.gray_boroad_bg)
                dataBinding.zanIv.setImageResource(R.mipmap.zan_grey_feidian3)
                dataBinding.zanTv.setTextColor(resources.getColor(R.color.diver_line_color))
            }
        }
        viewModel.collects.observe(this){
            DialogUtils.showListData(R.layout.pop_rv_collect,it,this,
                object:PopListAdapter.ItemsListSetData<PopRvCollectBinding, ArticleCollect>{
                    override fun setData(inflate: PopRvCollectBinding, d: ArticleCollect) {
                        inflate.data = d
                    }

                    override fun onClick(d: ArticleCollect) {
//                        viewModel.articleReward(ArticleReward(articleId,d))
                    }

                })
        }
        articleId = intent.getStringExtra(AppRouter.param_id)?:""
        loadArticle(articleId)
    }

    private fun loadArticle(articleId:String) {
        if (articleId.isEmpty()) {
            ToastUtils.showError("找不到该文章信息")
            finish()
            return
        }
        viewModel.getArticleDetail(articleId)
        viewModel.getArticleReward(articleId)
        viewModel.getArticleComment(articleId)
    }
}

