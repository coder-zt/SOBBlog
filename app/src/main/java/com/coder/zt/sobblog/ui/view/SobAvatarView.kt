package com.coder.zt.sobblog.ui.view

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import androidx.annotation.RequiresApi
import com.coder.zt.sobblog.R
import com.luck.picture.lib.tools.ScreenUtils
import kotlin.math.min
import kotlin.math.sqrt


/**
 * 自定义View-用户头像
 */

@RequiresApi(Build.VERSION_CODES.M)
class SobAvatarView(context: Context, attrs: AttributeSet): androidx.appcompat.widget.AppCompatImageView(context, attrs) {

    companion object{
        private const val TAG = "SobAvatarView"
    }

    /**
     * 默认属性
     */
    private var isCircle:Boolean = true
    private var isVip:Boolean = false

    private var defaultBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.mipmap.tab_profile_normal)
    private var borderWidth = ScreenUtils.dip2px(context, 3f)
    private val defBorderWidth = ScreenUtils.dip2px(context, 4f)
    private var viewHeight = 0
    private var viewWidth = 0
    private val sqrt2 = sqrt(2.0)
    private var smallR:Float = 0f
    private lateinit var circleBitmap:Bitmap
    private lateinit var roundBitmap:Bitmap
    private lateinit var vipIconRect:RectF
    private val paint:Paint by lazy{
        val p = Paint()
        p.color = resources.getColor(R.color.sob_vip_color, null)
        p
    }

    private val path:Path by lazy{
        val p = Path()
        val leftX = vipIconRect.left + vipIconRect.width() * 0.3f
        val upY = vipIconRect.top + vipIconRect.height() * 0.3f
        val rightX = vipIconRect.right - vipIconRect.height() * 0.3f
        val downX = vipIconRect.left + vipIconRect.height() * 0.5f
        val downY = vipIconRect.bottom - vipIconRect.height() * 0.3f
        p.moveTo(leftX, upY)//左上起始点
        p.lineTo(downX, downY)//下中点
        p.lineTo(rightX, upY)//右上末尾点
        p
    }

    init {
        val obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.SobAvatarView)
        isCircle = obtainStyledAttributes.getBoolean(R.styleable.SobAvatarView_isCircle, isCircle)
        isVip = obtainStyledAttributes.getBoolean(R.styleable.SobAvatarView_isVip, isVip)
    }


    override fun onDraw(canvas: Canvas?) {
        canvas?.let {
            if(viewHeight == 0 || viewWidth == 0){
                //计算相关属性的值
                calcValues()
            }
            if(isCircle){
                drawCircleStyle(it)
            }else{
                drawRoundStyle(it)
            }
            //画字母V
            drawLetterV(it)
        }
        if (canvas == null) {
            super.draw(canvas)
        }
    }

    /**
     * 画圆角矩形图像
     */
    private fun drawRoundStyle(it: Canvas) {
        //画圆角矩形背景
        val roundR = ScreenUtils.dip2px(context, 8f).toFloat()
        if (isVip) {
            val backgroundRect = RectF(0.0f, 0.0f, viewWidth.toFloat(), viewHeight.toFloat())
            it.drawRoundRect(backgroundRect, roundR, roundR, paint)
        }
        //画用户头像
        it.drawBitmap(roundBitmap, paddingLeft.toFloat(), paddingTop.toFloat(), paint)
        if (isVip) {
            //画VIP图片的矩形背景
            paint.color = resources.getColor(R.color.sob_vip_color, null)
            val ratio = 0.67f
            it.drawRoundRect(
                vipIconRect,
                roundR * ratio * 0.8f,
                roundR * ratio * 0.8f,
                paint
            )
        }
    }

    /**
     * 画圆形头像
     */
    private fun drawCircleStyle(it: Canvas) {
        //画圆形背景
        if (isVip) {
            it.drawCircle(
                viewWidth / 2.0f,
                viewHeight / 2.0f,
                min(viewWidth, viewHeight) / 2.0f,
                paint
            )
        }
        //画圆形头像图片
        it.drawBitmap(circleBitmap, paddingLeft.toFloat(), paddingTop.toFloat(), paint)
        //画圆形VIP背景
        if (isVip) {
            it.drawCircle(viewWidth - smallR, viewHeight - smallR, smallR, paint)
        }
    }

    /**
     * 画图案V
     */
    private fun drawLetterV(it: Canvas) {
        if (isVip) {
            paint.color = Color.WHITE
            paint.style = Paint.Style.STROKE
            paint.strokeCap = Paint.Cap.ROUND
            paint.strokeWidth = viewWidth / 30.0f
            it.drawPath(path, paint)
            paint.style = Paint.Style.FILL
            paint.color = resources.getColor(R.color.sob_vip_color, null)
        }
    }

    /**
     * 计算相关参数
     */
    private fun calcValues() {
        //计算控件的宽高
        viewHeight = height - paddingBottom - paddingTop
        viewWidth = width - paddingStart - paddingEnd
        //将设置的图片转化为控件大小的Bitmap
        val image: Bitmap = drawableToBitmap(drawable)
        val resSizeBitmap = resizeBitmap(image, viewWidth, viewHeight)
        //VIP broad宽度
        borderWidth =
            (defBorderWidth * 1.0 * (viewWidth * 1.0 / ScreenUtils.dip2px(context, 60f))).toInt()
        //VIP图标半径
        smallR = viewWidth / 2.0f - ((viewWidth - borderWidth) / 2.0f) * (sqrt2 / 2.0f).toFloat()
        if (isCircle) {
            circleBitmap = createCircleImage(resSizeBitmap, viewWidth, viewHeight)
        } else {
            roundBitmap = createRoundImage(resSizeBitmap, viewWidth, viewHeight)
        }
        vipIconRect = RectF(
            viewWidth - 2 * smallR, viewWidth - 2 * smallR,
            viewWidth.toFloat(), viewHeight.toFloat()
        )
    }

    /**
     * 创建圆角矩形图片
     */
    private fun createRoundImage(bitmap: Bitmap, width: Int, height: Int): Bitmap {
        paint.isAntiAlias = true
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
        return target
    }

    /**
     * darwable转为bitmap
     */
    private fun drawableToBitmap(drawable: Drawable?): Bitmap {
        if(drawable == null){
            return defaultBitmap
        }else if(drawable is BitmapDrawable){
            return drawable.bitmap
        }
        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0,0,canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }


    /**
     * 将bitmap改变其他大小
     */
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

    /**
     * 创建圆形图片
     */
    private fun createCircleImage(bitmap: Bitmap, width:Int, height:Int):Bitmap {
        paint.isAntiAlias = true
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
        return target
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    fun isVip(vip:Boolean){
        isVip = vip
        viewHeight = 0
        viewWidth = 0
        postInvalidate()
    }

    fun update() {
        viewHeight = 0
        viewWidth = 0
        postInvalidate()
    }
}