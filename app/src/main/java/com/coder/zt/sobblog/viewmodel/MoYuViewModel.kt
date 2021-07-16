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



    /**
     * 获取摸鱼动态
     */
    fun getRecommendMiniFeed(loadMore:Boolean){
        viewModelScope.launch{
            moyuDisplayData.value = MoYuRepository.getInstance().getRecommendMinifeed(loadMore)
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
    fun sendComment(content: String, commentId: String) {
        viewModelScope.launch {
            val thumbUp = MoYuRepository.getInstance().comment(MYCommentSender(content, commentId))
            ToastUtils.show(thumbUp.message, !thumbUp.success)
        }
    }

    /**
     * 回复评论
     *
     * commentId: "1412829969465319426"
    content: "回复测试"
    momentId: "1412324516285227010"
    targetUserId: "1216830916760965120"


    MYReplySender(commentId=1412829969465319426, content=回复测试, momentId=1412324516285227010, targetUserId=1216830916760965120)
     */
    fun sendReply(sender: MYReplySender) {
        viewModelScope.launch {
            Log.d(TAG, "sendReply: $sender")
            //commentId: "1415723837252743170"
            //content: "可以了，做好了app内部升级就发出来大家体验体验"
            //momentId: "1415701882357198850"
//            targetUserId: "1139423796017500160"
            val data = MoYuRepository.getInstance().reply(sender)
            ToastUtils.show(data.message, !data.success)
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