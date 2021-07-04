package com.coder.zt.sobblog.net.api

import com.coder.zt.sobblog.model.moyu.MYComment
import com.coder.zt.sobblog.model.moyu.MoYuList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MoYu {

    @GET("/ct/moyu/list/{topicId}/{page}")
    fun getMinifeedByID(@Path("topicId") id:Int, @Path("page") page:Int)


    /**
     * 获取摸鱼列表
     */
    @GET("/ct/moyu/list/recommend/{page}")
    fun getRecommend( @Path("page") page:Int): Call<MoYuList>
    //https://api.sunofbeach.net/ct/moyu/comment/1410400457637076994/1

    @GET("/ct/moyu/comment/{commentId}/{page}")
    fun getMinifeedComment(@Path("commentId") commentId:String, @Path("page") page:Int): Call<MYComment>

}