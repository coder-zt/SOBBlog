package com.coder.zt.sobblog.utils

import android.graphics.Color
import android.os.Build
import android.text.Html
import android.view.Gravity
import android.view.LayoutInflater

import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.SOBApp
import com.coder.zt.sobblog.databinding.ToastContentViewBinding


class ToastUtils {

    companion object{
        //普通Toast展示
        fun show(msg:String){
            show(msg, false)
        }

        /**
         * 展示网络操作结果
         */
        fun showResult(pair: Pair<Boolean, String>){
            show(pair.second, pair.first)
        }


        /**
         * 提示错误信息
         */
        fun showError(msg:String){
            show(msg, true)
        }

        //普通Toast展示
        @RequiresApi(Build.VERSION_CODES.N)
        fun show(msg:String, error: Boolean){
            val toast = Toast(SOBApp.getContext())
            toast.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.TOP, 0, ScreenUtils.dp2px(60))
            toast.duration = Toast.LENGTH_LONG
            val inflate = DataBindingUtil.inflate<ToastContentViewBinding>(
                LayoutInflater.from(SOBApp.getContext()),
                R.layout.toast_content_view, null, false
            )
            //内容
            val sp = Html.fromHtml(msg,0, SOBApp.getContext().let { EmojiImageGetter(it, 2) }, null)
            inflate.tvContent.text = sp
            if(error){
                //背景
                inflate.llContainer.setBackgroundResource(R.drawable.toast_error_bg)
                //图标
                inflate.ivIcon.setBackgroundResource(R.mipmap.error)
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