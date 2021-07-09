package com.coder.zt.sobblog.net

import com.coder.zt.sobblog.model.user.LoginInfo
import com.coder.zt.sobblog.net.api.Article
import com.coder.zt.sobblog.net.api.User
import com.coder.zt.sobblog.net.base.NetWorkBase


class ArticleNetWork: NetWorkBase() {
        private val articleService = ServiceCreator.create(Article::class.java)

        suspend fun getArticleDetail(id:String) = articleService.articleDetail(id).await()




        companion object{
                private var network: ArticleNetWork? = null

                fun getInstance(): ArticleNetWork {
                        if (network == null) {
                                synchronized(ArticleNetWork::class.java) {
                                        if (network == null) {
                                                network = ArticleNetWork()
                                        }
                                }
                        }
                        return network!!
                }
        }

}