package com.coder.zt.sobblog.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.PagerSnapHelper
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.ActivityPictureBrowseBinding
import com.coder.zt.sobblog.ui.adapter.PictureBrowseAdapter
import com.coder.zt.sobblog.ui.base.BaseActivity
import com.coder.zt.sobblog.ui.view.listener.CustomPagerSnapHelper
import com.coder.zt.sobblog.ui.view.listener.RVPageChangeListener
import com.coder.zt.sobblog.utils.AppRouter
import com.coder.zt.sobblog.utils.ToastUtils

class PictureBrowseActivity:BaseActivity<ActivityPictureBrowseBinding>(){

    companion object{
        private const val TAG = "PictureBrowseActivity"
    }
    private  var picUrls:List<String>? = null
    private var position = 0
    override fun getLayoutId() = R.layout.activity_picture_browse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getIntentData()
        initView()
    }

    private fun getIntentData() {
        if (intent.getStringArrayListExtra(AppRouter.param_data) == null) {
            ToastUtils.showError("找不到图片数据！")
            finish()
            return
        }else{
            picUrls = intent.getStringArrayListExtra(AppRouter.param_data)!!
            position = intent.getIntExtra(AppRouter.param_Position, 0)
        }
    }

    private fun initView() {
        val snapHelper = CustomPagerSnapHelper(){
            Log.d(TAG, "initView: snapHelper $it")
        }
        picUrls?.let {
            dataBinding.rvPicContainer.adapter = PictureBrowseAdapter(it)
            dataBinding.rvPicContainer.scrollToPosition(position)
            snapHelper.attachToRecyclerView(dataBinding.rvPicContainer)

        }
        dataBinding.rvPicContainer.addOnScrollListener(RVPageChangeListener(snapHelper){
            Log.d(TAG, "initView:RVPageChangeListener  $it")
        })
       
    }

}