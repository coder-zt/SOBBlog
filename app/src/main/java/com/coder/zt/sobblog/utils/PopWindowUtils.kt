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
import com.coder.zt.sobblog.databinding.PopPullStyleBinding
import com.coder.zt.sobblog.ui.adapter.PopListAdapter
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import kotlin.math.min

object PopWindowUtils {

    private const val TAG = "PopWindowUtils"
    private const val heightRatio = 0.6f

    fun <T : ViewDataBinding, D> showListData(
        layoutId: Int, items: List<D>, activity: Activity,
        callback: PopListAdapter.ItemsListSetData<T, D>,fixHeight: Boolean
    ) {
        val popBind = DataBindingUtil.inflate<PopListShowBinding>(
            LayoutInflater.from(activity),
            R.layout.pop_list_show, null, false
        )
        Log.d(TAG, "showListData: ")
        val height = (ScreenUtils.getScreenHeight() * heightRatio).toInt()
        val pop = PopupWindow(
            ViewGroup.LayoutParams.MATCH_PARENT, if(fixHeight){height}
                                                else{ViewGroup.LayoutParams.WRAP_CONTENT}
        )
        popBind.rvContainer.adapter = PopListAdapter(layoutId, items,
            object : PopListAdapter.ItemsListSetData<T, D> {
                override fun setData(inflate: T, d: D) {
                    callback.setData(inflate, d)
                }

                override fun onClick(d: D) {
                    pop.dismiss()
                    callback.onClick(d)
                }
            })

        pop.isOutsideTouchable = true
        pop.isFocusable = true
        pop.contentView = popBind.root
        pop.setOnDismissListener {
            Log.d(TAG, "showListData: Dismiss")
            ScreenUtils.resortWindowBackground(activity)
        }

        ScreenUtils.setWindowBackground(activity, 0.3f)
        pop.showAtLocation(activity.window.decorView.rootView, Gravity.BOTTOM, 0, 0)
    }

    fun showTakePictureStyle(size: Int, activity: Activity, view: View) {
        val pop =
            PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val popBind = DataBindingUtil.inflate<PopPullStyleBinding>(
            LayoutInflater.from(activity),
            R.layout.pop_pull_style, null, false
        )
        pop.isOutsideTouchable = true
        pop.isFocusable = true
        pop.contentView = popBind.root
        popBind.tvCancel.setOnClickListener {
            pop.dismiss()
        }
        popBind.tvCamera.setOnClickListener {
            pop.dismiss()
            PictureSelector.create(activity)
                .openCamera(PictureMimeType.ofImage())
                .imageEngine(GlideEngine.createGlideEngine()) // 请参考Demo GlideEngine.java
                .forResult(PictureConfig.REQUEST_CAMERA)
        }
        popBind.tvAlbum.setOnClickListener {
            pop.dismiss()
            PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())
                .maxSelectNum(size)
                .imageEngine(GlideEngine.createGlideEngine()) // 请参考Demo GlideEngine.java
                .forResult(PictureConfig.CHOOSE_REQUEST)
        }
        pop.setOnDismissListener {
            ScreenUtils.resortWindowBackground(activity)
        }
        ScreenUtils.setWindowBackground(activity, 0.3f)
        pop.showAtLocation(view, Gravity.BOTTOM, 0, 0)
    }

}