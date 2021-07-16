package com.coder.zt.sobblog.model.datamanager

import android.app.Activity
import com.coder.zt.sobblog.model.user.SobUserInfo
import com.coder.zt.sobblog.utils.AppRouter
import com.coder.zt.sobblog.utils.Constants
import com.coder.zt.sobblog.utils.SPUtils
import com.coder.zt.sobblog.utils.ToastUtils

object UserDataMan {

    init{

    }
    private var loginState = LoginState.NULL

        enum class LoginState{
            LOGINED,
            NULL,
            LOGOUTED
        }

    private  var userInfo: SobUserInfo? = null

    fun save(info:SobUserInfo){
        userInfo = info
        saveUserInfo(info)
    }

    fun getUserInfo():SobUserInfo?{
        val user = readUserInfo()
        if (user != null) {
            loginState = LoginState.LOGINED
        }
        return user
    }

    private fun readUserInfo():SobUserInfo?{
        if (userInfo == null) {
            return SPUtils.getInstance().getObject(Constants.SP_KEY_USER_INFO, SobUserInfo::class.java)
        }
        return  userInfo
    }

    private fun saveUserInfo(info:SobUserInfo){
        SPUtils.getInstance().saveObject(Constants.SP_KEY_USER_INFO, info)
    }

    fun checkUserLoginState(activity: Activity,errorMsg:String):Boolean{
        return if (loginState != LoginState.LOGINED) {
            ToastUtils.showError(errorMsg)
            AppRouter.toLoginActivity(activity)
            false
        }else{
            true
        }
    }

}