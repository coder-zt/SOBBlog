package com.coder.zt.sobblog.demo

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.ActivityDemoBinding
import com.coder.zt.sobblog.ui.base.BaseActivity
import java.io.BufferedReader
import java.io.InputStreamReader

class ArticleDemoActivity :BaseActivity<ActivityDemoBinding>() {

    override fun getLayoutId() = R.layout.activity_demo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        dataBinding.wvArticle.settings.javaScriptEnabled = true
        dataBinding.wvArticle.loadDataWithBaseURL("file:///android_asset/",getTestHtml("article_demo"), "text/html", "utf-8", null)
    }

    private fun getTestHtml(fileNme:String):String{
        val input = InputStreamReader(resources.assets.open("SyntaxHighlighter/html/$fileNme.html"))
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
}