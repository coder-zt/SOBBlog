package com.coder.zt.sobblog.ui.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.animation.DecelerateInterpolator
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.Scroller
import java.lang.RuntimeException
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class PullScrollView(context:Context,attrs: AttributeSet):RelativeLayout(context, attrs) {


    companion object{
        private const val TAG = "PullScrollView"
    }
    private enum class PullState{
        ON_REFRESH,
        ON_NULL
    }
    private var state = PullState.ON_NULL
    private var topView: View? = null
    private var contentView:ScrollView? = null
    private var bottomView:View? = null
    private var startY:Int = 0
    private var pullDistance:Int = 0
    val mScroller:Scroller by lazy {
        Scroller(context, DecelerateInterpolator())
    }
    val mTouchSlop:Int by lazy{
        ViewConfiguration.get(context).scaledTouchSlop
    }


    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount != 2) {
            throw RuntimeException("子view只能有两个")
        }
        topView = getChildAt(0)
        contentView = getChildAt(1) as ScrollView
//        bottomView = getChildAt(2)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        Log.d(TAG, "onLayout: $pullDistance")
        val topViewHeight:Int = topView?.height as Int
        topView?.layout(l, -topViewHeight - pullDistance, r, t - pullDistance)
        contentView?.layout(l, - pullDistance, r, b)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if(scrollY < 0){
            return true
        }
        when(ev?.action){
            MotionEvent.ACTION_DOWN ->{
                startY = ev.y.toInt()
            }
            MotionEvent.ACTION_MOVE ->{
                val moveY = ev.y.toInt()
                val detlaY = moveY - startY
                if(getTopPosition() && detlaY > mTouchSlop){
                    ev.action = MotionEvent.ACTION_DOWN
                    return true
                }
            }
            MotionEvent.ACTION_UP ->{

            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_DOWN ->{
                startY = event.y.toInt()
            }
            MotionEvent.ACTION_MOVE ->{
                val detlaY:Int = (event.y - startY).toInt()
                if(getTopPosition() && scrollY <= 0){
                    val distanceSlide = -(detlaY * 0.8).toInt()
                    val topHeight =  topView?.height
                    val distance = max(distanceSlide, -topHeight!!)
                    pullMove(distance + 1)

                }
                return true
            }
            MotionEvent.ACTION_UP ->{
                if(state == PullState.ON_REFRESH && scrollY < 0 && abs(scrollY) > topView?.height!!){
//                    resetView(scrollY - topView?.height!!)
                    returnView()
                    return true
                }else if(state == PullState.ON_REFRESH && scrollY < 0 && abs(scrollY) < topView?.height!!){
                    return true
                }
                Log.d(TAG, "onTouchEvent:pullDistance = $pullDistance   topViewHeight = ${topView?.height}")
                if(pullDistance < 0 && abs(pullDistance) < topView?.height!!){
                    Log.d(TAG, "onTouchEvent: 返回")
                    returnView()
                }else if(state != PullState.ON_REFRESH && pullDistance < 0 && abs(pullDistance) == topView?.height!!){
//                    state = PullState.ON_REFRESH
                    Log.d(TAG, "onTouchEvent: 开始加载 pullDistance $pullDistance")
                    returnView()
                }else{
                    Log.d(TAG, "onTouchEvent: 其他")
                }
            }
        }
        return true
    }

    private fun returnView() {
        val i = (-pullDistance)*1.0 / (topView?.height!!)
        Log.d(TAG, "resetView: $pullDistance")
        mScroller.startScroll(0,pullDistance-2,0, -pullDistance+2, (400 * i) .toInt())
        requestLayout()
//        state = PullState.ON_NULL
    }

//    private fun resetView(i: Int) {
//        Log.d(TAG, "resetView: $i")
////        mScroller.startScroll(0,i,0, -i)
//            returnView()
////        requestLayout()
//    }

    private fun pullMove(distance: Int) {
        Log.d(TAG, "pullMove: $distance")
        pullDistance = distance
        requestLayout()
    }

    private fun getTopPosition():Boolean{
        return contentView?.scrollY!! <= 0
    }

    override fun computeScroll() {
        super.computeScroll()
        Log.d(TAG, "computeScroll: 咚咚咚")
        if (mScroller.computeScrollOffset()) {
            Log.d(TAG, "computeScroll: ${mScroller.currY}")
            pullMove(mScroller.currY)
        }else{
            Log.d(TAG, "computeScroll: ${mScroller.currY}")
        }
    }

}