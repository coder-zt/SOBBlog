package com.coder.zt.sobblog.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coder.zt.sobblog.model.article.ArticleComment
import com.coder.zt.sobblog.model.article.ArticleDetail
import com.coder.zt.sobblog.model.article.ArticleInfo
import com.coder.zt.sobblog.model.article.ArticleReward
import com.coder.zt.sobblog.model.base.ResponseData
import com.coder.zt.sobblog.model.user.RewardUserInfo
import com.coder.zt.sobblog.repository.ArticleRepository
import com.luck.picture.lib.tools.ToastUtils
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
            com.coder.zt.sobblog.utils.ToastUtils.show(message.message, message.success)
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


}