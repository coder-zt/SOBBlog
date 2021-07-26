package com.coder.zt.sobblog.model.base

data class UpdateInfo(
    val ApkMd5: String,
    val ApkSize: Int,
    val Code: Int,
    val DownloadUrl: String,
    val ModifyContent: String,
    val Msg: String,
    val UpdateStatus: Int,
    val UploadTime: String,
    val VersionCode: Int,
    val VersionName: String
)