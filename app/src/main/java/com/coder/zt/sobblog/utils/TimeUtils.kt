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
        //判断是否在同一天
        if(isTheSameDay(date, curDate)){
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
        }else{
            val pathDay = SimpleDateFormat("yyyy-MM-dd").format(date .time)
            val curDay = SimpleDateFormat("yyyy-MM-dd").format(curDate.time)
            val pathDate = SimpleDateFormat("yyyy-MM-dd").parse(pathDay)
            val curDate1 = SimpleDateFormat("yyyy-MM-dd").parse(curDay)
            val diffTime = curDate1!!.time - pathDate!!.time
            return when(val diff:Int = (diffTime/(1000*3600*24)).toInt()){
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
        }

    }

    @SuppressLint("SimpleDateFormat")
    private fun isTheSameDay(date: Date, curDate: Date): Boolean {
        val pathTime = SimpleDateFormat("yyyy-MM-dd").format(date)
        val curTime = SimpleDateFormat("yyyy-MM-dd").format(curDate)
        return  pathTime == curTime
    }
}