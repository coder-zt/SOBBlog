package com.coder.zt.sobblog.model.moyu

data class MoYuList(
    val code: Int,
    val `data`: Data,
    val message: String,
    val success: Boolean
)

data class Data(
    val currentPage: Int,
    val hasNext: Boolean,
    val hasPre: Boolean,
    val list: List<MiniFeed>,
    val pageSize: Int,
    val total: Int,
    val totalPage: Int
)

data class MiniFeed(
    val avatar: String,
    val commentCount: Int,
    val company: String,
    val content: String,
    val createTime: String,
    val id: String,
    val images: List<String>,
    val linkCover: Any,
    val linkTitle: String,
    val linkUrl: String,
    val nickname: String,
    val position: String,
    val thumbUpCount: Int,
    val thumbUpList: List<Any>,
    val topicId: Any,
    val topicName: Any,
    val userId: String,
    val vip: Boolean
)