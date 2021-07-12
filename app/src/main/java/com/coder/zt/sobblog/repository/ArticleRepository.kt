package com.coder.zt.sobblog.repository

import com.coder.zt.sobblog.model.article.ArticleDetail
import com.coder.zt.sobblog.net.ArticleNetWork


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

    suspend fun getArticleDetail(id:String):ArticleDetail{
        return ArticleNetWork.getInstance().getArticleDetail(id).data
    }
}