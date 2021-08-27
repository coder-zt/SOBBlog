package com.coder.zt.sobblog.model.datamanager

import android.util.Log
import com.coder.zt.sobblog.utils.Constants
import com.coder.zt.sobblog.utils.SPUtils

object EmojiDataMan {
    private const val EMOJI_SP_KEY = "emoji_sp_key"
    val recentlyEmojis = mutableListOf<Int>()
    init {
        val indexList = SPUtils.getInstance().readList(EMOJI_SP_KEY)
        for (index in indexList){
            if (index.isNotEmpty()) {
                recentlyEmojis.add(index.toInt())
            }
        }
    }

    /**
     * 获取是否要展示的最近使用的表情
     * 有返回1没有返回0
     */
    fun getShowRecentlyEmoji():Int{
        return if(recentlyEmojis.isNotEmpty()){1}else{0}
    }

    fun getShowEmojiTitle(position: Int): Boolean {
        return position == 0 || position == getRecentlyEmojiCount()
    }

    fun getRecentlyEmojiCount(): Int {
        return recentlyEmojis.size + getShowRecentlyEmoji()
    }

    fun getEmojiTitle(position: Int): String {
        return if (getShowRecentlyEmoji() == 1 && position == 0) {
            "最近使用"
        }else{
            "所有表情"
        }
    }


    private const val TAG = "EmojiDataMan"
    fun getEmojiIndex(position: Int): Int {
        Log.d(TAG, "getEmojiIndex: $position")
        return if (getShowRecentlyEmoji() == 1) {//有最近使用表情设置
            if(position <= getRecentlyEmojiCount()){
                recentlyEmojis[position - 1]
            }else{
                position - getRecentlyEmojiCount()
            }
        }else{//没有有最近使用表情设置
            position
        }
    }

    fun addRecentlyEmoji(num:Int):Boolean{
        if(recentlyEmojis.contains(num)){
            if (recentlyEmojis[0] == num) {
                return false
            }
            recentlyEmojis.remove(num)
        }
        if (recentlyEmojis.size == 8) {
            recentlyEmojis.removeAt(7)
        }
        recentlyEmojis.add(0, num)
//        SPUtils.getInstance().saveList(EMOJI_SP_KEY, recentlyEmojis)
        return true
    }
}