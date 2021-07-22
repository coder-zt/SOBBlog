package com.coder.zt.sobblog.ui.view.webwiew

import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient

class ArticleWebViewClient:WebViewClient() {

    companion object{
        private const val TAG = "MyClient"
    }
    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        view?.loadUrl("javascript:addTouchListener()")
    }
}