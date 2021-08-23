package com.coder.zt.sobblog.ui.adapter

import android.graphics.Color
import android.os.Build
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.RvDiscoveryItemBinding
import com.coder.zt.sobblog.databinding.RvEmojiItemBinding
import com.coder.zt.sobblog.ui.adapter.EmojiAdapter.*
import com.coder.zt.sobblog.utils.EmojiImageGetter
import java.lang.StringBuilder

class EmojiAdapter(val callback:(emojiUrl:String)->Unit) : RecyclerView.Adapter<ViewItem>() {


    private val views = mutableListOf<ViewItem>()

    class ViewItem(val databind:RvEmojiItemBinding):RecyclerView.ViewHolder(databind.root) {

        @RequiresApi(Build.VERSION_CODES.N)
        fun setData(position: Int) {
            databind.tvEmoji.setIndex(position)
            val content = "<span><img src=\"https://cdn.sunofbeaches.com/emoji/${position + 1}.png\"></span>"
            val sp = Html.fromHtml(content,0, EmojiImageGetter(databind.tvEmoji.context, 3), null)
            databind.tvEmoji.text = sp
            databind.tvEmoji.visibility = View.VISIBLE
            databind.tvEmoji.setBackgroundColor(Color.WHITE)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewItem {
        val inflate = DataBindingUtil.inflate<RvEmojiItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.rv_emoji_item, parent, false
        )

        return ViewItem(inflate)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ViewItem, position: Int) {
        holder.setData(position)
        if (!views.contains(holder)) {
            views.add(holder)
        }
        holder.databind.root.setOnClickListener {
            callback("<span><img src=\"https://cdn.sunofbeaches.com/emoji/${position + 1}.png\"></span>")
        }
    }

    override fun getItemCount() = 130

    fun hideBottomRightIocn(deletePosition: IntArray) {
        for (view in views) {
            val alpha = view.databind.tvEmoji.onSlide(deletePosition)
            view.databind.vCover.alpha = alpha
        }
    }

}
