package com.coder.zt.sobblog.model.moyu

//content为评论内容，momentId为动态Id，targetUserId是被评论内容的用户Id，commentId为被评论内容的Id
data class MYReplySender(
    val commentId: String,
    val content: String,
    val momentId: String,
    val targetUserId: String
)