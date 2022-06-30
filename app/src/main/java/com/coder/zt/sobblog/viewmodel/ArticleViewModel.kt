package com.coder.zt.sobblog.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coder.zt.sobblog.model.article.*
import com.coder.zt.sobblog.model.base.ResponseData
import com.coder.zt.sobblog.model.user.RewardUserInfo
import com.coder.zt.sobblog.repository.ArticleRepository
import com.coder.zt.sobblog.utils.ToastUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ArticleViewModel:ViewModel() {

    companion object{
        private const val TAG = "ArticleViewModel"
    }
    val articleList:MutableLiveData<List<ArticleInfo>> = MutableLiveData()
    val articleDetail:MutableLiveData<ArticleDetail> = MutableLiveData()
    val rewardInfo:MutableLiveData<List<RewardUserInfo>> = MutableLiveData()
    val commentInfo:MutableLiveData<List<ArticleComment>> = MutableLiveData()
    val checkThumbUp:MutableLiveData<ResponseData<String>> = MutableLiveData()
    val collects:MutableLiveData<List<ArticleCollect>> = MutableLiveData()


    fun loadRecommendArticle() {
        viewModelScope.launch {
            articleList.value = ArticleRepository.getInstance().getRecommendArticleList(false)
        }
    }

    fun loadMoreRecommendArticle() {
        viewModelScope.launch {
            articleList.value = ArticleRepository.getInstance().getRecommendArticleList(true)
        }
    }

    /**
     * 获取文章详细内容
     */
    fun getArticleDetail(id:String){
        viewModelScope.launch {
            articleDetail.value = ArticleRepository.getInstance().getArticleDetail(id)
        }
    }

    /**
     * 获取文章打赏信息
     */
    fun getArticleReward(id: String) {
        viewModelScope.launch {
            rewardInfo.value = ArticleRepository.getInstance().getArticleReward(id)
        }
    }

    /**
     * 获取文章的评论
     */
    fun getArticleComment(id: String) {
        viewModelScope.launch {
            commentInfo.value = ArticleRepository.getInstance().getArticleComment(id, 1)
        }
    }
    /**
     * 点赞文章
     */
    fun articleThumbUp(id: String) {
        viewModelScope.launch {
            val message = ArticleRepository.getInstance().articleThumbUp(id)
            Log.d(TAG, "articleThumbUp: $message")
            ToastUtils.show(message.message, !message.success)
            articleCheckThumbUp(id)
        }
    }

    /**
     * 查询我是否已经点赞文章
     */
    fun articleCheckThumbUp(id: String) {
        viewModelScope.launch {
            checkThumbUp.value = ArticleRepository.getInstance().articleCheckThumbUp(id)
        }
    }

    /**
     * 查询我是否已经点赞文章
     */
    fun articleReward(reward: ArticleReward) {
        viewModelScope.launch {
            checkThumbUp.value = ArticleRepository.getInstance().articleReward(reward)
            getArticleReward(reward.articleId)
        }
    }

    /**
     * 查询用户的收藏目录
     */
    fun getCollect() {
        viewModelScope.launch {
            collects.value = ArticleRepository.getInstance().getCollect(1)
        }
    }


}