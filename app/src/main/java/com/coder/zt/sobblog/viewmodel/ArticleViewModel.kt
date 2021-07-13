package com.coder.zt.sobblog.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coder.zt.sobblog.model.article.ArticleComment
import com.coder.zt.sobblog.model.article.ArticleDetail
import com.coder.zt.sobblog.model.article.ArticleInfo
import com.coder.zt.sobblog.model.user.RewardUserInfo
import com.coder.zt.sobblog.repository.ArticleRepository
import kotlinx.coroutines.launch

class ArticleViewModel:ViewModel() {

    val articleList:MutableLiveData<List<ArticleInfo>> = MutableLiveData()
    val articleDetail:MutableLiveData<ArticleDetail> = MutableLiveData()
    val rewardInfo:MutableLiveData<List<RewardUserInfo>> = MutableLiveData()
    val commentInfo:MutableLiveData<List<ArticleComment>> = MutableLiveData()

    fun getRecommendArticleList(page:Int){
        viewModelScope.launch {
            articleList.value = ArticleRepository.getInstance().getRecommendArticleList(page)
        }
    }
    fun getArticleDetail(id:String){
        viewModelScope.launch {
            articleDetail.value = ArticleRepository.getInstance().getArticleDetail(id)
        }
    }

    fun getArticleReward(id: String) {
        viewModelScope.launch {
            rewardInfo.value = ArticleRepository.getInstance().getArticleReward(id)
        }
    }

    fun getArticleComment(id: String) {
        viewModelScope.launch {
            commentInfo.value = ArticleRepository.getInstance().getArticleComment(id, 1)
        }
    }
}