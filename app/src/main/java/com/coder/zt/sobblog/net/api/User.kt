package com.coder.zt.sobblog.net.api


import com.coder.zt.sobblog.model.base.ServerResponse
import com.coder.zt.sobblog.model.moyu.MoYuList
import com.coder.zt.sobblog.model.user.LoginInfo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface User {//

    @POST("/uc/user/login/{captcha}")
    fun login(@Path("captcha") captcha:String, @Body loginInfo: LoginInfo): Call<ServerResponse>

}