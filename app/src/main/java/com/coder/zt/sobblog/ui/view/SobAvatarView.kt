package com.coder.zt.sobblog.ui.view

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import androidx.annotation.RequiresApi
import com.coder.zt.sobblog.R
import com.luck.picture.lib.tools.ScreenUtils
import kotlin.math.min


/**
 * 自定义View-用户头像
 */

class SobAvatarView(context: Context, attrs: AttributeSet): androidx.appcompat.widget.AppCompatImageView(context, attrs) {

    companion object{
        private const val TAG = "SobAvatarView"
    }


    private var defaultBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.mipmap.tab_profile_normal)
    private val borderWidth = ScreenUtils.dip2px(context, 3f)
    private val isCircle = false
    private val paint:Paint by lazy{
        Paint()
    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun draw(canvas: Canvas?) {
        canvas?.let {
            Log.d(TAG, "draw: ${ScreenUtils.dip2px(context, 60f)}")
            val height = height - paddingBottom - paddingTop
            val width = width - paddingStart - paddingEnd
            Log.d(TAG, "draw: width: $width height: $height")
            val image:Bitmap = drawableToBitmap(drawable)
            val resSizeBitmap = resizeBitmap(image, width, height)
            if(isCircle){
                val sqrt2 = Math.sqrt(2.0)
                val smallR:Float = width/2.0f - ((width-borderWidth)/2.0f)*(sqrt2/2.0f).toFloat()//图标半径
                paint.color = resources.getColor(R.color.sob_vip_color, null)
                it.drawCircle(width/2.0f, height/2.0f, min(width,height)/2.0f, paint)
                it.drawBitmap(createCircleImage(resSizeBitmap, width, height),
                    paddingLeft.toFloat(), paddingTop.toFloat(), paint)
                paint.color = resources.getColor(R.color.sob_vip_color, null)
                it.drawCircle(width - smallR, height - smallR, smallR, paint)
                paint.color = Color.WHITE
                paint.textAlign = Paint.Align.CENTER
                paint.textSize = 30f
                paint.strokeWidth = ScreenUtils.dip2px(context, 2f).toFloat()
                it.drawText("V",width - smallR, height - smallR/1.7f, paint)
            }else{
                //画圆角矩形背景
                paint.color = resources.getColor(R.color.sob_vip_color, null)
                val backgroundRect = RectF(0.0f,0.0f, width.toFloat(), height.toFloat())
                val roundR = ScreenUtils.dip2px(context, 8f).toFloat()
                it.drawRoundRect(backgroundRect, roundR, roundR, paint)
                //画用户头像
                it.drawBitmap(createRoundImage(resSizeBitmap, width, height),
                    paddingLeft.toFloat(), paddingTop.toFloat(), paint)
                //画VIP图片的矩形背景
                val ratio = 0.67f
                val iconBgRect = RectF(width*ratio,(height * ratio),width.toFloat(),height.toFloat())
                paint.color = resources.getColor(R.color.sob_vip_color, null)
                it.drawRoundRect(iconBgRect, roundR*ratio*0.8f, roundR*ratio*0.8f, paint)
                //画字母V
                val path = Path()
                val leftX = iconBgRect.left + iconBgRect.width() * 0.3f
                val upY = iconBgRect.top + iconBgRect.height() * 0.2f
                val rightX = iconBgRect.right - iconBgRect.height() * 0.3f
                val downX = iconBgRect.left + iconBgRect.height() * 0.5f
                val downY = iconBgRect.bottom - iconBgRect.height() * 0.3f
                path.moveTo(leftX, upY)//左上起始点
                path.lineTo(downX, downY)//下中点
                path.lineTo(rightX, upY)//右上末尾点
                paint.color = Color.WHITE
                paint.style = Paint.Style.STROKE
                paint.strokeCap = Paint.Cap.ROUND
                paint.strokeWidth = ScreenUtils.dip2px(context, 1f).toFloat()
                it.drawPath(path, paint)
                paint.style = Paint.Style.FILL
            }

        }
        if (canvas == null) {
            super.draw(canvas)
        }

    }

    private fun createRoundImage(bitmap: Bitmap, width: Int, height: Int): Bitmap {
        paint.isAntiAlias = true
        paint.color = Color.BLUE
        val target = Bitmap.createBitmap(bitmap.width, bitmap.width, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(target)
        val min = Math.min(bitmap.width, bitmap.height)
        val dstRect = Rect(0, 0, width, height)
        val srcRect = Rect(0, 0, min, min)
        val backgroundRect = RectF(0.0f + borderWidth,0.0f + borderWidth, width.toFloat() - borderWidth, height.toFloat() - borderWidth)
        val roundR = ScreenUtils.dip2px(context, 8f).toFloat()
        canvas.drawRoundRect(backgroundRect, roundR - borderWidth, roundR - borderWidth, paint)
        // 核心代码取两个图片的交集部分
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, srcRect, dstRect, paint)
        paint.xfermode = null
//        canvas.restoreToCount(sc)
        return target
    }

    private fun drawableToBitmap(drawable: Drawable?): Bitmap {
        if(drawable == null){
            return defaultBitmap
        }else if(drawable is BitmapDrawable){
            (drawable as BitmapDrawable).bitmap
        }
        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0,0,canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }


    private fun resizeBitmap(bitmap: Bitmap, newWidth:Int, newHeight:Int):Bitmap{
        val width = bitmap.width
        val height = bitmap.height
        val x = (newWidth - width) / 2
        val y = (newHeight - height) / 2
        if (x > 0 && y > 0) {
            return Bitmap.createBitmap(bitmap, 0, 0, width, height, null, true)
        }
        var scale = 1f
        scale = if (width > height) {
            // 按照宽度进行等比缩放
            newWidth.toFloat() / width
        } else {
            // 按照高度进行等比缩放
            // 计算出缩放比
            newHeight.toFloat() / height
        }
        val matrix = Matrix()
        matrix.postScale(scale, scale)
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true)
    }

    private fun createCircleImage(bitmap: Bitmap, width:Int, height:Int):Bitmap {
        paint.isAntiAlias = true
        paint.color = Color.BLUE
        val target = Bitmap.createBitmap(bitmap.width, bitmap.width, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(target)
        val min = Math.min(bitmap.width, bitmap.height)
        val dstRect = Rect(0, 0, width, height)
        val srcRect = Rect(0, 0, min, min)
        canvas.drawCircle((width / 2f),(height / 2f), (Math.min(width, height) / 2f) - borderWidth, paint)
        // 核心代码取两个图片的交集部分
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, srcRect, dstRect, paint)
        paint.xfermode = null
//        canvas.restoreToCount(sc)
        return target
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(width, height)
    }
}