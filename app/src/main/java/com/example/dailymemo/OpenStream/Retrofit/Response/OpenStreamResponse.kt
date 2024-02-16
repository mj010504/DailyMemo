package com.example.dailymemo.OpenStream.Retrofit.Response

import com.example.dailymemo.MyStream.Retrofit.Response.post
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
    @SerializedName("postList") val postList : List<post>
)

