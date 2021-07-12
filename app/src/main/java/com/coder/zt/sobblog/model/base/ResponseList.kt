package com.coder.zt.sobblog.model.base

import com.coder.zt.sobblog.model.moyu.MiniFeed

data class ResponseList<T>(val code: Int,
                           val `data`: ResponseListData<T>,
                           val  message: String,
                           val success: Boolean):ResponseBase<ResponseListData<T>>(code, data, message, success) {

}