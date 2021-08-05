package com.coder.zt.sobblog.model.moyu

data class Moment(
    val avatar: String,
    val commentCount: Int,
    val company: String,
    val content: String,
    val createTime: String,
    val hasThumbUp: Boolean,
    val id: String,
    val images: List<String>,
    val linkCover: String,
    val linkTitle: String,
    val linkUrl: String,
    val nickname: String,
    val position: Int,
    val thumbUpCount: Int,
    val thumbUpList: List<String>,
    val topicId: String,
    val topicName: String,
    val userId: String,
    val vip: Boolean
)