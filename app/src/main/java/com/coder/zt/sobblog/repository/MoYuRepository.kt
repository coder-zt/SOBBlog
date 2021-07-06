package com.coder.zt.sobblog.repository

import com.coder.zt.sobblog.model.base.ServerResponse
import com.coder.zt.sobblog.model.moyu.MYComment
import com.coder.zt.sobblog.model.moyu.MoYuDataDisplay
import com.coder.zt.sobblog.model.moyu.MoYuDataDisplay.MiniFeed.Comment.SubComment
import com.coder.zt.sobblog.net.MoYuNetWork
import com.coder.zt.sobblog.utils.Constants

class MoYuRepository {
    suspend fun getRecommendMinifeed(page:Int):MoYuDataDisplay{
        val recommend = MoYuNetWork.getInstance().getRecommendMinifeed(page)
        return if (recommend.code == Constants.SUCCESS_CODE) {
            val displayMiniFeed = mutableListOf<MoYuDataDisplay.MiniFeed>()
            for (miniFeed in recommend.data.list) {
                val displayComments = mutableListOf<MoYuDataDisplay.MiniFeed.Comment>()
                //获取动态的评论数据
                if(miniFeed.commentCount > 0){
                    val minifeedComment = getMinifeedComment(miniFeed.id, 1)
                    for (comment in minifeedComment) {
                        val childDisplayComment = mutableListOf<SubComment>()
                        //获取评论的子评论
                        for (subComment in comment.subComments) {
                            childDisplayComment.add(SubComment(subComment))
                        }
                        val displayComment = MoYuDataDisplay.MiniFeed.Comment(comment.content,
                            comment.id,
                            comment.nickname,
                            childDisplayComment
                        )
                        displayComments.add(displayComment)
                    }
                }
                displayMiniFeed.add(MoYuDataDisplay.MiniFeed(miniFeed, displayComments))
            }
            MoYuDataDisplay(recommend.data.currentPage, displayMiniFeed)
        }else{
            MoYuDataDisplay(recommend.data.currentPage, listOf())
        }
    }

    suspend fun getMinifeedComment(commentId:String, page:Int):List<MYComment.Data.Comment>{
        val comment = MoYuNetWork.getInstance().getMinifeedComment(commentId, page)
        return if (comment.code == Constants.SUCCESS_CODE) {
            comment.data.list
        }else{
            listOf()
        }
    }

    suspend fun thumbUp(momentId:String):ServerResponse{
        val comment = MoYuNetWork.getInstance().thumbUp(momentId)
        return comment
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