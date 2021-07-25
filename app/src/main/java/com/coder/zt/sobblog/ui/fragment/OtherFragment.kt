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

class OtherFragment: BaseFragment<FragmentOtherBinding>() {

    companion object{
        private const val TAG = "OtherFragment"
    }



    override fun onResume() {
        super.onResume()
    }

    override fun initView() {
       dataBinding.wvEgg.loadData("<div width=\"100%\" height=\"100%\" background-color=\"red\">\n" +
               "<p><font line-height=\"100px\" color=\"white\"> this is a color egg</font></p>\n" +
               "<p><font line-height=\"100px\" color=\"white\"> this is a color egg</font></p>\n" +
               "<p><font line-height=\"100px\" color=\"white\"> this is a color egg</font></p>\n" +
               "<p><font line-height=\"100px\" color=\"white\"> this is a color egg</font></p>\n" +
               "<p><font line-height=\"100px\" color=\"white\"> this is a color egg</font></p>\n" +
               "<p><font line-height=\"100px\" color=\"white\"> this is a color egg</font></p>\n" +
               "<p><font line-height=\"100px\" color=\"white\"> this is a color egg</font></p>\n" +
               "<p><font line-height=\"100px\" color=\"white\"> this is a color egg</font></p>\n" +
               "<p><font line-height=\"100px\" color=\"white\"> this is a color egg</font></p>\n" +
               "<p><font line-height=\"100px\" color=\"white\"> this is a color egg</font></p>\n" +
               "<p><font line-height=\"100px\" color=\"white\"> this is a color egg</font></p>\n" +
               "<p><font line-height=\"100px\" color=\"white\"> this is a color egg</font></p>\n" +
               "<p><font line-height=\"100px\" color=\"white\"> this is a color egg</font></p>\n" +
               "<p><font line-height=\"100px\" color=\"white\"> this is a color egg</font></p>\n" +
               "<p><font line-height=\"100px\" color=\"white\"> this is a color egg</font></p>\n" +
               "<p><font line-height=\"100px\" color=\"red\"> this is a color egg</font></p>\n" +
               "<p><font line-height=\"100px\" color=\"white\"> this is a color egg</font></p>\n" +
               "<p><font line-height=\"100px\" color=\"white\"> this is a color egg</font></p>\n" +
               "<p><font line-height=\"100px\" color=\"white\"> this is a color egg</font></p>\n" +
               "<p><font line-height=\"100px\" color=\"white\"> this is a color egg</font></p>\n" +
               "<p><font line-height=\"100px\" color=\"white\"> this is a color egg</font></p>\n" +
               "<p><font line-height=\"100px\" color=\"white\"> this is a color egg</font></p>\n" +
               "<p><font line-height=\"100px\" color=\"white\"> this is a color egg</font></p>\n" +
               "<p><font line-height=\"100px\" color=\"white\"> this is a color egg</font></p>\n" +
               "<p><font line-height=\"100px\" color=\"white\"> this is a color egg</font></p>\n" +
               "<p><font line-height=\"100px\" color=\"white\"> this is a color egg</font></p>\n" +
               "<p><font line-height=\"100px\" color=\"white\"> this is a color egg</font></p>\n" +
               "<p><font line-height=\"100px\" color=\"white\"> this is a color egg</font></p>\n" +
               "<p><font line-height=\"100px\" color=\"white\"> this is a color egg</font></p>\n" +
               "<p><font line-height=\"100px\" color=\"white\"> this is a color egg</font></p>\n" +
               "<p><font line-height=\"100px\" color=\"white\"> this is a color egg</font></p>\n" +
               "<p><font line-height=\"100px\" color=\"white\"> this is a color egg</font></p>\n" +
               "<p><font line-height=\"100px\" color=\"white\"> this is a color egg</font></p>\n" +
               "<p><font line-height=\"100px\" color=\"white\"> this is a color egg</font></p>\n" +
               "<p><font line-height=\"100px\" color=\"white\"> this is a color egg</font></p>\n" +
               "<p><font line-height=\"100px\" color=\"white\"> this is a color egg</font></p>\n" +
               "<p><font line-height=\"100px\" color=\"white\"> this is a color egg</font></p>\n" +
               "<p><font line-height=\"100px\" color=\"white\"> this is a color egg</font></p>\n" +
               "<p><font line-height=\"100px\" color=\"white\"> this is a color egg</font></p>\n" +
               "<p><font line-height=\"100px\" color=\"white\"> this is a color egg</font></p>\n" +
               "<p><font line-height=\"100px\" color=\"white\"> this is a color egg</font></p>\n" +
               "<p><font line-height=\"100px\" color=\"white\"> this is a color egg</font></p>\n" +
               "<p><font line-height=\"100px\" color=\"white\"> this is a color egg</font></p>\n" +
               "<p><font line-height=\"100px\" color=\"white\"> this is a color egg</font></p>\n" +
               "</div>", "text/html", "utf-8")
    }

    override fun initData() {

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_other
    }
}