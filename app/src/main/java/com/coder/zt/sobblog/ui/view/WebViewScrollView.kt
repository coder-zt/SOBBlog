package com.coder.zt.sobblog.ui.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.ScrollView
import androidx.core.widget.NestedScrollView

class WebViewScrollView(context: Context, attrs: AttributeSet):NestedScrollView(context, attrs) {

    private  var isSlide = true;
    companion object{
        private const val TAG = "WebViewScrollView"
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        Log.d(TAG, "onInterceptTouchEvent: ${super.onInterceptTouchEvent(ev)}")
        return super.onInterceptTouchEvent(ev) && isSlide
    }


    override fun onOverScrolled(scrollX: Int, scrollY: Int, clampedX: Boolean, clampedY: Boolean) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY)
        Log.d(TAG, "onOverScrolled: $scrollY  $clampedY")
        callback.invoke(clampedY)
    }

    lateinit var callback:(isBottom:Boolean)-> Unit
    fun setOverScrollListener(listener:(isBottom:Boolean)-> Unit){
        callback = listener
    }



        fun setSlide(slide: Boolean){
        isSlide = slide
    }
}