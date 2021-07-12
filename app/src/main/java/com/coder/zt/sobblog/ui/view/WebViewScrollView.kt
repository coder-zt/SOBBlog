package com.coder.zt.sobblog.ui.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.ScrollView

class WebViewScrollView(context: Context, attrs: AttributeSet):ScrollView(context, attrs) {

    private  var isSlide = true;
    companion object{
        private const val TAG = "WebViewScrollView"
    }
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        Log.d(TAG, "onInterceptTouchEvent: ${super.onInterceptTouchEvent(ev)}")
        return super.onInterceptTouchEvent(ev) && isSlide
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        Log.d(TAG, "onTouchEvent: ")
        return super.onTouchEvent(ev)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Log.d(TAG, "dispatchTouchEvent: ${super.dispatchTouchEvent(ev)}")
        return super.dispatchTouchEvent(ev)
    }

    fun setSlide(slide: Boolean){
        isSlide = slide
    }
}