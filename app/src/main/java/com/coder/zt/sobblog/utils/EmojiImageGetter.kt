package com.coder.zt.sobblog.utils


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.Html
import android.util.Base64
import android.util.Log
import android.widget.TextView
import androidx.core.graphics.scale


class EmojiImageGetter(val context: Context, val size:Int) :Html.ImageGetter {
    companion object{
        private const val TAG = "EmojiImageGetter"
    }
    // source = https://cdn.sunofbeaches.com/emoji/93.png
    override fun getDrawable(source: String?): Drawable {
        Log.d(TAG, "getDrawable: source ===>  $source")
        var drawable: Drawable? = null
        source?.let{
            if(it.endsWith(".png")){
                if (source.isNotEmpty()) {
                    // 截取图片名称   可根据自己的实际情况选择
                    val splitNames = source.split("/");
                    val imgName = splitNames[splitNames.size - 1]
                    val assetMan =  context.assets
                    drawable = BitmapDrawable.createFromStream( assetMan.open("emoji/$imgName"), imgName)
                    drawable?.setBounds(0, 0, drawable!!.getIntrinsicWidth() * size, drawable!!.getIntrinsicHeight() * size)
                }else{

                }
            }else{
                val bytes:ByteArray = Base64.decode(it.split(",")[1], Base64.DEFAULT)
                Log.d(TAG, "getDrawable: bytes.size: ${bytes.size}")
                var bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                val matrix = Matrix()
                matrix.setScale(3f, 3f)
                val bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
                Log.d(TAG, "getDrawable: width: ${bitmap.width}  height: ${bitmap.height}")
                drawable = BitmapDrawable(bm)
                drawable?.setBounds(0, 0, drawable!!.getIntrinsicWidth() * size, drawable!!.getIntrinsicHeight() * size)
            }
        }
        return drawable!!
    }
}