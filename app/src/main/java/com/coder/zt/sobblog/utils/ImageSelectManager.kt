package com.coder.zt.sobblog.utils

import android.util.Log
import com.luck.picture.lib.entity.LocalMedia

object ImageSelectManager {
    private const val TAG = "ImageSelectManager"
    private val imageContainer = mutableListOf<UpLoadImage>()
    fun putImages(list:List<LocalMedia>){
        imageContainer.clear()
        if (list.isNotEmpty()) {
            for (localMedia in list) {
                imageContainer.add(UpLoadImage(false, localMedia,""))
            }
        }
    }

    fun putImage(list:List<LocalMedia>):List<UpLoadImage>{
        val addImages = mutableListOf<UpLoadImage>()
        if (list.isNotEmpty()) {
            for (localMedia in list) {
                val image = UpLoadImage(false, localMedia,"")
                addImages.add(image)
                imageContainer.add(image)
            }
        }
        return addImages
    }

    fun getImages():MutableList<UpLoadImage>{
        return imageContainer
    }

    fun clear(){
        imageContainer.clear()
    }

    fun getImagesUrl(): List<String>? {
        val urlList = mutableListOf<String>()
        for (upLoadImage in imageContainer) {
            if(!upLoadImage.upload){
                ToastUtils.showError("有图片没有上传成功!")
                return null
            }
            urlList.add(upLoadImage.url)
        }
        return urlList
    }

    val callbacks = mutableListOf<()->Unit>()
    fun registerListener(function: () -> Unit) {
        callbacks.add(function)
    }

    fun unregisterListener(function: () -> Unit){
        var index = 0
        for (callback in callbacks) {
            if(callback == function){
                break
            }
            index++
        }
        callbacks.removeAt(index)
    }

    fun update(){
//        Log.d(TAG, "update: 图片数据更新")
        for (callback in callbacks) {
            callback()
        }
    }


    fun deleteImage(localMedia: UpLoadImage) {
        imageContainer.remove(localMedia)
        update()
    }


    class UpLoadImage(var upload:Boolean, val localMedia: LocalMedia, var url:String)
}