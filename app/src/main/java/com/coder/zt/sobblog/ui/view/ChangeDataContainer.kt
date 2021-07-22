package com.coder.zt.sobblog.ui.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.model.user.UserAchievement
import com.luck.picture.lib.tools.ScreenUtils

class ChangeDataContainer(context: Context, attrs: AttributeSet): LinearLayout(context, attrs)   {

companion object{
    private const val TAG = "ChangeDataContainer"
}

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val childWidth = getChildAt(0).measuredWidth
        val childHeight = getChildAt(0).measuredHeight
        val screenWidth = ScreenUtils.getScreenWidth(context)
        val spaceTotalWidth = screenWidth - childWidth * childCount
        val blankWidth = spaceTotalWidth/(childCount * 2)
        var left = 0
        for(i in 0 until childCount){
            val childView = getChildAt(i)
            left += blankWidth

            Log.d(TAG, "onFinishInflate: $height")
            val childTop = (height - childHeight)/2
            childView.layout(left, childTop,left + childWidth,childTop + childHeight)
            left += childWidth + blankWidth
        }
    }
    override fun onFinishInflate() {
        super.onFinishInflate()
        val width = getChildAt(0).measuredWidth
    }

    fun setData(achievement:UserAchievement){
        for(i in 0 until childCount){
            val childView = getChildAt(i) as ChangeDataView
            when(i){
                0->childView.setData("阅读总量", achievement.atotalView, achievement.articleDxView)
                1->childView.setData("粉丝总量", achievement.fansCount, achievement.fansDx)
                2->childView.setData("获赞总量", achievement.thumbUpTotal, achievement.thumbUpDx)
                3->childView.setData("Sunof币", achievement.sob, achievement.sobDx)

            }
        }
    }

}