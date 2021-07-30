package com.coder.zt.sobblog.ui.view.listener

import android.view.View
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView

class CustomPagerSnapHelper(val listener:(position:Int)->Unit):PagerSnapHelper() {

    private var oldPosition = -1

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager?): View? {
        return super.findSnapView(layoutManager)
    }

    override fun findTargetSnapPosition(
        layoutManager: RecyclerView.LayoutManager?,
        velocityX: Int,
        velocityY: Int
    ): Int {
        val pos = super.findTargetSnapPosition(layoutManager, velocityX, velocityY)
        if(oldPosition != pos){
            listener(pos)
        }
        return pos
    }
}