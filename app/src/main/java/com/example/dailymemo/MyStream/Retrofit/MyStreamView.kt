package com.example.dailymemo.MyStream.Retrofit

import com.example.dailymemo.MyStream.MyStreamRVAdapter
import com.example.dailymemo.MyStream.Retrofit.Response.openMyStreamResponse
import com.example.dailymemo.MyStream.Retrofit.Response.searchMyStreamResponse

interface MyStreamView {
    fun showMyStreamSuccess(resp : openMyStreamResponse)
    fun searchMyStreamSuccess(resp : searchMyStreamResponse)
    fun diaryPublicTypeSuccess(isPublic : Boolean)

    fun removeMyStreamDiarySuccess()
}