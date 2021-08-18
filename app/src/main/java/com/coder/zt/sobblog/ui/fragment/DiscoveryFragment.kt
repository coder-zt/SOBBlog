package com.coder.zt.sobblog.ui.fragment

import android.content.Intent
import android.os.Build
import android.text.Html
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.FragmentDiscoveryBinding
import com.coder.zt.sobblog.ui.activity.MoYuActivity
import com.coder.zt.sobblog.ui.adapter.DiscoveryModuleAdapter
import com.coder.zt.sobblog.ui.adapter.EmojiAdapter
import com.coder.zt.sobblog.ui.base.BaseFragment
import com.coder.zt.sobblog.utils.EmojiImageGetter
import com.coder.zt.sobblog.utils.ToastUtils
import java.lang.StringBuilder

class DiscoveryFragment:BaseFragment<FragmentDiscoveryBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_discovery
    }

    companion object{
        private const val TAG = "DiscoveryFragment"
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
                    ToastUtils.show("问答还没做呢，快去鱼塘摸摸鱼！")
                }
            }
        }
    }

    override fun initView() {
    }


}
