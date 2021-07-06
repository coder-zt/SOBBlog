package com.coder.zt.sobblog.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coder.zt.sobblog.model.moyu.MYComment
import com.coder.zt.sobblog.model.moyu.MoYuDataDisplay
import com.coder.zt.sobblog.repository.MoYuRepository
import com.coder.zt.sobblog.utils.ToastUtils
import kotlinx.coroutines.launch

class MoYuViewModel: ViewModel() {
companion object{
    private const val TAG = "MoYuViewModel"
}
    val moyuDisplayData:MutableLiveData<MoYuDataDisplay> = MutableLiveData()
    val slideDistance:MutableLiveData<Int> = MutableLiveData()

    fun getRecommendMiniFeed(page:Int){
        viewModelScope.launch{
            moyuDisplayData.value = MoYuRepository.getInstance().getRecommendMinifeed(page)
        }
    }

    fun thumbUP(momentId:String){
        viewModelScope.launch {
            Log.d(TAG, "thumbUP: $momentId")
            val thumbUp = MoYuRepository.getInstance().thumbUp(momentId)
            Log.d(TAG, "thumbUP: $thumbUp")
            ToastUtils.show(thumbUp.message, !thumbUp.success)
        }
    }

}