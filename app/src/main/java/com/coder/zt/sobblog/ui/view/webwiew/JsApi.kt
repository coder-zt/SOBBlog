package com.coder.zt.sobblog.ui.view.webwiew

import android.util.Log
import android.webkit.JavascriptInterface


class JsApi(val callback:(code:EventCode)->Unit):Object() {
    companion object{
        private const val TAG = "JsApi"
    }

    enum class EventCode{
        Event_MOVE,
        Event_UP,
        Event_DOWN
    }
    // 定义JS需要调用的方法
    // 被JS调用的方法必须加入@JavascriptInterface注解
    @JavascriptInterface
    fun log(msg: String?) {
        Log.d(TAG, "log: $msg")
    }

    @JavascriptInterface
    fun touchEvent(eventCode: Int) {
        Log.d(TAG, "log: $eventCode")
        when(eventCode){
            0 -> callback.invoke(EventCode.Event_DOWN)
            1 -> callback.invoke(EventCode.Event_MOVE)
            2 -> callback.invoke(EventCode.Event_UP)
        }
    }
}