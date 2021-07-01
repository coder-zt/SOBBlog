package com.coder.zt.sobblog.model.moyu

data class MYComment(
    val code: Int,
    val `data`: Data,
    val message: String,
    val success: Boolean
){
    data class Data(
        val currentPage: Int,
        val hasNext: Boolean,
        val hasPre: Boolean,
        val list: List<Comment>,
        val pageSize: Int,
        val total: Int,
        val totalPage: Int
    ){
        data class Comment(
            val avatar: String,
            val company: String,
            val content: String,
            val createTime: String,
            val id: String,
            val momentId: String,
            val nickname: String,
            val position: String,
            val subComments: List<SubComment>,
            val thumbUp: Int,
            val thumbUpList: List<Any>,
            val userId: String,
            val vip: Boolean
        ){
            data class SubComment(
                val avatar: String,
                val commentId: String,
                val company: Any,
                val content: String,
                val createTime: String,
                val id: String,
                val nickname: String,
                val position: Any,
                val targetUserId: Any,
                val targetUserIsVip: Boolean,
                val targetUserNickname: String,
                val thumbUpList: List<Any>,
                val userId: String,
                val vip: Boolean
            )
        }

    }
}





