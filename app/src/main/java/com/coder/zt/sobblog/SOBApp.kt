package com.coder.zt.sobblog

import android.app.Application
import android.content.Context
import android.util.Log
import com.coder.zt.sobblog.model.base.UpdateInfo
import com.coder.zt.sobblog.utils.Constants
import com.coder.zt.sobblog.utils.GsonUtils
import com.coder.zt.sobblog.utils.OKHttpUpdateHttpService
import com.xuexiang.xupdate.XUpdate
import com.xuexiang.xupdate.entity.UpdateError.ERROR.CHECK_NO_NEW_VERSION
import com.xuexiang.xupdate.utils.UpdateUtils

import com.umeng.commonsdk.UMConfigure

class SOBApp:Application() {


    override fun onCreate() {
        super.onCreate()
        _context = this
        initAppUpdate()

    }

    private fun initAppUpdate() {
        XUpdate.get()
            .debug(BuildConfig.DEBUG)
            .isWifiOnly(false) // By default, only version updates are checked under WiFi
            .isGet(true) // The default setting uses Get request to check versions
            .isAutoMode(false) // The default setting is non automatic mode
            .debug(true)
            .param(
                "versionCode",
                UpdateUtils.getVersionCode(this)
            ) // Set default public request parameters
            .param("appKey", packageName)
            .setOnUpdateFailureListener { error ->
                // Set listening for version update errors
                if (error.code != CHECK_NO_NEW_VERSION) {          // Handling different errors
                }
            }
            .supportSilentInstall(true) // Set whether silent installation is supported. The default is true
            .setIUpdateHttpService(OKHttpUpdateHttpService()) // This must be set! Realize the network request function.
            .init(this)

        XUpdate.get().setILogger { priority, tag, message, t ->
            //实现日志记录功能
            Log.d(TAG, "initAppUpdate: $message")
            if(message.contains("服务端返回的最新版本信息")){
                val infoObj = message.replace("服务端返回的最新版本信息:", "")
                val updateInfo = GsonUtils.getInstance().fromJson(infoObj, UpdateInfo::class.java)
                val split = updateInfo.Msg.split("&&&&")
                if(split.size > 1){
                    Constants.firstMsg = split[0]
                    Constants.finallyMsg = split[1]
                }

            }
        }
        UMConfigure.preInit(this,"60f5c062a6f90557b7bed448", "beta")
//        UMConfigure.init(this,UMConfigure.DEVICE_TYPE_PHONE,"60f5c062a6f90557b7bed448")
        UMConfigure.init(this, "60f5c062a6f90557b7bed448", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, null)
    }

    companion object {

        private const val TAG = "SOBApp"

        var _context: Application? = null
        fun getContext(): Context {
            return _context!!
        }

    }
}
