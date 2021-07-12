package com.coder.zt.sobblog.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.ActivityArticleDetailBinding
import com.coder.zt.sobblog.model.article.TestArticleContent
import com.coder.zt.sobblog.ui.base.BaseActivity
import com.coder.zt.sobblog.ui.view.webwiew.JsApi
import com.coder.zt.sobblog.ui.view.webwiew.MyClient
import com.coder.zt.sobblog.utils.ScreenUtils
import com.coder.zt.sobblog.viewmodel.ArticleDetailViewModel
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
class ArticleDetailActivity:BaseActivity<ActivityArticleDetailBinding>(){

companion object{
    private const val TAG = "ArticleDetailActivity"
}

    val viewModel: ArticleDetailViewModel by lazy {
        ViewModelProvider(this).get(ArticleDetailViewModel::class.java)
    }
    val webClient: MyClient by lazy {
        MyClient()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    private fun initData() {
        viewModel.articleDetail.observe(this){
            Log.d(TAG, "initData: ${it.data}")
            dataBinding.data = it.data
            Glide.with(this).load(it.data.covers[0]).into(dataBinding.articleCover)
            Glide.with(this).load(it.data.avatar).into(dataBinding.userAvatar)
            val articleContent = it.data.content

        }
    }

    private fun handleContent(articleContent: String):String {
        val document = Jsoup.parse(TestArticleContent.html3.replace("{{template}}",articleContent))
        verseChild(document.body())
        return document.toString()
    }

    private fun verseChild(e: Element?) {
        e?.let {
            for (child in e.children()) {
                Log.d(TAG, "verseChild: ${child.tagName()}")
                if (child.tagName() == "code") {
                    child.parent().attributes().add("class", "brush: ${getLanguageTye(child.attr("class"))};")
                    child.parent().attributes().add("name", "code;")
                    Log.d(TAG, "verseChild: per attr ${getLanguageTye(child.attr("class"))}")
                    child.parent().appendText(child.text())
                    child.remove()
                }
                verseChild(child)
            }
        }
    }

    private fun getLanguageTye(attr: String): String {
        Log.d(TAG, "getLanguageTye: $attr")
        if (attr.isEmpty()) {
            return ""
        }else{
            return when(attr){
                "language-shell" -> "ps"
                "language-java" -> "java"
                else -> ""
            }
        }
    }


    @SuppressLint("SetJavaScriptEnabled")
    override fun onResume() {
        super.onResume()
        dataBinding.wvArticle.isHorizontalScrollBarEnabled = false
        dataBinding.wvArticle.settings.javaScriptEnabled = true
        dataBinding.wvArticle.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        dataBinding.wvArticle.settings.javaScriptCanOpenWindowsAutomatically = true
        dataBinding.wvArticle.webViewClient = webClient
        dataBinding.wvArticle.webChromeClient = WebChromeClient()
        dataBinding.wvArticle.addJavascriptInterface(JsApi(), "androidApi") //AndroidtoJS类对象映射到js的test对象
        dataBinding.wvArticle.loadUrl("file:///android_asset/index.html" )
//        viewModel.getArticleDetail("1413768218035724289")
    }


    override fun getLayoutId(): Int {
        Log.d(TAG, "getLayoutId: ${ScreenUtils.getScreenWidth()}")
        return R.layout.activity_article_detail
    }
}

