package com.coder.zt.sobblog.net

import android.util.Log
import com.coder.zt.sobblog.utils.Constants
import com.coder.zt.sobblog.utils.SPUtils
import kotlinx.coroutines.flow.callbackFlow
import okhttp3.Interceptor
import okhttp3.Response

private const val TAG = "HeaderInterceptor"

private const val headerKey = "l_c_i"

class HeaderInterceptor:Interceptor {

    private var mTokenKey:String = ""

    override fun intercept(chain: Interceptor.Chain): Response {
        Log.d(TAG, "intercept: 拦截前 ${chain.request().url}")
        val requestUrl = chain.request().url.toString()

        val oldSobToken = SPUtils.getInstance().read(Constants.BASE_URL + "_sob_token")
        val requestBuilder = chain.request().newBuilder().addHeader("sob_token",oldSobToken)
        val response = if(requestUrl.contains("uc/user/login/")){//登录接口
                            val newRequest = requestBuilder.addHeader(headerKey,mTokenKey).build()
                            chain.proceed(newRequest)
                        }else{
                            chain.proceed(requestBuilder.build())
                        }
        val tokenKey = response.headers[headerKey]
        tokenKey?.let {
            mTokenKey = it
        }
        val sobToken = response.headers["sob_token"]
        sobToken?.let {
            Log.d(TAG, "intercept: 保存token")
            SPUtils.getInstance().save(Constants.BASE_URL + "_sob_token", it)
        }
        Log.d(TAG, "intercept: 拦截后 tokenKey ===> $sobToken")
        return response
    }

}