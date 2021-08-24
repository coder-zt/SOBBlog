package com.coder.zt.sobblog.model.datamanager

import android.util.Log

object EmojiDataMan {

    val recentlyEmojis = listOf<Int>(2,4,6,8,10)

    /**
     * 获取是否要展示的最近使用的表情
     * 有返回1没有返回0
     */
    fun getShowRecentlyEmoji():Int{
        return 1
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

    fun getShowEmojiIndex(position: Int): Int {
        return if (getShowRecentlyEmoji() == 1) {//有最近使用表情设置
            position - 2 - getRecentlyEmojiCount()
        }else{//没有有最近使用表情设置
            position - 2
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
            position - 1
        }
    }
}