package com.coder.zt.sobblog.net.api


import com.coder.zt.sobblog.model.base.ResponseData
import com.coder.zt.sobblog.model.base.ResponseListData
import com.coder.zt.sobblog.model.user.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface User {

    /**
     * 登录
     */
    @POST("/uc/user/login/{captcha}")
    fun login(@Path("captcha") captcha:String, @Body loginInfo: LoginInfo): Call<ResponseData<String>>

    /**
     * 注销登录
     */
    @GET("/uc/user/logout")
    fun logout(): Call<ResponseData<String>>


    /**
     * 获取验证码
     */
    @GET("/uc/ut/captcha")
    fun captcha(@Query("code") random:Int): Call<ResponseBody>

    /**
     * 检查token
     */
    @GET("/uc/user/checkToken")
    fun checkToken():  Call<ResponseData<SobUserInfo>>

    /**
     * 获取个人成就
     */
    @GET("ast/ucenter/achievement")
    fun achievement():  Call<ResponseData<UserAchievement>>

    /**
     * 获取互动信息
     */
    @GET("ct/msg/count")
    fun interactInfo():  Call<ResponseData<InteractInfo>>

    /**
     * Sunof币的信息
     */
    @GET("ast/ucenter/sob-trade/{userId}/{page}")
    fun sobTrade(@Path("userId") id:String, @Path("page")  page:Int):Call<ResponseListData<SunofCoinInfo>>

}