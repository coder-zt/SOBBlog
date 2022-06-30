package com.coder.zt.sobblog.ui.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.coder.zt.sobblog.databinding.ItemEmptyBinding
import com.coder.zt.sobblog.databinding.ItemErrorBinding
import com.coder.zt.sobblog.databinding.ItemLoadingBinding
import com.coder.zt.sobblog.ui.adapter.state.EmptyVH
import com.coder.zt.sobblog.ui.adapter.state.ErrorVH
import com.coder.zt.sobblog.ui.adapter.state.LoadingVH
import com.coder.zt.sobblog.ui.view.layoutInflater
import com.coder.zt.sobblog.utils.ToastUtils

/**
 * recyclerview的状态适配器
 * 显示状态：
 *      加载中
 *      加载完成
 *      数据为空
 *      加载错误
 */
private const val TAG = "StateAdapter"

abstract class StateAdapter<T>: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var mState: State
    private lateinit var mRecyclerView: RecyclerView
    protected val mData:MutableList<T> = mutableListOf()

    
//    init {
//        setLoadState(State.Loading)
//    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        Log.d(TAG, "onAttachedToRecyclerView: ")
        mRecyclerView = recyclerView
        setLoadState(State.Loading)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(mState){
            State.Empty ->{
                val viewBinding = 
                    ItemEmptyBinding.inflate(parent.layoutInflater, parent, false)
                EmptyVH(viewBinding)
            } 
            State.Loading -> {
                val viewBinding =
                    ItemLoadingBinding.inflate(parent.layoutInflater, parent, false)
                LoadingVH(viewBinding)
            }
            State.Error -> {
                val viewBinding =
                    ItemErrorBinding.inflate(parent.layoutInflater, parent, false)
                ErrorVH(viewBinding)
            }
            State.Loaded -> {
                getContentVH(parent, viewType)
            }
        }
    }

    abstract fun getContentVH(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ErrorVH -> {
                holder.let { 
                    it.setOnTryListener { onTry() }
                }
            }
            else -> bindContentView(holder, position)
        }
    }

    fun onTry(){
        
    }

    abstract fun bindContentView(holder: RecyclerView.ViewHolder, position: Int)

    override fun getItemCount(): Int {
        return when(mState){
            State.Empty, State.Loading,State.Error -> 1
            State.Loaded -> mData.size
        }
    }

    fun setData(data:List<T>?){
        if(data == null){
            setLoadState(State.Error)
        }else{
            Log.d(TAG, "setData: ${data.size}")
            mData.addAll(data)
            if(data.isEmpty()){
                setLoadState(State.Empty)
            }else{
                setLoadState(State.Loaded)
            }
        }
    }

    fun addData(data:List<T>?){
        if(data == null){
            ToastUtils.show("加载更多失败.")
        }else{
            mData.addAll(data)
            if(data.isEmpty()){
                ToastUtils.show("没有更多数据.")
            }else{
                notifyDataSetChanged()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setLoadState(state: State){
        Log.d(TAG, "setLoadState: $state")
        mRecyclerView.removeAllViews()
        mState = state
        notifyDataSetChanged()
    }
}

public enum class State{
    Loading,Loaded,Empty,Error
}