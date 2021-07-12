package com.coder.zt.sobblog.ui.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.webkit.WebView
import java.lang.Math.abs

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
    private  var startX:Float = 0.0f
    private  var startY:Float = 0.0f
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
                        //纵向
                        if(abs(currentX - startX)>abs(currentY - startY)){
                            callback.invoke(false)
                        //横向
                        }else{
                            callback.invoke(true)
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                    }
                    else -> {
                    }
                }
        }
        return super.onTouchEvent(event)
    }

    lateinit var callback: (vertical:Boolean)->Unit

    fun setSlideListener(listener: (vertical:Boolean)->Unit){
        callback = listener
    }

}