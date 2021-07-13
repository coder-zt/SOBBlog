package com.coder.zt.sobblog.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.RvBlankCommentBinding
import com.coder.zt.sobblog.databinding.RvChildCommentBinding
import com.coder.zt.sobblog.databinding.RvParentCommentBinding
import com.coder.zt.sobblog.model.article.ArticleComment

class ArticleCommentAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val mData:MutableList<Comment> = mutableListOf()

    companion object{
        const val PARENT = 1
        const val CHILD = 2
        const val BLANK = 3
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when(viewType){
            PARENT->{
                val inflate = DataBindingUtil.inflate<RvParentCommentBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.rv_parent_comment,
                    parent,
                    false
                )
                ParentView(inflate)
            }
            CHILD->{
                val inflate = DataBindingUtil.inflate<RvChildCommentBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.rv_child_comment,
                    parent,
                    false
                )
                ChildView(inflate)
            }
            BLANK->{
                val inflate = DataBindingUtil.inflate<RvBlankCommentBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.rv_blank_comment,
                    parent,
                    false
                )
                BlankView(inflate)
            }
            else->{
                val inflate = DataBindingUtil.inflate<RvBlankCommentBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.rv_blank_comment,
                    parent,
                    false
                )
                BlankView(inflate)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ParentView) {
            holder.setData(mData[position])
        }else if (holder is ChildView) {
            holder.setData(mData[position])
        }
    }

    override fun getItemCount(): Int = mData.size

    override fun getItemViewType(position: Int): Int {

        return mData[position].type
    }

    fun setData(data:List<ArticleComment>){
        for (datum in data) {
            mData.add(Comment(datum))
            for(sub in datum.subComments){
                mData.add(Comment(sub))
            }
            mData.add(Comment())
        }
        notifyDataSetChanged()
    }

    class ParentView(val data:RvParentCommentBinding):RecyclerView.ViewHolder(data.root) {

        fun setData(get: Comment) {
            data.data = get
        }

    }

    class ChildView(val data:RvChildCommentBinding):RecyclerView.ViewHolder(data.root) {
        fun setData(get: Comment) {
            data.data = get
        }

    }

    class BlankView(data:RvBlankCommentBinding):RecyclerView.ViewHolder(data.root) {

    }


    data class Comment(val type:Int,
        val _id: String,
        val articleId: String,
        val avatar: String,
        val commentContent: String,
        val isTop: String,
        val nickname: String,
        val parentId: String,
        val publishTime: String,
        val role: String,
        val userId: String,
        val vip: Boolean
    ){
        constructor(comment:ArticleComment):this(
            PARENT,
            comment._id,
            comment.articleId,
            comment.avatar,
            comment.commentContent,
            comment.isTop,
            comment.nickname,
            comment.parentId,
            comment.publishTime,
            comment.role?:"",
            comment.userId,
            comment.vip,
        )

        constructor(comment:ArticleComment.SubComment):this(
            CHILD,
            comment._id,
            comment.articleId,
            comment.yourAvatar,
            comment.content,
            "false",
            comment.yourNickname,
            comment.parentId,
            comment.publishTime,
            comment.yourRole?:"",
            comment.beUid,
            comment.vip,
        )
        constructor():this(
            BLANK,
            "-1",
            "-1",
            "-1",
            "-1",
            "false",
            "-1",
            "-1",
            "-1",
            "-1",
            "-1",
            false,
        )
    }
}
