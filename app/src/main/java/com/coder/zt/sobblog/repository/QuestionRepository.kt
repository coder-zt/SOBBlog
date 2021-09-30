package com.coder.zt.sobblog.repository

import com.coder.zt.sobblog.model.question.QuestionItem
import com.coder.zt.sobblog.net.QuestionNetWork

class QuestionRepository {

    companion object {
        private const val TAG = "UserRepository"
        private var instance: QuestionRepository? = null

        fun getInstance(): QuestionRepository {
            if (instance == null) {
                synchronized(UserRepository::class.java) {
                    if (instance == null) {
                        instance = QuestionRepository()
                    }
                }
            }
            return instance!!
        }
    }
    //最新问题
    private var lastestPage = 1
    //待解决的问题
    private var noAnswerPage = 1
    //推荐问题
    private var hotPage = 1

    suspend fun getQuestionListByState(state:String, loadMore:Boolean):List<QuestionItem>{
        when(state){
            "lastest" ->{
                if(loadMore){
                    lastestPage++
                }else{
                    lastestPage = 1
                }
                val result = QuestionNetWork.getInstance().getQuestionListByState(lastestPage, state)
                if (!result.success && lastestPage != 1) {
                    lastestPage--
                }
                return result.data.list
            }
            "noanswer" ->{
                if(loadMore){
                    noAnswerPage++
                }else{
                    noAnswerPage = 1
                }
                val result = QuestionNetWork.getInstance().getQuestionListByState(lastestPage, state)
                if (!result.success  && noAnswerPage != 1) {
                    noAnswerPage--
                }
                return result.data.list
            }
            "hot" ->{
                if(loadMore){
                    hotPage++
                }else{
                    hotPage = 1
                }
                val result = QuestionNetWork.getInstance().getQuestionListByState(lastestPage, state)
                if (!result.success && hotPage != 1) {
                    hotPage--
                }
                return result.data.list
            }
        }
        return listOf()
    }
}