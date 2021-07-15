package com.coder.zt.sobblog.ui.activity

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.PopupWindow
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.ActivityMoyuBinding
import com.coder.zt.sobblog.databinding.PopPullStyleBinding
import com.coder.zt.sobblog.ui.adapter.MoYuAdapter
import com.coder.zt.sobblog.ui.base.BaseActivity
import com.coder.zt.sobblog.ui.view.RefreshView
import com.coder.zt.sobblog.utils.*
import com.coder.zt.sobblog.viewmodel.MoYuViewModel
import com.google.gson.Gson
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia

class MoYuActivity:BaseActivity<ActivityMoyuBinding>() {


    companion object{
        private const val TAG = "MoYuActivity"
    }

    lateinit var commentIdTemp:String



    private val moyuViewModel: MoYuViewModel by lazy {
        ViewModelProvider(this).get(MoYuViewModel::class.java)
    }

    val adapter by lazy {
        MoYuAdapter(){ doType: MoYuAdapter.DO_TYPE, any: Any ->
            when(doType){
                MoYuAdapter.DO_TYPE.THUMB_UP ->{
                    moyuViewModel.thumbUP(any as String)
                }
                MoYuAdapter.DO_TYPE.COMMENT ->{
                    commentMinifeed(any as String)
                }
                MoYuAdapter.DO_TYPE.REPLY ->{
                    moyuViewModel.thumbUP(any as String)
                }
                MoYuAdapter.DO_TYPE.GET_COMMENT ->{//获取该动态的评论
                    moyuViewModel.getMiniFeedComment(any as String)
                }
            }

        }
    }

    /**
     * 评论动态
     */
    private fun commentMinifeed(minifeedId: String) {
        commentIdTemp = minifeedId
        dataBinding.commentBarLl.visibility = View.VISIBLE
        dataBinding.commentInputEt.requestFocus()
        val imm =  getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(dataBinding.commentInputEt, InputMethodManager.SHOW_IMPLICIT)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidBug5497Workaround.assistActivity(this)
        initView()
        initData()
    }

    private fun initView() {
        moyuViewModel.slideDistance.value = 0
        dataBinding.rvMoyu.adapter = adapter
        dataBinding.apply {
            this.pullView.setPullDownListener {
                val distance = this.loadingView.setDistance(it.toFloat())
                distance
            }
            pullView.setOnRefreshListener(object: RefreshView.OnRefreshListener{
                override fun onRefresh() {
                    pullView.postDelayed({
                        loadingView.loadingFinished()
                        pullView.refreshFinished()
                    },1000)
                }

                override fun onLoading() {
                    pullView.postDelayed({
                        pullView.loadedFinished()
                    },10000)
                }

            })
            pullView.setContentSlideListener {
                Log.d(TAG, "initView: it ---> $it")
                if(it != 0){
                    val imm =  getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    val v = window.peekDecorView()
                    if (null != v) {
                        imm.hideSoftInputFromWindow(v.windowToken, 0)
                    }
                    dataBinding.commentBarLl.visibility = View.GONE
                    commentIdTemp = ""
                    moyuViewModel?.comment?.postValue("")
                }
                Log.d(TAG, "initView: $it")
                var value = moyuViewModel?.slideDistance?.value
                if (value != null) {
                    value += it
                    moyuViewModel?.slideDistance?.value = value
                }
            }
        }
        dataBinding.commentSendTv.setOnClickListener {
            val content = moyuViewModel.comment.value
            if (!content.isNullOrBlank() && !commentIdTemp.isEmpty()) {
                moyuViewModel.sendCommend(content!!, commentIdTemp)
            }
        }
        dataBinding.ivCamera.setOnClickListener{
            //选择发布动态形式
            showPullStyle()
        }
        dataBinding.ivCamera.setOnLongClickListener {
            //直接跳转到编辑动态页面
            AppRouter.toEditMinifeedActivity(this)
            true
        }
    }

    private fun showPullStyle() {
        Log.d(TAG, "showPullStyle: 显示pop")
        val pop = PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        val popBind = DataBindingUtil.inflate<PopPullStyleBinding>(LayoutInflater.from(this),
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
                .maxSelectNum(4)
                .imageEngine(GlideEngine.createGlideEngine()) // 请参考Demo GlideEngine.java
                .forResult(PictureConfig.CHOOSE_REQUEST)
        }
        pop.setOnDismissListener {
            ScreenUtils.resortWindowBackground(this)
        }
        ScreenUtils.setWindowBackground(this, 0.3f)
        pop.showAtLocation(dataBinding.pullView, Gravity.BOTTOM, 0 ,0 )
    }

    private fun initData() {
        dataBinding.moyuViewModel = moyuViewModel
        moyuViewModel.moyuDisplayData.observe(this){
            Log.d(TAG, "initData: ${Gson().toJson(it)}")
            adapter.setData(it)
        }
        moyuViewModel.slideDistance.observe(this){
            val height = ScreenUtils.dp2px(320)
            var a = 0
            if(1.0f * it/height < 1){
               a =  (1.0f * it/height * 255).toInt()
            }else{
                a =  255
            }
            dataBinding.topBarRl.setBackgroundColor(Color.argb(a,242,242,242))
            dataBinding.titleTv.setTextColor(Color.argb(a,0,0,0))
        }
        moyuViewModel.comment.observe(this){
            if(it.isNotEmpty()){
                dataBinding.commentSendTv.setBackgroundResource(R.drawable.send_btn_active_bg)
                dataBinding.commentSendTv.setTextColor(Color.WHITE)
            }else{
                dataBinding.commentSendTv.setBackgroundResource(R.drawable.send_btn_normal_bg)
                dataBinding.commentSendTv.setTextColor(Color.GRAY)
            }
        }
        moyuViewModel.feedComment.observe(this){
            adapter.setComment(it)
        }
    }

    override fun onResume() {
        super.onResume()
        moyuViewModel.getRecommendMiniFeed(1)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_moyu
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                PictureConfig.REQUEST_CAMERA, PictureConfig.CHOOSE_REQUEST->{
                    val result:List<LocalMedia> = PictureSelector.obtainMultipleResult(data)
                    for (localMedia in result) {
                        Log.d(TAG, "相册：onActivityResult: ${localMedia.path}")

                    }
                    ImageSelectManager.putImages(result)
                    AppRouter.toEditMinifeedActivity(this)
                }
            }
        }
    }

}