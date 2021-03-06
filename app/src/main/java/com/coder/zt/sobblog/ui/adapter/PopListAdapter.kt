package com.coder.zt.sobblog.ui.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class PopListAdapter<T:ViewDataBinding,D>(
    val layoutId:Int,
    val items:List<D>, val callback: ItemsListSetData<T, D>
):RecyclerView.Adapter<PopListAdapter.ItemView<T,D>>() {

    companion object{
        private const val TAG = "PopListAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemView<T, D> {
        val inflate = DataBindingUtil.inflate<T>(
            LayoutInflater.from(parent.context),
            layoutId,
            parent,
            false
        )
        return ItemView(inflate)
    }

    override fun onBindViewHolder(holder: ItemView<T,D>, position: Int) {
       holder.setData(items[position], callback)
    }

    override fun getItemCount() = items.size


    class ItemView<T:ViewDataBinding,D>(val inflate:T):RecyclerView.ViewHolder(inflate.root) {
        fun setData(data: D, callback:ItemsListSetData<T,D>) {
            callback.setData(inflate, data)
            inflate.root.setOnClickListener{
                callback.onClick(data)
            }
        }
    }

    interface ItemsListSetData<T:ViewDataBinding,D>{
        fun setData(inflate:T, d:D)

        fun onClick(d:D)
    }
}
