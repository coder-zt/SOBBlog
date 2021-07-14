package com.coder.zt.sobblog.ui.activity

import android.content.Intent
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.ActivityEditMinifeedBinding
import com.coder.zt.sobblog.databinding.PopPullStyleBinding
import com.coder.zt.sobblog.databinding.PopRvTopicBinding
import com.coder.zt.sobblog.model.moyu.TopicItem
import com.coder.zt.sobblog.ui.adapter.AlbumPhotoAdapter
import com.coder.zt.sobblog.ui.adapter.PopListAdapter
import com.coder.zt.sobblog.ui.base.BaseActivity
import com.coder.zt.sobblog.utils.*
import com.coder.zt.sobblog.viewmodel.MoYuViewModel
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia

class EditMinifeedActivity:BaseActivity<ActivityEditMinifeedBinding>() {

    companion object{
        private const val TAG = "EditMinifeedActivity"
    }

    lateinit var topicData:List<TopicItem>

    private val viewModel:MoYuViewModel by lazy{
        ViewModelProvider(this).get(MoYuViewModel::class.java)
    }

    private val adapter: AlbumPhotoAdapter by lazy{
        AlbumPhotoAdapter(this)
    }
    override fun getLayoutId() = R.layout.activity_edit_minifeed

    override fun onResume() {
        super.onResume()
        initView()
        initData()
    }

    private fun initData() {
        viewModel.topicItem.observe(this){
            topicData = it
        }
        viewModel.getTopicItems()
    }

    private fun initView() {
        dataBinding.rvPicContainer.adapter = adapter
        adapter.setData(ImageSelectManager.getImages())
        adapter.setSelectListener {
            showPullStyle(it)
        }
        dataBinding.tvTopic.setOnClickListener {
            if (topicData.isEmpty()) {
                ToastUtils.showError("获取话题失败，重新获取中")
            }else{
                showTopicList(topicData)
            }
        }
    }

    private fun showTopicList(it:List<TopicItem>) {
    PopWindowUtils.showListData(R.layout.pop_rv_topic,it,this,
        object:PopListAdapter.ItemsListSetData<PopRvTopicBinding,TopicItem>{
            override fun setData(inflate: PopRvTopicBinding, d: TopicItem) {
                inflate.data = d
            }
        })

    }

    private fun showPullStyle(size:Int) {
        val pop = PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val popBind = DataBindingUtil.inflate<PopPullStyleBinding>(
            LayoutInflater.from(this),
            R.layout.pop_pull_style, null ,false)
        pop.isOutsideTouchable = true
        pop.isFocusable = true
        pop.contentView = popBind.root
        popBind.tvCancel.setOnClickListener {
            pop.dismiss()
        }
        popBind.tvCamera.setOnClickListener {
            pop.dismiss()
            PictureSelector.create(this)
                .openCamera(PictureMimeType.ofImage())
                .imageEngine(GlideEngine.createGlideEngine()) // 请参考Demo GlideEngine.java
                .forResult(PictureConfig.REQUEST_CAMERA)
        }
        popBind.tvAlbum.setOnClickListener {
            pop.dismiss()
            PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .maxSelectNum(size)
                .imageEngine(GlideEngine.createGlideEngine()) // 请参考Demo GlideEngine.java
                .forResult(PictureConfig.CHOOSE_REQUEST)
        }
        pop.setOnDismissListener {
            ScreenUtils.resortWindowBackground(this)
        }
        ScreenUtils.setWindowBackground(this, 0.3f)
        pop.showAtLocation(dataBinding.root, Gravity.BOTTOM, 0 ,0 )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                PictureConfig.REQUEST_CAMERA, PictureConfig.CHOOSE_REQUEST->{
                    val result:List<LocalMedia> = PictureSelector.obtainMultipleResult(data)
                    ImageSelectManager.putImage(result)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ImageSelectManager.clear()
    }
}