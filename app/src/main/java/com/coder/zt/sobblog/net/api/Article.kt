package com.coder.zt.sobblog.net.api

import com.coder.zt.sobblog.model.article.ArticleDetailResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Article {

    /**
     * 获取验证码
     */
    @GET("/ct/article/detail/{articleId}")
    fun articleDetail(@Path("articleId") articleId:String): Call<ArticleDetailResponse>

}