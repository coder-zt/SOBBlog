package com.coder.zt.sobblog.utils

import android.app.Activity
import android.content.res.Resources




object ScreenUtils {
        /**
         * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
         */
        fun dp2px(dpValue: Int): Int {
            return (0.5f + dpValue * Resources.getSystem().displayMetrics.density).toInt()
        }

        /**
         * 根据手机的宽
         */
        fun getScreenWidth(): Int {
            return Resources.getSystem().displayMetrics.widthPixels
        }

    /**
     * 根据手机的高
     */
    fun getScreenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }

    fun setWindowBackground(activity:Activity, roate:Float){
        val lp  = activity.window.attributes
        lp.alpha = roate
        activity.window.attributes = lp
    }

    fun resortWindowBackground(activity:Activity,){
        val lp  = activity.window.attributes
        lp.alpha =  1.0f
        activity.window.attributes = lp
    }
}