package com.coder.zt.sobblog.ui.activity

import android.os.Bundle
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.ActivityIndexQuestionBinding
import com.coder.zt.sobblog.ui.adapter.HomePagerAdapter
import com.coder.zt.sobblog.ui.adapter.QuestionPagerAdapter
import com.coder.zt.sobblog.ui.base.BaseActivity
import com.coder.zt.sobblog.ui.fragment.HotQuestionFragment
import com.coder.zt.sobblog.ui.fragment.NewQuestionFragment
import com.coder.zt.sobblog.ui.fragment.WaittingQuestionFragment

class QuestionIndexActivity: BaseActivity<ActivityIndexQuestionBinding>() {

    override fun getLayoutId() = R.layout.activity_index_question

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding.vpContent.adapter = QuestionPagerAdapter(supportFragmentManager, listOf(
            NewQuestionFragment(), WaittingQuestionFragment(), HotQuestionFragment()
        ))
    }
}