package com.coder.zt.sobblog.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coder.zt.sobblog.model.datamanager.UserDataMan
import com.coder.zt.sobblog.model.user.*
import com.coder.zt.sobblog.net.UserNetWork
import com.coder.zt.sobblog.repository.UserRepository
import com.coder.zt.sobblog.utils.Constants
import com.coder.zt.sobblog.utils.GsonUtils
import kotlinx.coroutines.launch
import kotlin.math.log

class UserViewModel:ViewModel() {

    companion object{
        private const val TAG = "UserViewModel"
    }
    val loginResult = MutableLiveData<Boolean>()
    val logoutResult = MutableLiveData<Pair<Boolean, String>>()
    val loginMessage = MutableLiveData<String>()
    val captchaBitmap = MutableLiveData<Bitmap>()
    val achievement = MutableLiveData<UserAchievement>()
    val interactInfo = MutableLiveData<InteractInfo>()
    val sunofCoinInfo = MutableLiveData<List<SunofCoinInfo>>()
    val thumbUpMessage = MutableLiveData<List<ThumbUpMessage>>()
    val replyMessage = MutableLiveData<List<ReplyMessage>>()
    val momentMessage = MutableLiveData<List<MomentMessage>>()
    val systemMessage = MutableLiveData<List<SystemMessage>>()

    fun login(captcha:String, loginInfo: LoginInfo){
        Log.d(TAG, "login: ${loginInfo.password}")
        viewModelScope.launch {
            val login = UserRepository.getInstance().login(captcha, loginInfo)
            Log.d(TAG, "login: ${login}")
            loginMessage.postValue(login.message)
            loginResult.postValue(login.success)
        }
    }

    fun logout(){
        viewModelScope.launch {
            val logout = UserRepository.getInstance().logout()
            Log.d(TAG, "login: $logout")
            logoutResult.postValue(Pair(logout.success, logout.message))
        }
    }


    fun captcha(){
        viewModelScope.launch {
            val login = UserRepository.getInstance().captcha()
            captchaBitmap.postValue(login!!)
        }
    }

    fun checkToken(){
        viewModelScope.launch {
            val response = UserRepository.getInstance().checkToken()
            Log.d(TAG, "checkToken: $response")
            if(response.success){
                UserDataMan.save(response.data)
                loginResult.value = true
            }else{
                UserDataMan.clear()
            }
        }
    }

    fun getAchievement(){
        viewModelScope.launch {
            if(UserDataMan.isLogin()){
                val response = UserRepository.getInstance().getAchievement()
                if (response.success) {
                    response.data.let {
                        achievement.value = it
                    }
                }
            }
        }
    }

    /**
     * ??????????????????
     */
    fun getInteractInfo(){
        viewModelScope.launch {
            if(UserDataMan.isLogin()){
                val response = UserRepository.getInstance().getInteractInfo()
                if (response.success) {
                    response.data.let {
                        interactInfo.value = it
                    }
                }
            }
        }
    }

    /**
     * ??????Sunof????????????
     */
    fun getSunofCoinInfo(loadMore:Boolean){
        viewModelScope.launch {
            if(UserDataMan.isLogin()){
                val response = UserRepository.getInstance().getSobCoinInfo(loadMore)
                sunofCoinInfo.value = response
            }
        }
    }
    /**
     * ???????????????????????????
     */
    fun getSystemMessage(loadMore:Boolean){
        viewModelScope.launch {
            if(UserDataMan.isLogin()){
                val response = UserRepository.getInstance().getSystemMessage(loadMore)
                systemMessage.value = response
            }
        }
    }

    /**
     * ?????????????????????
     */
    fun getThumbUpMessage(loadMore: Boolean) {
        viewModelScope.launch {
            if(UserDataMan.isLogin()){
                val response = UserRepository.getInstance().getThumbUpMessage(loadMore)
                thumbUpMessage.value = response
            }
        }
    }

    /**
     * ????????????????????????
     */
    fun getReplyMessage(loadMore: Boolean) {
        viewModelScope.launch {
            if(UserDataMan.isLogin()){
                val response = UserRepository.getInstance().getReplyMessage(loadMore)
                replyMessage.value = response
            }
        }
    }

    /**
     * ??????@??????????????????
     */
    fun updateReplyMessageState(messageId: String) {
        viewModelScope.launch {
            if(UserDataMan.isLogin()){
                val response = UserRepository.getInstance().updateReplyMessageState(messageId)
                if(response){
                    getReplyMessage(false)
                }
            }
        }
    }


    /**
     * ????????????????????????
     */
    fun getMomentMessage(loadMore: Boolean) {
        viewModelScope.launch {
            if(UserDataMan.isLogin()){
                val response = UserRepository.getInstance().getMomentMessage(loadMore)
                momentMessage.value = response
            }
        }
    }


    /**
     * ??????@??????????????????
     */
    fun updateMomentMessageState(messageId: String) {
        viewModelScope.launch {
            if(UserDataMan.isLogin()){
                val response = UserRepository.getInstance().updateMomentMessageState(messageId)
                if(response){
                    getMomentMessage(false)
                }
            }
        }
    }

}