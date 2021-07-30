package com.coder.zt.sobblog.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.RvMoyuCommentTopBinding
import com.coder.zt.sobblog.databinding.RvPictureBrowseBinding
import com.coder.zt.sobblog.databinding.RvPictureItemBinding

class PictureBrowseAdapter(val picUrls: List<String>) : RecyclerView.Adapter<PictureBrowseAdapter.ItemView>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ):ItemView {
        val inflate = DataBindingUtil.inflate<RvPictureBrowseBinding>(
            LayoutInflater.from(parent.context),
            R.layout.rv_picture_browse,
            parent,
            false
        )
        return ItemView(inflate)
    }

    override fun onBindViewHolder(holder: ItemView, position: Int) {
        holder.setData(picUrls[position])
    }

    override fun getItemCount()=picUrls.size

    class ItemView(val databinding:RvPictureBrowseBinding ):RecyclerView.ViewHolder(databinding.root) {
        fun setData(url: String) {
            Glide.with(databinding.root).load(url).into(databinding.ivBrowse)
        }

    }

}
