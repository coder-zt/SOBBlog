package com.coder.zt.sobblog.model.moyu

import java.lang.reflect.Constructor

//摸鱼模块显示自定义数据
data class MoYuDataDisplay(
    val curPage:Int,//当前加载页数
    val data:List<MiniFeed>//动态列表
    ){
    //单条动态数据
    data class MiniFeed(
        val id:String,//id
        val avatar:String,//头像
        val nickName:String,//昵称
        val content:String,//动态内容
        val topic: String,//动态话题
        val comment:List<Comment>,//动态评论
        val images: List<String>,//动态图片
        val vip: Boolean,//该用户是否为VIP
        val thumbUpCount: Int,//点赞数
        val thumbUpList: List<String>,//点赞id
        val commentCount: Int,//评论数
        val company: String,//用户公司
        val position: String,//用户职位
        val createTime: String,//发布时间
    ){
        constructor(data:com.coder.zt.sobblog.model.moyu.MiniFeed,commentData:List<Comment>):this(
            data.id,
            data.avatar,
            data.nickname,
            data.content,
            data.topicName?:"",
            commentData,
        data.images,
        data.vip,
        data.thumbUpCount,
        data.thumbUpList,
        data.commentCount,
            data.company?:"游民",
            data.position?:"无业",
        data.createTime){
            
        }


        data class Comment(
            val content: String,//评论内容
            val momentId: String,//评论ID
            val nickname: String,//用户昵称
            val subComments: List<SubComment>,//子评论
        ){

            data class SubComment(
                val commentId: String,//评论ID
                val content: String,//内容
                val nickname: String,//用户昵称
                val targetUserNickname: String,//回复用户昵称
            ){
                constructor(data:MYComment.SubComment):this(
                    data.commentId,
                    data.content,
                    data.nickname,
                    data.targetUserNickname)
            }
        }

    }
}