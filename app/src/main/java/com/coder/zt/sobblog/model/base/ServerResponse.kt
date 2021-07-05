package com.coder.zt.sobblog.model.base

data class ServerResponse(val code: Int,
                          val `data`: Any,
                          val message: String,
                          val success: Boolean) {

}