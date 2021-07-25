package com.coder.zt.sobblog.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.RvBlankCommentBinding
import com.coder.zt.sobblog.databinding.RvChildCommentBinding
import com.coder.zt.sobblog.databinding.RvMoyuCommentTopBinding
import com.coder.zt.sobblog.databinding.RvParentCommentBinding
import com.coder.zt.sobblog.model.article.ArticleComment
import com.coder.zt.sobblog.model.moyu.MYComment

class ArticleCommentAdapter(val objectId: String, val callback:(code: MoYuAdapter.DOTYPE, data:Any) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val mData:MutableList<Comment> = mutableListOf()

    companion object{
        const val TOP = 0
        const val PARENT = 1
        const val CHILD = 2
        const val BLANK = 3
    }
    init {
        //添加顶部评论控件
        mData.add(Comment(true, objectId))
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when(viewType){
            TOP->{
                val inflate = DataBindingUtil.inflate<RvMoyuCommentTopBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.rv_moyu_comment_top,
                    parent,
                    false
                )
                return TopView(inflate)
            }
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
        if (holder is TopView) {
            holder.setData(mData[position], callback)
        }else if (holder is ParentView) {
            holder.setData(mData[position], callback)
        }else if (holder is ChildView) {
            holder.setData(mData[position], callback)
        }
    }

    override fun getItemCount(): Int = mData.size

    override fun getItemViewType(position: Int): Int {
        return mData[position].type
    }

    fun setData(data:List<ArticleComment>){
        for (datum in data) {
            mData.add(Comment(datum, datum.subComments.isEmpty()))
            for((index, sub) in datum.subComments.withIndex()){
                mData.add(Comment(sub, index + 1 == datum.subComments.size))
            }
            mData.add(Comment(false, ""))//空白间隔
        }
        notifyDataSetChanged()
    }

    fun setMYData(data:List<MYComment>){
        for (datum in data) {
            mData.add(Comment(datum, datum.subComments.isEmpty()))
            for((index, sub) in datum.subComments.withIndex()){
                mData.add(Comment(sub, datum.momentId,index + 1 == datum.subComments.size))
            }
        }
        notifyDataSetChanged()
    }

    /**
     * 顶部评论UI
     */
    class TopView(val inflate: RvMoyuCommentTopBinding) : RecyclerView.ViewHolder(inflate.root) {

        fun setData(comment: Comment, callback:(code: MoYuAdapter.DOTYPE, data:Any) -> Unit) {
            inflate.root.setOnClickListener{
                callback.invoke(MoYuAdapter.DOTYPE.COMMENT, comment.objectId)
            }
        }

    }


    /**
     * 父评论
     */
    class ParentView(val data:RvParentCommentBinding):RecyclerView.ViewHolder(data.root) {

        fun setData(comment: Comment, callback:(code: MoYuAdapter.DOTYPE, data:Any) -> Unit) {
            data.data = comment
            if(comment.finally){
                data.root.setBackgroundResource(R.drawable.rv_comment_one_bg)
            }else{
                data.root.setBackgroundResource(R.drawable.rv_comment_top_bg)
            }
            data.tvReply.setOnClickListener {
                callback(MoYuAdapter.DOTYPE.REPLY, comment)
            }
        }

    }

    /**
     * 子评论
     */
    class ChildView(val data:RvChildCommentBinding):RecyclerView.ViewHolder(data.root) {
        fun setData(comment: Comment, callback:(code: MoYuAdapter.DOTYPE, data:Any) -> Unit) {
            data.data = comment
            if(comment.finally){
                data.root.setBackgroundResource(R.drawable.rv_comment_bottom_bg)
            }else{
                data.root.setBackgroundResource(R.drawable.rv_comment_middle_bg)
            }
            data.tvReply.setOnClickListener {
                callback(MoYuAdapter.DOTYPE.REPLY, comment)
            }
        }

    }

    //空白Item
    class BlankView(data:RvBlankCommentBinding):RecyclerView.ViewHolder(data.root) {

    }


    /**
     * 适配器的数据适配
     */
    data class Comment(val type:Int,//评论类型
                       val _id: String,//评论ID
                       val objectId: String,//对象Id（文章、摸鱼动态）
                       val userId: String,//用户ID
                       val userName: String,//用户名称
                       val userAvatar: String,//用户头像
                       val vip: Boolean,//是不是Vip
                       val content: String,//评论内容
                       val targetId: String,//被回复用户id
                       val targetName: String,//被回复用户名称
                       val parentId: String,//父评论ID
                       val publishTime: String,//发布时间
                       val isTop: Boolean,//置顶
                       val first:Boolean,//第一个
                       val finally:Boolean//最后一个
    ){
        constructor(comment:ArticleComment, finally: Boolean):this(
            PARENT,
            comment._id,
            comment.articleId,
            comment.userId,
            comment.nickname,
            comment.avatar,
            comment.vip,
            comment.commentContent,
            "",//父评论没有被回复用户
            "",//父评论没有被回复用户
            comment.parentId,
            comment.publishTime,
            comment.isTop,
            true,
            finally
        )

        constructor(comment:ArticleComment.SubComment, finally: Boolean):this(
            CHILD,
            comment._id,
            comment.articleId,
            comment.yourUid,
            comment.yourNickname,
            comment.yourAvatar,
            comment.vip,
            comment.content,
            comment.beUid,
            comment.beNickname,
            comment.parentId,
            comment.publishTime,
            false,
            false,
            finally
        )
        constructor(comment:MYComment, finally: Boolean):this(
            PARENT,
            comment.id,
            comment.momentId,
            comment.userId,
            comment.nickname,
            comment.avatar,
            comment.vip,
            comment.content,
            "",//父评论没有被回复用户
            "",//父评论没有被回复用户
            "",//父评论没有被父评论
            comment.createTime,
            false,
            true,
            finally
        )

        constructor(comment:MYComment.SubComment,objectId: String, finally: Boolean):this(
            CHILD,
            comment.commentId,
            objectId,
            comment.userId,
            comment.nickname,
            comment.avatar,
            comment.vip,
            comment.content,
            comment.targetUserId?:"",
            comment.targetUserNickname,
            comment.commentId,//父评论ID
            comment.createTime,
            false,
            false,
            finally
        )

        constructor(isTop: Boolean, objectId: String):this(
            if(isTop){TOP}else{BLANK},
            "",
            objectId,//顶部的平论需要知道对象的id
            "",
            "",
            "",
            false,
            "",
            "",
            "",
            "",
            "",
            false,
            false,
            false
        )
    }
}