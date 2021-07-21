package com.coder.zt.sobblog.ui.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
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


@BindingAdapter("url")
fun setImageViewRes(iv: ImageView, url:String){
    val corners = RoundedCorners(ScreenUtils.dp2px(6))
    val override = RequestOptions.bitmapTransform(corners)
    Glide.with(iv.context).load(url).apply(override).into(iv)
}

