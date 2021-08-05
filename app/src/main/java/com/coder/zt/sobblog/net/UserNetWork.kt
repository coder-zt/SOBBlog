package com.coder.zt.sobblog.net

import com.coder.zt.sobblog.model.user.LoginInfo
import com.coder.zt.sobblog.net.api.User
import com.coder.zt.sobblog.net.base.NetWorkBase


class UserNetWork: NetWorkBase() {
        private val userService = ServiceCreator.create(User::class.java)

        suspend fun login(captcha:String, loginInfo:LoginInfo) = userService.login(captcha, loginInfo).await()

        suspend fun logout() = userService.logout().await()

        suspend fun captcha(random:Int) = userService.captcha(random).await()

        suspend fun checkToken() = userService.checkToken().await()

        suspend fun achievement() = userService.achievement().await()

        suspend fun interactInfo() = userService.interactInfo().await()

        suspend fun sunofCoinInfo(userId:String, page:Int) = userService.sobTrade(userId, page).await()

        suspend fun systemMessage(page:Int) = userService.systemMessage(page).await()

        suspend fun thumbUpMessage(page:Int) = userService.thumbUpMessage(page).await()


        companion object{
                private var network: UserNetWork? = null

                fun getInstance(): UserNetWork {
                        if (network == null) {
                                synchronized(UserNetWork::class.java) {
                                        if (network == null) {
                                                network = UserNetWork()
                                        }
                                }
                        }
                        return network!!
                }
        }

}