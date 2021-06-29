package com.coder.zt.sobblog.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.FragmentMoyuBinding
import com.coder.zt.sobblog.ui.adapter.MoYuAdapter
import com.coder.zt.sobblog.viewmodel.MoYuViewModel

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
        dataBinding?.scrollView?.doOnPreDraw {
            Log.d(TAG, "initView: do on preDraw")
        }
    }

    private fun initData() {
        activity?.let {
            moyuViewModel.moyuList.observe(it){
                Log.d(TAG, "initData: ${it.code}")
                adapter.setData(it.data.list)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        moyuViewModel.getRecommendMiniFeed(1)
    }
}