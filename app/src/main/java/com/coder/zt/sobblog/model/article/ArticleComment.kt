package com.coder.zt.sobblog.model.article

data class ArticleComment(
    val _id: String,
    val articleId: String,
    val avatar: String,
    val commentContent: String,
    val isTop: Boolean,
    val nickname: String,
    val parentId: String,
    val publishTime: String,
    val role: String,
    val subComments: List<SubComment>,
    val userId: String,
    val vip: Boolean
){
    data class SubComment(
        val _id: String,
        val articleId: String,
        val beNickname: String,
        val beUid: String,
        val content: String,
        val parentId: String,
        val publishTime: String,
        val vip: Boolean,
        val yourAvatar: String,
        val yourNickname: String,
        val yourRole: String,
        val yourUid: String
    )
}




