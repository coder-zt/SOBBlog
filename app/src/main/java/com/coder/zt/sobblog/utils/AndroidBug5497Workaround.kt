package com.coder.zt.sobblog.utils


import android.app.Activity
import android.graphics.Rect
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.widget.ContentFrameLayout
import com.coder.zt.sobblog.SOBApp


class AndroidBug5497Workaround(val activity: Activity) {
    companion object{
        private const val TAG = "AndroidBug5497Workaroun"
        fun assistActivity(activity: Activity) {
            AndroidBug5497Workaround(activity)
        }

    }

    private var mChildOfContent: View? = null
    private var usableHeightPrevious = 0
    private var frameLayoutParams: FrameLayout.LayoutParams? = null

    init {
        val content = (activity.window.decorView as ViewGroup)
        Log.d(TAG, "decorView type :${content.javaClass} ")
        Log.d(TAG, "decorView children count :${content.childCount} ")
        val child = content.getChildAt(0) as ViewGroup
        Log.d(TAG, "child type :${child.javaClass} ")
        Log.d(TAG, "child children count :${child.childCount} ")
        mChildOfContent = child.getChildAt(1)
        Log.d(TAG, "child child type :${child.getChildAt(1).javaClass} ")
        Log.d(TAG, "child child children count :${(child.getChildAt(1) as ViewGroup).childCount} ")
        child.getViewTreeObserver()?.addOnGlobalLayoutListener { possiblyResizeChildOfContent() }
        frameLayoutParams = child.getLayoutParams() as FrameLayout.LayoutParams?
    }

    private fun possiblyResizeChildOfContent() {
        val isShow = NavigationUtils.hasNavigationBar(activity)
        Log.d(TAG, "possiblyResizeChildOfContent:$isShow 是否显示了导航条 ===> ${NavigationUtils.isMiuiFullScreen(activity)}")
        val usableHeightNow = computeUsableHeight()//剩余内容的高度
        if (usableHeightNow != usableHeightPrevious) {
            val usableHeightSansKeyboard = mChildOfContent!!.rootView.height//整个屏幕的高度
            val heightDifference = usableHeightSansKeyboard - usableHeightNow
            if (heightDifference > usableHeightSansKeyboard / 4) {
                Log.d(TAG, "possiblyResizeChildOfContent: 大于")
                frameLayoutParams!!.height = usableHeightSansKeyboard - heightDifference + 96
            } else {
                Log.d(TAG, "possiblyResizeChildOfContent: 小于等于")
                frameLayoutParams!!.height = usableHeightSansKeyboard - if(isShow || NavigationUtils.isMiuiFullScreen(activity)){
                    NavigationUtils.getNavigationBarHeight(SOBApp.getContext())
                }else{
                    0
                }
            }
            mChildOfContent!!.requestLayout()
            usableHeightPrevious = usableHeightNow
        }
    }

    private fun computeUsableHeight(): Int {
        val r = Rect()
        mChildOfContent!!.getWindowVisibleDisplayFrame(r)
        Log.d(TAG, "computeUsableHeight: $r")
        return r.bottom - r.top
    }
}