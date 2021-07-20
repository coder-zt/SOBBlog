package com.coder.zt.sobblog.model.article

data class ArticleCollect(
    val _id: String,
    val avatar: Any,
    val cover: String,
    val createTime: String,
    val description: String,
    val favoriteCount: Int,
    val followCount: Int,
    val name: String,
    val permission: String,
    val userId: String,
    val userName: Any
)