package com.example.dailymemo.OpenStream.Retrofit.Response

import com.google.gson.annotations.SerializedName
import org.w3c.dom.Comment



data class WriteCommentResponse (
    @SerializedName("isSuccess") val isSuccess : Boolean,
    @SerializedName("code") val code : String,
    @SerializedName("message") val message : String,
    @SerializedName("result") val result : CommentResult
)



data class ShowCommentResponse (
    @SerializedName("isSuccess") val isSuccess : Boolean,
    @SerializedName("code") val code : String,
    @SerializedName("message") val message : String,
    @SerializedName("result") val result : ShowCommentResult
)

data class ShowCommentResult(
    @SerializedName("listSize") val listSize : Int,
    @SerializedName("hasNext") val hasNext : Boolean,
    @SerializedName("isFirst") val isFirst : Boolean,
    @SerializedName("isLast") val isLast : Boolean,
    @SerializedName("commentList") val commentList : List<CommentResult>
)

data class CommentResult(
    @SerializedName("commentId") val commentId : Int,
    @SerializedName("nickname") val nickName : String,
    @SerializedName("detail") var detail : String,
    @SerializedName("isAuthor") val isAuthor : Boolean
)

data class RemoveCommentResponse(
    @SerializedName("isSuccess") val isSuccess : Boolean,
    @SerializedName("code") val code : String,
    @SerializedName("message") val message : String,
    @SerializedName("result") val result : RemoveCommentResult
)

data class RemoveCommentResult(
    @SerializedName("commentSize") val commentSize : Int
)

data class ChangeCommentResponse(
    @SerializedName("isSuccess") val isSuccess : Boolean,
    @SerializedName("code") val code : String,
    @SerializedName("message") val message : String,
    @SerializedName("result") val result : CommentResult
)

