package com.coder.zt.sobblog.net

import android.provider.SyncStateContract
import android.util.Log
import com.coder.zt.sobblog.BuildConfig
import com.coder.zt.sobblog.utils.Constants
import com.coder.zt.sobblog.utils.GsonUtils
import com.coder.zt.sobblog.utils.SPUtils
import com.google.gson.Gson
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class CookiesManager : CookieJar {
    companion object{
        private const val TAG = "CookiesManager"
    }
    private val cookieStoreBlog = HashMap<String, List<Cookie>>()

    override fun saveFromResponse(httpUrl: HttpUrl, list: List<Cookie>) {
        Log.d(TAG,"Response httpUrl:$httpUrl")
        if (Constants.BASE_URL.contains(httpUrl.host)) {
            cookieStoreBlog.put(Constants.BASE_URL, list)
            val listStr = mutableListOf<String>()
            for (cookie in list) {
                listStr.add(GsonUtils.getInstance().toJson(cookie))
            }
            SPUtils.getInstance().saveList(Constants.BASE_URL, listStr)
        }
    }

    override fun loadForRequest(httpUrl: HttpUrl): List<Cookie> {
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
        return list
    }
}