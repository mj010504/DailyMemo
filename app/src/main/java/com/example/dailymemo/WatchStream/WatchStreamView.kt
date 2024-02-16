package com.example.dailymemo.WatchStream

import com.example.dailymemo.OpenStream.Retrofit.Response.DiaryResult
import com.example.dailymemo.OpenStream.Retrofit.Response.LikeResult

interface WatchStreamView {
    fun showDiarySuccess(resp : DiaryResult)
    fun onLikeBtnClickSuccess(resp : LikeResult)
}