package com.coder.zt.sobblog.ui.base

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.ActivityLoginBinding
import com.coder.zt.sobblog.utils.AppRouter
import com.coder.zt.sobblog.utils.Constants
import com.xuexiang.xupdate.XUpdate

open abstract class BaseActivity<T:ViewDataBinding>:AppCompatActivity() {

    val dataBinding: T by lazy{
        DataBindingUtil.setContentView(this, getLayoutId())
    }

    protected abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= 21) {
            val decorView = window.decorView
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    override fun onResume() {
        super.onResume()
    }

     fun onBack(view:View) {
        finish()
    }
}