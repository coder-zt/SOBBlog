package com.coder.zt.sobblog.net

import android.util.Log
import com.coder.zt.sobblog.utils.Constants
import com.coder.zt.sobblog.utils.GsonUtils
import com.coder.zt.sobblog.utils.SPUtils
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class CookiesManager : CookieJar {
    companion object{
        private const val TAG = "CookiesManager"
    }
    private val cookieStoreBlog = HashMap<String, List<Cookie>>()

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        Log.d(TAG,"Response httpUrl:$url")
        if (Constants.BASE_URL.contains(url.host)) {
            cookieStoreBlog.put(Constants.BASE_URL, cookies)
            val listStr = mutableListOf<String>()
            for (cookie in cookies) {
                listStr.add(GsonUtils.getInstance().toJson(cookie))
            }
            SPUtils.getInstance().saveList(Constants.BASE_URL, listStr)
        }else{
            Log.d(TAG,"saveFromResponse 未保存:${url.host}")
        }
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        val list = mutableListOf<Cookie>()
        if (!cookieStoreBlog[Constants.BASE_URL].isNullOrEmpty()) {
            return cookieStoreBlog[Constants.BASE_URL]!!
        }
        val results = SPUtils.getInstance().readList(Constants.BASE_URL)
        for (result in results) {
            if(result.isEmpty()){
                break
            }
            list.add(GsonUtils.getInstance().fromJson(result,Cookie::class.java))
        }
        Log.d(TAG, "loadForRequest: $list")
        return list
    }
}