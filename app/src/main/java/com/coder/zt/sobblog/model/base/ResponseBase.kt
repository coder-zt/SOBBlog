package com.coder.zt.sobblog.model.base

import com.coder.zt.sobblog.model.moyu.MiniFeed

open class ResponseBase<T>(code: Int,
                         `data`: T,
                        message: String,
                        success: Boolean) {

}