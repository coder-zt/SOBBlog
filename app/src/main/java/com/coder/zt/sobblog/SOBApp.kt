package com.coder.zt.sobblog

import android.app.Application
import android.content.Context

class SOBApp:Application() {


    override fun onCreate() {
        super.onCreate()
        _context = this

    }

    companion object {
        var _context: Application? = null
        fun getContext(): Context {
            return _context!!
        }

    }
}
