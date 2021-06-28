package com.coder.zt.sobblog.net.api

import com.coder.zt.sobblog.model.moyu.MoYuList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MoYu {

    @GET("/ct/moyu/list/{topicId}/{page}")
    fun getMinifeedByID(@Path("topicId") id:Int, @Path("page") page:Int)

    @GET("/ct/moyu/list/recommend/{page}")
    fun getRecommend( @Path("page") page:Int): Call<MoYuList>
}