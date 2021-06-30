package com.coder.zt.sobblog.ui.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ScrollView
import androidx.core.widget.NestedScrollView

class CustomScrollView(context: Context, attrs: AttributeSet): NestedScrollView(context, attrs) {

    companion object{
        private const val TAG = "CustomScrollView"
    }

    lateinit var callback:()->Unit
    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
//        Log.d(TAG, "onScrollChanged: l: $l, t: $t, oldl: $oldl, oldt: $oldt")
//        Log.d(TAG, "onScrollChanged: scrollY ---> $scrollY")
        Log.d(TAG, "onScrollChanged: getChildAt(0).measuredHeight ---> $childCount")
        if(getChildAt(0).measuredHeight == measuredHeight + scrollY){
            Log.d(TAG, "onScrollChanged: 下滑到了底部")
            callback.invoke()
        }

    }

    fun setSlideBottomListener(listener:()->Unit){
        callback = listener
    }

}