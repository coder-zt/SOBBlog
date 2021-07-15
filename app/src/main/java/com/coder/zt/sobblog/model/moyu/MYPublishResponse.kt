package com.coder.zt.sobblog.model.moyu

data class MYPublishResponse(
    val avatar: String,
    val commentCount: Int,
    val company: Any,
    val content: String,
    val createTime: String,
    val id: String,
    val images: List<Any>,
    val linkCover: String,
    val linkTitle: String,
    val linkUrl: String,
    val nickname: String,
    val position: Any,
    val thumbUpCount: Int,
    val thumbUpList: List<Any>,
    val topicId: String,
    val topicName: String,
    val userId: String,
    val vip: Boolean
)