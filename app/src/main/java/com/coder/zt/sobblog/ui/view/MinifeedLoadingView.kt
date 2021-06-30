package com.coder.zt.sobblog.ui.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import android.widget.FrameLayout
import android.widget.Scroller
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.utils.ScreenUtils
import kotlin.math.abs
import kotlin.math.min

class MinifeedLoadingView(context: Context, attrs: AttributeSet): View(context, attrs) {

    companion object{
        private const val TAG = "MinifeedLoadingView"
    }
    init {
        setBackgroundColor(Color.TRANSPARENT)
        visibility = GONE
    }

    val loadingIcon:Bitmap by lazy {
        BitmapFactory.decodeResource(resources, R.mipmap.ic_minifeed_loading)
    }

    val bitmapRect by lazy {
        Rect((loadingIcon.width * 0.025).toInt(), (loadingIcon.height * 0.025).toInt(),
            (loadingIcon.width * 0.975).toInt(), (loadingIcon.height* 0.975).toInt())
    }

    val dstRect by lazy {
        Rect(0, 0, width, height)
    }

    val paint by lazy {
        val p = Paint()
        p.color = Color.WHITE
        p
    }

    val loadingLen = ScreenUtils.dp2px(80)

    val mScroller: Scroller by lazy {
        Scroller(context, DecelerateInterpolator())
    }
    private var distance:Float = 0.0f
    private var rotate:Float = 0.0f
    private var updateLocal:Boolean = true
    private var loading:Boolean = false

    fun setDistance(len:Float){
        if(!updateLocal){
            if(abs(len) < loadingLen && !loading){
                callback.invoke()
                loading = true
            }
            return
        }
        distance = abs(len)
        rotate = distance
        visibility = VISIBLE
        updateLayoutParams<FrameLayout.LayoutParams> {
            this.topMargin = min(loadingLen, distance.toInt() - height)
            if(this.topMargin >= loadingLen){
                updateLocal = false
            }
        }
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.rotate(rotate, width/2.0f,height/2.0f)
        canvas?.drawCircle(width/2.0f,height/2.0f, width/2.0f, paint)
        canvas?.drawBitmap(loadingIcon, bitmapRect,dstRect, paint)
        if(!updateLocal){
            rotate += 10
            invalidate()
        }
    }

    private lateinit var callback: () -> Unit

    fun setLoadingListener( listener:() -> Unit){
        this.callback = listener
    }

    fun setLoadingFinished(){
        updateLocal = true
        loading = false
        mScroller.startScroll(0, loadingLen, 0, -loadingLen, 1000)
    }

    override fun computeScroll() {
        super.computeScroll()
        if (mScroller.computeScrollOffset()) {
            Log.d(TAG, "computeScroll: ${mScroller.currY}")
            setDistance(mScroller.currY.toFloat())
            postInvalidate()
        }
    }
}