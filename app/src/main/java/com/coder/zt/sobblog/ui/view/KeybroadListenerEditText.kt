package com.coder.zt.sobblog.ui.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.KeyEvent
import android.widget.EditText
import android.widget.LinearLayout

class KeybroadListenerEditText(context: Context, attrs: AttributeSet): androidx.appcompat.widget.AppCompatEditText(context, attrs) {

    companion object{
        private const val TAG = "KeybroadListenerEditTex"
    }

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent?): Boolean {
        Log.d(TAG, "onKeyPreIme: keycode:$keyCode   event:$event")
        return super.onKeyPreIme(keyCode, event)

    }

}