package com.example.dailymemo.OpenStream.Retrofit

import com.example.dailymemo.OpenStream.Retrofit.Response.ChangeCommentResponse
import com.example.dailymemo.OpenStream.Retrofit.Response.DiaryResponse
import com.example.dailymemo.OpenStream.Retrofit.Response.LikeResponse
import com.example.dailymemo.OpenStream.Retrofit.Response.OpenStreamResponse
import com.example.dailymemo.OpenStream.Retrofit.Response.RemoveCommentResponse
import com.example.dailymemo.OpenStream.Retrofit.Response.ShowCommentResponse
import com.example.dailymemo.OpenStream.Retrofit.Response.WriteCommentResponse
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

    fun writeComment(postId: Int, userId: Int, detail :String) {
        val openStreamService = getRetrofit().create(OpenStreamRetrofitInterface::class.java)

        openStreamService.writeComment(postId, userId, detail).enqueue(object: Callback<WriteCommentResponse> {

            override fun onResponse(
                call: Call<WriteCommentResponse>,
                response: Response<WriteCommentResponse>
            ) {
                TODO("Not yet implemented")
            }

            override fun onFailure(call: Call<WriteCommentResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun showComment(postId : Int, userId : Int) {
        val openStreamService = getRetrofit().create(OpenStreamRetrofitInterface::class.java)

        openStreamService.showComment(postId, userId).enqueue(object: Callback<ShowCommentResponse> {

            override fun onResponse(
                call: Call<ShowCommentResponse>,
                response: Response<ShowCommentResponse>
            ) {
                TODO("Not yet implemented")
            }

            override fun onFailure(call: Call<ShowCommentResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun removeComment(commentId : Int) {
        val openStreamService = getRetrofit().create(OpenStreamRetrofitInterface::class.java)

        openStreamService.removeComment(commentId).enqueue(object: Callback<RemoveCommentResponse> {

            override fun onResponse(
                call: Call<RemoveCommentResponse>,
                response: Response<RemoveCommentResponse>
            ) {
                TODO("Not yet implemented")
            }

            override fun onFailure(call: Call<RemoveCommentResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun changeComment(commentId : Int, userId : Int, editDetail : String) {
        val openStreamService = getRetrofit().create(OpenStreamRetrofitInterface::class.java)

        openStreamService.changeComment(commentId, userId, editDetail).enqueue(object: Callback<ChangeCommentResponse> {

            override fun onResponse(
                call: Call<ChangeCommentResponse>,
                response: Response<ChangeCommentResponse>
            ) {
                TODO("Not yet implemented")
            }

            override fun onFailure(call: Call<ChangeCommentResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}