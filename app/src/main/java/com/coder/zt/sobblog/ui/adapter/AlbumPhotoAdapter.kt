package com.coder.zt.sobblog.ui.adapter

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.coder.zt.sobblog.R
import com.coder.zt.sobblog.databinding.RvPictureItemBinding
import com.coder.zt.sobblog.utils.GlideEngine
import com.coder.zt.sobblog.utils.ImageSelectManager
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.entity.LocalMedia

class AlbumPhotoAdapter(val activity:Activity):RecyclerView.Adapter<AlbumPhotoAdapter.PictureView>(){

    companion object{
        private const val TAG = "AlbumPhotoAdapter"
    }
    private lateinit var mData:MutableList<ImageSelectManager.UpLoadImage>
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
                    .openExternalPreview(position, getLocalMedias())
            }
        }
    }

    private fun getLocalMedias(): MutableList<LocalMedia> {
        val list = mutableListOf<LocalMedia>()
        for (mDatum in mData) {
            list.add(mDatum.localMedia)
        }
        return list
    }

    override fun getItemCount(): Int {
        val size = mData.size
        return if(size == 4){
            size
        }else{
            size + 1
        }
    }


    fun setSelectListener(callback:(size:Int)->Unit){
        selectListener = callback
    }

    fun update() {
        Log.d(TAG, "update: 数据刷新")
        mData = ImageSelectManager.getImages()
        notifyDataSetChanged()
    }


    class PictureView(val bind:RvPictureItemBinding):RecyclerView.ViewHolder(bind.root) {

        fun setData(localMedia: ImageSelectManager.UpLoadImage,callback:()->Unit) {
            val decodeFile = BitmapFactory.decodeFile(localMedia.localMedia.fileName)
            bind.ivPicture.setImageBitmap(decodeFile)
            if(localMedia.upload){
                bind.ivDelete.visibility = View.VISIBLE
                bind.blvLoading.visibility = View.GONE
            }else{
                bind.ivDelete.visibility = View.GONE
                bind.blvLoading.visibility = View.VISIBLE
            }
            bind.ivAdd.visibility = View.GONE
            bind.ivPicture.visibility = View.VISIBLE
            bind.ivPicture.setImageBitmap(BitmapFactory.decodeFile(localMedia.localMedia.realPath))
            bind.ivPicture.setOnClickListener{
                callback()
            }
            bind.ivDelete.setOnClickListener{
                ImageSelectManager.deleteImage(localMedia)
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
