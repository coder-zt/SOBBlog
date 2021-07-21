package com.coder.zt.sobblog.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coder.zt.sobblog.model.datamanager.UserDataMan
import com.coder.zt.sobblog.model.user.LoginInfo
import com.coder.zt.sobblog.model.user.SobUserInfo
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
    val loginMessage = MutableLiveData<String>()
    val captchaBitmap = MutableLiveData<Bitmap>()

    fun login(captcha:String, loginInfo: LoginInfo){
        Log.d(TAG, "login: ${loginInfo.password}")
        viewModelScope.launch {
            val login = UserRepository.getInstance().login(captcha, loginInfo)
            Log.d(TAG, "login: ${login}")
            loginMessage.postValue(login.message)
            loginResult.postValue(login.success)
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
            if(response.success){
                UserDataMan.save(response.data)
            }
            loginMessage.postValue(response.message)
            loginResult.postValue(response.success)
        }
    }

    fun getAchievement(){
        viewModelScope.launch {
            val response = UserRepository.getInstance().getAchievement()
            Log.d(TAG, "getAchievement: ${response.data}")
        }
    }

}