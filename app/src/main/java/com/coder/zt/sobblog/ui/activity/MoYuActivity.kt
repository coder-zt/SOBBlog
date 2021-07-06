package com.coder.zt.sobblog.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.ActivityMoyuBinding
import com.coder.zt.sobblog.ui.adapter.MoYuAdapter
import com.coder.zt.sobblog.ui.base.BaseActivity
import com.coder.zt.sobblog.ui.view.RefreshView
import com.coder.zt.sobblog.utils.ScreenUtils
import com.coder.zt.sobblog.viewmodel.MoYuViewModel
import com.google.gson.Gson

class MoYuActivity:BaseActivity() {


    companion object{
        private const val TAG = "MoYuActivity"
    }
    

    val dataBinding: ActivityMoyuBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_moyu)
    }

    val moyuViewModel: MoYuViewModel by lazy {
        ViewModelProvider(this).get(MoYuViewModel::class.java)
    }

    val adapter by lazy {
        MoYuAdapter(){ doType: MoYuAdapter.DO_TYPE, any: Any ->
            when(doType){
                MoYuAdapter.DO_TYPE.THUMB_UP ->{
                    moyuViewModel.thumbUP(any as String)
                }
                MoYuAdapter.DO_TYPE.COMMENT ->{
                    moyuViewModel.thumbUP(any as String)
                }
                MoYuAdapter.DO_TYPE.REPLY ->{
                    moyuViewModel.thumbUP(any as String)
                }
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        moyuViewModel.slideDistance.value = 0
        dataBinding.rvMoyu.adapter = adapter
        dataBinding.apply {
            this.pullView.setPullDownListener {
                val distance = this.loadingView.setDistance(it.toFloat())
                distance
            }
            pullView.setOnRefreshListener(object: RefreshView.OnRefreshListener{
                override fun onRefresh() {
                    pullView.postDelayed({
                        loadingView.loadingFinished()
                        pullView.refreshFinished()
                    },1000)
                }

                override fun onLoading() {
                    pullView.postDelayed({
                        pullView.loadedFinished()
                    },10000)
                }

            })
            pullView.setContentSlideListener {
                adapter.checkChildrenState()
                Log.d(TAG, "initView: $it")
                var value = moyuViewModel.slideDistance.value
                if (value != null) {
                    value += it
                    moyuViewModel.slideDistance.value = value
                }
            }
        }
    }

    private fun initData() {
        moyuViewModel.moyuDisplayData.observe(this){
            Log.d(TAG, "initData: ${Gson().toJson(it)}")
            adapter.setData(it.data)
        }
        moyuViewModel.slideDistance.observe(this){
            val height = ScreenUtils.dp2px(320)
            var a = 0
            if(1.0f * it/height < 1){
               a =  (1.0f * it/height * 255).toInt()
            }else{
                a =  255
            }
            dataBinding.topBarRl.setBackgroundColor(Color.argb(a,242,242,242))
            dataBinding.titleTv.setTextColor(Color.argb(a,0,0,0))
        }
    }

    override fun onResume() {
        super.onResume()
        moyuViewModel.getRecommendMiniFeed(1)
    }
}