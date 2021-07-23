package com.coder.zt.sobblog.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.RvArticleInfoBinding
import com.coder.zt.sobblog.model.article.ArticleInfo

class ArticleInfoAdapter(val callback: (id: String) -> Unit):RecyclerView.Adapter<ArticleInfoAdapter.ArticleView>() {

companion object{
    private const val TAG = "ArticleInfoAdapter"
}
    val mData:MutableList<ArticleInfo> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleView {
        val inflate = DataBindingUtil.inflate<RvArticleInfoBinding>(
            LayoutInflater.from(parent.context),
            R.layout.rv_article_info,
            parent,
            false
        )
        return ArticleView(inflate)
    }

    override fun onBindViewHolder(holder: ArticleView, position: Int) {
        holder.setData(mData[position], callback)
    }

    override fun getItemCount() :Int{
        return mData.size
    }

    fun setData(data:List<ArticleInfo>){
        Log.d(TAG, "setData: ")
        mData.clear()
        if(data.isNotEmpty()){
            mData.addAll(data)
        }
        notifyDataSetChanged()
    }

    fun addData(data: List<ArticleInfo>) {
        if(data.isNotEmpty()){
            mData.addAll(data)
        }
        notifyDataSetChanged()
    }

    class ArticleView(val bind:RvArticleInfoBinding):RecyclerView.ViewHolder(bind.root){
        fun setData(articleInfo: ArticleInfo, callback:(id:String)->Unit) {
            Log.d(TAG, "setData: ${articleInfo.avatar}")
            bind.data = articleInfo
            bind.root.setOnClickListener{
                callback(articleInfo.id)
            }
        }

    }
}
