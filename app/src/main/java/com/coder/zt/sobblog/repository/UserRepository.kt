package com.coder.zt.sobblog.repository

import com.coder.zt.sobblog.model.user.LoginInfo
import com.coder.zt.sobblog.net.UserNetWork

class UserRepository {

    suspend fun login(captcha:String, loginInfo: LoginInfo):Pair<Boolean, String>{
        val netWork = UserNetWork.getInstance().login(captcha, loginInfo)
        return if(netWork.code == 10000){
            Pair(true, netWork.message)
        }else{
            Pair(false, netWork.message)
        }
    }


    companion object {

        private var instance: UserRepository? = null

        fun getInstance(): UserRepository {
            if (instance == null) {
                synchronized(UserRepository::class.java) {
                    if (instance == null) {
                        instance = UserRepository()
                    }
                }
            }
            return instance!!
        }

    }
}