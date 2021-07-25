package com.coder.zt.sobblog.ui.base

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.ActivityLoginBinding
import com.coder.zt.sobblog.utils.AppRouter
import com.umeng.analytics.MobclickAgent
import com.coder.zt.sobblog.utils.Constants
import com.coder.zt.sobblog.utils.StatusBarUtil
import com.xuexiang.xupdate.XUpdate

open abstract class BaseActivity<T:ViewDataBinding>:AppCompatActivity() {
    companion object{
        private const val TAG = "BaseActivity"
    }
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
//        //底部虚拟导航栏适配
//        if (StatusBarUtil.hasNavigationBarShow(this)) {
//            Log.d(TAG, "onCreate: hasNavigationBarShow")
//            Log.d(TAG, "onCreate: ${StatusBarUtil.getNavigationBarHeight(this)}")
//            getWindow().getDecorView().findViewById<FrameLayout>(android.R.id.content).setPadding(0, 0, 0, StatusBarUtil.getNavigationBarHeight(this))
//        }
    }


     fun onBack(view:View) {
        finish()
    }

    override fun onResume() {
        super.onResume()
        MobclickAgent.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        MobclickAgent.onPause(this)
    }
}