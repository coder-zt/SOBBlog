package com.coder.zt.sobblog.model.moyu

data class MiniFeed(
    val avatar: String,
    val commentCount: Int,
    val company: String,
    val content: String,
    val createTime: String,
    val id: String,
    val images: List<String>,
    val linkCover: String,
    val linkTitle: String,
    val linkUrl: String,
    val nickname: String,
    val position: String,
    var thumbUpCount: Int,
    val thumbUpList: MutableList<String>,
    val topicId: Any,
    val topicName: String,
    val userId: String,
    val vip: Boolean
)