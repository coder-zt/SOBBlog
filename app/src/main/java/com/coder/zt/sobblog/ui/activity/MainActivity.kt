package com.coder.zt.sobblog.ui.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.ActivityMainBinding
import com.coder.zt.sobblog.ui.adapter.HomePagerAdapter
import com.coder.zt.sobblog.ui.base.BaseActivity
import com.coder.zt.sobblog.ui.fragment.IndexFragment
import com.coder.zt.sobblog.ui.fragment.MineFragment
import com.coder.zt.sobblog.ui.fragment.DiscoveryFragment
import com.coder.zt.sobblog.ui.fragment.OtherFragment
import com.google.android.material.tabs.TabLayout

class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object{
        private const val TAG = "MainActivity"
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        dataBinding.tableBar.apply {
            addTab(newTab().setText("首页").setIcon(R.mipmap.ic_launcher))
            addTab(newTab().setText("摸鱼").setIcon(R.mipmap.ic_launcher))
            addTab(newTab().setText("其他").setIcon(R.mipmap.ic_launcher))
            addTab(newTab().setText("我").setIcon(R.mipmap.ic_launcher))
        }
        val adapter = HomePagerAdapter(supportFragmentManager, listOf(IndexFragment(),DiscoveryFragment(),OtherFragment(),MineFragment()))
        dataBinding.viewPager.adapter = adapter
        dataBinding.viewPager.currentItem = 0

    }

    private fun initData() {

    }

    override fun onResume() {
        super.onResume()

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }
}