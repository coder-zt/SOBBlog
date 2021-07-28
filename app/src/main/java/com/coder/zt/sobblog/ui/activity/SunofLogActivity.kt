package com.coder.zt.sobblog.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.ActivitySunofBinding
import com.coder.zt.sobblog.ui.adapter.ArticleInfoAdapter
import com.coder.zt.sobblog.ui.adapter.PopListAdapter
import com.coder.zt.sobblog.ui.adapter.SunofCoinAdapter
import com.coder.zt.sobblog.ui.base.BaseActivity
import com.coder.zt.sobblog.viewmodel.UserViewModel
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader

class SunofLogActivity:BaseActivity<ActivitySunofBinding>() {

    companion object{
        private const val TAG = "SunofLogActivity"
    }
    private var loadMore = false

    private val userViewModel: UserViewModel by lazy {
        ViewModelProvider(this).get(UserViewModel::class.java)
    }
    private val adapter:SunofCoinAdapter by lazy {
        SunofCoinAdapter()
    }

    override fun getLayoutId(): Int {
        Log.d(TAG, "getLayoutId: ")
       return R.layout.activity_sunof
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        dataBinding.rvContent.adapter = adapter
        dataBinding.srlContainer.setRefreshHeader(ClassicsHeader(this))
        dataBinding.srlContainer.setRefreshFooter(ClassicsFooter(this))
        dataBinding.srlContainer.setOnLoadMoreListener {
            loadMore = true
            loadData()

        }
        dataBinding.srlContainer.setOnRefreshListener {
            loadMore = false
            loadData()
        }
    }

    private fun initData() {
        userViewModel.sunofCoinInfo.observe(this){
            Log.d(TAG, "initData: 加载数据成功！ ${it.size}")
                adapter.setData(it,loadMore)
                if(loadMore){
                    dataBinding.srlContainer.finishLoadMore()
                }else{
                    dataBinding.srlContainer.finishRefresh()
                }
        }
    }

    override fun onResume() {
        super.onResume()
        loadMore = false
        loadData()
    }

    private fun loadData() {
        userViewModel.getSunofCoinInfo(loadMore)
    }

}
