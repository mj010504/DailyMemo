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

class DailyBoardService {
    private lateinit var dailyBoardView: DailyBoardView
    private lateinit var diaryView : DiaryView

    fun setDailyBoardView(dailyBoardView: DailyBoardView) {
        this.dailyBoardView = dailyBoardView
    }

    fun setDirayView(diaryView: DiaryView) {
        this.diaryView = diaryView
    }

    fun showDailyBoard(userId : Int, page : Int) {
        val dailyBoardService = getRetrofit().create(DailyBoardRetoriftinterface::class.java)

        dailyBoardService.showDailyBoard(userId,page).enqueue(object: Callback<showDailyBoardResponse> {
            override fun onResponse(
                call: Call<showDailyBoardResponse>,
                response: Response<showDailyBoardResponse>
            ) {
                if(response.code() == 200) {
                    dailyBoardView.onShowDailyBoardSuccess(response.body()!!)
                }
                else {

                }
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
                    if(response.code() == 200) {
                        dailyBoardView.changeDailyBoardStreamSuccess()
                    }
                    else {

                    }
                }

                override fun onFailure(call: Call<ChangeStreamResponse>, t: Throwable) {

                }


            })
    }

    fun onDailyBoardRemoveBtnClick(diaryPhotoId: Int) {
        val dailyBoardService = getRetrofit().create(DailyBoardRetoriftinterface::class.java)

        dailyBoardService.onDailyBoardRemoveBtnClick(diaryPhotoId).enqueue(object: Callback<onDailyBoardRemoveBtnClickResponse>{
            override fun onResponse(
                call: Call<onDailyBoardRemoveBtnClickResponse>,
                response: Response<onDailyBoardRemoveBtnClickResponse>
            ) {
                if(response.code() == 200) {
                    dailyBoardView.onDailyBoardRemoveBtnClick(response.body()!!.result.status)
                }
                else {

                }
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
                if(response.code() == 200) {
                    dailyBoardView.storeImageSuccess(response.body()!!)
                }
                else {

                }
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
                if(response.code() == 200) {
                   diaryView.writeDiarySuccess()
                }
                else {

                }
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
                if(response.code() == 200) {
                    diaryView.showStreamDiarySuccess(response.body()!!)
                }
                else {

                }
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
                if(response.code() == 200) {
                    dailyBoardView.showDiaryPreviewSuccess(response.body()!!)
                }
                else {

                }
            }

            override fun onFailure(call: Call<showDiaryPreviewResponse>, t: Throwable) {

            }

        })
    }
}