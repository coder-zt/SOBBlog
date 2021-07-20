package com.coder.zt.sobblog.net.api

import com.coder.zt.sobblog.model.article.*
import com.coder.zt.sobblog.model.base.ResponseContentData
import com.coder.zt.sobblog.model.base.ResponseData
import com.coder.zt.sobblog.model.base.ResponseListData
import com.coder.zt.sobblog.model.user.RewardUserInfo
import retrofit2.Call
import retrofit2.http.*

//文章作者打赏：https://api.sunofbeach.net/ast/prise-qr-code/1139423796017500160
//查询自己是否点赞文章https://api.sunofbeach.net/ct/article/check-thumb-up/1410937277084966914
interface ArticleApi {


    /**
     * 获取文章列表
     */
    @GET("ct/content/home/recommend/{page}")
    fun recommendArticleList(@Path("page") page:Int): Call<ResponseListData<ArticleInfo>>

    /**
     * 获取文章详情
     */
    @GET("/ct/article/detail/{articleId}")
    fun articleDetail(@Path("articleId") articleId:String): Call<ResponseData<ArticleDetail>>


    /**
     *  获取文章赞赏列表
     */
    @GET("/ast/prise/article/{articleId}")
    fun getArticleReward(@Path("articleId") articleId: String):Call<ResponseData<List<RewardUserInfo>>>


    /**
     *  获取文章评论
     */
    @GET("/ct/article/comment/{articleId}/{page}")
    fun articleComment(@Path("articleId") articleId: String, @Path("page") page:Int):Call<ResponseContentData<ArticleComment>>

    /**
     * 文章点赞
     */
    @PUT("ct/article/thumb-up/{articleId}")
    fun articleThumbUp(@Path("articleId") articleId: String):Call<ResponseData<String>>

    /**
     * 查询我是否给该文章点赞
     */
    @GET("ct/article/check-thumb-up/{articleId}")
    fun articleCheckThumbUp(@Path("articleId") articleId: String):Call<ResponseData<String>>

    /**
     * 文章打赏
     */
    @POST("ast/prise/article")
    fun articleReward(@Body reward: ArticleReward):Call<ResponseData<String>>

    /**
     * 获取收藏
     */
    @GET("ct/collection/list/{page}")
    fun collect(@Path("page") page: Int):Call<ResponseContentData<ArticleCollect>>
}