package com.coder.zt.sobblog.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.umeng.analytics.MobclickAgent

abstract class BaseFragment<T: ViewDataBinding>:Fragment() {

    lateinit var dataBinding:T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        initView()
        initData()
        return dataBinding.root
    }

    abstract fun initView()

    abstract fun initData()

    abstract fun getLayoutId():Int

    override fun onResume() {
        super.onResume()
        MobclickAgent.onResume(activity)
    }

    override fun onPause() {
        super.onPause()
        MobclickAgent.onPause(activity)
    }
}