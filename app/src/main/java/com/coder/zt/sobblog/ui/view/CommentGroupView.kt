package com.coder.zt.sobblog.ui.view

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.ui.adapter.EmojiAdapter
import com.coder.zt.sobblog.utils.EmojiImageGetter
import com.coder.zt.sobblog.utils.ToastUtils
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import java.lang.Math.abs

@RequiresApi(Build.VERSION_CODES.N)
class CommentGroupView(context: Context, attrs: AttributeSet):  ConstraintLayout(context, attrs)    {
companion object{
    private const val TAG = "CommentGroupView"
}

    private val editContentList = mutableListOf<String>()
    private val adapter = EmojiAdapter(){
        editContentList.add(it)
        val sp = Html.fromHtml(it,0, EmojiImageGetter(context, 2), null)
        commentInputEt.text.insert(commentInputEt.selectionEnd, sp)
    }
    val contentView: View by lazy {
        LayoutInflater.from(context).inflate(R.layout.comment_group_view, this)
    }
        val rvEmoji:RecyclerView by lazy{
            contentView.findViewById(R.id.rv_emoji)
        }
        val commentInputEt:EditText by lazy {
            contentView.findViewById(R.id.comment_input_et)
        }
        val tvSendBtn:TextView by lazy {
            contentView.findViewById(R.id.comment_send_tv)
        }
        val ivEmojiSwitch:ImageView by lazy {
            contentView.findViewById(R.id.iv_emoji)
        }

        val ivDeleteBtn:ImageView by lazy {
                    contentView.findViewById(R.id.iv_delete)
                }
    val refreshLayout: SmartRefreshLayout by lazy {
        contentView.findViewById(R.id.srl_container)
    }

        init {
            rvEmoji.layoutManager = GridLayoutManager(context, 8, GridLayoutManager.VERTICAL, false)
            rvEmoji.adapter = adapter
            commentInputEt.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    Log.d(TAG, "beforeTextChanged: ${s?.length}")
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    Log.d(TAG, "onTextChanged:  start -> $start  before -> $before  count -> $count" )
                    s?.let {
                        Log.d(TAG, "onTextChanged: s -> $s")
                    }
                    s?.let{
                        if(before > 0 ){//删除了内容
                            deleteContent(start,start + before)
                        }

                        if(count > 0){//新增了内容
                            val res = s.subSequence(start, start+count)
                            addContent(start,start + count, res)
                        }
                    }
                    tvSendBtn.setOnClickListener {
                        ToastUtils.show(listToString(editContentList))
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                    s?.let{
                        commentInputEt.setSelection(s.length)
                        if(it.isNotEmpty()){
                            tvSendBtn.setBackgroundResource(R.drawable.send_btn_active_bg)
                            tvSendBtn.setTextColor(Color.WHITE)
                        }else{
                            tvSendBtn.setBackgroundResource(R.drawable.send_btn_normal_bg)
                            tvSendBtn.setTextColor(Color.GRAY)
                        }
                    }
                }

            })
            ivEmojiSwitch.setOnClickListener {
                rvEmoji.visibility = View.VISIBLE
            }
            rvEmoji.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                Log.d(TAG, "emoji recyclerView is slide: $scrollX  $oldScrollX")
                val deletePosition = IntArray(2)
                ivDeleteBtn.getLocationInWindow(deletePosition)
                    if(kotlin.math.abs(scrollX) > 0){
                        adapter.hideBottomRightIcon(deletePosition)
                    }
            }
            refreshLayout.setHeaderHeight(60f)
            refreshLayout.setFooterHeight(80f)
            refreshLayout.setOnRefreshListener {
                refreshLayout.finishRefresh()
            }
            refreshLayout.setOnLoadMoreListener {
                refreshLayout.finishLoadMore()
            }
        }

        private fun addContent(start: Int, count: Int, content:CharSequence) {
            for ( index in start until count){
                val intValue = content[index - start].toInt()
                if(intValue == 65532){
                    continue
                }
                editContentList.add(index, content[index - start].toString())
            }
        }

        private fun deleteContent(start: Int, before: Int) {
            for ( index in start until before ){
                editContentList.removeAt(start)
            }
        }

        private fun listToString(editContentList: MutableList<String>): String {
            return StringBuilder().run {
                for (item in editContentList) {
                    append(item)
                }
                toString()
            }

        }

}