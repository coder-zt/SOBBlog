package com.coder.zt.sobblog.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.coder.zt.sobblog.R

class OtherFragment: Fragment() {

    companion object{
        private const val TAG = "OtherFragment"
    }


    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView: ")

        val view = inflater.inflate(R.layout.fragment_other, container, false)
        initData()
        return view
    }

    private fun initData() {
    }

    override fun onResume() {
        super.onResume()
    }
}