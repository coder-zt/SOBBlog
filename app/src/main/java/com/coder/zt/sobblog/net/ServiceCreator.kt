package com.coder.zt.sobblog.net

import com.coder.zt.sobblog.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {

    private val httpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC
        }

    val cookieJar by lazy {
        CookiesManager()
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .cookieJar(cookieJar)
        .build()



    private val retrofit:Retrofit = Retrofit.Builder()
                            .baseUrl(Constants.BASE_URL)
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()

    fun <T> create(serviceClass: Class<T>):T = retrofit.create(serviceClass)

    private val bingRetrofit:Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BING_BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> createBing(serviceClass: Class<T>):T = bingRetrofit.create(serviceClass)
}