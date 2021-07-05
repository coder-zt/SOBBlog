package com.coder.zt.sobblog.ui.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.SOBApp
import com.coder.zt.sobblog.databinding.ActivityLoginBinding
import com.coder.zt.sobblog.model.user.LoginInfo
import com.coder.zt.sobblog.ui.base.BaseActivity
import com.coder.zt.sobblog.utils.Constants
import com.coder.zt.sobblog.viewmodel.UserViewModel
import okhttp3.internal.and
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class LoginActivity:BaseActivity() {

    val userViewModel:UserViewModel by lazy {
        ViewModelProvider(this).get(UserViewModel::class.java)
    }
    val dataBinding:ActivityLoginBinding by lazy{
        DataBindingUtil.setContentView(this, R.layout.activity_login)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }

    private fun initData() {
        userViewModel.loginResult.observe(this){
            Toast.makeText(this,userViewModel.loginMessage.value, Toast.LENGTH_SHORT ).show()
        }
    }

    private fun initView() {
        dataBinding.randomCode = java.util.Random().nextInt()
        dataBinding.ivHumanCode.setOnClickListener {
            Glide.with(it.context)
                .load(Constants.HUMAN_CODE_URL + java.util.Random().nextInt())
                .into(it as ImageView)
        }
        dataBinding.tvLoginBtn.setOnClickListener (object: View.OnClickListener{
            override fun onClick(v: View?) {
                val phoneNum = dataBinding.tvPhoneNum.text.toString()
                if(phoneNum.isEmpty()){
                    Toast.makeText(SOBApp._context,"手机号不能为空！", Toast.LENGTH_SHORT ).show()
                    return
                }
                val password = dataBinding.tvPhoneNum.text.toString()
                if(password.isEmpty()){
                    Toast.makeText(SOBApp._context,"密码不能为空！", Toast.LENGTH_SHORT ).show()
                    return
                }
                val captcha = dataBinding.tvPhoneNum.text.toString()
                if(captcha.isEmpty()){
                    Toast.makeText(SOBApp._context,"验证码不能为空！", Toast.LENGTH_SHORT ).show()
                    return
                }
                userViewModel.login(captcha, LoginInfo(phoneNum, md5(password)))
            }

        })
    }

    fun md5(string: String): String {
        if (TextUtils.isEmpty(string)) {
            return ""
        }
        var md5: MessageDigest? = null
        try {
            md5 = MessageDigest.getInstance("MD5")
            val bytes = md5.digest(string.toByteArray())
            var result: String = ""
            for (b in bytes) {
                var temp = Integer.toHexString(b and 0xff)
                if (temp.length == 1) {
                    temp = "0$temp"
                }
                result += temp
            }
            return result
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return ""
    }
}