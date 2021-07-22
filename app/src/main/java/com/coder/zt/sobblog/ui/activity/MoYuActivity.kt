package com.coder.zt.sobblog.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.ActivityMoyuBinding
import com.coder.zt.sobblog.databinding.ActivityMoyuBindingImpl
import com.coder.zt.sobblog.model.datamanager.UserDataMan
import com.coder.zt.sobblog.model.moyu.MYReplySender
import com.coder.zt.sobblog.ui.adapter.MYCommentAdapter
import com.coder.zt.sobblog.ui.adapter.MoYuAdapter
import com.coder.zt.sobblog.ui.adapter.SlideLayoutManager
import com.coder.zt.sobblog.ui.base.BaseActivity
import com.coder.zt.sobblog.ui.view.RefreshView
import com.coder.zt.sobblog.utils.*
import com.coder.zt.sobblog.viewmodel.MoYuViewModel
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.entity.LocalMedia

class MoYuActivity:BaseActivity<ActivityMoyuBinding>() {


    companion object{
        private const val TAG = "MoYuActivity"
    }

    //动态的ID
    lateinit var minifeedIdTemp:String
    //回复评论的ID
    private var commentIdTemp:String? = null
    //被评论用户的ID
    private var targetUserId:String? = null

    private var loadMore:Boolean = false



    private val viewModel: MoYuViewModel by lazy {
        ViewModelProvider(this).get(MoYuViewModel::class.java)
    }

    val adapter by lazy {
        MoYuAdapter(){ doType: MoYuAdapter.DO_TYPE, any: Any ->
            when(doType){
                MoYuAdapter.DO_TYPE.THUMB_UP ->{//点赞
                    if(UserDataMan.checkUserLoginState(this, getString(R.string.check_login_thumb_up_tips))) {
                        viewModel.thumbUP(any as String)
                    }
                }
                MoYuAdapter.DO_TYPE.COMMENT ->{//评论动态
                    if(UserDataMan.checkUserLoginState(this, getString(R.string.check_login_comment_tips))){
                        minifeedIdTemp = any as String//动态id
                        targetUserId = ""//被评论者的ID
                        commentIdTemp = ""//评论ID
                        showCommentInput("")
                    }
                }
                MoYuAdapter.DO_TYPE.REPLY ->{//回复评论
                    if(UserDataMan.checkUserLoginState(this, getString(R.string.check_login_comment_tips))) {
                        UserDataMan.getUserInfo()!!.let{
                            val comment = any as MYCommentAdapter.CommentDataBean
                            minifeedIdTemp = comment.momentId//动态ID
                            targetUserId = comment.fromId//被评论者的ID
                            commentIdTemp = comment.commentId//评论ID
                            showCommentInput(comment.from)
                        }
                    }

                }
                MoYuAdapter.DO_TYPE.GET_COMMENT ->{//获取该动态的评论
                    viewModel.getMiniFeedComment(any as String)
                }
            }

        }
    }

    /**
     * 显示评论输入窗口
     */
    private fun showCommentInput(toName: String) {
        dataBinding.commentBarLl.visibility = View.VISIBLE
        dataBinding.commentInputEt.requestFocus()
        if (toName.isNotEmpty()) {
            dataBinding.commentInputEt.hint = "@$toName:"
        }else{
            dataBinding.commentInputEt.setHint(R.string.str_comment_hint)
        }
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
        viewModel.slideDistance.value = 0
        dataBinding.rvMoyu.adapter = adapter
        dataBinding.rvMoyu.layoutManager = LinearLayoutManager(this)
        dataBinding.apply {
            this.refreshView.setPullDownListener {
                val distance = this.loadingView.setDistance(it.toFloat())
                distance
            }
            refreshView.setOnRefreshListener(object: RefreshView.OnRefreshListener{
                override fun onRefresh() {
                    loadMore = false
                    viewModel.getRecommendMiniFeed(loadMore)
                }

                override fun onLoading() {
                    loadMore = true
                    viewModel.getRecommendMiniFeed(loadMore)
                }

            })
            refreshView.setContentSlideListener {
                if(it != viewModel?.slideDistance?.value){
                    closeCommentInput()
                }
                viewModel?.slideDistance?.value = it
            }
        }
        dataBinding.commentSendTv.setOnClickListener {
            val content = viewModel.comment.value
            if (content.isNullOrEmpty()) {
                ToastUtils.showError(getString(R.string.comment_empty_tips))
                return@setOnClickListener
            }
            //发送评论
            if (targetUserId.isNullOrEmpty()) {
                if (!content.isNullOrBlank() && !minifeedIdTemp.isEmpty()) {
                    viewModel.sendComment(content, minifeedIdTemp)
                }
            }else{//回复评论
                if (!content.isNullOrBlank() && !minifeedIdTemp.isEmpty()) {
                    viewModel.sendReply(MYReplySender(commentIdTemp!!,content,minifeedIdTemp,targetUserId!!))
                }
            }
            closeCommentInput()
        }
        dataBinding.ivCamera.setOnClickListener{
            if(UserDataMan.checkUserLoginState(this, getString(R.string.check_login_publish_tips))){
                //选择发布动态形式
                showPullStyle()
            }

        }
        dataBinding.ivCamera.setOnLongClickListener {
            if(UserDataMan.checkUserLoginState(this, getString(R.string.check_login_publish_tips))){
                //直接跳转到编辑动态页面
                AppRouter.toEditMinifeedActivity(this)
            }
            true
        }
    }

