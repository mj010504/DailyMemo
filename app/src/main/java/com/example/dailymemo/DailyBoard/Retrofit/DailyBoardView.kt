package com.example.dailymemo.DailyBoard.Retrofit

import com.example.dailymemo.DailyBoard.Retrofit.Response.showDailyBoardResponse
import com.example.dailymemo.DailyBoard.Retrofit.Response.showDiaryPreviewResponse
import com.example.dailymemo.DailyBoard.Retrofit.Response.storeImageResponse

interface DailyBoardView {
    fun onShowDailyBoardSuccess(resp : showDailyBoardResponse)
    fun storeImageSuccess(resp : storeImageResponse)
    fun showDiaryPreviewSuccess(resp : showDiaryPreviewResponse)
    fun changeDailyBoardStreamSuccess()
    fun onDailyBoardRemoveBtnClick(status : Boolean)
}