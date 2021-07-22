package com.coder.zt.sobblog.net.api

import com.coder.zt.sobblog.model.article.ArticleInfo
import com.coder.zt.sobblog.model.base.ResponseListData
import com.coder.zt.sobblog.model.moyu.WarpData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BingWarpApi {
    /**
     * 获取文章列表
     */
    @GET("/hp/api/model?scope=web&FORM=EDNTHT}")
    fun warpData(): Call<WarpData>
}