package com.example.dailymemo.OpenStream.Retrofit.Response

import com.google.gson.annotations.SerializedName

data class LikeResponse (
    @SerializedName("isSuccess") val isSuccess : Boolean,
    @SerializedName("code") val code : String,
    @SerializedName("message") val message : String,
    @SerializedName("result") val result : LikeResult
)

data class LikeResult (
    @SerializedName("postId") val postId : Int,
    @SerializedName("likes") val likes : Int,
    @SerializedName("like_check") val like_check : Boolean,
)

