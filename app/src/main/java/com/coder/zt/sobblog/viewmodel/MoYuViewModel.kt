package com.coder.zt.sobblog.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coder.zt.sobblog.model.moyu.MYComment
import com.coder.zt.sobblog.model.moyu.MYCommentSender
import com.coder.zt.sobblog.model.moyu.MoYuDataDisplay
import com.coder.zt.sobblog.model.moyu.TopicItem
import com.coder.zt.sobblog.repository.MoYuRepository
import com.coder.zt.sobblog.utils.ToastUtils
import kotlinx.coroutines.launch

class MoYuViewModel: ViewModel() {
companion object{
    private const val TAG = "MoYuViewModel"
}
    val moyuDisplayData:MutableLiveData<MoYuDataDisplay> = MutableLiveData()
    val slideDistance:MutableLiveData<Int> = MutableLiveData()
    val comment:MutableLiveData<String> = MutableLiveData()
    val feedComment:MutableLiveData<MutableList<MoYuDataDisplay.MiniFeed.Comment>> = MutableLiveData()
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

            val displayComments = mutableListOf<MoYuDataDisplay.MiniFeed.Comment>()
            for (comment in minifeedComment) {
                val childDisplayComment = mutableListOf<MoYuDataDisplay.MiniFeed.Comment.SubComment>()
                //获取评论的子评论
                for (subComment in comment.subComments) {
                    childDisplayComment.add(MoYuDataDisplay.MiniFeed.Comment.SubComment(
                            subComment
                        )
                    )
                }
                val displayComment = MoYuDataDisplay.MiniFeed.Comment(comment.content,
                    comment.id,
                    comment.nickname,
                    childDisplayComment
                )
                displayComments.add(displayComment)
            }
            feedComment.value = displayComments
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

}