package com.coder.zt.sobblog.ui.fragment

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.FragmentNewQuestionBinding
import com.coder.zt.sobblog.ui.base.BaseFragment
import com.coder.zt.sobblog.viewmodel.QuestionViewModel

class NewQuestionFragment:BaseFragment<FragmentNewQuestionBinding>() {

    companion object{
        private const val TAG = "NewQuestionFragment"
    }

    val questionViewModel:QuestionViewModel by lazy {
        ViewModelProvider(this).get(QuestionViewModel::class.java)
    }
    override fun initView() {

    }

    override fun initData() {
        questionViewModel.lastestQuestions.observe(this){
            for (questionItem in it) {
                Log.d(TAG, "initData: $questionItem")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        questionViewModel.getLastestQuestionList(false)
    }

    override fun getLayoutId() = R.layout.fragment_new_question
}