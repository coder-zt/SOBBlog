package com.coder.zt.sobblog.model.base

/**
 * 服务器返回非列表数据
 */
data class ResponseListData<T>(
    val currentPage: Int,
    val hasNext: Boolean,
    val hasPre: Boolean,
    val list: List<T>,
    val pageSize: Int,
    val total: Int,
    val totalPage: Int
)
