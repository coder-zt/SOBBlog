package com.coder.zt.sobblog.utils

import android.content.Context
import android.util.Log
import com.coder.zt.sobblog.SOBApp
import java.lang.StringBuilder

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
        SOBApp.getContext().getSharedPreferences(Constants.SP_NAME,Context.MODE_PRIVATE)
    }

    fun save(key:String, value:String){
        val edit = sp?.edit()
        edit?.putString(key, value)
        edit?.apply()
    }

    fun read(key: String): String {
        var value = ""
        sp.getString(key, "")?.let {
            value = it
        }
        return value
    }

    fun saveObject(key:String, obj:Any) {
        val json = GsonUtils.getInstance().toJson(obj)
        save(key, json)
    }

    fun <T> getObject(key:String, obj:Class<T>):T?{
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
        Log.d(TAG, "saveList: $value")
        save(key,value)
    }

    fun readList(key:String):List<String>{
        val values = read(key)
        Log.d(TAG, "readList: $values")
        val result = mutableListOf<String>()
        if(values.isNotBlank()){
            val splitStrings = values.split(Constants.SPLIT_SIGN)
            for (splitStr in splitStrings) {
                result.add(splitStr)
            }
        }
        return result
    }

}