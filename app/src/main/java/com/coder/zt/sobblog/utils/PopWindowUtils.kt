package com.coder.zt.sobblog.utils

import android.app.Activity
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.PopListShowBinding
import com.coder.zt.sobblog.ui.adapter.PopListAdapter

object PopWindowUtils {

    private const val TAG = "PopWindowUtils"
    fun <T : ViewDataBinding, D> showListData(layoutId:Int, items:List<D>, activity:Activity,
                                              callback: PopListAdapter.ItemsListSetData<T,D>){
        val pop = PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtils.dp2px(300))
        val popBind = DataBindingUtil.inflate<PopListShowBinding>(
            LayoutInflater.from(activity),
            R.layout.pop_list_show, null ,false)
        pop.isOutsideTouchable = true
        pop.isFocusable = true
        pop.contentView = popBind.root
        pop.setOnDismissListener {
            Log.d(TAG, "showListData: Dismiss")
            ScreenUtils.resortWindowBackground(activity)
        }
        popBind.rvContainer.adapter = PopListAdapter<T,D>(layoutId, items,callback)
        ScreenUtils.setWindowBackground(activity , 0.3f)
        pop.showAtLocation(activity.window.decorView.rootView, Gravity.BOTTOM, 0 ,0 )
    }
}