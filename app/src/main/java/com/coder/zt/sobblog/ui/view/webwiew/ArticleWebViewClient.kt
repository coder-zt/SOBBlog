package com.coder.zt.sobblog.ui.view.webwiew

import android.webkit.WebView
import android.webkit.WebViewClient

class ArticleWebViewClient():WebViewClient() {

    companion object{
        private const val TAG = "MyClient"
    }

    private lateinit var callback:(url:String)->Unit

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        view?.loadUrl("javascript:addTouchListener()")
    }

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        url?.let {
            callback.invoke(it)
        }
        return true
    }

    fun setOpenUrlListener(listener:(url:String)->Unit){
        callback = listener
    }
}