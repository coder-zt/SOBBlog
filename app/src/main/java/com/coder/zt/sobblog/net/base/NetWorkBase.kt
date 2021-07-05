package com.coder.zt.sobblog.net.base

import com.coder.zt.sobblog.net.MoYuNetWork
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

open class NetWorkBase {
    companion object {

        suspend fun <T> Call<T>.await():T{
            return suspendCoroutine {
                enqueue(object :  Callback<T> {
                    override fun onFailure(call: Call<T>, t: Throwable) {
                        it.resumeWithException(t)
                    }

                    override fun onResponse(call: Call<T>, response: Response<T>) {
                        val body = response.body()
                        if (body != null) it.resume(body)
                        else it.resumeWithException(RuntimeException("response body is null"))
                    }
                })
            }
        }

    }
}