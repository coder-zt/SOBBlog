package com.coder.zt.sobblog.ui.view

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView

class ArticleWebView(context: Context, attrs: AttributeSet):WebView(context, attrs) {

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        scrollTo(l,t)
    }
}