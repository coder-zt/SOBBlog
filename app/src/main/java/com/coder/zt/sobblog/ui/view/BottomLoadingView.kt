package com.coder.zt.sobblog.ui.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import com.coder.zt.sobblog.R

class BottomLoadingView(context: Context, attrs: AttributeSet): View(context, attrs)  {

    companion object{
        private const val TAG = "BottomLoadingView"
    }
    init {
        setBackgroundColor(Color.TRANSPARENT)
    }

    val loadingIcon: Bitmap by lazy {
        BitmapFactory.decodeResource(resources, R.mipmap.adx)
    }

    var rotateValue:Float = 0.0f
    var rotateSwitch:Boolean = false

    val paint = Paint()

    val bitmapRect by lazy {
        Rect(0,0 ,loadingIcon.width, loadingIcon.height)
    }

    val dstRect by lazy {
        Rect(0, 0, width, height)
    }

    fun startRotate() {
        rotateSwitch = true
        post(object : Runnable {
            override fun run() {
                rotateValue += 10.0f
                //范围判断，如果超过了360度，则变成0度
                rotateValue = if (rotateValue <= 360) rotateValue else 0.0f
                invalidate()
                //判断是否再旋转
                if (rotateSwitch && isVisible) {
                    removeCallbacks(this)
                    postDelayed(this, 20)
                }
            }
        })
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            it.rotate(rotateValue, (width/2).toFloat(), (height/2).toFloat())
            it.drawBitmap(loadingIcon, bitmapRect, dstRect, paint)
            rotateValue += 10
        }
        invalidate()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Log.d(TAG, "onAttachedToWindow: ")
        startRotate()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Log.d(TAG, "onDetachedFromWindow: ")
        stopRotate()
    }

    private fun stopRotate() {
        rotateSwitch = false
    }
}