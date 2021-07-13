package com.coder.zt.sobblog.model.user

data class RewardUserInfo(
    val avatar: String,
    val createTime: String,
    val nickname: String,
    val sob: Int,
    val userId: String,
    val vip: Boolean
){
    constructor(nickname: String, sob: Int,createTime: String):this("",createTime, nickname, sob,"",false)
}