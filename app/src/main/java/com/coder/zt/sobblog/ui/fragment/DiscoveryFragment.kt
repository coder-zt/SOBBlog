package com.coder.zt.sobblog.ui.fragment

import android.content.Intent
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.FragmentDiscoveryBinding
import com.coder.zt.sobblog.ui.activity.MainActivity
import com.coder.zt.sobblog.ui.activity.MoYuActivity
import com.coder.zt.sobblog.ui.adapter.DiscoveryModuleAdapter
import com.coder.zt.sobblog.ui.base.BaseFragment

class DiscoveryFragment:BaseFragment<FragmentDiscoveryBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_discovery
    }

    override fun initData() {
        dataBinding.rvModule.adapter = DiscoveryModuleAdapter(){
            when(it){
                //摸鱼
                DiscoveryModuleAdapter.ItemType.TYPE_MOYU->{
                    val intent = Intent(activity, MoYuActivity::class.java)
                    startActivity(intent)
                }
                //问答
                DiscoveryModuleAdapter.ItemType.TYPE_QUESTION->{

                }
            }
        }
    }

    override fun initView() {

    }


}
