package com.coder.zt.sobblog.ui.adapter

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import android.text.Html
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.target.ImageViewTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.ui.activity.MainActivity
import com.coder.zt.sobblog.ui.view.SobAvatarView
import com.coder.zt.sobblog.utils.ScreenUtils
import com.coder.zt.sobblog.utils.TimeUtils
import com.coder.zt.sobblog.utils.TransUtils
import java.lang.StringBuilder
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "DataBindingAdapter"


@BindingAdapter(value=["pic_url", "pic_corner"], requireAll = false)
fun setImageViewCorner(iv: ImageView, pic_url:String?, pic_corner: Int?){
    pic_url?.let {
        var override:RequestOptions? = null
        pic_corner?.let {
            if(pic_corner > 0){
                val corners = RoundedCorners(ScreenUtils.dp2px(pic_corner))
                override = RequestOptions.bitmapTransform(corners)
            }
        }
        if (override == null) {
            Glide.with(iv.context).load(pic_url).into(iv)
        }else{
            Glide.with(iv.context).load(pic_url).apply(override!!).into(iv)
        }

    }
}


@BindingAdapter("avatar")
fun setAvatarImage(iv: SobAvatarView, avatar:String){
    if (avatar == "null") {
        return
    }
    Glide.with(iv.context).asBitmap().load(avatar).listener(object:RequestListener<Bitmap>{
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Bitmap>?,
            isFirstResource: Boolean
        ): Boolean {
            Log.d(TAG, "onLoadFailed: ")
            return true
        }

        @RequiresApi(Build.VERSION_CODES.M)
        override fun onResourceReady(
            resource: Bitmap?,
            model: Any?,
            target: Target<Bitmap>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            Log.d(TAG, "onResourceReady: ")
            resource?.let {
                iv.update(it)
            }
            return true
        }

    }).into(iv)
}


@BindingAdapter("url")
fun setImageViewRes(iv: ImageView, url:String){
    val corners = RoundedCorners(ScreenUtils.dp2px(6))
    val override = RequestOptions.bitmapTransform(corners)
    Glide.with(iv.context).load(url).apply(override).into(iv)
}


/**
 * 分享链接的图片设置Icon
 */
@BindingAdapter("link_url")
fun setLinkIcon(iv: ImageView, link_url:String){
    if (link_url == "no_link") {
        return
    }
    val parttern = Regex("(.*?)/.*?")
    val results = parttern.findAll(link_url)
    val iconUrl = StringBuilder().run{
        for ((index, result) in results.withIndex()) {
            if(index >= 3){
                break
            }
            append(result.value)
        }
        append("favicon.ico")
        toString()
    }
    val corners = RoundedCorners(ScreenUtils.dp2px(4))
    val override = RequestOptions.bitmapTransform(corners)
    Glide.with(iv.context).load(iconUrl).apply(override).into(iv)
}

/**
 * 分享链接的Host
 */
@BindingAdapter("url_host")
fun setLinkHost(tv: TextView, url_host:String){
    if (url_host == "no_link") {
        return
    }
    val parttern = Regex("(.*?)/.*?")
    val results = parttern.findAll(url_host)
    var urlHost = StringBuilder().run{
        for ((index, result) in results.withIndex()) {
            if(index >= 3){
                break
            }
            append(result.value)
        }
        delete(length - 1, length)
        toString()
    }
    urlHost = urlHost.replace("https://", "")
    urlHost = urlHost.replace("http://", "")
    tv.text = urlHost
}


/**
 * 时间的显示
 */
@RequiresApi(Build.VERSION_CODES.N)
@BindingAdapter("time_text")
fun setTimeText(tv: TextView, time_text:String){
    Log.d(TAG, "setTimeText: $time_text")
    val date = TransUtils.transStrToDate(time_text)
    tv.text = TimeUtils.getTimeShowText(date)
}

/**
 * VIP昵称的颜色
 */
@RequiresApi(Build.VERSION_CODES.M)
@BindingAdapter("vip_text_color")
fun setVipTextColor(tv: TextView, vip_text_color:Boolean){
    Log.d(TAG, "setVipTextColor: $vip_text_color")
    if(vip_text_color){
        tv.setTextColor(tv.resources.getColor(R.color.sob_vip_color, null) )
    }
}

/**
 * 评论回复
 */
@BindingAdapter(value=["reply_target", "reply_content"], requireAll = false)
fun setReplyContent(tv: TextView, reply_target:String,reply_content:String){
   tv.text = Html.fromHtml("<font color=\"black\">回复</font><font color=\"#409eef\">@${reply_target}</font><font color=\"black\"> ：${reply_content}</font>")

}



