package com.coder.zt.sobblog.utils

import android.content.res.Resources




class ScreenUtils {
    companion object{
        /**
         * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
         */
        fun dp2px(dpValue: Float): Int {
            return (0.5f + dpValue * Resources.getSystem().displayMetrics.density).toInt()
        }
    }
}