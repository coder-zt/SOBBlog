package com.coder.zt.sobblog.ui.adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SlideLayoutManager(val context: Context):LinearLayoutManager(context) {

    private val childrenMap = mutableMapOf<Int, View>()
    override fun onLayoutCompleted(state: RecyclerView.State?) {
        super.onLayoutCompleted(state)
        for (i in 0 until childCount){
            getChildAt(i)?.let { childrenMap.put(i, it) }
        }
    }

    override fun computeVerticalScrollOffset(state: RecyclerView.State): Int {
            val firstVisiablePosition = findFirstVisibleItemPosition()
            val firstVisiableView = findViewByPosition(firstVisiablePosition)
            var offsetY = (-firstVisiableView!!.y).toInt()
            for (i in 0 until firstVisiablePosition) {
                offsetY += (if (childrenMap[i] == null) 0 else childrenMap[i]?.height)!!
            }
            return offsetY
    }
}