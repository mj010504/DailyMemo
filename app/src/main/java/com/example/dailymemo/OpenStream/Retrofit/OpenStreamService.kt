package com.example.dailymemo.OpenStream.Retrofit

import com.example.dailymemo.OpenStream.Retrofit.Response.DiaryResponse
import com.example.dailymemo.OpenStream.Retrofit.Response.LikeResponse
import com.example.dailymemo.OpenStream.Retrofit.Response.OpenStreamResponse
import com.example.dailymemo.getRetrofit
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

class OpenStreamService {
    private lateinit var openStreamView: OpenStreamView

    fun setOpenStreamView(openStreamView: OpenStreamView) {
        this.openStreamView = openStreamView
    }

    fun showOpenStream(userId : Int, page : Int) {
        val openStreamService = getRetrofit().create(OpenStreamRetrofitInterface::class.java)


        openStreamService.showOpenStream(userId, page).enqueue(object: Callback<OpenStreamResponse> {
            override fun onResponse(
                call: Call<OpenStreamResponse>,
                response: Response<OpenStreamResponse>
            ) {
                val openStreamReponse: OpenStreamResponse = response.body()!!
                TODO("Not yet implemented")
            }

            override fun onFailure(call: Call<OpenStreamResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }


    fun search(userId : Int, query : String, page : Int) {
        val openStreamService = getRetrofit().create(OpenStreamRetrofitInterface::class.java)


        openStreamService.search(userId, query, page).enqueue(object: Callback<OpenStreamResponse> {
            override fun onResponse(
                call: Call<OpenStreamResponse>,
                response: Response<OpenStreamResponse>
            ) {
                val openStreamReponse: OpenStreamResponse = response.body()!!
                TODO("Not yet implemented")
            }

            override fun onFailure(call: Call<OpenStreamResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun showDiary(post_id : Int) {
        val openStreamService = getRetrofit().create(OpenStreamRetrofitInterface::class.java)


        openStreamService.showDiary(post_id).enqueue(object: Callback<DiaryResponse> {

            override fun onResponse(call: Call<DiaryResponse>, response: Response<DiaryResponse>) {
                val diaryResponse: DiaryResponse = response.body()!!
                TODO("Not yet implemented")
            }

            override fun onFailure(call: Call<DiaryResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun like(userId : Int, post_id : Int) {
        val openStreamService = getRetrofit().create(OpenStreamRetrofitInterface::class.java)

        openStreamService.like(userId, post_id).enqueue(object: Callback<LikeResponse> {

            override fun onResponse(call: Call<LikeResponse>, response: Response<LikeResponse>) {
                val likeReponse : LikeResponse = response.body()!!
                TODO("Not yet implemented")
            }

            override fun onFailure(call: Call<LikeResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

}