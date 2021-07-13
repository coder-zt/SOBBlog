package com.coder.zt.sobblog.ui.adapter

import android.opengl.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.RvArticleRewardBinding
import com.coder.zt.sobblog.model.user.RewardUserInfo
import com.coder.zt.sobblog.utils.ScreenUtils

class RewardAdapter:RecyclerView.Adapter<RewardAdapter.ItemView>() {

    companion object{
        private const val TAG = "RewardAdapter"
    }
    private val mData:MutableList<RewardUserInfo> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemView {
        val inflate = DataBindingUtil.inflate<RvArticleRewardBinding>(
            LayoutInflater.from(parent.context),
            R.layout.rv_article_reward,
            parent,
            false
        )
        return ItemView(inflate)
    }

    override fun onBindViewHolder(holder: ItemView, position: Int) {
        if(position == 0){
            holder.setData(RewardUserInfo("头", -1, "时间") )
        }else{
            holder.setData(mData[position - 1])
        }
    }

    override fun getItemCount(): Int {
        return if(mData.size > 0){
            mData.size + 1
        }else{
            0
        }
    }

    fun setData(data:List<RewardUserInfo>){
        mData.clear()
        if (data.isNotEmpty()) {
            mData.addAll(data)
        }
        notifyDataSetChanged()
    }

    class ItemView(val inflate:RvArticleRewardBinding):RecyclerView.ViewHolder(inflate.root) {
        fun setData(rewardUserInfo: RewardUserInfo) {
            Log.d(TAG, "setData: ${rewardUserInfo.sob}")
            if (rewardUserInfo.sob != -1) {
                inflate.tvSob.text = "${rewardUserInfo.sob}"
                inflate.nicknameTv.text = rewardUserInfo.nickname
                inflate.tvTime.text = rewardUserInfo.createTime.substring(0,10)
                inflate.ivAvatar.visibility = View.VISIBLE
                inflate.ivBlank.visibility = View.GONE
                val corners = RoundedCorners(ScreenUtils.dp2px(14))
                val override = RequestOptions.bitmapTransform(corners)
                Glide.with(inflate.ivAvatar.context).load(rewardUserInfo.avatar).apply(override).into(inflate.ivAvatar)
            }else{
                inflate.ivAvatar.visibility = View.GONE
                inflate.ivBlank.visibility = View.VISIBLE
            }
        }

    }


}