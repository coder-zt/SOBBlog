package com.coder.zt.sobblog.ui.view

import android.content.Context
import android.os.Build
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.ui.adapter.EmojiAdapter
import com.coder.zt.sobblog.utils.EmojiImageGetter
import java.lang.StringBuilder
import java.nio.file.Watchable

@RequiresApi(Build.VERSION_CODES.N)
class CommentGroupView(context: Context, attrs: AttributeSet):  ConstraintLayout(context, attrs)    {
companion object{
    private const val TAG = "CommentGroupView"
}
    val editContent = StringBuilder()
    private var contentLen = 0
    var contentView: View = LayoutInflater.from(context).inflate(R.layout.comment_group_view, this)
    init {
        val rvEmoji:RecyclerView = contentView.findViewById(R.id.rv_emoji)
        val commentInputEt:EditText = contentView.findViewById(R.id.comment_input_et)
        rvEmoji.layoutManager = GridLayoutManager(context, 8)
        rvEmoji.adapter = EmojiAdapter(){
            editContent.append(it)
            val sp = Html.fromHtml(editContent.toString(),0, EmojiImageGetter(context, 2), null)
            (commentInputEt as TextView).text = sp
        }
        commentInputEt.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.d(TAG, "beforeTextChanged: ${s?.length}")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let{
                    Log.d(TAG, "onTextChanged: s.length = ${s.length}  start = $start befor = $before count = $count")
                }
//                s?.let {
//                   val i = it.iterator()
//                   while( i.hasNext()){
//                       Log.d(TAG, "onTextChanged: ${i.next().toInt()}")
//                       Log.d(TAG, "onTextChanged: ${commentInputEt.selectionStart}")
////                       Log.d(TAG, "onTextChanged: ${i.nextChar()}")
//                }
//                }

            }

            override fun afterTextChanged(s: Editable?) {
                Log.d(TAG, "afterTextChanged: ${s?.length}")
            }

        })
    }
}