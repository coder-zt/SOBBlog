package com.coder.zt.sobblog.utils

import com.google.gson.Gson

class GsonUtils {
    companion object{
        private var instance:  Gson? = null

        fun getInstance():  Gson {
            if (instance == null) {
                instance =  Gson()
            }
            return instance!!
        }
    }
}