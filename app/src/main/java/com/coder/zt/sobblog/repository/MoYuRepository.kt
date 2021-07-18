package com.coder.zt.sobblog.repository

import android.net.Uri
import android.text.TextUtils
import android.util.Log
import com.coder.zt.sobblog.SOBApp
import com.coder.zt.sobblog.model.base.ResponseData
import com.coder.zt.sobblog.model.moyu.*
import com.coder.zt.sobblog.net.MoYuNetWork
import com.coder.zt.sobblog.utils.Constants
import com.coder.zt.sobblog.utils.ImageSelectManager
import com.luck.picture.lib.compress.CompressionPredicate
import com.luck.picture.lib.compress.Luban
import com.luck.picture.lib.compress.OnCompressListener
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.tools.PictureFileUtils.getPath
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class MoYuRepository {


    private var moyuMinifeedPage = 1

    suspend fun getRecommendMinifeed(loadMore:Boolean):List<MiniFeed>{
        if(loadMore){
            moyuMinifeedPage++
        }else{
            moyuMinifeedPage = 1
        }
        val recommend = MoYuNetWork.getInstance().getRecommendMinifeed(moyuMinifeedPage)
        return if (recommend.code == Constants.SUCCESS_CODE) {
           recommend.data.list
        }else{
            moyuMinifeedPage--
            listOf()
        }
    }

    suspend fun getRecommendMinifeed(page:Int):List<MiniFeed>{
        val recommend = MoYuNetWork.getInstance().getRecommendMinifeed(page)
        return if (recommend.code == Constants.SUCCESS_CODE) {
            recommend.data.list
        }else{
            listOf()
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

    suspend fun reply(reply: MYReplySender):ResponseData<String>{

        return MoYuNetWork.getInstance().reply(reply)
    }

    suspend fun minifeedTopics():List<TopicItem>{
        val comment = MoYuNetWork.getInstance().minifeedTopics()
        return comment.data
    }

    suspend fun uploadImage(media:ImageSelectManager.UpLoadImage, file: File){
        val request: RequestBody = RequestBody.create("image/jpg".toMediaTypeOrNull(), file)
        val image = MultipartBody.Part.createFormData("image", file.name, request)
        val comment = MoYuNetWork.getInstance().uploadImage(image)
        media.url = comment.data
        media.upload = true
        ImageSelectManager.update()
    }



    suspend fun publishMinifeed(minifeed: MinifeedSender):ResponseData<MYPublishResponse>{
        val comment = MoYuNetWork.getInstance().publishMinifeed(minifeed)
        return comment
    }


    companion object {

        private const val TAG = "MoYuRepository"

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