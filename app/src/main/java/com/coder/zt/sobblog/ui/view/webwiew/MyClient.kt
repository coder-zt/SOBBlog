package com.coder.zt.sobblog.ui.view.webwiew

import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient

class MyClient:WebViewClient() {

    companion object{
        private const val TAG = "MyClient"
    }
    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        Log.d(TAG, "onPageFinished:${url} ")
        view?.loadUrl("javascript:start()")
    }
}