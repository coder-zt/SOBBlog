package com.coder.zt.sobblog.net.api

import com.coder.zt.sobblog.model.base.ResponseData
import com.coder.zt.sobblog.model.base.ResponseListData
import com.coder.zt.sobblog.model.moyu.MYComment
import com.coder.zt.sobblog.model.moyu.MYCommentSender
import com.coder.zt.sobblog.model.moyu.MiniFeed
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
     * 获取单条摸鱼动态的评论
     */
    @GET("/ct/moyu/comment/{commentId}/{page}")
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
}