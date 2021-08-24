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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.RvEmojiItemBinding
import com.coder.zt.sobblog.databinding.RvEmojiTitleBinding
import com.coder.zt.sobblog.model.datamanager.EmojiDataMan
import com.coder.zt.sobblog.ui.adapter.EmojiAdapter.ViewItem
import com.coder.zt.sobblog.utils.EmojiImageGetter

class EmojiAdapter(val callback:(emojiUrl:String)->Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val views = mutableListOf<ViewItem>()



    companion object{
        private const val TAG = "EmojiAdapter"
    }

    private val viewData = mutableListOf<ViewItem>()

    private val typeTitle = 0
    private val typeEmoji = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == typeEmoji){
            val inflate =  DataBindingUtil.inflate<RvEmojiItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.rv_emoji_item, parent, false)
            ViewItem(inflate)
        }else{
            val inflate =  DataBindingUtil.inflate<RvEmojiTitleBinding>(
                LayoutInflater.from(parent.context),
                R.layout.rv_emoji_title, parent, false)
            ViewTitle(inflate)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewItem) {
            val index = EmojiDataMan.getEmojiIndex(position)
            holder.setData(index)
            Log.d(TAG, "onBindViewHolder: position = $position   index = $index")
            if (!views.contains(holder)) {
                views.add(holder)
            }
            holder.databind.root.setOnClickListener {
                callback("<span><img src=\"https://cdn.sunofbeaches.com/emoji/${index}.png\"></span>")
            }
            viewData.add(holder)
        }else if(holder is ViewTitle){
            holder.setData(position)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if( EmojiDataMan.getShowEmojiTitle(position)){
            typeTitle
        }else{
            typeEmoji
        }
    }

    override fun getItemCount() = 131 + EmojiDataMan.getRecentlyEmojiCount()

    fun hideBottomRightIcon(deletePosition: IntArray) {
        Log.d(TAG, "hideBottomRightIcon: ")
        for (view in views) {
            val alpha = view.databind.tvEmoji.onSlide(deletePosition)
            view.databind.vCover.alpha = alpha
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val manager = recyclerView.layoutManager
        if(manager is GridLayoutManager){
            manager.spanSizeLookup = object:GridLayoutManager.SpanSizeLookup(){
                override fun getSpanSize(position: Int): Int {
                    Log.d(TAG, "getSpanSize: position ---> $position")
                    return if(getItemViewType(position) == typeTitle ){
                        8
                    }else{
                       1
                    }
                }

            }
        }
    }

    class ViewItem(val databind:RvEmojiItemBinding):RecyclerView.ViewHolder(databind.root) {

        @RequiresApi(Build.VERSION_CODES.N)
        fun setData(position: Int) {
            databind.tvEmoji.setIndex(position)
            val content =
                "<span><img src=\"https://cdn.sunofbeaches.com/emoji/${position}.png\"></span>"
            val sp = Html.fromHtml(content, 0, EmojiImageGetter(databind.tvEmoji.context, 3), null)
            databind.tvEmoji.text = sp
//            databind.tvEmoji.visibility = View.VISIBLE
//            databind.tvEmoji.setBackgroundColor(Color.WHITE)
//                val alpha = databind.tvEmoji.onSlide(deletePosition)
//                databind.vCover.alpha = alpha
        }
    }

    class ViewTitle(val databind:RvEmojiTitleBinding):RecyclerView.ViewHolder(databind.root) {

            @RequiresApi(Build.VERSION_CODES.N)
            fun setData(position: Int) {
                databind.tvTitle.text = EmojiDataMan.getEmojiTitle(position)
            }
        }

}
