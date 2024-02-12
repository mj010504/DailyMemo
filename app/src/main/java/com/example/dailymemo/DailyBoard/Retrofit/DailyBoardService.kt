package com.example.dailymemo.DailyBoard.Retrofit

import com.example.dailymemo.DailyBoard.Retrofit.Response.ChangeStreamRequest
import com.example.dailymemo.DailyBoard.Retrofit.Response.ChangeStreamResponse
import com.example.dailymemo.DailyBoard.Retrofit.Response.onDailyBoardRemoveBtnClickResponse
import com.example.dailymemo.DailyBoard.Retrofit.Response.showDailyBoardResponse
import com.example.dailymemo.DailyBoard.Retrofit.Response.showDiaryPreviewRequest
import com.example.dailymemo.DailyBoard.Retrofit.Response.showDiaryPreviewResponse
import com.example.dailymemo.DailyBoard.Retrofit.Response.showStreamDiaryResponse
import com.example.dailymemo.DailyBoard.Retrofit.Response.storeImageResponse
import com.example.dailymemo.DailyBoard.Retrofit.Response.writeDiaryRequest
import com.example.dailymemo.DailyBoard.Retrofit.Response.writeDiaryResponse

import com.example.dailymemo.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body

class DailyBoardService {
    private lateinit var dailyBoardView: DailyBoardView

    fun setMyStreamView(dailyBoardView: DailyBoardView) {
        this.dailyBoardView = dailyBoardView
    }

    fun showDailyBoard(userId : Int, page : Int) {
        val dailyBoardService = getRetrofit().create(DailyBoardRetoriftinterface::class.java)

        dailyBoardService.showDailyBoard(userId,page).enqueue(object: Callback<showDailyBoardResponse> {
            override fun onResponse(
                call: Call<showDailyBoardResponse>,
                response: Response<showDailyBoardResponse>
            ) {

            }

            override fun onFailure(call: Call<showDailyBoardResponse>, t: Throwable) {

            }

        })
    }

    fun changeDailyBoardStream(dailyPhotoId : Int, streamName: String) {
        val dailyBoardService = getRetrofit().create(DailyBoardRetoriftinterface::class.java)

        val request = ChangeStreamRequest(dailyPhotoId, streamName)
        dailyBoardService.changeDailyBoardStream(request)
            .enqueue(object : Callback<ChangeStreamResponse> {
                override fun onResponse(
                    call: Call<ChangeStreamResponse>,
                    response: Response<ChangeStreamResponse>
                ) {

                }

                override fun onFailure(call: Call<ChangeStreamResponse>, t: Throwable) {

                }


            })
    }

    fun onDailyBoardRemoveBtnClick(dailyPhotoId: Int) {
        val dailyBoardService = getRetrofit().create(DailyBoardRetoriftinterface::class.java)

        dailyBoardService.onDailyBoardRemoveBtnClick(dailyPhotoId).enqueue(object: Callback<onDailyBoardRemoveBtnClickResponse>{
            override fun onResponse(
                call: Call<onDailyBoardRemoveBtnClickResponse>,
                response: Response<onDailyBoardRemoveBtnClickResponse>
            ) {

            }

            override fun onFailure(call: Call<onDailyBoardRemoveBtnClickResponse>, t: Throwable) {

            }

        })
    }


    fun storeImage(diaryId : Int, images: List<String>) {
        val dailyBoardService = getRetrofit().create(DailyBoardRetoriftinterface::class.java)

        dailyBoardService.storeImage(diaryId,images).enqueue(object: Callback<storeImageResponse>{
            override fun onResponse(
                call: Call<storeImageResponse>,
                response: Response<storeImageResponse>
            ) {

            }

            override fun onFailure(call: Call<storeImageResponse>, t: Throwable) {

            }
        })

    }

    fun writeDiary(streamId : Int, detail : String) {
        val dailyBoardService = getRetrofit().create(DailyBoardRetoriftinterface::class.java)

        val request = writeDiaryRequest(streamId,detail)
        dailyBoardService.writeDiary(request).enqueue(object : Callback<writeDiaryResponse>{
            override fun onResponse(
                call: Call<writeDiaryResponse>,
                response: Response<writeDiaryResponse>
            ) {

            }

            override fun onFailure(call: Call<writeDiaryResponse>, t: Throwable) {

            }

        })
    }

    fun showStreamDiary(streamId : Int) {
        val dailyBoardService = getRetrofit().create(DailyBoardRetoriftinterface::class.java)

        dailyBoardService.showStreamDiary(streamId).enqueue(object : Callback<showStreamDiaryResponse>{
            override fun onResponse(
                call: Call<showStreamDiaryResponse>,
                response: Response<showStreamDiaryResponse>
            ) {

            }

            override fun onFailure(call: Call<showStreamDiaryResponse>, t: Throwable) {

            }

        })
    }

    fun showDiaryPreview(diaryPhotoId : Int, streamId : Int) {
        val dailyBoardService = getRetrofit().create(DailyBoardRetoriftinterface::class.java)

        val request = showDiaryPreviewRequest(diaryPhotoId,streamId)
        dailyBoardService.showDiaryPreview(request).enqueue(object : Callback<showDiaryPreviewResponse>{
            override fun onResponse(
                call: Call<showDiaryPreviewResponse>,
                response: Response<showDiaryPreviewResponse>
            ) {

            }

            override fun onFailure(call: Call<showDiaryPreviewResponse>, t: Throwable) {

            }

        })
    }
}