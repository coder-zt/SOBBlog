package com.coder.zt.sobblog.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.FragmentOtherBinding
import com.coder.zt.sobblog.ui.base.BaseFragment
import com.coder.zt.sobblog.utils.Constants

class OtherFragment: BaseFragment<FragmentOtherBinding>() {

    companion object{
        private const val TAG = "OtherFragment"
    }



    override fun onResume() {
        super.onResume()
        dataBinding.wvEgg.postDelayed ({
            setData()
        },1000)
    }

    private fun setData() {
        dataBinding.wvEgg.loadData("<div width=\"100%\" height=\"100%\" background-color=\"red\">\n" +
                "<p><font line-height=\"100px\" color=\"white\"> ${Constants.firstMsg}</font></p>\n" +
                "<p><font line-height=\"100px\" color=\"white\"> ${Constants.firstMsg}</font></p>\n" +
                "<p><font line-height=\"100px\" color=\"white\"> ${Constants.firstMsg}</font></p>\n" +
                "<p><font line-height=\"100px\" color=\"white\"> ${Constants.firstMsg}</font></p>\n" +
                "<p><font line-height=\"100px\" color=\"white\"> ${Constants.firstMsg}</font></p>\n" +
                "<p><font line-height=\"100px\" color=\"white\"> ${Constants.firstMsg}</font></p>\n" +
                "<p><font line-height=\"100px\" color=\"white\"> ${Constants.firstMsg}</font></p>\n" +
                "<p><font line-height=\"100px\" color=\"white\"> ${Constants.firstMsg}</font></p>\n" +
                "<p><font line-height=\"100px\" color=\"white\"> ${Constants.firstMsg}</font></p>\n" +
                "<p><font line-height=\"100px\" color=\"white\"> ${Constants.firstMsg}</font></p>\n" +
                "<p><font line-height=\"100px\" color=\"white\"> ${Constants.firstMsg}</font></p>\n" +
                "<p><font line-height=\"100px\" color=\"white\"> ${Constants.firstMsg}</font></p>\n" +
                "<p><font line-height=\"100px\" color=\"white\"> ${Constants.firstMsg}</font></p>\n" +
                "<p><font line-height=\"100px\" color=\"white\"> ${Constants.firstMsg}</font></p>\n" +
                "<p><font line-height=\"100px\" color=\"white\"> ${Constants.firstMsg}</font></p>\n" +
                "<p><font line-height=\"100px\" color=\"white\"> ${Constants.firstMsg}</font></p>\n" +
                "<p><font line-height=\"100px\" color=\"white\"> ${Constants.firstMsg}</font></p>\n" +
                "<p><font line-height=\"100px\" color=\"white\"> ${Constants.firstMsg}</font></p>\n" +
                "<p><font line-height=\"100px\" color=\"white\"> ${Constants.firstMsg}</font></p>\n" +
                "<p><font line-height=\"100px\" color=\"white\"> ${Constants.firstMsg}</font></p>\n" +
                "<p><font line-height=\"100px\" color=\"white\"> ${Constants.firstMsg}</font></p>\n" +
                "<p><font line-height=\"100px\" color=\"white\"> ${Constants.firstMsg}</font></p>\n" +
                "<p><font line-height=\"100px\" color=\"white\"> ${Constants.firstMsg}</font></p>\n" +
                "<p><font line-height=\"100px\" color=\"white\"> ${Constants.firstMsg}</font></p>\n" +
                "<p><font line-height=\"100px\" color=\"white\"> ${Constants.firstMsg}</font></p>\n" +
                "<p><font line-height=\"100px\" color=\"white\"> ${Constants.firstMsg}</font></p>\n" +
                "<p><font line-height=\"100px\" color=\"white\"> ${Constants.firstMsg}</font></p>\n" +
                "<p><font line-height=\"100px\" color=\"white\"> ${Constants.firstMsg}</font></p>\n" +
                "<p><font line-height=\"100px\" color=\"white\"> ${Constants.firstMsg}</font></p>\n" +
                "<p><font line-height=\"100px\" color=\"white\"> ${Constants.firstMsg}</font></p>\n" +
                "<p><font line-height=\"100px\" color=\"white\"> ${Constants.firstMsg}</font></p>\n" +
                "<p><font line-height=\"100px\" color=\"white\"> ${Constants.firstMsg}</font></p>\n" +
                "<p><font line-height=\"100px\" color=\"white\"> ${Constants.firstMsg}</font></p>\n" +
                "<p><font line-height=\"100px\" color=\"white\"> ${Constants.firstMsg}</font></p>\n" +
                "<p><font line-height=\"100px\" color=\"white\"> ${Constants.firstMsg}</font></p>\n" +
                "<p><font line-height=\"100px\" color=\"white\"> ${Constants.firstMsg}</font></p>\n" +
                "<p><font line-height=\"100px\" color=\"white\"> ${Constants.firstMsg}</font></p>\n" +
                "<p><font line-height=\"100px\" color=\"white\"> ${Constants.firstMsg}</font></p>\n" +
                "<p><font line-height=\"100px\" color=\"white\"> ${Constants.firstMsg}</font></p>\n" +
                "<p><font line-height=\"100px\" color=\"white\"> ${Constants.firstMsg}</font></p>\n" +
                "<p><font line-height=\"100px\" color=\"white\"> ${Constants.firstMsg}</font></p>\n" +
                "<p><font line-height=\"100px\" color=\"white\"> ${Constants.firstMsg}</font></p>\n" +
                "<p><font line-height=\"100px\" color=\"white\"> ${Constants.firstMsg}</font></p>\n" +
                "</div>", "text/html", "utf-8")
    }

    override fun initView() {

    }

    override fun initData() {

    }



    override fun getLayoutId(): Int {
        return R.layout.fragment_other
    }
}