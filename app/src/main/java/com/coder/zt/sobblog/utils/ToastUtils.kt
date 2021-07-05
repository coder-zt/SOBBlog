package com.coder.zt.sobblog.utils

import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView

import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.SOBApp
import com.coder.zt.sobblog.databinding.ToastContentViewBinding
import okhttp3.CookieJar
import org.w3c.dom.Text


class ToastUtils {

    companion object{
        //普通Toast展示
        fun show(msg:String){
            show(msg, false)
        }

        fun showError(msg:String){
            show(msg, true)
        }

        //普通Toast展示
        fun show(msg:String, error: Boolean){
            val toast = Toast(SOBApp._context)
            toast.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.TOP, 0, ScreenUtils.dp2px(60))
            toast.duration = Toast.LENGTH_LONG
            val inflate = DataBindingUtil.inflate<ToastContentViewBinding>(
                LayoutInflater.from(SOBApp._context),
                R.layout.toast_content_view, null, false
            )
            //内容
            inflate.tvContent.text = msg
            if(error){
                //背景
                inflate.llContainer.setBackgroundResource(R.drawable.toast_error_bg)
                //图标
                inflate.ivIcon.setBackgroundResource(R.mipmap.error)
                //        <!--    67C23A-->
                //        <!--    fe6c6c-->、
                //字体颜色
                inflate.tvContent.setTextColor(Color.parseColor("#fe6c6c"))

            }else{
                //背景
                inflate.llContainer.setBackgroundResource(R.drawable.toast_ok_bg)
                //图标
                inflate.ivIcon.setBackgroundResource(R.mipmap.ok)
                //字体颜色
                inflate.tvContent.setTextColor(Color.parseColor("#67C23A"))
            }
            toast.view = inflate.root
            toast.show()
        }
    }
}