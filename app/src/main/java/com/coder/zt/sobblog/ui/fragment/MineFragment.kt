package com.coder.zt.sobblog.ui.fragment

import androidx.core.util.Pair
import androidx.lifecycle.ViewModelProvider
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.FragmentMineBinding
import com.coder.zt.sobblog.model.datamanager.UserDataMan
import com.coder.zt.sobblog.ui.adapter.MiniMuenAdapter
import com.coder.zt.sobblog.ui.base.BaseFragment
import com.coder.zt.sobblog.viewmodel.UserViewModel

class MineFragment:BaseFragment<FragmentMineBinding>() {

    private val userViewModel: UserViewModel by lazy{
        ViewModelProvider(this).get(UserViewModel::class.java)

    }

    companion object{
        private const val TAG = "MineFragment"
        private val menuList = listOf<Pair<Int, String>>(
            Pair(R.mipmap.tab_home,"点赞"),
            Pair(R.mipmap.tab_activity,"消息"),
            Pair(R.mipmap.tab_find,"收藏"),
            Pair(R.mipmap.tab_profile,"设置"))
    }

    override fun getLayoutId() = R.layout.fragment_mine


    override fun initView() {
        dataBinding.data = UserDataMan.getUserInfo()
        dataBinding.rvMiniMenu.adapter = MiniMuenAdapter(menuList){
        }
    }

    override fun initData() {
        userViewModel.getAchievement()
    }


}