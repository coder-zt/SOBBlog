package com.coder.zt.sobblog.ui.adapter

import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import android.widget.ImageView
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
import com.coder.zt.sobblog.ui.view.SobAvatarView
import com.coder.zt.sobblog.utils.ScreenUtils

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

@RequiresApi(Build.VERSION_CODES.M)
@BindingAdapter("avatar")
fun setAvatarImage(iv: SobAvatarView, avatar:String){
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

        override fun onResourceReady(
            resource: Bitmap?,
            model: Any?,
            target: Target<Bitmap>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            Log.d(TAG, "onResourceReady: ")
            iv.setImageBitmap(resource)
            iv.update()
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

