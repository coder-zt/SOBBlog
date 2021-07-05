package com.coder.zt.sobblog.net.api


import com.coder.zt.sobblog.model.base.ServerResponse
import com.coder.zt.sobblog.model.moyu.MoYuList
import com.coder.zt.sobblog.model.user.LoginInfo
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface User {//

    @POST("/uc/user/login/{captcha}")
    fun login(@Path("captcha") captcha:String, @Body loginInfo: LoginInfo): Call<ServerResponse>


    @GET("/uc/ut/captcha")
    fun captcha(@Query("code") random:Int): Call<ResponseBody>
}