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

data class changeProfileImageResponse(
    @SerializedName("isSuccess") val isSuccess : Boolean,
    @SerializedName("code") val code : String,
    @SerializedName("message") val message : String,
    @SerializedName("result") val result : changeProfileImageResult
)

data class changeProfileImageResult(
    @SerializedName("profilePhoto") val profilePhoto : String
)

data class ChangeProfileRequest(
    @SerializedName("profilePhoto") val profilePhoto: String
)

data class ChangeStreamNameReqeust(
    @SerializedName("keyword") val keyword: String,
    @SerializedName("streamId") val streamId : Int
)

data class changeStreamNameResponse(
    @SerializedName("isSuccess") val isSuccess : Boolean,
    @SerializedName("code") val code : String,
    @SerializedName("message") val message : String,
    @SerializedName("result") val result : Boolean
)

