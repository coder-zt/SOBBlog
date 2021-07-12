package com.coder.zt.sobblog.ui.activity

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.ActivityArticleDetailBinding
import com.coder.zt.sobblog.ui.base.BaseActivity
import com.coder.zt.sobblog.ui.view.webwiew.JsApi
import com.coder.zt.sobblog.ui.view.webwiew.MyClient
import com.coder.zt.sobblog.utils.ScreenUtils
import com.coder.zt.sobblog.viewmodel.ArticleDetailViewModel
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.io.*

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
            Log.d(TAG, "initData: ${it}")
            dataBinding.data = it
            Glide.with(this).load(it.covers[0]).into(dataBinding.articleCover)
            Glide.with(this).load(it.avatar).into(dataBinding.userAvatar)
            val articleContent = it.content
            val html = handleContent(articleContent)
            saveHtml(html)
            dataBinding.wvArticle.loadDataWithBaseURL("file:///android_asset/",html, "text/html", "utf-8", null)
        }
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

            return "text"
        }else{
            return when(attr){
                "language-shell" -> "ps"
                "language-java" -> "java"
                "language-kotlin" -> "kotlin"
                "language-xml" -> "xml"
                "language-json" -> "json"
                else -> ""
            }
        }
    }

    private var slideDir = true

    @SuppressLint("SetJavaScriptEnabled")
    override fun onResume() {
        super.onResume()
        verifyStoragePermissions()
        dataBinding.wvArticle.isHorizontalScrollBarEnabled = false
        dataBinding.wvArticle.settings.javaScriptEnabled = true
        dataBinding.wvArticle.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        dataBinding.wvArticle.settings.javaScriptCanOpenWindowsAutomatically = true
        dataBinding.wvArticle.webViewClient = webClient
        dataBinding.wvArticle.webChromeClient = WebChromeClient()
        dataBinding.wvArticle.addJavascriptInterface(JsApi(){
         when(it){
             JsApi.EventCode.Event_UP->{
                 dataBinding.wbsv.setSlide(true)
             }
             JsApi.EventCode.Event_MOVE->{
                 Log.d(TAG, "onResume: MOVE")
                 dataBinding.wbsv.setSlide(slideDir)
             }
             JsApi.EventCode.Event_DOWN->{
                 dataBinding.wbsv.setSlide(slideDir )
             }
         }

        }, "androidApi") //AndroidtoJS类对象映射到js的test对象
        dataBinding.wvArticle.setSlideListener {
            slideDir = it
        }
        loadArticle()
        dataBinding.topBarLl.setOnClickListener {

            dataBinding.wvArticle.loadUrl("javascript:addTouchListener()")
        }
    }

    private fun loadArticle() {
        viewModel.getArticleDetail("1410937277084966914")
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

    fun verifyStoragePermissions() {
        try {
            //检测是否有写的权限
            val  permission = ActivityCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(this,
                    arrayOf(
                        "android.permission.READ_EXTERNAL_STORAGE",
                        "android.permission.WRITE_EXTERNAL_STORAGE"
                    ), 111);
            }else{
                Log.d(TAG, "verifyStoragePermissions: 以授权")
            }
        } catch ( e:Exception) {
            e.printStackTrace();
        }
    }


    override fun getLayoutId(): Int {
        Log.d(TAG, "getLayoutId: ${ScreenUtils.getScreenWidth()}")
        return R.layout.activity_article_detail
    }
}

