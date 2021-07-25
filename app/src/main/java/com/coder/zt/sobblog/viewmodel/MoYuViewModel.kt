package com.coder.zt.sobblog.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coder.zt.sobblog.SOBApp
import com.coder.zt.sobblog.model.moyu.*
import com.coder.zt.sobblog.repository.MoYuRepository
import com.coder.zt.sobblog.ui.adapter.MoYuAdapter
import com.coder.zt.sobblog.utils.ImageSelectManager
import com.coder.zt.sobblog.utils.ToastUtils
import kotlinx.coroutines.launch
import me.shouheng.compress.Compress
import me.shouheng.compress.listener.CompressListener
import me.shouheng.compress.strategy.Strategies
import java.io.File


class MoYuViewModel: ViewModel() {
companion object{
    private const val TAG = "MoYuViewModel"
    const val EVENT_PUBLISH = 111
}
    val moyuDisplayData:MutableLiveData<List<MiniFeed>> = MutableLiveData()
    val slideDistance:MutableLiveData<Int> = MutableLiveData()
    val comment:MutableLiveData<String> = MutableLiveData()
    val feedComment:MutableLiveData<List<MYComment>> = MutableLiveData()
    val topicItem:MutableLiveData<List<TopicItem>> = MutableLiveData()
    val eventObserver:MutableLiveData<Pair<Int, Boolean>> = MutableLiveData()
    val changeItemId:MutableLiveData<Pair<String,MoYuAdapter.DOTYPE>> = MutableLiveData()
    val bingWarpUrl:MutableLiveData<String> = MutableLiveData()

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
            if(thumbUp.success){
                changeItemId.value = Pair(momentId, MoYuAdapter.DOTYPE.THUMB_UP)
            }
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
     */
//    commentId: "1419320369843560450"
//    content: "明天可能能更新一下"
//    momentId: "1419249162863628289"
//    targetUserId: "1139423796017500160"
    fun sendReply(sender: MYReplySender) {
        viewModelScope.launch {
            Log.d(TAG, "sendReply: $sender")
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
        compressPicture(File(media.localMedia.realPath)){

            viewModelScope.launch{
                MoYuRepository.getInstance().uploadImage(media,it)
            }

        }

    }

    
    private fun compressPicture(file: File, callback:(path:File)->Unit) {
       Compress.with(SOBApp._context, file)
            .setQuality(70)
            .setCompressListener(object : CompressListener {
                override fun onStart() {
                }

                override fun onSuccess(result: File?) {
                    result?.let {
                        callback(it)
                    }
                    }

                override fun onError(throwable: Throwable?) {
                }
            })
            .strategy(Strategies.luban())
            .launch()
    }

    /**
     * 发布动态
     */
    fun publishMinifeed(media:MinifeedSender){
        viewModelScope.launch{
            val message = MoYuRepository.getInstance().publishMinifeed(media)
            ToastUtils.show(message.message)
            eventObserver.value = Pair(EVENT_PUBLISH, message.success)
        }
    }

    /**
     * 发布动态
     */
    fun getWarpUrl(){
        viewModelScope.launch{
            val url = MoYuRepository.getInstance().getWarpData()
            bingWarpUrl.value = url
        }
    }


}