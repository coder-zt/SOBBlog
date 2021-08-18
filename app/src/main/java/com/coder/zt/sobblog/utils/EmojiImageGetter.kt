package com.coder.zt.sobblog.utils


import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.Html
import android.util.Log
import android.widget.TextView


class EmojiImageGetter(val context: Context, val size:Int) :Html.ImageGetter {
    companion object{
        private const val TAG = "EmojiImageGetter"
    }
    // source = https://cdn.sunofbeaches.com/emoji/93.png
    override fun getDrawable(source: String?): Drawable {
        Log.d(TAG, "getDrawable: source ===>  $source")
        var drawable: Drawable? = null
        if (source != null && source.isNotEmpty()) {
            // 截取图片名称   可根据自己的实际情况选择
           val splitNames = source.split("/");
           val imgName = splitNames[splitNames.size - 1]
           val assetMan =  context.assets
            drawable = BitmapDrawable.createFromStream( assetMan.open("emoji/$imgName"), imgName)
            drawable?.setBounds(0, 0, drawable.getIntrinsicWidth() * size, drawable.getIntrinsicHeight() * size)
        }
        return drawable!!
    }
}