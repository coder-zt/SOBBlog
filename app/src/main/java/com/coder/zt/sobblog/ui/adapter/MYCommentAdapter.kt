package com.coder.zt.sobblog.ui.adapter

import android.annotation.SuppressLint
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.RvMoyuCommentTopBinding
import com.coder.zt.sobblog.databinding.RvMoyuInteractionBinding
import com.coder.zt.sobblog.model.moyu.MYComment

class MYCommentAdapter( val callback:(code: MoYuAdapter.DO_TYPE, data:Any) -> Unit)
    :RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    companion object{
        private const val TAG = "MYCommentAdapter"
        private const val parentComment:String = "parent_comment"
    }
    private val DATA_TYPE_TOP:Int = 0
    private val DATA_TYPE_COMMENT:Int = 1
    private val DATA_TYPE_REPLY:Int = 2

    private val mCommentData:MutableList<CommentDataBean> = mutableListOf()


    fun setData(comments: List<MYComment>){
        Log.d(TAG, "setData: ${comments.size}")
        for (comment in comments) {
            mCommentData.add(CommentDataBean(comment.nickname, parentComment, comment.content))
            for (subComment in comment.subComments) {
                mCommentData.add(CommentDataBean(subComment.nickname, subComment.targetUserNickname, subComment.content))
            }
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):RecyclerView.ViewHolder {
         when (viewType) {
            DATA_TYPE_TOP ->{
                    val inflate = DataBindingUtil.inflate<RvMoyuCommentTopBinding>(
                        LayoutInflater.from(parent.context),
                        R.layout.rv_moyu_comment_top,
                        parent,
                        false
                    )
                    return TopView(inflate)
                }
            DATA_TYPE_REPLY,
            DATA_TYPE_COMMENT -> {
                val inflate = DataBindingUtil.inflate<RvMoyuInteractionBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.rv_moyu_interaction,
                    parent,
                    false
                )
                return ItemView(inflate)
            }
        }
        val inflate = DataBindingUtil.inflate<RvMoyuInteractionBinding>(
            LayoutInflater.from(parent.context),
            R.layout.rv_moyu_interaction,
            parent,
            false
        )
        return ItemView(inflate)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            DATA_TYPE_TOP -> {
                (holder as TopView).setData( callback)
            }
            DATA_TYPE_REPLY,
            DATA_TYPE_COMMENT -> {
                (holder as ItemView).setData(mCommentData[position - 1], callback)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 -> {//当有人点赞时显示点赞情况
                DATA_TYPE_TOP
            }
            mCommentData[position - 1].to == parentComment -> {
                DATA_TYPE_COMMENT
            }
            else -> {
                DATA_TYPE_COMMENT
            }
        }
    }
    override fun getItemCount(): Int {
        return mCommentData.size + 1
    }

   data class CommentDataBean(val from:String, val to:String, val content:String)

    class ItemView(val inflate: RvMoyuInteractionBinding?) : RecyclerView.ViewHolder(inflate!!.root) {
        @SuppressLint("SetTextI18n")
        fun setData(commentDataBean: CommentDataBean?,callback:(code: MoYuAdapter.DO_TYPE, data:Any) -> Unit) {
            inflate?.diver?.visibility = View.GONE
            //评论
            if(commentDataBean?.from != "有点赞"){
                if(commentDataBean?.to == parentComment){
                    Log.d(TAG, "setData 父评论: $commentDataBean")
                    inflate?.content?.text = Html.fromHtml( "<font color=\"#294F6C\">${commentDataBean.from}</font><font color=\"#000000\">:${commentDataBean.content}</font>")
                }else{
                    Log.d(TAG, "setData 子评论: $commentDataBean")//
                    inflate?.content?.text = Html.fromHtml( "<font color=\"#294F6C\">${commentDataBean?.from}</font><font color=\"#000000\">回复</font><font color=\"#294F6C\">${commentDataBean?.to}</font><font color=\"#000000\">:${commentDataBean?.content}</font>")
                }
            }
        }


    }

    class TopView(val inflate: RvMoyuCommentTopBinding?) : RecyclerView.ViewHolder(inflate!!.root) {

        fun setData(callback:(code: MoYuAdapter.DO_TYPE, data:Any) -> Unit) {
            inflate?.root?.setOnClickListener{
                callback.invoke(MoYuAdapter.DO_TYPE.COMMENT, "ID")
            }
        }


    }
}
