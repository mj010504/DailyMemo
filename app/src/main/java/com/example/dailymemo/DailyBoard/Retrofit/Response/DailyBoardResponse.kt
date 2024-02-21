package com.example.dailymemo.DailyBoard.Retrofit.Response

import com.example.dailymemo.MyStream.Retrofit.Response.post
import com.example.dailymemo.MyStream.Retrofit.Response.serachMyStreamResult
import com.google.gson.annotations.SerializedName

data class showDailyBoardResponse(
    @SerializedName("isSuccess") val isSuccess : Boolean,
    @SerializedName("code") val code : String,
    @SerializedName("message") val message : String,
    @SerializedName("result") val result : showDailyBoardResult
)

data class showDailyBoardResult(
    @SerializedName("listSize") val listSize : Int,
    @SerializedName("hasNext") val hasNext : Boolean,
    @SerializedName("isFirst") val isFirst : Boolean,
    @SerializedName("isLast") val isLast : Boolean,
    @SerializedName("diaryList") val diaryList : List<Diary>
)

data class Diary(
    @SerializedName("id") val id  : Int,
    @SerializedName("detail") val detail : String,
    @SerializedName("isPublic") val isPublic: Boolean,
    @SerializedName("diaryPhotoList") val diaryPhotoList: List<DiaryPhoto>
)


data class ChangeStreamRequest(
    @SerializedName("dailyPhotoId") val dailyPhotoId: Int,
    @SerializedName("streamId") val streamId: Int
)

data class ChangeStreamResponse(
    @SerializedName("isSuccess") val isSuccess : Boolean,
    @SerializedName("code") val code : String,
    @SerializedName("message") val message : String,
    @SerializedName("result") val result : changeStreamResult
)

data class changeStreamResult(
    @SerializedName("dailyPhotoId") val dailyPhotoId : Int,
    @SerializedName("status") val status : Boolean,
    @SerializedName("streamName") val streamName: String
)

data class onDailyBoardRemoveBtnClickResponse(
    @SerializedName("isSuccess") val isSuccess : Boolean,
    @SerializedName("code") val code : String,
    @SerializedName("message") val message : String,
    @SerializedName("result") val result : onDailyBoardRemoveBtnClickResult
)

data class onDailyBoardRemoveBtnClickResult(
    @SerializedName("dailyPhotoId") val dailyPhotoId : Int,
    @SerializedName("status") val status : Boolean,
    @SerializedName("streamName") val streamName: String
)

data class ImageRequest(
    val images: List<String>
)

data class storeImageResponse(
    @SerializedName("isSuccess") val isSuccess : Boolean,
    @SerializedName("code") val code : String,
    @SerializedName("message") val message : String,
    @SerializedName("result") val result : storeImageResult
)

data class storeImageResult(
    @SerializedName("id") val id : Int,
    @SerializedName("detail") val detail : String,
    @SerializedName("isPublic") var isPublic: Boolean,
    @SerializedName("diaryPhotoList") val diaryPhotoList : List<DiaryPhoto>
)

data class DiaryPhoto(
    @SerializedName("id") val id : Int,
    @SerializedName("url") val url : String,
    @SerializedName("status") var status : Boolean,
)

data class writeDiaryRequest(
    @SerializedName("streamId") val streamId : Int,
    @SerializedName("detail") val detail : String
)

data class writeDiaryResponse(
    @SerializedName("isSuccess") val isSuccess : Boolean,
    @SerializedName("code") val code : String,
    @SerializedName("message") val message : String,
    @SerializedName("result") val result : writeDiaryResult
)

data class writeDiaryResult(
    @SerializedName("streamId") val streamId : Int,
    @SerializedName("streamImg") val streamImg : String,
    @SerializedName("detail") val detail: String
)

data class showStreamDiaryResponse(
    @SerializedName("isSuccess") val isSuccess : Boolean,
    @SerializedName("code") val code : String,
    @SerializedName("message") val message : String,
    @SerializedName("result") val result : showStreamDiaryResult
)

data class showStreamDiaryResult(
    @SerializedName("streamId") val streamId : Int,
    @SerializedName("streamImg") val streamImg : String,
    @SerializedName("detail") val detail: String?
)


data class showDiaryPreviewResponse(
    @SerializedName("isSuccess") val isSuccess : Boolean,
    @SerializedName("code") val code : String,
    @SerializedName("message") val message : String,
    @SerializedName("result") val result : showDiaryPreviewResult
)

data class showDiaryPreviewResult(
    @SerializedName("straemId") val streamId : Int,
    @SerializedName("streamName") val streamName: String,
    @SerializedName("imageList") val imageList : List<String>,
    @SerializedName("imgSize") val imgSize: Int,
    @SerializedName("detail") val detail : String,
    @SerializedName("createdAt") val createdAt : String
)