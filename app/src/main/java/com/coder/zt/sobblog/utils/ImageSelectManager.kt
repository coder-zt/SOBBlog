package com.coder.zt.sobblog.utils

import com.luck.picture.lib.entity.LocalMedia

object ImageSelectManager {

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

    fun getImages():List<UpLoadImage>{
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

    class UpLoadImage(var upload:Boolean, val localMedia: LocalMedia, var url:String)
}