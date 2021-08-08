package com.coder.zt.sobblog.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.ActivityInteractBinding
import com.coder.zt.sobblog.model.user.MomentMessage
import com.coder.zt.sobblog.model.user.ReplyMessage
import com.coder.zt.sobblog.model.user.SystemMessage
import com.coder.zt.sobblog.model.user.ThumbUpMessage
import com.coder.zt.sobblog.ui.adapter.InteractMsgAdapter
import com.coder.zt.sobblog.ui.base.BaseActivity
import com.coder.zt.sobblog.utils.AppRouter
import com.coder.zt.sobblog.utils.Constants
import com.coder.zt.sobblog.utils.ToastUtils
import com.coder.zt.sobblog.viewmodel.UserViewModel
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader

class InteractMessageActivity:BaseActivity<ActivityInteractBinding>() {

    companion object{
        private const val TAG = "InteractMessageActivity"
    }
    private var loadMore = false
    private val userViewModel:UserViewModel by lazy {
        ViewModelProvider(this).get(UserViewModel::class.java)
    }
    private val replyAdapter:InteractMsgAdapter<ReplyMessage> = InteractMsgAdapter(Constants.InteractType.typeReply, this)
    private val thumbUpAdapter:InteractMsgAdapter<ThumbUpMessage> = InteractMsgAdapter(Constants.InteractType.typeThumbUp, this)
    private val momentCommentAdapter:InteractMsgAdapter<MomentMessage> = InteractMsgAdapter(Constants.InteractType.typeMontentComment, this)
    private val systemAdapter:InteractMsgAdapter<SystemMessage> = InteractMsgAdapter(Constants.InteractType.typeSyetem, this)

    override fun getLayoutId() = R.layout.activity_interact
    private var interactType = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        initView()
    }

    private fun initData() {
        interactType = intent.getIntExtra(AppRouter.param_type,-1)
        if(interactType == -1){
            ToastUtils.showError("获取信息类型失败！")
            finish()
        }
    }


    private fun initView() {
        var typeName = "互动管理"
        when(interactType){
            Constants.InteractType.typeSyetem->{
                typeName = "系统通知"
                dataBinding.rvContent.adapter = systemAdapter
                userViewModel.systemMessage.observe(this){
                    systemAdapter.setData(it,loadMore)
                    if(loadMore){
                        dataBinding.srlContainer.finishLoadMore()
                    }else{
                        dataBinding.srlContainer.finishRefresh()
                    }
                }
            }
            Constants.InteractType.typeThumbUp->{
                typeName = "给朕点赞"
                dataBinding.rvContent.adapter = thumbUpAdapter
                userViewModel.thumbUpMessage.observe(this){
                    thumbUpAdapter.setData(it,loadMore)
                    if(loadMore){
                        dataBinding.srlContainer.finishLoadMore()
                    }else{
                        dataBinding.srlContainer.finishRefresh()
                    }
                }
            }
            Constants.InteractType.typeMontentComment->{
                typeName = "动态评论"
                momentCommentAdapter.setMessageOnClickListener {
                    userViewModel.updateMomentMessageState(it)
                }
                dataBinding.rvContent.adapter = momentCommentAdapter
                userViewModel.momentMessage.observe(this){
                    Log.d(TAG, "initView: $typeName")
                    momentCommentAdapter.setData(it,loadMore)
                    if(loadMore){
                        dataBinding.srlContainer.finishLoadMore()
                    }else{
                        dataBinding.srlContainer.finishRefresh()
                    }
                }
            }
            Constants.InteractType.typeReply->{
                typeName = "@朕的"
                replyAdapter.setMessageOnClickListener {
                    userViewModel.updateReplyMessageState(it)
                }
                dataBinding.rvContent.adapter = replyAdapter
                userViewModel.replyMessage.observe(this){
                    replyAdapter.setData(it,loadMore)
                    if(loadMore){
                        dataBinding.srlContainer.finishLoadMore()
                    }else{
                        dataBinding.srlContainer.finishRefresh()
                    }
                }
            }
        }
        loadData()
        dataBinding.srlContainer.setRefreshHeader(ClassicsHeader(this))
        dataBinding.srlContainer.setRefreshFooter(ClassicsFooter(this))
        dataBinding.srlContainer.setOnLoadMoreListener {
            loadMore = true
            loadData()

        }
        dataBinding.srlContainer.setOnRefreshListener {
            loadMore = false
            loadData()
        }
        dataBinding.tvInteract.text = typeName
    }

    private fun loadData() {
        when(interactType){
            Constants.InteractType.typeThumbUp->{
                userViewModel.getThumbUpMessage(loadMore)
            }
            Constants.InteractType.typeSyetem->{
                userViewModel.getSystemMessage(loadMore)
            }
            Constants.InteractType.typeReply->{
                userViewModel.getReplyMessage(loadMore)
            }
            Constants.InteractType.typeMontentComment->{
                userViewModel.getMomentMessage(loadMore)
            }
        }
    }

}