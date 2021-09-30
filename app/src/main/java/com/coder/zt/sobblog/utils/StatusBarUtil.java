package com.coder.zt.sobblog.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;


public class StatusBarUtil {

    private static final String TAG = "StatusBarUtil";

    public static boolean hasNavigationBarShow(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return false;
        }
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        //获取整个屏幕的高度
        display.getRealMetrics(outMetrics);
        int heightPixels = outMetrics.heightPixels;
        int widthPixels = outMetrics.widthPixels;
        //获取内容展示部分的高度
        outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        int heightPixelsContent = outMetrics.heightPixels;
        int widthPixelsContent = outMetrics.widthPixels;
        int h = heightPixels - heightPixelsContent;
        int w = widthPixels - widthPixelsContent;
        return w > 0 || h > 0;  //竖屏和横屏两种情况
    }

    /**
     * 获取导航栏高度
     *
     * @param context
     * @return
     */
    public static int getNavigationBarHeight(Context context) {
        Log.d(TAG, "getNavigationBarHeight: " + checkHasNavigationBar(context));
        return getSystemComponentDimen(context, "navigation_bar_height");
    }

    private static boolean checkHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        if ((Settings.Global.getInt(context.getContentResolver(), "force_fsg_nav_bar", 0) != 0)) {
            hasNavigationBar = true;
        }
        return hasNavigationBar;
    }

    /**
     * 横屏可通过 widthPixels - widthPixels2 > 0 来判断底部导航栏是否存在
     * @param windowManager
     * @return true表示有虚拟导航栏 false没有虚拟导航栏
     */
    public static boolean isNavigationBarShow(WindowManager windowManager)
    {
        Display defaultDisplay = windowManager.getDefaultDisplay();
        //获取屏幕高度
        DisplayMetrics outMetrics = new DisplayMetrics();
        defaultDisplay.getRealMetrics(outMetrics);
        int heightPixels = outMetrics.heightPixels;
        //宽度
        int widthPixels = outMetrics.widthPixels;


        //获取内容高度
        DisplayMetrics outMetrics2 = new DisplayMetrics();
        defaultDisplay.getMetrics(outMetrics2);
        int heightPixels2 = outMetrics2.heightPixels;
        //宽度
        int widthPixels2 = outMetrics2.widthPixels;
        Log.d(TAG, "isNavigationBarShow: " + (heightPixels - heightPixels2));
        return heightPixels - heightPixels2 > 0 || widthPixels - widthPixels2 > 0;
    }

    public static int getSystemComponentDimen(Context context, String dimenName) {
        // 反射手机运行的类：android.R.dimen.status_bar_height.
        int statusHeight = -1;
        try {
            int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                statusHeight = context.getResources().getDimensionPixelSize(resourceId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }
}
