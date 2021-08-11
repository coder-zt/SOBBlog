package com.coder.zt.sobblog.demo

import android.os.Bundle
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import androidx.lifecycle.ViewModelProvider
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.ActivityDemoBinding
import com.coder.zt.sobblog.ui.base.BaseActivity
import com.coder.zt.sobblog.ui.view.ArticleWebView
import com.coder.zt.sobblog.ui.view.webwiew.JsApi
import com.coder.zt.sobblog.viewmodel.ArticleViewModel
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.io.BufferedReader
import java.io.InputStreamReader

class ArticleDemoActivity :BaseActivity<ActivityDemoBinding>() {

    override fun getLayoutId() = R.layout.activity_demo

    companion object{
        private const val TAG = "ArticleDemoActivity"
    }
    private val articleViewModel by lazy{
        ViewModelProvider(this).get(ArticleViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }

    private fun initData() {
        articleViewModel.articleDetail.observe(this){
            val htmlContent = handleHtmlContent(it.content)
            dataBinding.wvArticle.loadDataWithBaseURL("file:///android_asset/",htmlContent, "text/html", "utf-8", null)

        }
    }

    private fun handleHtmlContent(content: String): String {
        //处理code中是关键字的情况
        val handleText = handleKeywords(content)
        val template = getTestHtml("article_demo")
        val document = Jsoup.parse(template.replace("{{template}}",handleText))
        verseChild(document.body())
        return document.toString()
    }

    private fun verseChild(body: Element?) {
        body?.let {
            for (child in body.children()) {
                if (child.tagName() == "code") {
                    //给代码父标签添加属性，可以自定义样式
                    child.parent().attributes().add("class", "code_block")
                    child.attributes().put("class", "brush: ${getLanguageType(child.attr("class"))}; toolbar:false; quick-code:false;")
                }
                verseChild(child)
            }
        }
    }

    private fun getLanguageType(attr: String): String {
        Log.d(TAG, "getLanguageType: $attr")
        if (attr.isEmpty()) {
            return "text"
        }else{
            return when(attr){
                "language-shell" -> "ps"
                "language-java" -> "java"
                "language-kotlin" -> "kotlin"
                "language-xml" -> "xml"
                "language-sql" -> "sql"
                "language-json" -> "text"
                else -> "text"
            }
        }
    }

    private fun handleKeywords(content: String): String {
        var handleText = content
        val parttern = Regex("<code>(.*?)</code>")
        val results = parttern.findAll(handleText)
        for (result in results) {
            var keyString: String = result.value
            keyString = keyString.replace("<code>", "<span class=\"key_word\">")
            keyString = keyString.replace("</code>", "</span>")
            handleText = handleText.replace(result.value, keyString)
        }
        return handleText
    }

    private fun initView() {
        dataBinding.wvArticle.settings.javaScriptEnabled = true
        dataBinding.wvArticle.addJavascriptInterface(DemoJsApi(), "androidApi")
        dataBinding.btnSource.setOnClickListener{
            dataBinding.wvArticle.loadUrl("javascript:window.androidApi.printPageSource('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');")
        }
    }

    private fun getTestHtml(fileNme:String):String{
        val input = InputStreamReader(resources.assets.open("syntaxhighlighter/html/$fileNme.html"))
        val bfReader = BufferedReader(input)
        val sbHtml = StringBuilder()
        var line = ""
        line = bfReader.readLine();
        while(line.isNotBlank()){
            sbHtml.append("\n")
            sbHtml.append(line)
            try {
                line = bfReader.readLine()

            }catch (e:Exception){
                e.printStackTrace()
                break
            }
        }
        bfReader.close()
        return sbHtml.toString()
    }

    override fun onResume() {
        super.onResume()
        articleViewModel.getArticleDetail("1418562537657585666")
    }
}