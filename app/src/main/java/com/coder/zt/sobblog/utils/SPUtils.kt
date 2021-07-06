package com.coder.zt.sobblog.utils

import android.content.Context
import com.coder.zt.sobblog.SOBApp
import com.coder.zt.sobblog.repository.UserRepository
import java.lang.StringBuilder
import java.util.*

class SPUtils {

    companion object {
        private const val TAG = "SPUtils"
        private var instance: SPUtils? = null

        fun getInstance(): SPUtils {
            if (instance == null) {
                synchronized(SPUtils::class.java) {
                    if (instance == null) {
                        instance = SPUtils()
                    }
                }
            }
            return instance!!
        }



    }

    val sp by lazy {
        SOBApp._context?.getSharedPreferences(Constants.SP_NAME,Context.MODE_PRIVATE)
    }

    fun save(key:String, value:String){
        val edit = sp?.edit()
        edit?.putString(key, value)
        edit?.apply()
    }

    fun read(key: String):String?{
        return sp?.getString(key, "")
    }

    fun saveObject(key:String, obj:Any) {
        val json = GsonUtils.getInstance().toJson(obj)
        save(key, json)
    }

    fun <T> getObject(key:String, obj:Class<T>):T? {
        val result = read(key)
        if(result.isNullOrEmpty()){
            return null
        }
        return GsonUtils.getInstance().fromJson(result, obj)
    }

    fun saveList(key:String, values:List<String>){
        val value = StringBuilder().run {
            for (value in values) {
                append(value)
                append(Constants.SPLIT_SIGN)
            }
            toString()
        }
        save(key,value)
    }

    fun readList(key:String):List<String>{
        val values = read(key)
        val result = mutableListOf<String>()
        values?.let {
            val splitStrs = it.split(Constants.SPLIT_SIGN)
            for (splitStr in splitStrs) {
                result.add(splitStr)
            }
        }
        return result
    }

}