package com.coder.zt.sobblog.ui.activity

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.PagerAdapter
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.ActivityMainBinding
import com.coder.zt.sobblog.ui.adapter.HomePagerAdapter
import com.coder.zt.sobblog.ui.base.BaseActivity
import com.coder.zt.sobblog.ui.fragment.IndexFragment
import com.coder.zt.sobblog.ui.fragment.MineFragment
import com.coder.zt.sobblog.ui.fragment.MoYuFragment
import com.coder.zt.sobblog.ui.fragment.OtherFragment
import com.coder.zt.sobblog.viewmodel.MoYuViewModel
import com.google.android.material.tabs.TabLayout

class MainActivity : BaseActivity() {

    companion object{
        private const val TAG = "MainActivity"
    }

    private val data:ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        data.tableBar.apply {
            addTab(newTab().setText("首页").setIcon(R.mipmap.ic_launcher))
            addTab(newTab().setText("摸鱼").setIcon(R.mipmap.ic_launcher))
            addTab(newTab().setText("其他").setIcon(R.mipmap.ic_launcher))
            addTab(newTab().setText("我").setIcon(R.mipmap.ic_launcher))
        }
        val adapter = HomePagerAdapter(supportFragmentManager, listOf(IndexFragment(),MoYuFragment(),OtherFragment(),MineFragment()))
        data.viewPager.adapter = adapter
        data.viewPager.currentItem = 1
    }

    private fun initData() {

    }

    override fun onResume() {
        super.onResume()

    }
}