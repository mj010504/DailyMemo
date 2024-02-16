package com.example.dailymemo.DailyBoard.Retrofit

import com.example.dailymemo.DailyBoard.Retrofit.Response.showStreamDiaryResponse

interface DiaryView {
    fun writeDiarySuccess()
    fun showStreamDiarySuccess(resp : showStreamDiaryResponse)
}