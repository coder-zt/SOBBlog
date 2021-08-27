package com.coder.zt.sobblog.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.TextView
import java.lang.Math.abs

@SuppressLint("AppCompatCustomView")
class EmojiView(context: Context, attrs: AttributeSet): TextView(context, attrs) {

    companion object{
        private const val TAG = "EmojiView"
    }

    private var index = -1
    private val position = IntArray(2)



 fun setIndex(i:Int){
     index = i
 }

    fun onSlide(deletePosition: IntArray):Float {
        if(index != -1 && (index - 1) % 8 >= 6){
            getLocationInWindow(position)
            val distance = (deletePosition[1] - position[1]) * 4
            val alpha = when {
                distance > 255 -> 255
                distance < 0 -> 0
                else -> kotlin.math.abs(position[1] - deletePosition[1])
            }
            return (255 - alpha)/255f
        }
        return 0f
    }
}