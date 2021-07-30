package com.coder.zt.sobblog.ui.view.listener

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper

class RVPageChangeListener(val snapHelper:CustomPagerSnapHelper, val listener:(position:Int)->Unit):RecyclerView.OnScrollListener() {

    private var oldPosition = -1

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        val view = snapHelper.findSnapView(recyclerView.layoutManager)
        view?.let{
            val position = recyclerView.layoutManager?.getPosition(it)!!
            if(oldPosition != position && newState != RecyclerView.SCROLL_STATE_IDLE && position > -1){
                listener(position)
            }
        }
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
    }
}