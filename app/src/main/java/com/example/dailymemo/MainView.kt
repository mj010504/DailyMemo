package com.example.dailymemo

import com.example.dailymemo.MyStream.Retrofit.Response.stream

interface MainView {
    fun makeMyStreamSuccess(streamId : Int, streamName : String)
    fun showWatchStreamSuccess(streamList : List<stream>)
}