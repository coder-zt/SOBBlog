package com.coder.zt.sobblog.model.base

/**
 * 服务器返回非列表数据
 */
data class ResponseData<T>(val code: Int,
                           val `data`: T,
                           val message: String,
                           val success: Boolean) {
}