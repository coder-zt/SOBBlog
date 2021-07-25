package com.coder.zt.sobblog.model.moyu

//content为评论内容，momentId为动态Id，targetUserId是被评论内容的用户Id，commentId为被评论内容的Id
data class MYReplySender(
    val momentId: String,
    val commentId: String,
    val targetUserId: String,
    val content: String
)