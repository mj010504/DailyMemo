package com.example.dailymemo.DailyBoard.Retrofit

import com.example.dailymemo.DailyBoard.Retrofit.Response.ChangeStreamRequest
import com.example.dailymemo.DailyBoard.Retrofit.Response.ChangeStreamResponse
import com.example.dailymemo.DailyBoard.Retrofit.Response.ImageRequest

import com.example.dailymemo.DailyBoard.Retrofit.Response.onDailyBoardRemoveBtnClickResponse
import com.example.dailymemo.DailyBoard.Retrofit.Response.showDailyBoardResponse
import com.example.dailymemo.DailyBoard.Retrofit.Response.showDiaryPreviewResponse

import com.example.dailymemo.DailyBoard.Retrofit.Response.showStreamDiaryResponse
import com.example.dailymemo.DailyBoard.Retrofit.Response.storeImageResponse
import com.example.dailymemo.DailyBoard.Retrofit.Response.writeDiaryRequest
import com.example.dailymemo.DailyBoard.Retrofit.Response.writeDiaryResponse
import com.example.dailymemo.OpenStream.Retrofit.Response.changeProfileImageResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface DailyBoardRetoriftinterface {
    @GET("stream/private/daliyBoard/diary")
    fun showDailyBoard(@Header("Authorization") jwt: String?,@Query("userId") userId: Int, @Query("page") page : Int) : Call<showDailyBoardResponse>

    @PUT("diaryPhoto/change/stream")
    fun changeDailyBoardStream(@Body changestreamRequest : ChangeStreamRequest) : Call<ChangeStreamResponse>

    @PUT("diaryPhoto/exclude")
    fun onDailyBoardRemoveBtnClick(@Query("dailyPhotoId") dailyPhotoId : Int ) : Call<onDailyBoardRemoveBtnClickResponse>

    @POST("stream/private/daliyBoard/photo")
    fun storeImage(@Header("Authorization") jwt: String?, @Body images: ImageRequest) : Call<storeImageResponse>

    @PUT("diary/record")
    fun writeDiary(@Body writeDiaryRequest: writeDiaryRequest) : Call<writeDiaryResponse>

    @GET("diary/record/{streamId}")
    fun showStreamDiary(@Path("streamId") streamId : Int) : Call<showStreamDiaryResponse>

    @GET("diary/preview/{streamId}")
    fun showDiaryPreview(@Path("streamId") streamId :Int) : Call<showDiaryPreviewResponse>

}