package com.coder.zt.sobblog.ui.view.webwiew

import android.util.Log
import android.webkit.JavascriptInterface
import java.io.*


class JsApi(val callback:(code:EventCode)->Unit):Object() {
    companion object{
        private const val TAG = "JsApi"
    }

    enum class EventCode{
        Event_MOVE,
        Event_UP,
        Event_DOWN
    }

    @JavascriptInterface
    fun log(msg: String?) {
        Log.d(TAG, "log: $msg")
    }

    @JavascriptInterface
    fun touchEvent(eventCode: Int) {
        Log.d(TAG, "touchEvent: $eventCode")
        when(eventCode){
            0 -> callback.invoke(EventCode.Event_DOWN)
            1 -> callback.invoke(EventCode.Event_MOVE)
            2 -> callback.invoke(EventCode.Event_UP)
        }
    }

    @JavascriptInterface
    fun printPageSource(html: String?) {
        Log.d(TAG, "pageSource: $html")
        saveHtml(html!!)
    }

    private fun saveHtml(html: String) {
        val f = File("data/data/com.coder.zt.sobblog/result.html")
        if (!f.exists()) {
            f.createNewFile()
        }
        val fos = FileOutputStream(f)
        val input = OutputStreamWriter(BufferedOutputStream(fos))
        val bfReader = BufferedWriter(input)
        bfReader.write(html)
        bfReader.flush()
        bfReader.close()
    }
}