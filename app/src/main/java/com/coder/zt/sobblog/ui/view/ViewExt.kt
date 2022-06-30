package com.coder.zt.sobblog.ui.view

import android.view.LayoutInflater
import android.view.View

inline val View.layoutInflater: LayoutInflater
    get()= LayoutInflater.from(context)

inline fun  View.click(crossinline listener:(v:View)->Unit) {
    setOnClickListener{
        listener(it)
    }
}