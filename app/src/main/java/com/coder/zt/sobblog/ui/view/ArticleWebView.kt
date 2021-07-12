package com.coder.zt.sobblog.ui.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.webkit.WebView

class ArticleWebView(context: Context, attrs: AttributeSet):WebView(context, attrs) {

    companion object{
        private const val TAG = "ArticleWebView"
    }
    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        Log.d(TAG, "onScrollChanged: l$l, t$t, oldl$oldl, oldt$oldt")
        scrollTo(l,t)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        Log.d(TAG, "onInterceptTouchEvent: ${super.onInterceptTouchEvent(ev)}")
        return true
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Log.d(TAG, "dispatchTouchEvent: ${super.dispatchTouchEvent(ev)}")
        return super.dispatchTouchEvent(ev)
    }
    private  var perX:Float? = 0.0f
    private  var perY:Float? = 0.0f
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d(TAG, "onTouchEvent: ")
        return super.onTouchEvent(event)
    }
}