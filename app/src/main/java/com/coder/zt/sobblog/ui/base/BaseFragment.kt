package com.coder.zt.sobblog.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<T: ViewDataBinding>:Fragment() {

    lateinit var dataBinding:T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate<T>(inflater, getLayoutId(), container, false)
        initData()
        return dataBinding.root
    }

    abstract fun initData()

    abstract fun getLayoutId():Int
}