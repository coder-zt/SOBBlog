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
import com.coder.zt.sobblog.databinding.RvMoyuInteractionBinding
import com.coder.zt.sobblog.model.moyu.MoYuDataDisplay

class MYCommentAdapter(val likeCount:Int, val comment: List<MoYuDataDisplay.MiniFeed.Comment>) : RecyclerView.Adapter<MYCommentAdapter.ItemView>() {


    companion object{
        private const val TAG = "MYCommentAdapter"
        private const val parentComment:String = "parent_comment"
    }
    private val DATA_TYPE_LIKE:Int = 0
    private val DATA_TYPE_COMMENT:Int = 1
    private val DATA_TYPE_REPLY:Int = 2
    private val noLikes:Int = 0
    private val mCommentData:MutableList<CommentDataBean> = mutableListOf()
    private var hasLikes = 0
    init {
        hasLikes = if(likeCount > 0){
            1
        }else{
            0
        }
        for (comment in comment) {
            mCommentData.add(CommentDataBean(comment.nickname, parentComment, comment.content))
            for (subComment in comment.subComments) {
                mCommentData.add(CommentDataBean(subComment.nickname, subComment.targetUserNickname, subComment.content))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ItemView {
        val inflate = DataBindingUtil.inflate<RvMoyuInteractionBinding>(
            LayoutInflater.from(parent.context),
            R.layout.rv_moyu_interaction,
            parent,
            false
        )
        return ItemView(inflate)
    }

    override fun onBindViewHolder(holder: ItemView, position: Int) {
        when (getItemViewType(position)) {
            DATA_TYPE_LIKE -> {
                if(itemCount > 1){
                    holder.setData(likeCount, CommentDataBean("有点赞","有评论","显示分割线"))
                }else{
                    holder.setData(likeCount, null)
                }
            }
            DATA_TYPE_REPLY,
            DATA_TYPE_COMMENT -> {
                holder.setData(noLikes, mCommentData[position - hasLikes])
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == 0 && likeCount > 0){//当有人点赞时显示点赞情况
            DATA_TYPE_LIKE
        }else if(mCommentData[position - hasLikes].to == parentComment){
            DATA_TYPE_COMMENT
        }else{
            DATA_TYPE_COMMENT
        }
    }
    override fun getItemCount(): Int {
        return mCommentData.size + hasLikes
    }

   data class CommentDataBean(val from:String, val to:String, val content:String)

    class ItemView(val inflate: RvMoyuInteractionBinding?) : RecyclerView.ViewHolder(inflate!!.root) {
        @SuppressLint("SetTextI18n")
        fun setData(likeCount: Int, commentDataBean: CommentDataBean?) {
            inflate?.diver?.visibility = View.GONE
            //评论
            if(likeCount == 0){
                if(commentDataBean?.to == parentComment){
                    Log.d(TAG, "setData 父评论: $commentDataBean")
                    inflate?.content?.text = Html.fromHtml( "<font color=\"#294F6C\">${commentDataBean.from}</font><font color=\"#000000\">:${commentDataBean.content}</font>")
                }else{
                    Log.d(TAG, "setData 子评论: $commentDataBean")//
                    inflate?.content?.text = Html.fromHtml( "<font color=\"#294F6C\">${commentDataBean?.from}</font><font color=\"#000000\">回复</font><font color=\"#294F6C\">${commentDataBean?.to}</font><font color=\"#000000\">:${commentDataBean?.content}</font>")
                }
            //点赞人数
            }else{
                commentDataBean?.apply {
                    inflate?.diver?.visibility = View.VISIBLE
                }
                inflate?.content?.text = "有${likeCount}个觉得很赞"
            }
        }


    }
}
