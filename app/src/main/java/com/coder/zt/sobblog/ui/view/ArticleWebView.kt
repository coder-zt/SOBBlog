package com.coder.zt.sobblog.ui.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import com.coder.zt.sobblog.ui.activity.ArticleDetailActivity
import com.coder.zt.sobblog.ui.view.webwiew.ArticleWebViewClient
import com.coder.zt.sobblog.ui.view.webwiew.JsApi
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.io.*
import java.lang.Math.abs

class ArticleWebView(context: Context, attrs: AttributeSet):WebView(context, attrs) {

    companion object{
        private const val TAG = "ArticleWebView"
    }

    private  var startX:Float = 0.0f
    private  var startY:Float = 0.0f
    private var slideDir = true
    val webClient: ArticleWebViewClient by lazy {
        ArticleWebViewClient()
    }
    private var wbsv:WebViewScrollView? = null

    init{
        isHorizontalScrollBarEnabled = false
        settings.javaScriptEnabled = true
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        settings.javaScriptCanOpenWindowsAutomatically = true
        webViewClient = webClient
        webChromeClient = WebChromeClient()
        addJavascriptInterface(JsApi(){
            when(it){
                JsApi.EventCode.Event_UP->{
                    wbsv?.setSlide(true)
                }
                JsApi.EventCode.Event_MOVE->{
                    wbsv?.setSlide(slideDir)
                }
                JsApi.EventCode.Event_DOWN->{
                    wbsv?.setSlide(slideDir )
                }
            }
        }, "androidApi") //AndroidtoJS类对象映射到js的test对象
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        Log.d(TAG, "onScrollChanged: l$l, t$t, oldl$oldl, oldt$oldt")
        scrollTo(l,t)
    }
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            val currentX =event.x
            val currentY = event.y
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        startX = currentX
                        startY = currentY
                    }
                    MotionEvent.ACTION_MOVE -> {
                        slideDir = kotlin.math.abs(currentX - startX) <= kotlin.math.abs(currentY - startY)
                    }
                    MotionEvent.ACTION_UP -> {
                    }
                    else -> {
                    }
                }
        }
        return super.onTouchEvent(event)
    }


    fun loadArticle(articleContent: String) {
        val html = handleContent(articleContent)
        //可以保存html到本地进行检查
        //saveHtml(html)
        loadDataWithBaseURL("file:///android_asset/",html, "text/html", "utf-8", null)
    }

    private fun handleContent(articleContent: String):String {
        Log.d(TAG, "handleContent: $articleContent")
        val template = getTestHtml("article_template")
        val document = Jsoup.parse(template.replace("{{template}}",articleContent))
        verseChild(document.body())
        return document.toString()
    }

    private fun verseChild(e: Element?) {
        e?.let {
            for (child in e.children()) {
                Log.d(TAG, "verseChild: ${child.tagName()}")
                if (child.tagName() == "code") {
                    child.parent().attributes().add("class", "brush: ${getLanguageTye(child.attr("class"))}; toolbar:false; code")
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
            return "text"
        }else{
            return when(attr){
                "language-shell" -> "ps"
                "language-java" -> "java"
                "language-kotlin" -> "kotlin"
                "language-xml" -> "xml"
                "language-json" -> "text"
                else -> ""
            }
        }
    }

    private fun saveHtml(html: String) {
        val f = File("data/data/com.coder.zt.sobblog/index.html")
        if (!f.exists()) {
            f.createNewFile()
        }
        val fos = FileOutputStream(f)
        val input = OutputStreamWriter(BufferedOutputStream(fos))
        val bfReader = BufferedWriter(input)
        bfReader.write(html)
        bfReader.flush()
        bfReader.close()
    }

    private fun getTestHtml(fileNme:String):String{
        val input = InputStreamReader(resources.assets.open("$fileNme.html"))
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

    fun setWebViewScrollView(sv:WebViewScrollView?){
        wbsv = sv
    }
}