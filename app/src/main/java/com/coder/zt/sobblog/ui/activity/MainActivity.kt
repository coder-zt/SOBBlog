package com.coder.zt.sobblog.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.ActivityMainBinding
import com.coder.zt.sobblog.model.datamanager.UserDataMan
import com.coder.zt.sobblog.ui.adapter.HomePagerAdapter
import com.coder.zt.sobblog.ui.base.BaseActivity
import com.coder.zt.sobblog.ui.fragment.IndexFragment
import com.coder.zt.sobblog.ui.fragment.MineFragment
import com.coder.zt.sobblog.ui.fragment.DiscoveryFragment
import com.coder.zt.sobblog.ui.fragment.OtherFragment
import com.coder.zt.sobblog.utils.Constants
import com.coder.zt.sobblog.utils.ToastUtils
import com.coder.zt.sobblog.viewmodel.UserViewModel
import com.google.android.material.tabs.TabLayout
import com.xuexiang.xupdate.XUpdate

class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object{
        private const val TAG = "MainActivity"
    }

    val userViewModel: UserViewModel by lazy {
        ViewModelProvider(this).get(UserViewModel::class.java)
    }

    val miniFragment:MineFragment by lazy{
        MineFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {

        dataBinding.tableBar.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.item_index-> dataBinding.viewPager.currentItem = 0
                R.id.item_discovery-> dataBinding.viewPager.currentItem = 1
                R.id.item_other-> dataBinding.viewPager.currentItem = 2
                R.id.item_mine-> {
                    dataBinding.viewPager.currentItem = 3
                    miniFragment.setData()
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
        val adapter = HomePagerAdapter(supportFragmentManager, listOf(IndexFragment(),DiscoveryFragment(),OtherFragment(),miniFragment))
        dataBinding.viewPager.adapter = adapter
        dataBinding.viewPager.currentItem = 0
        dataBinding.viewPager.offscreenPageLimit = 4
    }

    private fun initData() {
        checkUserState()
    }

    override fun onResume() {
        super.onResume()
        XUpdate.newBuild(this)
            .updateUrl(Constants.UPDATE_URL)
            .update()

    }

    private fun checkUserState() {
        userViewModel.checkToken()

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }
}