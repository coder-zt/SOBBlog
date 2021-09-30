package com.coder.zt.sobblog.net.api


import com.coder.zt.sobblog.model.base.ResponseListData
import com.coder.zt.sobblog.model.question.QuestionItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface QuestionApi {

    @GET("/ct/wenda/list?category=-2")
    fun getListByState(@Query("page") page:Int, @Query("state") state:String): Call<ResponseListData<QuestionItem>>
}