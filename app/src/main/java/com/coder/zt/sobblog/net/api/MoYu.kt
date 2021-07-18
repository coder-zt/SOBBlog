package com.coder.zt.sobblog.net.api

import com.coder.zt.sobblog.model.base.ResponseData
import com.coder.zt.sobblog.model.base.ResponseList
import com.coder.zt.sobblog.model.base.ResponseListData
import com.coder.zt.sobblog.model.moyu.*
import okhttp3.MultipartBody
import okhttp3.MultipartReader
import retrofit2.Call
import retrofit2.http.*

interface MoYu {

    @GET("/ct/moyu/list/{topicId}/{page}")
    fun getMinifeedByID(@Path("topicId") id:Int, @Path("page") page:Int)


    /**
     * 获取摸鱼列表
     */
    @GET("/ct/moyu/list/recommend/{page}")
    fun getRecommend( @Path("page") page:Int): Call<ResponseListData<MiniFeed>>


    /**
     * 上传图片
     *
     * {
    "success": true,
    "code": 10000,
    "message": "上传成功.",//https://images.sunofbeaches.com/content/2021_07_15/865253509971312640.jpg
    "data": "https://images.sunofbeaches.com/content/2021_07_01/860186568067907584.jpg"
    }
     */
    @Multipart
    @POST("/ct/image/mo_yu")
    fun uploadImage( @Part image: MultipartBody.Part): Call<ResponseData<String>>


    /**
     * 发布动态
     */
    @POST("/ct/moyu")
    fun minifeed(@Body miniFeed:MinifeedSender): Call<ResponseData<MYPublishResponse>>

    /**
     * 获取单条摸鱼动态的评论
     */
    @GET("/ct/moyu/comment/{commentId}/{page}?sort=1")
    fun getMinifeedComment(@Path("commentId") commentId:String, @Path("page") page:Int): Call<ResponseListData<MYComment>>

    /**
     * 动态点赞
     */
    @PUT("/ct/moyu/thumb-up/{momentId}")
    fun thumbUp(@Path("momentId") momentId:String):Call<ResponseData<String>>

    /**
     * 动态评论
     */
    @POST("/ct/moyu/comment")
    fun comment(@Body comment:MYCommentSender): Call<ResponseData<String>>

    /**
     * 回复评论
     */
    @POST("/ct/moyu/sub-comment")
    fun reply(@Body comment:MYReplySender): Call<ResponseData<String>>
    /**
     * 获取动态话题
     */
    @GET("/ct/moyu/topic")
    fun minifeedTopics(): Call<ResponseList<TopicItem>>
}