    private fun closeCommentInput() {
        dataBinding.commentInputEt.text.clear()
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        val v = window.peekDecorView()
        if (null != v) {
            imm.hideSoftInputFromWindow(v.windowToken, 0)
        }
        minifeedIdTemp = ""//动态ID
        targetUserId = ""//被评论者的ID
        commentIdTemp = ""//评论ID
        dataBinding.commentBarLl.visibility = View.GONE
    }

    private fun showPullStyle() {
        PopWindowUtils.showTakePictureStyle(4,this,dataBinding.ivCamera)
    }

    private fun initData() {
        dataBinding.data = viewModel
        viewModel.moyuDisplayData.observe(this){
            if (loadMore) {//加载结束
                Log.d(TAG, "initData: 获取数据添加")
                dataBinding.refreshView.postDelayed({
                    adapter.addData(it)
                },100)

                dataBinding.refreshView.loadedFinished()

            }else{//刷新结束
                dataBinding.refreshView.refreshFinished()
                dataBinding.loadingView.loadingFinished()
                adapter.setData(it)
            }
        }
        viewModel.slideDistance.observe(this){
            val height = ScreenUtils.dp2px(320)
            var a = 0
            Log.d(TAG, "initData: $it")
            if(1.0f * it/height < 1){
               a =  (1.0f * it/height * 255).toInt()
            }else{
                a =  255
            }
            dataBinding.topBarRl.setBackgroundColor(Color.argb(a,242,242,242))
            dataBinding.titleTv.setTextColor(Color.argb(a,0,0,0))
            if(a>127){
                dataBinding.ivBack.setImageResource(R.mipmap.ic_back_dark)
            }else{
                dataBinding.ivBack.setImageResource(R.mipmap.back_white)
            }
        }
        viewModel.comment.observe(this){
            if(it.isNotEmpty()){
                dataBinding.commentSendTv.setBackgroundResource(R.drawable.send_btn_active_bg)
                dataBinding.commentSendTv.setTextColor(Color.WHITE)
            }else{
                dataBinding.commentSendTv.setBackgroundResource(R.drawable.send_btn_normal_bg)
                dataBinding.commentSendTv.setTextColor(Color.GRAY)
            }
        }
        viewModel.feedComment.observe(this){
            adapter.setComment(it)
        }
        viewModel.changeItemId.observe(this){
            when(it.second){
                MoYuAdapter.DO_TYPE.THUMB_UP ->{//点赞
                    Log.d(TAG, "initData: 点赞后更新数据")
                    adapter.updateThumbUp(it.first)
                }
                MoYuAdapter.DO_TYPE.COMMENT ->{//评论动态

                }
                MoYuAdapter.DO_TYPE.REPLY ->{//回复评论

                }
            }
        }
        viewModel.bingWarpUrl.observe(this){
            adapter.setTopWarpUrl(it)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getRecommendMiniFeed(loadMore)
        viewModel.getWarpUrl()
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
                    ImageSelectManager.putImages(result)
                    AppRouter.toEditMinifeedActivity(this)
                }
            }
        }
    }

}