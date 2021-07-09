package com.coder.zt.sobblog.ui.activity

import android.os.Bundle
import android.util.Log
import android.webkit.WebSettings
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.ActivityArticleDetailBinding
import com.coder.zt.sobblog.databinding.ActivityLoginBinding
import com.coder.zt.sobblog.model.article.TestArticleContent
import com.coder.zt.sobblog.ui.base.BaseActivity
import com.coder.zt.sobblog.utils.ScreenUtils
import com.coder.zt.sobblog.viewmodel.ArticleDetailViewModel

class ArticleDetailActivity:BaseActivity<ActivityArticleDetailBinding>(){

companion object{
    private const val TAG = "ArticleDetailActivity"
}

    val viewModel: ArticleDetailViewModel by lazy {
        ViewModelProvider(this).get(ArticleDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    private fun initData() {
        viewModel.articleDetail.observe(this){
            val articleContent = it.data.content
//            Log.d(TAG, "initData: $articleContent")
            val result = articleContent.replace("<pre>", "<pre class=\"brush: java;\">")
            Log.d(TAG, "initData: $result")
            val temp = TestArticleContent.html.replace("{{template}}", result)
            Log.d(TAG, "initData: $temp")
            dataBinding.wvArticle.loadDataWithBaseURL("file:///android_asset", temp, "text/html", "utf-8", null);
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getArticleDetail("1400314073681293314")
        dataBinding.wvArticle.isHorizontalScrollBarEnabled = false
        dataBinding.wvArticle.settings.javaScriptEnabled = true
        dataBinding.wvArticle.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
    }
    override fun getLayoutId(): Int {
        Log.d(TAG, "getLayoutId: ${ScreenUtils.getScreenWidth()}")
        return R.layout.activity_article_detail
    }
}