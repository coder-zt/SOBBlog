package com.coder.zt.sobblog.net

import com.coder.zt.sobblog.net.api.ArticleApi
import com.coder.zt.sobblog.net.base.NetWorkBase


class ArticleNetWork: NetWorkBase() {
    private val articleService = ServiceCreator.create(ArticleApi::class.java)

    suspend fun getRecommendArticleList(page:Int) = articleService.recommendArticleList(page).await()

    suspend fun getArticleDetail(id:String) = articleService.articleDetail(id).await()

    suspend fun getArticleReward(id: String) = articleService.articleReward(id).await()

    suspend fun getArticleComment(id: String, page:Int) = articleService.articleComment(id, page).await()


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