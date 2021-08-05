package com.coder.zt.sobblog.utils

import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*

object TransUtils {

    private const val TAG = "TransUtils"

    @RequiresApi(Build.VERSION_CODES.N)
    fun transStrToDate(timeText: String):Date {
        return SimpleDateFormat("yyyy-MM-dd HH:mm").parse(timeText)
    }

    /**
     * 根据链接分发到不同的目的地
     */
    fun dispatchShareLink(activity: Activity, url: String) {
        if(url.contains(Constants.WEBSITE_URL)){//网站内部
            if(url.contains("/a/")){//链接为网站的文章
                val parttern = Regex("\\d+")
                val result = parttern.find(url)
                result?.let {
                    AppRouter.toArticleDetailActivity(activity, result.value)
                }
            }else if(url.contains("/m/")){//链接为网站的摸鱼动态
                val parttern = Regex("\\d+")
                val result = parttern.find(url)
                result?.let {
                    AppRouter.toMomentDetailActivity(activity, result.value)
                }
            }else if(url.endsWith("#/index/setting/sobRecord")){
                AppRouter.toSunofLogActivity(activity)
            }
        }else{//网站外部链接
            AppRouter.toOutsideWebsite(activity, url)
        }

    }
}