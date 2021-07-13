package com.coder.zt.sobblog.model.article

data class ArticleInfo(
    val avatar: String,
    val covers: List<String>,
    val createTime: String,
    val id: String,
    val nickName: String,
    val thumbUp: Int,
    val title: String,
    val type: Int,
    val userId: String,
    val viewCount: Int,
    val vip: Boolean
)