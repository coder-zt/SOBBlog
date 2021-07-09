package com.coder.zt.sobblog.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coder.zt.sobblog.model.article.ArticleDetailResponse
import com.coder.zt.sobblog.repository.ArticleRepository
import kotlinx.coroutines.launch

class ArticleDetailViewModel:ViewModel() {

    val articleDetail:MutableLiveData<ArticleDetailResponse> = MutableLiveData()

    fun getArticleDetail(id:String){
        viewModelScope.launch {
            articleDetail.value = ArticleRepository.getInstance().getArticleDetail(id)
        }
    }
}