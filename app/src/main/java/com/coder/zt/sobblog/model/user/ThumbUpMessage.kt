package com.coder.zt.sobblog.model.user

data class ThumbUpMessage(
    val _id: String,
    val avatar: String,
    val beUid: String,
    val hasRead: String,
    val nickname: String,
    val thumbTime: String,
    val timeText: String,
    val title: String,
    val uid: String,
    val url: String
)