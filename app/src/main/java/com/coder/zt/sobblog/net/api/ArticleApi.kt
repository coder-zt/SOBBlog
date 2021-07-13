package com.coder.zt.sobblog.net.api

import com.coder.zt.sobblog.model.article.ArticleComment
import com.coder.zt.sobblog.model.article.ArticleDetail
import com.coder.zt.sobblog.model.base.ResponseData
import com.coder.zt.sobblog.model.base.ResponseList
import com.coder.zt.sobblog.model.base.ResponseSortData
import com.coder.zt.sobblog.model.user.RewardUserInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
//文章作者打赏：https://api.sunofbeach.net/ast/prise-qr-code/1139423796017500160
//查询自己是否点赞文章https://api.sunofbeach.net/ct/article/check-thumb-up/1410937277084966914
interface ArticleApi {

    /**
     * 获取文章详情
     */
    @GET("/ct/article/detail/{articleId}")
    fun articleDetail(@Path("articleId") articleId:String): Call<ResponseData<ArticleDetail>>


    /**
     *  获取文章赞赏列表
     */
    @GET("/ast/prise/article/{articleId}")
    fun articleReward(@Path("articleId") articleId: String):Call<ResponseData<List<RewardUserInfo>>>


    /**
     *  获取文章评论
     */
    @GET("/ct/article/comment/{articleId}/{page}")
    fun articleComment(@Path("articleId") articleId: String, @Path("page") page:Int):Call<ResponseSortData<ArticleComment>>
}