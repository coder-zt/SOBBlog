package com.coder.zt.sobblog.repository

import android.util.Log
import com.coder.zt.sobblog.model.article.ArticleComment
import com.coder.zt.sobblog.model.article.ArticleDetail
import com.coder.zt.sobblog.model.article.ArticleInfo
import com.coder.zt.sobblog.model.user.RewardUserInfo
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

    private var  articleInfoPage:Int = 1
    suspend fun getRecommendArticleList(loadMore:Boolean):List<ArticleInfo>{
        if(loadMore){
            articleInfoPage++
        }else{
            articleInfoPage = 1
        }
        val i = ArticleNetWork.getInstance().getRecommendArticleList(articleInfoPage).data.list
        if(i.isEmpty() && articleInfoPage != 1){
            articleInfoPage--
        }
        return i
    }

    suspend fun getArticleDetail(id:String):ArticleDetail{
        return ArticleNetWork.getInstance().getArticleDetail(id).data
    }

    suspend fun getArticleReward(id: String): List<RewardUserInfo> {
        return ArticleNetWork.getInstance().getArticleReward(id).data
    }

    suspend fun getArticleComment(id: String, page:Int): List<ArticleComment> {
        return ArticleNetWork.getInstance().getArticleComment(id, page).data.content
    }
}