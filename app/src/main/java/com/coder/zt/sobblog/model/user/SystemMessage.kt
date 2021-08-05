package com.coder.zt.sobblog.model.user

data class SystemMessage(
    val _id: String,
    val content: String,
    val publishTime: String,
    val state: String,
    val title: String,
    val userId: String
)