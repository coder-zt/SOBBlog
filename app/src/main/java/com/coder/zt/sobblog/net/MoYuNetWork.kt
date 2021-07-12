package com.coder.zt.sobblog.net

import com.coder.zt.sobblog.model.moyu.MYCommentSender
import com.coder.zt.sobblog.net.api.MoYu
import com.coder.zt.sobblog.net.base.NetWorkBase


class MoYuNetWork: NetWorkBase() {

    private val moYuService = ServiceCreator.create(MoYu::class.java)

    suspend fun getRecommendMinifeed(page:Int) = moYuService.getRecommend(page).await()


    suspend fun getMinifeedComment(commentId:String, page:Int) = moYuService.getMinifeedComment(commentId, page).await()

    suspend fun thumbUp(momentId:String) = moYuService.thumbUp(momentId).await()

    suspend fun comment(comment: MYCommentSender) = moYuService.comment(comment).await()


    companion object{

                private var network: MoYuNetWork? = null

                fun getInstance(): MoYuNetWork {
                        if (network == null) {
                                synchronized(MoYuNetWork::class.java) {
                                        if (network == null) {
                                                network = MoYuNetWork()
                                        }
                                }
                        }
                        return network!!
                }
        }

}