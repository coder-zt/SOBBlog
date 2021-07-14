package com.coder.zt.sobblog.ui.adapter

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.RvPictureItemBinding
import com.coder.zt.sobblog.utils.GlideEngine
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.entity.LocalMedia

class AlbumPhotoAdapter(val activity:Activity):RecyclerView.Adapter<AlbumPhotoAdapter.PictureView>(){

    private val mData:MutableList<LocalMedia> = mutableListOf()
    private lateinit var selectListener:(size:Int)->Unit
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PictureView {
        val inflate = DataBindingUtil.inflate<RvPictureItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.rv_picture_item,
            parent,
            false
        )
        return PictureView(inflate)
    }

    override fun onBindViewHolder(holder: PictureView, position: Int) {
        if(mData.size < 4 && position == mData.size){
            holder.setData(position, selectListener)
        }else{
            holder.setData(mData[position]){
                PictureSelector.create(activity)
                    .themeStyle(R.style.picture_default_style)
                    .isNotPreviewDownload(true)
                    .imageEngine(GlideEngine.createGlideEngine())
                    .openExternalPreview(position, mData)
            }
        }
    }

    override fun getItemCount(): Int {
        val size = mData.size
        return if(size == 4){
            size
        }else{
            size + 1
        }
    }

    fun setData(images: List<LocalMedia>) {
        mData.clear()
        if (images.isNotEmpty()) {
            mData.addAll(images)
        }
        notifyDataSetChanged()
    }


    fun addData(images: List<LocalMedia>) {
        if (images.isNotEmpty()) {
            mData.addAll(images)
        }
        notifyDataSetChanged()
    }

    fun setSelectListener(callback:(size:Int)->Unit){
        selectListener = callback
    }


    class PictureView(val bind:RvPictureItemBinding):RecyclerView.ViewHolder(bind.root) {

        fun setData(localMedia: LocalMedia,callback:()->Unit) {
//            localMedia.path
            val decodeFile = BitmapFactory.decodeFile(localMedia.realPath)
            bind.ivPicture.setImageBitmap(decodeFile)
            bind.blvLoading.visibility = View.GONE
            bind.ivAdd.visibility = View.GONE
            bind.ivDelete.visibility = View.GONE
            bind.ivPicture.setOnClickListener{
                callback()
            }
        }


        fun setData(position:Int, callback:(size:Int)->Unit) {
            bind.blvLoading.visibility = View.GONE
            bind.ivDelete.visibility = View.GONE
            bind.ivPicture.visibility = View.GONE
            bind.ivAdd.visibility = View.VISIBLE
            bind.ivAdd.setOnClickListener{
                callback(4-position)
            }
        }

    }
}
