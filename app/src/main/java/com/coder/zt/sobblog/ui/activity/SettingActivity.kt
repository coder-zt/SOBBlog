package com.coder.zt.sobblog.ui.activity

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.ActivitySettingBinding
import com.coder.zt.sobblog.ui.base.BaseActivity
import com.coder.zt.sobblog.utils.AppRouter
import com.coder.zt.sobblog.utils.ToastUtils
import com.coder.zt.sobblog.viewmodel.UserViewModel

class SettingActivity:BaseActivity<ActivitySettingBinding>() {

    private val userViewModel:UserViewModel by lazy {
        ViewModelProvider(this).get(UserViewModel::class.java)
    }
    override fun getLayoutId() = R.layout.activity_setting

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        dataBinding.tvLogoutBtn.setOnClickListener {
            userViewModel.logout()
        }
        dataBinding.tvSunof.setOnClickListener {
            AppRouter.toSunofLogActivity(this)
        }
    }


    private fun initData() {
        userViewModel.logoutResult.observe(this){
            ToastUtils.showResult(it)

            if(it.first){
                finish()
            }
        }
    }

}