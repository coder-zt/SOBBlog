package com.coder.zt.sobblog.ui.activity

import android.content.Intent
import android.media.Image
import android.os.Bundle
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
import com.coder.zt.sobblog.model.moyu.MinifeedSender
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }

    private fun initData() {
        viewModel.topicItem.observe(this){
            topicData = it
        }
        viewModel.getTopicItems()
        val images = ImageSelectManager.getImages()
        for (image in images) {
            viewModel.uploadImage(image)
        }
    }

    private fun initView() {
        dataBinding.rvPicContainer.adapter = adapter
        adapter.setData(ImageSelectManager.getImages())
        adapter.setSelectListener {
            PopWindowUtils.showTakePictureStyle(it, this, dataBinding.root)
        }
        dataBinding.tvTopic.setOnClickListener {
            if (topicData.isEmpty()) {
                ToastUtils.showError("获取话题失败，重新获取中")
            }else{
                showTopicList(topicData)
            }
        }
        dataBinding.tvTopicDelete.setOnClickListener {
            dataBinding.topicItem = null
            dataBinding.llTopicContainer.visibility = View.GONE
        }
        dataBinding.tvPublish.setOnClickListener {
            val imageUrls = ImageSelectManager.getImagesUrl()
            imageUrls?.let {
                    viewModel.publishMinifeed(
                        MinifeedSender(dataBinding.content,imageUrls,dataBinding.topicItem.id))
                }
        }
    }

    private fun showTopicList(it:List<TopicItem>) {
    PopWindowUtils.showListData(R.layout.pop_rv_topic,it,this,
        object:PopListAdapter.ItemsListSetData<PopRvTopicBinding,TopicItem>{
            override fun setData(inflate: PopRvTopicBinding, d: TopicItem) {
                inflate.data = d
            }

            override fun onClick(d: TopicItem) {
                Log.d(TAG, "onClick: 选择话题为%${d.topicName}")
                dataBinding.topicItem = d
                dataBinding.llTopicContainer.visibility = View.VISIBLE
            }
        })

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                PictureConfig.REQUEST_CAMERA, PictureConfig.CHOOSE_REQUEST->{
                    val result:List<LocalMedia> = PictureSelector.obtainMultipleResult(data)
                    for (localMedia in ImageSelectManager.putImage(result)) {
                        viewModel.uploadImage(localMedia)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ImageSelectManager.clear()
    }
}