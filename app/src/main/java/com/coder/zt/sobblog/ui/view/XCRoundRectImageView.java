package com.coder.zt.sobblog.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
/**
 * 自定义的圆角矩形ImageView，可以直接当组件在布局中使用。
 * @author caizhiming
 *
 */
public class XCRoundRectImageView extends androidx.appcompat.widget.AppCompatImageView{
    private Paint paint;
    public XCRoundRectImageView(Context context) {
        this(context,null);
    }
    public XCRoundRectImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public XCRoundRectImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        paint = new Paint();
    }
    /**
     * 绘制圆角矩形图片
     * @author caizhiming
     */
    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (null != drawable) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            Bitmap b = getRoundBitmap(bitmap, getWidth(), getHeight());
            final Rect rectSrc = new Rect(0, 0, b.getWidth(), b.getHeight());
            final Rect rectDest = new Rect(0,0,getWidth(),getHeight());
            paint.reset();
            canvas.drawBitmap(b, rectSrc, rectDest, paint);
        } else {
            super.onDraw(canvas);
        }
    }
    /**
     * 获取圆角矩形图片方法
     * @param bitmap
     * @param roundPx,一般设置成14
     * @return Bitmap
     * @author caizhiming
     */
    private static final String TAG = "XCRoundRectImageView";
    private Bitmap getRoundBitmap(Bitmap bitmap, int width, int height) {
        Log.d(TAG, "getRoundBitmap: " + width + "===" + height);
        Bitmap output = Bitmap.createBitmap(width,
                width, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xffFFFF00;
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
//        int x = bitmap.getWidth();
        canvas.drawCircle(width/2.0f, height/2.0f, height/2.0f, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }
}
