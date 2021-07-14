package com.coder.zt.sobblog.ui.adapter

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.coder.zt.sobblog.utils.ScreenUtils
import com.google.android.material.shape.CornerTreatment

private const val TAG = "DataBindingAdapter"
@BindingAdapter(value=["pic_url", "pic_corner"], requireAll = true)
fun setImageViewCorner(iv: ImageView, pic_url:String, pic_corner: Int){
    Log.d(TAG, "setImageViewCorner: $pic_url")
    val corners = RoundedCorners(ScreenUtils.dp2px(pic_corner))
    val override = RequestOptions.bitmapTransform(corners)
    //.override(ScreenUtils.dp2px(30f), ScreenUtils.dp2px(30f));
    Glide.with(iv.context).load(pic_url).apply(override).into(iv)
}


@BindingAdapter("url")
fun setImageViewRes(iv: ImageView, url:String){
    val corners = RoundedCorners(ScreenUtils.dp2px(6))
    val override = RequestOptions.bitmapTransform(corners)
        //.override(ScreenUtils.dp2px(30f), ScreenUtils.dp2px(30f));
    Glide.with(iv.context).load(url).apply(override).into(iv)
}

