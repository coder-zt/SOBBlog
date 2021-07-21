package com.coder.zt.sobblog.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.coder.zt.sobblog.R

class ChangeDataView(context: Context, attrs: AttributeSet): ConstraintLayout(context, attrs)   {

    init {
        LayoutInflater.from(context).inflate(R.layout.change_data_view, this)
    }
}