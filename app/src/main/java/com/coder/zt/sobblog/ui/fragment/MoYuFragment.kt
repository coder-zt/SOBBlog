package com.coder.zt.sobblog.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.FragmentMoyuBinding
import com.coder.zt.sobblog.ui.adapter.MoYuAdapter
import com.coder.zt.sobblog.ui.view.RefreshView
import com.coder.zt.sobblog.viewmodel.MoYuViewModel
import com.google.gson.Gson

class MoYuFragment:Fragment() {

    companion object{
        private const val TAG = "MoYuFragment"
    }

    val moyuViewModel: MoYuViewModel by lazy {
        ViewModelProvider(this).get(MoYuViewModel::class.java)
    }

    val adapter by lazy {
        MoYuAdapter()
    }


    
    var dataBinding:FragmentMoyuBinding? = null
    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView: ")
        val view = inflater.inflate(R.layout.fragment_moyu, container, false)
        dataBinding = DataBindingUtil.bind<FragmentMoyuBinding>(view)
        initView()
        initData()
        return view
    }

    private fun initView() {
        if (dataBinding != null) {
            dataBinding?.rvMoyu?.adapter = adapter
        }
        dataBinding?.apply {
            this.pullView.setPullDownListener {
                val distance = this.loadingView.setDistance(it.toFloat())
                distance
            }
            pullView.setOnRefreshListener(object:RefreshView.OnRefreshListener{
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
            }
        }
    }

    private fun initData() {
        activity?.let { context ->
            moyuViewModel.moyuDisplayData.observe(context){
                Log.d(TAG, "initData: ${Gson().toJson(it)}")
                adapter.setData(it.data)

            }
        }


    }

    override fun onResume() {
        super.onResume()
        moyuViewModel.getRecommendMiniFeed(1)
    }
}
