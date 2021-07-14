package com.coder.zt.sobblog.utils

import com.luck.picture.lib.entity.LocalMedia

object ImageSelectManager {

    private val imageContainer = mutableListOf<LocalMedia>()
    fun putImages(list:List<LocalMedia>){
        imageContainer.clear()
        if (!list.isEmpty()) {
            imageContainer.addAll(list)
        }
    }

    fun putImage(list:List<LocalMedia>){
        if (!list.isEmpty()) {
            imageContainer.addAll(list)
        }
    }

    fun getImages():List<LocalMedia>{
        return imageContainer
    }

    fun clear(){
        imageContainer.clear()
    }
}