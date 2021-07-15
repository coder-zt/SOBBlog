package com.coder.zt.sobblog.repository

import android.util.Log
import com.coder.zt.sobblog.model.base.ResponseData
import com.coder.zt.sobblog.model.moyu.*
import com.coder.zt.sobblog.net.MoYuNetWork
import com.coder.zt.sobblog.utils.Constants
import com.coder.zt.sobblog.utils.ImageSelectManager
import com.luck.picture.lib.entity.LocalMedia
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.MultipartReader
import okhttp3.RequestBody
import java.io.File


class MoYuRepository {
    suspend fun getRecommendMinifeed(page:Int):MoYuDataDisplay{
        val recommend = MoYuNetWork.getInstance().getRecommendMinifeed(page)
        return if (recommend.code == Constants.SUCCESS_CODE) {
            val displayMiniFeed = mutableListOf<MoYuDataDisplay.MiniFeed>()
            for (miniFeed in recommend.data.list) {
                val displayComments = mutableListOf<MoYuDataDisplay.MiniFeed.Comment>()
                displayMiniFeed.add(MoYuDataDisplay.MiniFeed(miniFeed, displayComments))
            }
            MoYuDataDisplay(1, displayMiniFeed)
        }else{
            MoYuDataDisplay(0, listOf())
        }
    }

    suspend fun getMinifeedComment(commentId:String, page:Int):List<MYComment>{
        val comment = MoYuNetWork.getInstance().getMinifeedComment(commentId, page)
        return if (comment.code == Constants.SUCCESS_CODE) {
            comment.data.list
        }else{
            listOf()
        }
    }

    suspend fun thumbUp(momentId:String):ResponseData<String>{
        val comment = MoYuNetWork.getInstance().thumbUp(momentId)
        return comment
    }

    suspend fun comment(comment: MYCommentSender):ResponseData<String>{
        val comment = MoYuNetWork.getInstance().comment(comment)
        return comment
    }

    suspend fun minifeedTopics():List<TopicItem>{
        val comment = MoYuNetWork.getInstance().minifeedTopics()
        return comment.data
    }

    suspend fun uploadImage(media: ImageSelectManager.UpLoadImage){
        val file = File(media.localMedia.realPath)
        val request:RequestBody = RequestBody.create("image/jpg".toMediaTypeOrNull(), file)
        val image = MultipartBody.Part.createFormData("image", file.name, request)
        val comment = MoYuNetWork.getInstance().uploadImage(image)
        Log.d("测试上传图片", "uploadImage: $comment")
        media.upload = comment.success
        media.url = comment.data
    }

    suspend fun publishMinifeed(minifeed: MinifeedSender):String{
        val comment = MoYuNetWork.getInstance().publishMinifeed(minifeed)
        return comment.data
    }


    companion object {

        private var instance: MoYuRepository? = null

        fun getInstance(): MoYuRepository {
            if (instance == null) {
                synchronized(MoYuRepository::class.java) {
                    if (instance == null) {
                        instance = MoYuRepository()
                    }
                }
            }
            return instance!!
        }

    }
}