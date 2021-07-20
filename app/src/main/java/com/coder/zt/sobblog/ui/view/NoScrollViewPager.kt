package com.coder.zt.sobblog.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.utils.ScreenUtils

class NoScrollViewPager(context: Context, attrs: AttributeSet): ViewPager(context, attrs){

    private val noScroll = true

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return if (noScroll) {
            false
        } else {
            super.onTouchEvent(event)
        }
    }


    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return if (noScroll) {
            false;
        } else {
            super.onInterceptTouchEvent(ev);
        }
    }

    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(item,false)
    }

}