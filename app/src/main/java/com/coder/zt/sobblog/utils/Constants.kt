package com.coder.zt.sobblog.utils

object Constants {

    const val UPDATE_URL:String = "http://115.159.127.23/soblog/info/update.json"
    const val SP_NAME: String = "SOB-SP"
    const val SPLIT_SIGN: String="@=@"

    const val SP_KEY_USER_INFO: String = "sp_key_user_info"
    const val SP_KEY_WARP_URL: String = "sp_key_warp_url"
    const val SP_KEY_WARP_TIME: String = "sp_key_warp_time"
    //https//www.sunofbeach.net/u/1284274686481473536
    const val BASE_URL = "https://api.sunofbeach.net"
    const val WEBSITE_URL = "sunofbeach.net"
    const val BING_BASE_URL = "https://cn.bing.com"
    const val SUCCESS_CODE = 10000
    var firstMsg = ""
    var finallyMsg = ""

    object InteractType{
        val typeSyetem = 0
        val typeReply = 1
        val typeThumbUp = 2
        val typeArticleComment = 3
        val typeMontentComment = 4
        val typeWeDa = 5
    }
}