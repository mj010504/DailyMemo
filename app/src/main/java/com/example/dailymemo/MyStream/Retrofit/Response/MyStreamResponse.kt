package com.example.dailymemo.MyStream.Retrofit.Response

import com.example.dailymemo.OpenStream.Retrofit.Response.OpenStreamResult
import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class openMyStreamResponse(
@SerializedName("isSuccess") val isSuccess : Boolean,
@SerializedName("code") val code : String,
@SerializedName("message") val message : String,
@SerializedName("result") val result : MyStreamResult
)

data class MyStreamResult(
    @SerializedName("listSize") val listSize : Int,
    @SerializedName("hasNext") val hasNext : Boolean,
    @SerializedName("isFirst") val isFirst : Boolean,
    @SerializedName("isLast") val isLast : Boolean,
    @SerializedName("postList") val postList : List<post>
)

data class post(
    @SerializedName("postId") val postId : Int,
    @SerializedName("nickname") val nickname : String,
    @SerializedName("postImg") val postImg : List<String>,
    @SerializedName("likes") val likes : Int,
    @SerializedName("isLike") val isLike : Boolean,
    @SerializedName("comments") val comments : Int,
    @SerializedName("createdAt") val createdAt : String,
    @SerializedName("isPublic") var isPublic: Boolean
)

data class searchMyStreamResponse(
    @SerializedName("isSuccess") val isSuccess : Boolean,
    @SerializedName("code") val code : String,
    @SerializedName("message") val message : String,
    @SerializedName("result") val result : serachMyStreamResult
)

data class serachMyStreamResult(
    @SerializedName("listSize") val listSize : Int,
    @SerializedName("hasNext") val hasNext : Boolean,
    @SerializedName("isFirst") val isFirst : Boolean,
    @SerializedName("isLast") val isLast : Boolean,
    @SerializedName("postList") val postList : List<post>
)


data class showWatchStreamResponse(
    @SerializedName("isSuccess") val isSuccess : Boolean,
    @SerializedName("code") val code : String,
    @SerializedName("message") val message : String,
    @SerializedName("result") val result : showWatchStreamResult
)

data class showWatchStreamResult(
    @SerializedName("listSize") val listSize : Int,
    @SerializedName("hasNext") val hasNext : Boolean,
    @SerializedName("isFirst") val isFirst : Boolean,
    @SerializedName("isLast") val isLast : Boolean,
    @SerializedName("streamList") val streamList : List<stream>
)

data class stream(
    @SerializedName("streamId") val streamId : Int,
    @SerializedName("userId") val userId : Int,
    @SerializedName("streamName") val streamName : String,
    @SerializedName("isPublic") val isPublic : Boolean
)

data class makeMyStreamResponse(
    @SerializedName("isSuccess") val isSuccess : Boolean,
    @SerializedName("code") val code : String,
    @SerializedName("message") val message : String,
    @SerializedName("result") val result : makeMyStreamResult
)

data class makeMyStreamResult(
    @SerializedName("streamId") val streamId : Int,
    @SerializedName("userId") val userId : Int,
    @SerializedName("streamName") val streamName : String,
    @SerializedName("isPublic") var isPublic : Boolean
)

data class modifyMyStreamRequest(
    @SerializedName("userId") val userId : Int,
    @SerializedName("detail") val detail : String,
)

data class modifyMyStreamResponse(
    @SerializedName("isSuccess") val isSuccess : Boolean,
    @SerializedName("code") val code : String,
    @SerializedName("message") val message : String,
    @SerializedName("result") val result : modifyMyStreamResult
)

data class modifyMyStreamResult(
    @SerializedName("postId") val postId : Int,
    @SerializedName("streamName") val streamName : String,
    @SerializedName("postImg") val postImg : List<String>,
    @SerializedName("detail") val detail : String,
    @SerializedName("likes") val likes : Int
)

data class diaryPublicTypeResponse(
    @SerializedName("isSuccess") val isSuccess : Boolean,
    @SerializedName("code") val code : String,
    @SerializedName("message") val message : String,
    @SerializedName("result") val result : diaryPublicTypeResult
)

data class diaryPublicTypeResult(
    @SerializedName("postId") val postId : Int,
    @SerializedName("streamName") val streamName : String,
    @SerializedName("postImg") val postImg : List<String>,
    @SerializedName("detail") val detail : String,
    @SerializedName("likes") val likes : Int,
    @SerializedName("comments") val comments : Int,
    @SerializedName("isPublic") var isPublic : Boolean,
    @SerializedName("createdAt") val createdAt: String
)

data class streamPublicTypeResponse(
    @SerializedName("isSuccess") val isSuccess : Boolean,
    @SerializedName("code") val code : String,
    @SerializedName("message") val message : String,
    @SerializedName("result") val result : streamPublicTypeResult
)

data class streamPublicTypeResult(
    @SerializedName("postId") val postId : Int,
    @SerializedName("streamName") val streamName : String,
    @SerializedName("postImg") val postImg : List<String>,
    @SerializedName("detail") val detail : String,
    @SerializedName("likes") val likes : Int,
    @SerializedName("comments") val comments : Int,
    @SerializedName("isPublic") var isPublic : Boolean,
    @SerializedName("createdAt") val createdAt: String
)

data class removeMyStreamResponse(
    @SerializedName("isSuccess") val isSuccess : Boolean,
    @SerializedName("code") val code : String,
    @SerializedName("message") val message : String,
    @SerializedName("result") val result : Int //삭제한 streamId
)

data class removeMyStreamDiaryResponse(
    @SerializedName("isSuccess") val isSuccess : Boolean,
    @SerializedName("code") val code : String,
    @SerializedName("message") val message : String,
    @SerializedName("result") val result : Int //삭제한 postId
)

