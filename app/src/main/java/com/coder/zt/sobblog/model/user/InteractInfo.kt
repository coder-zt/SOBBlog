package com.coder.zt.sobblog.model.user

data class InteractInfo(
    val articleMsgCount: Int,
    val atMsgCount: Int,
    val momentCommentCount: Int,
    val shareMsgCount: Int,
    val systemMsgCount: Int,
    val thumbUpMsgCount: Int,
    val wendaMsgCount: Int
) {
    fun getMsgCount():Int{
        return articleMsgCount + momentCommentCount + atMsgCount + shareMsgCount + systemMsgCount + thumbUpMsgCount + wendaMsgCount
    }
}