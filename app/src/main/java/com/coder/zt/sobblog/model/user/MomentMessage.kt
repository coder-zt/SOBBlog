package com.coder.zt.sobblog.model.user

data class MomentMessage(
    val _id: String,
    val avatar: String,
    val bUid: String,
    val content: String,
    val createTime: String,
    val hasRead: String,
    val momentId: String,
    val nickname: String,
    val timeText: Any,
    val title: String,
    val uid: String
)