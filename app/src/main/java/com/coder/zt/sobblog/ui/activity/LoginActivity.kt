package com.coder.zt.sobblog.ui.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.ActivityLoginBinding
import com.coder.zt.sobblog.model.datamanager.UserDataMan
import com.coder.zt.sobblog.model.user.LoginInfo
import com.coder.zt.sobblog.ui.base.BaseActivity
import com.coder.zt.sobblog.utils.MdUtils
import com.coder.zt.sobblog.utils.ToastUtils
import com.coder.zt.sobblog.viewmodel.UserViewModel


class LoginActivity:BaseActivity<ActivityLoginBinding>() {

    val userViewModel:UserViewModel by lazy {
        ViewModelProvider(this).get(UserViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }

    private fun initData() {
        userViewModel.loginResult.observe(this){
            if (it) {
                ToastUtils.show(userViewModel.loginMessage.value!!)
                userViewModel.checkToken()
                finish()
            }else{
                //没有登录成功，重新获取数据
                ToastUtils.showError(userViewModel.loginMessage.value!!)
                userViewModel.captcha()
            }
        }
        userViewModel.captchaBitmap.observe(this){
            dataBinding.ivHumanCode.setImageBitmap(it)
        }
    }

    private fun initView() {
        dataBinding.randomCode = java.util.Random().nextInt()
        dataBinding.ivHumanCode.setOnClickListener {
            userViewModel.captcha()
        }
        dataBinding.tvLoginBtn.setOnClickListener (object: View.OnClickListener{
            override fun onClick(v: View?) {
                val phoneNum = dataBinding.etPhoneNum.text.toString()
                if(phoneNum.isEmpty()){
                    ToastUtils.showError("手机号不能为空！")
                    return
                }
                val password = dataBinding.etPassword.text.toString()
                if(password.isEmpty()){
                    ToastUtils.showError("密码不能为空！")
                    return
                }
                val captcha = dataBinding.etVerify.text.toString()
                if(captcha.isEmpty()){
                    ToastUtils.showError("验证码不能为空！")
                    return
                }
                userViewModel.login(captcha, LoginInfo(phoneNum,    MdUtils.md5(password)))
            }

        })
    }


    override fun onResume() {
        super.onResume()
        userViewModel.captcha()
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

}