package com.coder.zt.sobblog.utils

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.coder.zt.sobblog.ui.activity.ArticleDetailActivity
import com.coder.zt.sobblog.ui.activity.EditMinifeedActivity
import com.coder.zt.sobblog.ui.activity.LoginActivity
import com.coder.zt.sobblog.ui.activity.SettingActivity

object AppRouter {

    private const val TAG = "AppRouter"
    val param_id = "id"

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

}