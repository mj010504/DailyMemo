package com.example.dailymemo.WatchStream.Comment

import com.example.dailymemo.OpenStream.Retrofit.Response.CommentResult
import com.example.dailymemo.OpenStream.Retrofit.Response.ShowCommentResponse


interface CommentView {
    fun showCommentSuccess(resp: ShowCommentResponse)
    fun writeCommentSuccess(resp : CommentResult)
    fun changeCommentSuccess(resp: CommentResult, detail : String)
    fun removeCommentSuccess(commentId : Int)
}