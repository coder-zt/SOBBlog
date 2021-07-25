package com.coder.zt.sobblog.ui.fragment

import android.view.View
import androidx.core.util.Pair
import androidx.lifecycle.ViewModelProvider
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.FragmentMineBinding
import com.coder.zt.sobblog.model.datamanager.UserDataMan
import com.coder.zt.sobblog.ui.adapter.MineMenuAdapter
import com.coder.zt.sobblog.ui.base.BaseFragment
import com.coder.zt.sobblog.utils.AppRouter
import com.coder.zt.sobblog.viewmodel.UserViewModel
class MineFragment:BaseFragment<FragmentMineBinding>() {

    private val userViewModel: UserViewModel by lazy{
        ViewModelProvider(this).get(UserViewModel::class.java)

    }

    private var initial = false
    companion object{
        private const val TAG = "MineFragment"
        private val menuList = listOf<Pair<Int, String>>(
            Pair(R.mipmap.icon_content,"内容管理"),
            Pair(R.mipmap.icon_interact,"互动管理"),
            Pair(R.mipmap.icon_setting,"设置"))
    }

    override fun getLayoutId():Int{
        initial = true
        return R.layout.fragment_mine
    }


    private val adapter = MineMenuAdapter(menuList) {
        when (it) {
            "设置" -> AppRouter.toSettingActivity(requireActivity())

        }
    }

    override fun initView() {
            dataBinding.rvMiniMenu.adapter = adapter
    }

    override fun initData() {

        userViewModel.achievement.observe(this){
            dataBinding.cdcChangeData.visibility = View.VISIBLE
            dataBinding.cdcChangeData.setData(it)
        }
        userViewModel.interactInfo.observe(this){
            adapter.setInteractInfo(it)
        }
    }



    fun setData() {
        if (initial) {
            dataBinding.data = UserDataMan.getUserInfo()
            if(UserDataMan.isLogin()){
                userViewModel.getAchievement()
                userViewModel.getInteractInfo()
            }else{
                dataBinding.cdcChangeData.visibility = View.GONE
            }
        }
    }


}

