package com.example.dailymemo

import androidx.lifecycle.ViewModel
import com.example.dailymemo.MyStream.Retrofit.Response.post

class PostViewModel : ViewModel() {
    lateinit var post : post
    var isMyStream: Boolean = false
}