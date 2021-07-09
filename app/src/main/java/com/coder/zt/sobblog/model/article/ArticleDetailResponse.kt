package com.coder.zt.sobblog.model.article

data class ArticleDetailResponse(
    val code: Int,
    val `data`: Data,
    val message: String,
    val success: Boolean
)

data class Data(
    val articleType: String,
    val avatar: String,
    val categoryId: String,
    val categoryName: String,
    val content: String,
    val contentType: String,
    val covers: List<String>,
    val createTime: String,
    val id: String,
    val isComment: String,
    val isTop: String,
    val isVip: String,
    val labels: List<String>,
    val nickname: String,
    val recommend: Int,
    val state: String,
    val thumbUp: Int,
    val title: String,
    val userId: String,
    val viewCount: Int
)