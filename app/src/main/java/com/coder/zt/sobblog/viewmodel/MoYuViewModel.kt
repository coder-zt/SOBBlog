package com.coder.zt.sobblog.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coder.zt.sobblog.model.moyu.*
import com.coder.zt.sobblog.repository.MoYuRepository
import com.coder.zt.sobblog.utils.ImageSelectManager
import com.coder.zt.sobblog.utils.ToastUtils
import kotlinx.coroutines.launch

class MoYuViewModel: ViewModel() {
companion object{
    private const val TAG = "MoYuViewModel"
}
    val moyuDisplayData:MutableLiveData<List<MiniFeed>> = MutableLiveData()
    val slideDistance:MutableLiveData<Int> = MutableLiveData()
    val comment:MutableLiveData<String> = MutableLiveData()
    val feedComment:MutableLiveData<List<MYComment>> = MutableLiveData()
    val topicItem:MutableLiveData<List<TopicItem>> = MutableLiveData()


    init {
        comment.value = "测试"
    }

    /**
     * 获取摸鱼动态
     */
    fun getRecommendMiniFeed(page:Int){
        viewModelScope.launch{
            moyuDisplayData.value = MoYuRepository.getInstance().getRecommendMinifeed(page)
        }
    }

    /**
     * 点赞
     */
    fun thumbUP(momentId:String){
        viewModelScope.launch {
            Log.d(TAG, "thumbUP: $momentId")
            val thumbUp = MoYuRepository.getInstance().thumbUp(momentId)
            Log.d(TAG, "thumbUP: $thumbUp")
            ToastUtils.show(thumbUp.message, !thumbUp.success)
        }
    }

    /**
     * 发送评论
     */
    fun sendCommend(content: String, commentId: String) {
        viewModelScope.launch {
            Log.d(TAG, "sendCommend:content: $content   commentId: $commentId")
            val thumbUp = MoYuRepository.getInstance().comment(MYCommentSender(content, commentId))
            Log.d(TAG, "sendCommend: $thumbUp")
            ToastUtils.show(thumbUp.message, !thumbUp.success)
        }
    }

    /**
     * 获取动态的评论
     */
    fun getMiniFeedComment(feedId: String) {
        viewModelScope.launch {
            val minifeedComment = MoYuRepository.getInstance().getMinifeedComment(feedId, 1)
            feedComment.value = minifeedComment
        }
    }

    /**
     * 获取话题
     */
    fun getTopicItems(){
        viewModelScope.launch{
            topicItem.value = MoYuRepository.getInstance().minifeedTopics()
        }
    }

    /**
     * 上传图片
     */
    fun uploadImage(media:ImageSelectManager.UpLoadImage){
        viewModelScope.launch{
            MoYuRepository.getInstance().uploadImage(media)
        }
    }

    /**
     * 发布动态
     */
    fun publishMinifeed(media:MinifeedSender){
        viewModelScope.launch{
            val message = MoYuRepository.getInstance().publishMinifeed(media)
            ToastUtils.show(message)
        }
    }

}