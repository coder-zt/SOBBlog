package com.coder.zt.sobblog.ui.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.coder.zt.sobblog.utils.ScreenUtils
import com.google.android.material.shape.CornerTreatment

class DataBindingAdapter {
}

@BindingAdapter("url")
fun setImageViewRes(iv: ImageView, url:String){
    val corners = RoundedCorners(ScreenUtils.dp2px(6f))
    val override = RequestOptions.bitmapTransform(corners)
        //.override(ScreenUtils.dp2px(30f), ScreenUtils.dp2px(30f));
    Glide.with(iv.context).load(url).apply(override).into(iv)
}