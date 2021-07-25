package com.coder.zt.sobblog.model.moyu

data class MYComment(
    val avatar: String,
    val company: String,
    val content: String,
    val createTime: String,
    val id: String,
    val momentId: String,
    val nickname: String,
    val position: String,//职位
    val subComments: List<SubComment>,
    val thumbUp: Int,
    val thumbUpList: List<String>,
    val userId: String,
    val vip: Boolean){

    data class SubComment(
        val avatar: String,
        val commentId: String,
        val company: String,
        val content: String,
        val createTime: String,
        val id: String,
        val nickname: String,
        val position: String,//职位
        val targetUserId: String,
        val targetUserIsVip: Boolean,
        val targetUserNickname: String,
        val thumbUpList: List<String>,
        val userId: String,
        val vip: Boolean)

}





