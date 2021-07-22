package com.coder.zt.sobblog.model.user

data class SobUserInfo(
    val avatar: String,
    val fansCount: Any,
    val followCount: Any,
    val id: String,
    val isVip: Boolean,
    val lev: Int,
    val nickname: String,
    val roles: Any,
    val token: String
)