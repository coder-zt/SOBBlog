package com.coder.zt.sobblog.ui.adapter

import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class HomePagerAdapter(fm: FragmentManager, private val fragments:List<Fragment>):FragmentPagerAdapter(fm) {


    companion object{
        private const val TAG = "HomePagerAdapter"
    }

    override fun getCount(): Int = 4
    
    override fun getItem(position: Int): Fragment{
        Log.d(TAG, "getItem: $position")
        return fragments[position]
    }


}