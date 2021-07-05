package com.coder.zt.sobblog.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.FileUtils
import android.util.Log
import com.coder.zt.sobblog.model.base.ServerResponse
import com.coder.zt.sobblog.model.user.LoginInfo
import com.coder.zt.sobblog.net.UserNetWork
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
    suspend fun login(captcha:String, loginInfo: LoginInfo): ServerResponse {
        val netWork = UserNetWork.getInstance().login(captcha, loginInfo)
        Log.d(TAG, "login: $netWork")
        return netWork
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



}