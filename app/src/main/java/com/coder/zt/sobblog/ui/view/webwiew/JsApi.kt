package com.coder.zt.sobblog.ui.view.webwiew

import android.util.Log
import android.webkit.JavascriptInterface


class JsApi:Object() {
    companion object{
        private const val TAG = "JsApi"
    }
    // 定义JS需要调用的方法
    // 被JS调用的方法必须加入@JavascriptInterface注解
    @JavascriptInterface
    fun log(msg: String?) {
        Log.d(TAG, "log: $msg")
    }
}