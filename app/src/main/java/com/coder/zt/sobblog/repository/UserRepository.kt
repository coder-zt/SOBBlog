package com.coder.zt.sobblog.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.coder.zt.sobblog.model.base.ResponseData
import com.coder.zt.sobblog.model.datamanager.UserDataMan
import com.coder.zt.sobblog.model.user.*
import com.coder.zt.sobblog.net.UserNetWork
import com.coder.zt.sobblog.utils.Constants
import java.io.IOException
import java.util.*


class UserRepository {

    companion object {
        private const val TAG = "UserRepository"
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

    private var sunofCoinPage = 1
    private var systemMessagePage = 1

    suspend fun login(captcha:String, loginInfo: LoginInfo): ResponseData<String> {
        val netWork = UserNetWork.getInstance().login(captcha, loginInfo)
        Log.d(TAG, "login: $netWork")
        return netWork
    }

    suspend fun logout(): ResponseData<String> {
        return UserNetWork.getInstance().logout()
    }

    suspend fun captcha(): Bitmap? {
        val netWork = UserNetWork.getInstance().captcha(Random().nextInt())
        var bitmap: Bitmap? = null
        val bys: ByteArray
        try {
            bys = netWork.bytes()
            bitmap = BitmapFactory.decodeByteArray(bys, 0, bys.size)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bitmap
    }


    suspend fun checkToken(): ResponseData<SobUserInfo> {
        val netWork = UserNetWork.getInstance().checkToken()
        Log.d(TAG, "login: ${netWork.data}")
        return netWork
    }

    suspend fun getAchievement(): ResponseData<UserAchievement> {
        val netWork = UserNetWork.getInstance().achievement()
        return netWork
    }

    suspend fun getInteractInfo(): ResponseData<InteractInfo> {
        val netWork = UserNetWork.getInstance().interactInfo()
        return netWork
    }

    suspend fun getSobCoinInfo(loadMore:Boolean): List<SunofCoinInfo> {
        if(loadMore){
            sunofCoinPage++
        }else{
            sunofCoinPage = 1
        }
        val netWork = UserNetWork.getInstance().sunofCoinInfo(UserDataMan.getUserInfo()!!.id, sunofCoinPage)
        return if (netWork.code == Constants.SUCCESS_CODE) {
            netWork.data.list
        }else{
            sunofCoinPage--
            listOf()
        }
    }


    suspend fun getSystemMessage(loadMore:Boolean): List<SystemMessage> {
        if(loadMore){
            systemMessagePage++
        }else{
            systemMessagePage = 1
        }
        val netWork = UserNetWork.getInstance().systemMessage(systemMessagePage)
        return if (netWork.code == Constants.SUCCESS_CODE) {
            netWork.data.content
        }else{
            systemMessagePage--
            listOf()
        }
    }

  suspend fun getThumbUpMessage(loadMore:Boolean): List<ThumbUpMessage> {
        if(loadMore){
            systemMessagePage++
        }else{
            systemMessagePage = 1
        }
        val netWork = UserNetWork.getInstance().thumbUpMessage(systemMessagePage)
        return if (netWork.code == Constants.SUCCESS_CODE) {
            netWork.data.content
        }else{
            systemMessagePage--
            listOf()
        }
    }


}