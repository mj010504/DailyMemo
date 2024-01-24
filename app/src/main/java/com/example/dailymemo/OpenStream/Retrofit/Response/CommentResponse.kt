package com.example.dailymemo.OpenStream.Retrofit.Response

import com.google.gson.annotations.SerializedName
import org.w3c.dom.Comment

data class WriteCommentResponse (
    @SerializedName("isSuccess") val isSuccess : Boolean,
    @SerializedName("code") val code : String,
    @SerializedName("message") val message : String,
    @SerializedName("result") val result : WriteCommentResult
)

data class WriteCommentResult (
    @SerializedName("commentId") val commentId : Int,
    @SerializedName("nickName") val nickName : String,
    @SerializedName("detail") val detail : String,
    @SerializedName("isAuthor") val isAuthor : Boolean
)

data class ShowCommentResponse (
    @SerializedName("isSuccess") val isSuccess : Boolean,
    @SerializedName("code") val code : String,
    @SerializedName("message") val message : String,
    @SerializedName("result") val result : ShowCommentResult
)

data class ShowCommentResult(
    @SerializedName("listSize") val listSize : Int,
    @SerializedName("commentList") val commentList : List<CommentResult>
)

data class CommentResult(
    @SerializedName("commentId") val commentId : Int,
    @SerializedName("nickName") val nickName : String,
    @SerializedName("detail") val detail : String,
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
    @SerializedName("result") val result : ChangeCommentResult
)

data class ChangeCommentResult(
    @SerializedName("commentId") val commentId : Int,
    @SerializedName("nickName") val nickName : String,
    @SerializedName("detail") val detail : String,
    @SerializedName("isAuthor") val isAuthor : Boolean
)