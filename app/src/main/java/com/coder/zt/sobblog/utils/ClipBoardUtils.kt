package com.coder.zt.sobblog.utils

import android.content.ClipboardManager
import android.content.Context
import com.coder.zt.sobblog.SOBApp

object ClipBoardUtils {

    fun paste(message:String){
        // 得到剪贴板管理器
        // 得到剪贴板管理器
        val cmb: ClipboardManager =
            SOBApp._context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        cmb.text = message.trim()
    }

}