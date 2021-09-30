package com.coder.zt.sobblog.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coder.zt.sobblog.model.question.QuestionItem
import com.coder.zt.sobblog.repository.QuestionRepository
import kotlinx.coroutines.launch

class QuestionViewModel:ViewModel() {

    val lastestQuestions = MutableLiveData<List<QuestionItem>>()
    val noAnswerQuestions = MutableLiveData<List<QuestionItem>>()
    val hotQuestions = MutableLiveData<List<QuestionItem>>()

    fun getLastestQuestionList(loadMore:Boolean){
        viewModelScope.launch {
            lastestQuestions.value = QuestionRepository.getInstance().getQuestionListByState("lastest", loadMore)
        }
    }

    fun geNoAnswerQuestionList(loadMore:Boolean){
        viewModelScope.launch {
            noAnswerQuestions.value = QuestionRepository.getInstance().getQuestionListByState("noanswer", loadMore)
        }
    }

    fun getHotQuestionList(loadMore:Boolean){
        viewModelScope.launch {
            hotQuestions.value = QuestionRepository.getInstance().getQuestionListByState("hot", loadMore)
        }
    }

}