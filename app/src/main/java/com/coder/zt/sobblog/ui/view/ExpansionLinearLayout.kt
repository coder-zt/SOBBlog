package com.coder.zt.sobblog.ui.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Scroller
import com.coder.zt.sobblog.utils.ScreenUtils

class ExpansionLinearLayout(context: Context, attrs:AttributeSet):LinearLayout(context, attrs) {

    companion object{
        private const val TAG = "ExpansionLinearLayout"
    }

    val mScroller:Scroller by lazy {
        Scroller(context)
    }
    init {
        visibility = GONE
    }
    private var visWidth:Int = 0
    private var initialWidth:Int = ScreenUtils.dp2px(180)
    private lateinit var contentView: View
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        if(initialWidth == 0){
            initialWidth = r-l
        }
        contentView.layout(contentView.width - visWidth, 0, contentView.width, contentView.height)
    }


    override fun onFinishInflate() {
        super.onFinishInflate()
        contentView = getChildAt(0)
    }

    override fun computeScroll() {
        if(mScroller.computeScrollOffset()){
            visWidth = mScroller.currX
            Log.d(TAG, "computeScroll: initialWidth = $initialWidth")
            requestLayout()
        }else{
            if (visWidth == 0) {
                visibility = GONE
            }
        }
        super.computeScroll()
    }

    fun switchShowState():Boolean{
        Log.d(TAG, "switchShowState: ")
        if(visibility == GONE){
            visibility = VISIBLE
            mScroller.startScroll(0,0,initialWidth, 0 , 500)
            requestLayout()
            return true
        }else if(visibility == VISIBLE){
            mScroller.startScroll(initialWidth,0,-initialWidth, 0 , 500)
            requestLayout()
        }
        return false
    }
}