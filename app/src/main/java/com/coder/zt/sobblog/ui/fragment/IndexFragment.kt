package com.coder.zt.sobblog.ui.fragment

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.FragmentIndexBinding
import com.coder.zt.sobblog.ui.adapter.ArticleInfoAdapter
import com.coder.zt.sobblog.ui.base.BaseFragment
import com.coder.zt.sobblog.utils.AppRouter
import com.coder.zt.sobblog.viewmodel.ArticleViewModel
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader

class IndexFragment:BaseFragment<FragmentIndexBinding>() {

    companion object{
        private const val TAG = "IndexFragment"
    }

    private val articleModelView:ArticleViewModel by lazy{
        ViewModelProvider(this).get(ArticleViewModel::class.java)
    }

    private val adapter: ArticleInfoAdapter by lazy{
            ArticleInfoAdapter(){
                AppRouter.toArticleDetailActivity(requireActivity(), it)
            }
    }

    private var loadMore = false

    override fun initData() {
        articleModelView.articleList.observe(this){
            if (!loadMore) {
                dataBinding.srlContainer.finishRefresh()
                adapter.setData(it)
            }else{
                dataBinding.srlContainer.finishLoadMore()
                adapter.addData(it)
            }
        }
        articleModelView.loadRecommendArticle()
    }


    override fun getLayoutId() = R.layout.fragment_index
    override fun initView() {
        dataBinding.rvContent.adapter = adapter
        dataBinding.srlContainer.setRefreshHeader(ClassicsHeader(activity))
        dataBinding.srlContainer.setRefreshFooter(ClassicsFooter(activity))
        dataBinding.srlContainer.setOnRefreshListener {
            loadMore = false
            articleModelView.loadRecommendArticle()
        }
        dataBinding.srlContainer.setOnLoadMoreListener {
            loadMore = true
            articleModelView.loadMoreRecommendArticle()
        }
    }
}