package com.example.dailymemo.OpenStream.Retrofit.Response

import com.google.gson.annotations.SerializedName

data class LikeResponse (
    @SerializedName("isSuccess") val isSuccess : Boolean,
    @SerializedName("code") val code : String,
    @SerializedName("message") val message : String,
    @SerializedName("result") val result : LikeResult
)

data class LikeResult (
    @SerializedName("code") val postId : Int,
    @SerializedName("code") val likes : Int,
    @SerializedName("code") val like_check : String,
)

