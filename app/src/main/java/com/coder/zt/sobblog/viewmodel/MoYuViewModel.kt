package com.coder.zt.sobblog.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coder.zt.sobblog.model.moyu.MoYuList
import com.coder.zt.sobblog.repository.MoYuRepository
import kotlinx.coroutines.launch

class MoYuViewModel: ViewModel() {

    val moyuList:MutableLiveData<MoYuList> = MutableLiveData()

    fun getRecommendMiniFeed(page:Int){
        viewModelScope.launch{
            moyuList.value = MoYuRepository.getInstance().getRecommendMinifeed(page)
        }

    }

}