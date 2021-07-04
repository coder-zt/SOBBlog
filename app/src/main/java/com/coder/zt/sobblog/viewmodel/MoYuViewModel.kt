package com.coder.zt.sobblog.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coder.zt.sobblog.model.moyu.MYComment
import com.coder.zt.sobblog.model.moyu.MoYuDataDisplay
import com.coder.zt.sobblog.repository.MoYuRepository
import kotlinx.coroutines.launch

class MoYuViewModel: ViewModel() {

    val moyuDisplayData:MutableLiveData<MoYuDataDisplay> = MutableLiveData()
    val comment:MutableLiveData<MYComment> = MutableLiveData()

    fun getRecommendMiniFeed(page:Int){
        viewModelScope.launch{
            moyuDisplayData.value = MoYuRepository.getInstance().getRecommendMinifeed(page)
        }
    }

    fun getMinifeedComment(commentId:String, page:Int){
        viewModelScope.launch{
//            comment.value = MoYuRepository.getInstance().getMinifeedComment(commentId, page)
        }

    }

}