package com.coder.zt.sobblog.utils

import android.annotation.SuppressLint
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {
    private const val TAG = "TimeUtils"

    @SuppressLint("SimpleDateFormat")
    fun getTimeShowText(date: Date):String{
        val curDate = Date()
        val curDay = SimpleDateFormat("dd").format(curDate).toInt()
        val pastDay = SimpleDateFormat("dd").format(date).toInt()
        val diff = curDay - pastDay
        return if(diff != 0){
            when(diff){
                1 -> "昨天"
                2 -> "前天"
                else -> {
                    if(diff < 7){
                        "${diff}天前"
                    }else{
                        val pathYear = SimpleDateFormat("yyyy").format(date)
                        val curYear = SimpleDateFormat("yyyy").format(curDate)
                        if(pathYear == curYear){
                            SimpleDateFormat("MM-dd").format(date)
                        }else{
                            SimpleDateFormat("yyyy-MM-dd").format(date)
                        }
                    }
                }
            }
        }else{
            val pathTime = SimpleDateFormat("HH:mm").format(date)
            val curTime = SimpleDateFormat("HH:mm").format(curDate)
            val pathMinute = pathTime.split(":")[0].toInt() * 60 + pathTime.split(":")[1].toInt()
            val curMinute = curTime.split(":")[0].toInt() * 60 + curTime.split(":")[1].toInt()
            Log.d(TAG, "getTimeShowText: pathTime: $pathTime  curTime: $curTime  pathMinute: $pathMinute curMinute: $curMinute")
            val diffMinute = curMinute - pathMinute
            return when {
                diffMinute == 0 -> "刚刚"
                diffMinute < 60 -> "${diffMinute}分钟前"
                else -> "${diffMinute/60}小时前"
            }
        }
    }
}