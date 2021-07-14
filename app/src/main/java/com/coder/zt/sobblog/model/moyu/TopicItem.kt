package com.coder.zt.sobblog.model.moyu

data class TopicItem(
    val contentCount: Int,
    val cover: String,
    val description: String,
    val followCount: Int,
    val hasFollowed: Boolean,
    val id: String,
    val topicName: String
)