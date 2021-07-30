package com.coder.zt.sobblog.ui.view.listener

import android.view.View
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView

class CustomPagerSnapHelper(val listener:(position:Int)->Unit):PagerSnapHelper() {

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager?): View? {
        return super.findSnapView(layoutManager)
    }

    override fun findTargetSnapPosition(
        layoutManager: RecyclerView.LayoutManager?,
        velocityX: Int,
        velocityY: Int
    ): Int {
        val pos = super.findTargetSnapPosition(layoutManager, velocityX, velocityY)
        listener(pos)
        return pos
    }
}