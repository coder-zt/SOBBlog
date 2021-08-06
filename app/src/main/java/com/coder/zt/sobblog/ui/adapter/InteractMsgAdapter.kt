package com.coder.zt.sobblog.ui.adapter

import android.app.Activity
import android.graphics.Color
import android.text.Html
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.URLSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.RvReplyMsgBinding
import com.coder.zt.sobblog.databinding.RvSystemMsgBinding
import com.coder.zt.sobblog.databinding.RvThumbUpMsgBinding
import com.coder.zt.sobblog.model.user.ReplyMessage
import com.coder.zt.sobblog.model.user.SystemMessage
import com.coder.zt.sobblog.model.user.ThumbUpMessage
import com.coder.zt.sobblog.utils.Constants
import com.coder.zt.sobblog.utils.TransUtils

class InteractMsgAdapter<T>(val type:Int, val activity: Activity):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object{
        private const val TAG = "InteractMsgAdapter"
    }

    private val mData = mutableListOf<T>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            Constants.InteractType.typeSyetem->{
                val inflate = DataBindingUtil.inflate<RvSystemMsgBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.rv_system_msg,
                    parent,
                    false
                )
                SystemMsgView(inflate)
            }
            Constants.InteractType.typeThumbUp->{
                val inflate = DataBindingUtil.inflate<RvThumbUpMsgBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.rv_thumb_up_msg,
                    parent,
                    false
                )
                ThumbUpMsgView(inflate)
            }
            Constants.InteractType.typeReply->{
                val inflate = DataBindingUtil.inflate<RvReplyMsgBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.rv_reply_msg,
                    parent,
                    false
                )
                ReplyMsgView(inflate)
            }
            else->{
                val inflate = DataBindingUtil.inflate<RvSystemMsgBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.rv_system_msg,
                    parent,
                    false
                )
                SystemMsgView(inflate)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SystemMsgView && mData[position] is SystemMessage) {
            holder.setData(mData[position] as SystemMessage, activity)
        }else if (holder is ThumbUpMsgView && mData[position] is ThumbUpMessage) {
            holder.setData(mData[position] as ThumbUpMessage, activity)
        }else if (holder is ReplyMsgView && mData[position] is ReplyMessage) {
            holder.setData(mData[position] as ReplyMessage, activity)
        }
    }

    override fun getItemViewType(position: Int) = type

    override fun getItemCount() = mData.size

    fun setData(data:List<T>,loadMore: Boolean){
        if (!loadMore) {
            mData.clear()
        }
        mData.addAll(data)
        notifyDataSetChanged()
    }

    class ReplyMsgView(private val dataBind:RvReplyMsgBinding):RecyclerView.ViewHolder(dataBind.root){
        fun setData(msg: ReplyMessage, activity: Activity) {
            dataBind.data = msg
            Log.d(TAG, "setData: repley message ---> $msg")
            val content = StringBuilder().run{
                append("回复了我的评论：「<a ")
                append("href='")
                append("https://www.sunofbeach.net")
                append(msg.url)
                append("' style='color:#406599' >")
                append(msg.content)
                append("</a>」")
                toString()
            }
            Log.d(TAG, "setData: $content")
            dataBind.tvContent.text = Html.fromHtml(content)
            dataBind.tvContent.movementMethod = LinkMovementMethod.getInstance()
            val text = dataBind.tvContent.text
            if (text is Spannable) {
                val end = text.length
                val spannable = dataBind.tvContent.text as Spannable
                val urls = spannable.getSpans(0, end, URLSpan::class.java)
                val  style=  SpannableStringBuilder(text)
                style.clearSpans();//should clear old spans
                for(url in urls){
                    val myURLSpan = MyURLSpan(url.getURL(), Color.parseColor("#406599"),activity)
//                    style.color(Color.parseColor("#406599"),)
//                    style.setSpan( ForegroundColorSpan(Color.parseColor(afterColor)),beforeText.length(), builder.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                    style.setSpan(myURLSpan,spannable.getSpanStart(url),spannable.getSpanEnd(url),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                dataBind.tvContent.setText(style)
            }
        }

    }

    class SystemMsgView(private val dataBind:RvSystemMsgBinding):RecyclerView.ViewHolder(dataBind.root){
        fun setData(msg: SystemMessage, activity: Activity) {
            dataBind.data = msg
            Log.d(TAG, "setData: ${msg.content}")
            val parttern = Regex("<a (.*?)</a>")
            val results = parttern.findAll(msg.content)
            val urlColorMap = mutableMapOf<String, String>()
            for (result in results) {
                Log.d(TAG, "setData: result ${result.value}")
                val aTagStr = result.value
                val urlParttern = Regex("href='(.*?)'")
                val urlResults = urlParttern.findAll(aTagStr)
                val colorParttern = Regex("'color:(.*?)'")
                val colorResults = colorParttern.findAll(aTagStr)
                var url = ""
                var color = ""
                if (urlResults.iterator().hasNext()) {
                    url = urlResults.iterator().next().value
                    url = url.replace("href='","")
                    url = url.replace("'","")
                    Log.d(TAG, "setData: re ---> url : $url")
                }
                if (colorResults.iterator().hasNext()) {
                    color = colorResults.iterator().next().value.subSequence(7, 14).toString()
                    Log.d(TAG, "setData: re ---> color : $color")
                }
                    if(color.isNotBlank()){
                        urlColorMap[url] = color
                    }else{
                        urlColorMap[url] = "#406599"
                    }
            }
            dataBind.tvContent.text = Html.fromHtml(msg.content)
            dataBind.tvContent.movementMethod = LinkMovementMethod.getInstance()
            val text = dataBind.tvContent.text
            if (text is Spannable) {
                val end = text.length
                val spannable = dataBind.tvContent.text as Spannable
                val urls = spannable.getSpans(0, end, URLSpan::class.java)
                val  style=  SpannableStringBuilder(text)
                style.clearSpans();//should clear old spans
                for (entry in urlColorMap.entries) {
                    Log.d(TAG, "setData: key:${entry.key} ==== value:${entry.value}")
                }
                for(url in urls){
                    val myURLSpan = MyURLSpan(url.url,Color.parseColor(urlColorMap[url.url]), activity)
                    style.setSpan(myURLSpan,spannable.getSpanStart(url),spannable.getSpanEnd(url),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                dataBind.tvContent.setText(style)
            }
        }

    }

    class ThumbUpMsgView(private val dataBind:RvThumbUpMsgBinding):RecyclerView.ViewHolder(dataBind.root){
        fun setData(msg: ThumbUpMessage, activity: Activity) {
            dataBind.data = msg
            val content = StringBuilder().run{
                append("给朕的内容点赞：「<a ")
                append("href='")
                append("https://www.sunofbeach.net")
                append(msg.url)
                append("' style='color:#406599' >")
                append(msg.title)
                append("</a>」")
                toString()
            }
            Log.d(TAG, "setData: $content")
            dataBind.tvContent.text = Html.fromHtml(content)
            dataBind.tvContent.movementMethod = LinkMovementMethod.getInstance()
            val text = dataBind.tvContent.text
            if (text is Spannable) {
                val end = text.length
                val spannable = dataBind.tvContent.text as Spannable
                val urls = spannable.getSpans(0, end, URLSpan::class.java)
                val  style=  SpannableStringBuilder(text)
                style.clearSpans();//should clear old spans
                for(url in urls){
                    val myURLSpan = MyURLSpan(url.getURL(), Color.parseColor("#406599"),activity)
//                    style.color(Color.parseColor("#406599"),)
//                    style.setSpan( ForegroundColorSpan(Color.parseColor(afterColor)),beforeText.length(), builder.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                    style.setSpan(myURLSpan,spannable.getSpanStart(url),spannable.getSpanEnd(url),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                dataBind.tvContent.setText(style)
            }
        }

    }


     class MyURLSpan(val mUrl:String,val color:Int,val activity: Activity): ClickableSpan() {

         override fun onClick(widget: View) {
             Log.d(TAG, "onClick: $mUrl")
             TransUtils.dispatchShareLink(activity, mUrl)
         }

         override fun updateDrawState(textPaint: TextPaint) {
             textPaint.color = color
         }
     }

}