package com.coder.zt.sobblog.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.utils.ScreenUtils

class TriangleView(context: Context, attrs: AttributeSet): View(context, attrs)  {


    private val trianglePaint:Paint by lazy {
        val p = Paint()
        p.color = Color.WHITE
        p.isAntiAlias = true
        p
    }

    private val borderPaint:Paint by lazy {
        val p = Paint()
        p.color = context.resources.getColor(R.color.diver_line_color)
        p.isAntiAlias = true

        p.strokeWidth = ScreenUtils.dp2px(1).toFloat()
        p
    }

    val path:Path by lazy{
        val p = Path()
        p
    }
    var setPath:Boolean = false

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            canvas.drawColor(Color.TRANSPARENT)
            if (!setPath) {
                path.lineTo(0.0f, height.toFloat())
                path.lineTo(width.toFloat(), height.toFloat())
                path.lineTo(width.toFloat()/2, 0.0f)
                path.lineTo(0.0f, height.toFloat())
                setPath = true
            }
            canvas.drawPath(path, trianglePaint)
            canvas.drawLine(0.0f, height.toFloat(), width.toFloat()/2, 0.0f, borderPaint)
            canvas.drawLine(width.toFloat()/2, 0.0f, width.toFloat(), height.toFloat(), borderPaint)
        }
    }
}