package com.example.dailymemo.OpenStream.Retrofit.Response

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class OpenStreamResponse (
    @SerializedName("isSuccess") val isSuccess : Boolean,
    @SerializedName("code") val code : String,
    @SerializedName("message") val message : String,
    @SerializedName("result") val result : OpenStreamResult

)

data class OpenStreamResult (
    @SerializedName("listSize") val listSize : Int,
    @SerializedName("hasNext") val hasNext : Boolean,
    @SerializedName("isFirst") val isFirst : Boolean,
    @SerializedName("isLast") val isLast : Boolean,
    @SerializedName("photoList") val photoList : List<photo>
)

data class photo (
    @SerializedName("postId") val postId : Int,
    @SerializedName("nickname") val nickname : String,
    @SerializedName("postImg") val postImg : List<String>,
    @SerializedName("likes") val likes : Int,
    @SerializedName("isLike") val isLike : Boolean,
    @SerializedName("comments") val comments : Int,
    @SerializedName("createdAt") val createdAt : LocalDate
)