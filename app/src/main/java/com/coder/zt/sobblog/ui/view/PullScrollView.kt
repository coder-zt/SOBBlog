package com.coder.zt.sobblog.ui.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Scroller
import java.lang.RuntimeException
import kotlin.math.abs
import kotlin.math.max

class PullScrollView(context:Context,attrs: AttributeSet): LinearLayout(context, attrs) {


    companion object{
        private const val TAG = "PullScrollView"
    }
    private enum class PullState{
        ON_REFRESH,
        ON_NULL
    }
    private var state = PullState.ON_NULL
    private var topView: View? = null
    private lateinit var contentView: CustomScrollView
    private var bottomView:View? = null
    private var startY:Int = 0
    private var pullDownDistance:Int = 0
    private var pullUpDistance:Int = 0
    private var touchBottom:Boolean = false

    val mScroller:Scroller by lazy {//DecelerateInterpolator()
        Scroller(context)
    }
    val mTouchSlop:Int by lazy{
        ViewConfiguration.get(context).scaledTouchSlop
    }

    fun getPullLen() = pullDownDistance

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount != 3) {
            throw RuntimeException("子view只能有三个")
        }
        topView = getChildAt(0)
        contentView = getChildAt(1) as CustomScrollView
        contentView.setSlideBottomListener {
            Log.d(TAG, "onFinishInflate: 下拉到了底部")
            touchBottom = true
        }
        bottomView = getChildAt(2)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        val topViewHeight:Int = topView?.height as Int
        val bottomViewHeight:Int = 300
        Log.d(TAG, "onLayout:pullDownDistance $pullDownDistance ")
        topView?.layout(l, -topViewHeight - pullDownDistance, r, t - pullDownDistance)
        contentView.layout(l, t - pullDownDistance-pullUpDistance , r, b-pullUpDistance)
        bottomView?.layout(l, b - pullUpDistance, r, t + bottomViewHeight- pullUpDistance)
        Log.d(TAG, "onLayout: bottomViewHeight ---> $bottomViewHeight")
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
                Log.d(TAG, "onInterceptTouchEvent: $detlaY")
                if(detlaY > 0){
                    if(getTopPosition() && detlaY > mTouchSlop){
                        ev.action = MotionEvent.ACTION_DOWN
                        return true
                    }
                }else{
                    if(getBottomPosition() && abs(detlaY) > mTouchSlop){
                        ev.action = MotionEvent.ACTION_DOWN
                        return true
                    }

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
                    val distanceSlide = -(detlaY * 0.4).toInt()
                    val topHeight =  topView?.height
                    val distance = max(distanceSlide, -topHeight!!)
                    pullMove(distanceSlide)

                }
                if(getBottomPosition()){
                    pullUp(detlaY)
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
                Log.d(TAG, "onTouchEvent:pullDistance = $pullDownDistance   topViewHeight = ${topView?.height}")
                if(pullDownDistance < 0 && abs(pullDownDistance) < topView?.height!!){
                    Log.d(TAG, "onTouchEvent: 返回")
                    returnView()
                }else if(state != PullState.ON_REFRESH && pullDownDistance < 0 && abs(pullDownDistance) == topView?.height!!){
//                    state = PullState.ON_REFRESH
                    Log.d(TAG, "onTouchEvent: 开始加载 pullDistance $pullDownDistance")
                    returnView()
                }else{
                    Log.d(TAG, "onTouchEvent: 其他")
                }
            }
        }
        return true
    }

    private fun pullUp(distance: Int) {
        Log.d(TAG, "pullUp: $distance")
        pullUpDistance = abs(distance)
        requestLayout()
    }

    private fun returnView() {
        val i = (-pullDownDistance)*1.0 / (topView?.height!!)
        Log.d(TAG, "resetView: $pullDownDistance")
        mScroller.startScroll(0,pullDownDistance,0, -pullDownDistance, (400 * i) .toInt())
        requestLayout()
//        state = PullState.ON_NULL
    }

//    private fun resetView(i: Int) {
//        Log.d(TAG, "resetView: $i")
////        mScroller.startScroll(0,i,0, -i)
//            returnView()
////        requestLayout()
//    }

    fun setClickListener(listener: (len:Int) -> Unit) {
        this.listener = listener
    }

    private lateinit var listener: (len:Int) -> Unit

    private fun pullMove(distance: Int) {
        pullDownDistance = distance
        listener.invoke(pullDownDistance)
        requestLayout()
    }

    private fun getTopPosition():Boolean{
        return contentView?.scrollY!! <= 0
    }
    private fun getBottomPosition():Boolean{
        return touchBottom
    }

    override fun computeScroll() {
        super.computeScroll()
        if (mScroller.computeScrollOffset()) {
            pullMove(mScroller.currY)
        }
    }

}