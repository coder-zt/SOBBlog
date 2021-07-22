package com.coder.zt.sobblog.ui.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.coder.zt.sobblog.R

class ChangeDataView(context: Context, attrs: AttributeSet): ConstraintLayout(context, attrs)   {
companion object{
    private const val TAG = "ChangeDataView"
}
    var contentView:View = LayoutInflater.from(context).inflate(R.layout.change_data_view, this)

    fun setData(name:String, num:Int, changNum:Int){
        contentView.findViewById<TextView>(R.id.tv_data_name).text = name
        contentView.findViewById<TextView>(R.id.tv_data_num).text = num.toLargeNumShow()
        contentView.findViewById<TextView>(R.id.tv_data_change).text = changNum.toString()
    }

    private fun Int.toLargeNumShow():String{
        if(this > 100000000){
            return "${String.format("%.2f",(this/100000000.0))}亿"
        }else if(this > 10000){
            return "${String.format("%.1f",(this/10000.0))}万"
        }
        return this.toString()
    }

}