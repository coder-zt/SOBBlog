package com.coder.zt.sobblog.model.datamanager

import com.coder.zt.sobblog.model.user.SobUserInfo
import com.coder.zt.sobblog.utils.Constants
import com.coder.zt.sobblog.utils.SPUtils

object UserDataMan {

    init{

    }

    private var userInfo: SobUserInfo? = null
    fun save(info:SobUserInfo){
        userInfo = info
        saveUserInfo(info)
    }

    fun getUserInfo():SobUserInfo?{
        return readUserInfo()
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

}