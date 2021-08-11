package com.coder.zt.sobblog.demo

import android.util.Log
import android.webkit.JavascriptInterface
import com.coder.zt.sobblog.ui.view.webwiew.JsApi
import java.io.*

class DemoJsApi {

    companion object{
        private const val TAG = "DemoJsApi"
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