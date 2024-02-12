package com.example.dailymemo.DailyBoard.Retrofit

import com.example.dailymemo.DailyBoard.Retrofit.Response.ChangeStreamRequest
import com.example.dailymemo.DailyBoard.Retrofit.Response.ChangeStreamResponse
import com.example.dailymemo.DailyBoard.Retrofit.Response.changeStreamResult
import com.example.dailymemo.DailyBoard.Retrofit.Response.onDailyBoardRemoveBtnClickResponse
import com.example.dailymemo.DailyBoard.Retrofit.Response.showDailyBoardResponse
import com.example.dailymemo.DailyBoard.Retrofit.Response.showDiaryPreviewRequest
import com.example.dailymemo.DailyBoard.Retrofit.Response.showDiaryPreviewResponse
import com.example.dailymemo.DailyBoard.Retrofit.Response.showStreamDiaryResponse
import com.example.dailymemo.DailyBoard.Retrofit.Response.storeImageResponse
import com.example.dailymemo.DailyBoard.Retrofit.Response.writeDiaryRequest
import com.example.dailymemo.DailyBoard.Retrofit.Response.writeDiaryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface DailyBoardRetoriftinterface {
    @GET("stream/private/daliyBoard/diary")
    fun showDailyBoard(@Query("userId") userId: Int, @Query("page") page : Int) : Call<showDailyBoardResponse>

    @PUT("diaryPhoto/change/stream")
    fun changeDailyBoardStream(@Body changestreamRequest : ChangeStreamRequest) : Call<ChangeStreamResponse>

    @PUT("diaryPhoto/exclude")
    fun onDailyBoardRemoveBtnClick(@Query("dailyPhotoId") dailyPhotoId : Int ) : Call<onDailyBoardRemoveBtnClickResponse>

    @POST("stream/private/daliyBoard/photo")
    fun storeImage(@Query("diaryId") diaryId : Int, @Body images: List<String>) : Call<storeImageResponse>

    @PUT("diary/record")
    fun writeDiary(@Body writeDiaryRequest: writeDiaryRequest) : Call<writeDiaryResponse>

    @GET("diary/record")
    fun showStreamDiary(@Body streamId : Int) : Call<showStreamDiaryResponse>

    @GET("diary/preview")
    fun showDiaryPreview(@Body showDiaryPreviewRequest: showDiaryPreviewRequest) : Call<showDiaryPreviewResponse>
}