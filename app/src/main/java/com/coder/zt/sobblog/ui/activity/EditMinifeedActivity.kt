package com.coder.zt.sobblog.ui.activity

import android.util.Log
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.ActivityEditMinifeedBinding
import com.coder.zt.sobblog.ui.base.BaseActivity
import com.coder.zt.sobblog.utils.ImageSelectManager

class EditMinifeedActivity:BaseActivity<ActivityEditMinifeedBinding>() {

    companion object{
        private const val TAG = "EditMinifeedActivity"
    }
    override fun getLayoutId() = R.layout.activity_edit_minifeed

    override fun onResume() {
        super.onResume()
        for (image in ImageSelectManager.getImages()) {
            Log.d(TAG, "onResume: ${image.path}")
        }
    }
}