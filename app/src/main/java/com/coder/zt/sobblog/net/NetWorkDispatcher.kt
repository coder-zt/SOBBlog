package com.coder.zt.sobblog.net

import com.coder.zt.sobblog.net.api.MoYu
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class NetWorkDispatcher {
    private val moYuService = ServiceCreator.create(MoYu::class.java)

    suspend fun getRecommendMinifeed(page:Int) = moYuService.getRecommend(page).await()


    suspend fun getMinifeedComment(commentId:String, page:Int) = moYuService.getMinifeedComment(commentId, page).await()

    private suspend fun <T> Call<T>.await():T{
        return suspendCoroutine {
            enqueue(object :  Callback<T>{
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

    companion object {

        private var network: NetWorkDispatcher? = null

        fun getInstance(): NetWorkDispatcher {
            if (network == null) {
                synchronized(NetWorkDispatcher::class.java) {
                    if (network == null) {
                        network = NetWorkDispatcher()
                    }
                }
            }
            return network!!
        }

    }
}