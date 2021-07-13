package com.coder.zt.sobblog.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.FragmentIndexBinding
import com.coder.zt.sobblog.viewmodel.ArticleViewModel
import com.coder.zt.sobblog.viewmodel.MoYuViewModel

class IndexFragment:Fragment() {

    companion object{
        private const val TAG = "IndexFragment"
    }

    private val articleModelView:ArticleViewModel by lazy{
        ViewModelProvider(this).get(ArticleViewModel::class.java)
    }
    lateinit var dataBinding:FragmentIndexBinding

    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView: ")

        val view = inflater.inflate(R.layout.fragment_index, container, false)
        dataBinding = DataBindingUtil.bind(view)!!
        initView()
        initData()
        return view
    }

    private fun initView() {

    }

    private fun initData() {
        articleModelView.articleList.observe(viewLifecycleOwner){
            for (articleInfo in it) {
                Log.d(TAG, "initData: $articleInfo")
            }
//            Log.d(TAG, "initData: ${it.size}")
        }
    }
    

    override fun onResume() {
        super.onResume()
        dataBinding.root.setOnClickListener{
            articleModelView.getRecommendArticleList(1)
        }
    }
}