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
        private val menuList = listOf(
            MineMenuAdapter.MenuItemData(1, "内容管理", R.mipmap.icon_content,0, MineMenuAdapter.ShowType.First,true,false),
            MineMenuAdapter.MenuItemData(11, "问题列表", R.mipmap.icon_interact, 0,MineMenuAdapter.ShowType.FirstSecond,false,false),
            MineMenuAdapter.MenuItemData(12, "文章管理", R.mipmap.icon_interact, 0,MineMenuAdapter.ShowType.FirstSecond,false,false),
            MineMenuAdapter.MenuItemData(13, "内容分享", R.mipmap.icon_interact, 0,MineMenuAdapter.ShowType.FirstSecond,false,false),
            MineMenuAdapter.MenuItemData(14, "我的收藏", R.mipmap.icon_interact, 0,MineMenuAdapter.ShowType.FirstSecond,false,false),
            MineMenuAdapter.MenuItemData(2, "互动管理", R.mipmap.icon_interact, 0,MineMenuAdapter.ShowType.First,true,false),
            MineMenuAdapter.MenuItemData(21, "回复我的", R.mipmap.icon_interact, 0,MineMenuAdapter.ShowType.FirstSecond,false,false),
            MineMenuAdapter.MenuItemData(22, "给我点赞", R.mipmap.icon_interact, 0,MineMenuAdapter.ShowType.FirstSecond,false,false),
            MineMenuAdapter.MenuItemData(23, "文章评论", R.mipmap.icon_interact, 0,MineMenuAdapter.ShowType.FirstSecond,false,false),
            MineMenuAdapter.MenuItemData(24, "动态评论", R.mipmap.icon_interact, 0,MineMenuAdapter.ShowType.FirstSecond,false,false),
            MineMenuAdapter.MenuItemData(25, "问题回答", R.mipmap.icon_interact, 0,MineMenuAdapter.ShowType.FirstSecond,false,false),
            MineMenuAdapter.MenuItemData(26, "系统通知", R.mipmap.icon_interact, 0,MineMenuAdapter.ShowType.FirstSecond,false,false),
            MineMenuAdapter.MenuItemData(3, "设置", R.mipmap.icon_setting,0, MineMenuAdapter.ShowType.First,true,false)
        )
    }

    override fun getLayoutId():Int{
        initial = true
        return R.layout.fragment_mine
    }


    private val adapter = MineMenuAdapter(menuList) {
        when (it.id) {
            3 -> AppRouter.toSettingActivity(requireActivity())

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

