package com.coder.zt.sobblog.utils

import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.DialogListShowBinding
import com.coder.zt.sobblog.ui.adapter.PopListAdapter

object DialogUtils {

    fun <T : ViewDataBinding, D> showListData(
        layoutId: Int, items: List<D>, activity: Activity,
        callback: PopListAdapter.ItemsListSetData<T, D>) {
        val builder = AlertDialog.Builder(activity)
        val popBind = DataBindingUtil.inflate<DialogListShowBinding>(
            LayoutInflater.from(activity),
            R.layout.dialog_list_show, null, false
        )
        builder.setView(popBind.root)
        val dialog = builder.create()
        dialog.setOnDismissListener {
            ScreenUtils.resortWindowBackground(activity)
        }
        popBind.rvContainer.adapter = PopListAdapter(layoutId, items,
            object : PopListAdapter.ItemsListSetData<T, D> {
                override fun setData(inflate: T, d: D) {
                    callback.setData(inflate, d)
                }

                override fun onClick(d: D) {
                    dialog.dismiss()
                    callback.onClick(d)
                }
            })
        ScreenUtils.setWindowBackground(activity, 0.3f)
        dialog.show()
    }
}