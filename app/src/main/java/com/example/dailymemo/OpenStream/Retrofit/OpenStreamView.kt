package com.example.dailymemo.OpenStream.Retrofit

import com.example.dailymemo.OpenStream.Retrofit.Response.LikeResponse
import com.example.dailymemo.OpenStream.Retrofit.Response.LikeResult
import com.example.dailymemo.OpenStream.Retrofit.Response.OpenStreamResponse

interface OpenStreamView {
    fun showOpenStreamSuccess(resp : OpenStreamResponse)
    fun searchOpenStreamSuccess(resp : OpenStreamResponse)


}