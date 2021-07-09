package com.coder.zt.sobblog.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.FileUtils
import android.util.Log
import com.coder.zt.sobblog.model.article.ArticleDetailResponse
import com.coder.zt.sobblog.model.base.ServerResponse
import com.coder.zt.sobblog.model.user.LoginInfo
import com.coder.zt.sobblog.net.ArticleNetWork
import com.coder.zt.sobblog.net.UserNetWork
import java.io.IOException
import java.util.*


class ArticleRepository {

    companion object {
        private const val TAG = "UserRepository"
        private var instance: ArticleRepository? = null

        fun getInstance(): ArticleRepository {
            if (instance == null) {
                synchronized(ArticleRepository::class.java) {
                    if (instance == null) {
                        instance = ArticleRepository()
                    }
                }
            }
            return instance!!
        }

    }

    suspend fun getArticleDetail(id:String):ArticleDetailResponse{
        return ArticleNetWork.getInstance().getArticleDetail(id)
    }
}