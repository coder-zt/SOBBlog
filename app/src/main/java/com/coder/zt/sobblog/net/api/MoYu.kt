package com.coder.zt.sobblog.net.api

import com.coder.zt.sobblog.model.base.ServerResponse
import com.coder.zt.sobblog.model.moyu.MYComment
import com.coder.zt.sobblog.model.moyu.MoYuList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MoYu {

    @GET("/ct/moyu/list/{topicId}/{page}")
    fun getMinifeedByID(@Path("topicId") id:Int, @Path("page") page:Int)


    /**
     * 获取摸鱼列表
     */
    @GET("/ct/moyu/list/recommend/{page}")
    fun getRecommend( @Path("page") page:Int): Call<MoYuList>

    /**
     * 获取单条摸鱼动态的评论
     */
    @GET("/ct/moyu/comment/{commentId}/{page}")
    fun getMinifeedComment(@Path("commentId") commentId:String, @Path("page") page:Int): Call<MYComment>

    /**
     * 动态点赞
     */
    @PUT("/ct/moyu/thumb-up/{momentId}")
    fun thumbUp(@Path("momentId") momentId:String):Call<ServerResponse>
}