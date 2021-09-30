package com.coder.zt.sobblog.net

import com.coder.zt.sobblog.net.api.QuestionApi
import com.coder.zt.sobblog.net.base.NetWorkBase

class QuestionNetWork:NetWorkBase() {

    private val questionService = ServiceCreator.create(QuestionApi::class.java)

    suspend fun getQuestionListByState(page:Int, state:String) = questionService.getListByState(page,state).await()

    companion object{
        private var network: QuestionNetWork? = null

        fun getInstance(): QuestionNetWork {
            if (network == null) {
                synchronized(UserNetWork::class.java) {
                    if (network == null) {
                        network = QuestionNetWork()
                    }
                }
            }
            return network!!
        }
    }
}