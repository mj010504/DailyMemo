package com.example.dailymemo.OpenStream.Retrofit.Response

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class DiaryResponse (
    @SerializedName("isSuccess") val isSuccess : Boolean,
    @SerializedName("code") val code : String,
    @SerializedName("message") val message : String,
    @SerializedName("result") val result : DiaryResult

)

data class DiaryResult (
    @SerializedName("postId") val postId : Int,
    @SerializedName("streamName") val streamName : String,
    @SerializedName("postImg") val postImg : List<String>,
    @SerializedName("detail") val detail : String,
    @SerializedName("likes") val likes : Int,
    @SerializedName ("comments") val comments : Int,
    @SerializedName("isPublic") val isPublic : Boolean,
    @SerializedName("createdAt") val createdAt : String
)

