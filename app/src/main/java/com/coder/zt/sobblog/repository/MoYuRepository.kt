package com.coder.zt.sobblog.repository

import com.coder.zt.sobblog.model.moyu.MoYuList
import com.coder.zt.sobblog.net.NetWorkDispatcher
import com.coder.zt.sobblog.utils.Constants

class MoYuRepository {
    suspend fun getRecommendMinifeed(page:Int):MoYuList?{
        val recommend = NetWorkDispatcher.getInstance().getRecommendMinifeed(page)
        return if (recommend.code == Constants.SUCCESS_CODE) {
            recommend
        }else{
            null
        }
    }

    companion object {

        private var instance: MoYuRepository? = null

        fun getInstance(): MoYuRepository {
            if (instance == null) {
                synchronized(MoYuRepository::class.java) {
                    if (instance == null) {
                        instance = MoYuRepository()
                    }
                }
            }
            return instance!!
        }

    }
}