package com.coder.zt.sobblog.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.coder.zt.sobblog.ui.activity.*
import java.text.FieldPosition
import java.util.ArrayList

object AppRouter {

    private const val TAG = "AppRouter"
    const val  param_id = "id"
    const val param_data = "data"
    const val param_Position = "position"

    /**
     * 跳转到文章详情
     */
    fun toArticleDetailActivity(activity: Activity, articleId:String){
        val intent = Intent(activity, ArticleDetailActivity::class.java).apply {
            putExtra(param_id, articleId)
        }
        activity.startActivity(intent)
    }

    /**
     * 跳转到发布动态详情
     */
    fun toEditMinifeedActivity(activity: Activity){
        val intent = Intent(activity, EditMinifeedActivity::class.java)
        activity.startActivity(intent)
    }

    /**
     * 跳转到登录页面
     */
    fun toLoginActivity(activity: Activity) {
        val intent = Intent(activity, LoginActivity::class.java)
        activity.startActivity(intent)
    }

    /**
     * 跳转到设置页面
     */
    fun toSettingActivity(activity: Activity){
        val intent = Intent(activity, SettingActivity::class.java)
        activity.startActivity(intent)
    }

    /**
     * 跳转到外部浏览器
     */
    @SuppressLint("QueryPermissionsNeeded")
    fun toOutsideWebsite(activity: Activity, url: String) {
        Log.d(TAG, "toOutsideWebsite: $url")
        val intent = Intent().apply {
            action = Intent.ACTION_VIEW
            data = Uri.parse(url)
        }
        // 注意此处的判断intent.resolveActivity()可以返回显示该Intent的Activity对应的组件名
        // 官方解释 : Name of the component implementing an activity that can display the intent
        if (intent.resolveActivity(activity.packageManager) != null) {
            activity.startActivity(Intent.createChooser(intent, "请选择浏览器"))
        } else {
            try {
                activity.startActivity(Intent.createChooser(intent, "请选择浏览器"))
            }catch (e:Exception){
                ClipBoardUtils.paste(url)
                ToastUtils.showError("打开链接出错，已将链接粘贴到剪切板");
            }
        }
    }

    /**
     * 跳转到获取Sunof币的记录activity
     */
    fun toSunofLogActivity(activity: Activity) {
        val intent = Intent(activity, SunofLogActivity::class.java)
        Log.d(TAG, "toSunofLogActivity: ")
        activity.startActivity(intent)
    }

    /**
     * 跳转到图片浏览的activity
     */
    fun toPictureBrowseActivity(activity: Activity,picUrls:ArrayList<String>, position: Int) {
        Log.d(TAG, "toPictureBrowseActivity: $picUrls")
        val intent = Intent(activity, PictureBrowseActivity::class.java)
        intent.putStringArrayListExtra(param_data, picUrls)
        intent.putExtra(param_Position, position)
        activity.startActivity(intent)
    }


}