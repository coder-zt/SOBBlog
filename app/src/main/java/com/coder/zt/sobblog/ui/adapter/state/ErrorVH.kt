package com.coder.zt.sobblog.ui.adapter.state

import androidx.recyclerview.widget.RecyclerView
import com.coder.zt.sobblog.databinding.ItemEmptyBinding
import com.coder.zt.sobblog.databinding.ItemErrorBinding
import com.coder.zt.sobblog.databinding.ItemLoadingBinding
import com.coder.zt.sobblog.ui.view.click

class ErrorVH(private val viewBinding:ItemErrorBinding):RecyclerView.ViewHolder(viewBinding.root) {


    fun setOnTryListener(onTry:()->Unit){
        viewBinding.btnTry.click { onTry() }
    }

}