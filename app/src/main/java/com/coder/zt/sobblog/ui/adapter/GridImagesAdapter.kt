package com.coder.zt.sobblog.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.updateLayoutParams
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.RvGridIvBinding
import com.coder.zt.sobblog.databinding.RvMoyuBinding
import com.coder.zt.sobblog.utils.ScreenUtils

class GridImagesAdapter(val picSize:Int,val images:List<String>) : RecyclerView.Adapter<GridImagesAdapter.ItemView>() {

    companion object{
        private const val TAG = "GridImagesAdapter"
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemView {
        val inflate = DataBindingUtil.inflate<RvGridIvBinding>(
            LayoutInflater.from(parent.context),
            R.layout.rv_grid_iv,
            parent,
            false
        )
        return ItemView(inflate, picSize)
    }

    override fun onBindViewHolder(holder: ItemView, position: Int) {
       holder.setImageSrc(images[position])
    }

    override fun getItemCount(): Int = picSize

    class ItemView(val viewBinding: RvGridIvBinding, val picSize: Int): RecyclerView.ViewHolder(viewBinding.root) {

        fun setImageSrc(url: String) {
            Log.d(TAG, "setImageSrc: $url")
            viewBinding.data = url
            val width = if(picSize > 1){
                80
            }else{
                320
            }
            viewBinding.image.updateLayoutParams {
                this.width = ScreenUtils.dp2px(width)
                this.height = ScreenUtils.dp2px(width)
            }
        }

    }
}
