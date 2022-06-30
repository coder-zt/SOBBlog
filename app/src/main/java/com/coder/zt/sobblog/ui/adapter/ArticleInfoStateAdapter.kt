package com.coder.zt.sobblog.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.RvArticleInfoBinding
import com.coder.zt.sobblog.model.article.ArticleInfo

class ArticleInfoStateAdapter(val callback: (id: String) -> Unit):StateAdapter<ArticleInfo>() {

companion object{
    private const val TAG = "ArticleInfoAdapter"
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

    override fun getContentVH(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflate = DataBindingUtil.inflate<RvArticleInfoBinding>(
            LayoutInflater.from(parent.context),
            R.layout.rv_article_info,
            parent,
            false
        )
        return ArticleView(inflate)
    }

    override fun bindContentView(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ArticleView -> {
                holder.setData(mData.get(position), callback)
            }
        }
    }

}
