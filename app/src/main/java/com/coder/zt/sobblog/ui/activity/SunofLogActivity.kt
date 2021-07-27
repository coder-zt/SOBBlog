package com.coder.zt.sobblog.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.ActivitySunofBinding
import com.coder.zt.sobblog.ui.adapter.ArticleInfoAdapter
import com.coder.zt.sobblog.ui.adapter.PopListAdapter
import com.coder.zt.sobblog.ui.base.BaseActivity
import com.coder.zt.sobblog.viewmodel.UserViewModel

class SunofLogActivity:BaseActivity<ActivitySunofBinding>() {

    companion object{
        private const val TAG = "SunofLogActivity"
    }

    private val userViewModel: UserViewModel by lazy {
        ViewModelProvider(this).get(UserViewModel::class.java)
    }

    override fun getLayoutId(): Int {
        Log.d(TAG, "getLayoutId: ")
       return R.layout.activity_sunof
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    private fun initData() {
        userViewModel.sunofCoinInfo.observe(this){
            for (sunofCoinInfo in it) {
                Log.d(TAG, "initData: $sunofCoinInfo")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        userViewModel.getSunofCoinInfo(false)
    }

}
