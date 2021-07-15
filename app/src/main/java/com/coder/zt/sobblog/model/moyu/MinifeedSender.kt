package com.coder.zt.sobblog.model.moyu

data class MinifeedSender(
    val content: String,
    val images: List<String>,
    val topicId: String = ""
)