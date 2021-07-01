package com.coder.zt.sobblog.ui.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.LinearLayout
import android.widget.Scroller
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

class RefreshView(context:Context, attrs: AttributeSet): LinearLayout(context, attrs) {


    companion object{
        private const val TAG = "PullScrollView"
    }
    private enum class RefreshState{
        ON_REFRESH,
        ON_LOADING,
        ON_NULL
    }
    private var state = RefreshState.ON_NULL
    private lateinit var topView: View
    private lateinit var contentView: RecyclerView
    private lateinit var bottomView:View
    private var startY:Int = 0
    private var pullDownDistance:Int = 0
    private var pullUpDistance:Int = 0
    private var touchBottom:Boolean = false
    private var touchTop:Boolean = false
    private var canRefresh:Boolean = false
    private val mScroller:Scroller by lazy {//DecelerateInterpolator()
        Scroller(context)
    }
    private val mTouchSlop:Int by lazy{
        ViewConfiguration.get(context).scaledTouchSlop
    }


    override fun onFinishInflate() {
        super.onFinishInflate()
//        if (childCount != 3) {
//            throw RuntimeException("子view只能有三个")
//        }
        topView = getChildAt(0)
        contentView = getChildAt(1) as RecyclerView
        bottomView = getChildAt(2)
        contentView.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                touchBottom = !contentView.canScrollVertically(1)
                touchTop = !contentView.canScrollVertically(-1)
                Log.d(TAG, "onScrolled: touchBottom:$touchBottom    touchTop: $touchTop")
            }
        })
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val topViewHeight:Int = topView.measuredHeight
        val bottomViewHeight:Int = bottomView.measuredHeight
        Log.d(TAG, "onLayout: bottomViewHeight $bottomViewHeight")
        topView.layout(l, -topViewHeight + pullDownDistance, r, t + pullDownDistance)
        contentView.layout(l, t + pullDownDistance-pullUpDistance , r, b- pullUpDistance)
        bottomView.layout(l, b - pullUpDistance, r, b + bottomViewHeight- pullUpDistance)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if(state != RefreshState.ON_NULL){
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
        if(state != RefreshState.ON_NULL){
            return true
        }
        when(event?.action){
            MotionEvent.ACTION_DOWN ->{
                startY = event.y.toInt()
            }
            MotionEvent.ACTION_MOVE ->{
                val detlaY:Int = (event.y - startY).toInt()
                if(getTopPosition()){
                    pullDown(abs((detlaY * 0.4).toInt()))

                }
                if(getBottomPosition()){
                    pullUp(abs(detlaY))
                }
                return true
            }
            MotionEvent.ACTION_UP ->{
                returnView()
            }
        }
        return true
    }

    private fun pullUp(distance: Int) {
        Log.d(TAG, "pullUp: $distance")
        pullUpDistance = distance
        requestLayout()
    }

    private fun returnView() {
        if(getTopPosition() && pullDownDistance >0){
            val i = (-pullDownDistance)*1.0 / (topView.height)
            mScroller.startScroll(0,pullDownDistance,0, -pullDownDistance, (400 * i) .toInt())
            if(canRefresh  && state != RefreshState.ON_REFRESH){
                state = RefreshState.ON_REFRESH
                Log.d(TAG, "returnView: 刷新数据")
                callback.onRefresh()
            }
            requestLayout()
        }else if(getBottomPosition() && pullUpDistance >0){
            Log.d(TAG, "returnView: getBottomPosition() ${getBottomPosition()}  pullUpDistance $pullUpDistance ")
//            val i = (pullUpDistance)*1.0 / (bottomView.height)
            val showDistance = if(pullUpDistance >= bottomView.height && state != RefreshState.ON_LOADING){
                Log.d(TAG, "returnView: 加载更多数据")
                callback.onLoading()
                state = RefreshState.ON_LOADING
                bottomView.height
            }else{
                0
            }
            mScroller.startScroll(0,pullUpDistance,0, showDistance -pullUpDistance , (400 * 1) .toInt())
            requestLayout()
            contentView.postInvalidate()
        }

    }


    fun setPullDownListener(listener: (len:Int) -> Boolean) {
        this.listener = listener
    }

    private lateinit var listener: (len:Int) -> Boolean

    private fun pullDown(distance: Int) {
        Log.d(TAG, "pullMove: distance ---> $distance")
        pullDownDistance = distance
        canRefresh = !listener.invoke(pullDownDistance)
        requestLayout()
    }

    private fun getTopPosition():Boolean{
        return touchTop
    }
    private fun getBottomPosition():Boolean{
        return touchBottom
    }

    override fun computeScroll() {
        super.computeScroll()
        if (mScroller.computeScrollOffset()) {
            if(getTopPosition() && pullDownDistance >0){
                pullDown(mScroller.currY)
            }
            if(getBottomPosition() && pullUpDistance >0){

                pullUp(mScroller.currY)
            }

        }
        Log.d(TAG, "computeScroll:pullUp getBottomPosition() ${getBottomPosition()}  pullUpDistance ${pullUpDistance} ")
        Log.d(TAG, "computeScroll: currY:${mScroller.currY}")
    }

    private lateinit var callback : OnRefreshListener

    fun setOnRefreshListener(listener: OnRefreshListener){
        callback = listener
    }

    interface OnRefreshListener{
        fun onRefresh()

        fun onLoading()
    }

    fun refreshFinished(){
        state = RefreshState.ON_NULL
    }

    fun loadedFinished(){
        state = RefreshState.ON_NULL
        mScroller.startScroll(0,bottomView.height,0, -bottomView.height , (400 * 1))
        requestLayout()
    }

}