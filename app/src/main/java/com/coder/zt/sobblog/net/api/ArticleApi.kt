package com.coder.zt.sobblog.net.api

import com.coder.zt.sobblog.model.article.ArticleDetail
import com.coder.zt.sobblog.model.base.ResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ArticleApi {

    /**
     * 获取验证码
     */
    @GET("/ct/article/detail/{articleId}")
    fun articleDetail(@Path("articleId") articleId:String): Call<ResponseData<ArticleDetail>>

}