package com.coder.zt.sobblog.model.moyu


data class MiniFeed(
    val avatar: String,
    val commentCount: Int,
    val company: String,
    val content: String,
    val createTime: String,
    val id: String,
    val images: List<String>,
    val linkCover: Any,
    val linkTitle: String,
    val linkUrl: String,
    val nickname: String,
    val position: String,
    val thumbUpCount: Int,
    val thumbUpList: List<String>,
    val topicId: Any,
    val topicName: String,
    val userId: String,
    val vip: Boolean
)