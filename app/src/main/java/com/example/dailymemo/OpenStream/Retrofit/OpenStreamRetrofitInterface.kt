package com.example.dailymemo.OpenStream.Retrofit

import com.example.dailymemo.OpenStream.Retrofit.Response.DiaryResponse
import com.example.dailymemo.OpenStream.Retrofit.Response.LikeResponse
import com.example.dailymemo.OpenStream.Retrofit.Response.OpenStreamResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface OpenStreamRetrofitInterface {

    @GET("/stream/public/{usedrId}?page={page}")
    fun showOpenStream(@Query("userId") userId : Int, @Query("page") page : Int) : Call<OpenStreamResponse>

    @GET("/stream/public/search/{userId}?query={query}&page={page}")
    fun search(@Query("userId") userId : Int, @Query("query") query : String, @Query("page") page : Int) : Call<OpenStreamResponse>

    @GET("/stream/public/posts/{post_id}")
    fun showDiary(@Query("post_id") post_id : Int) : Call<DiaryResponse>

    @POST("/stream/public/{post_id}/like/{userId}")
    fun like(@Query("post_id") post_id : Int, @Query("userId") userId: Int) : Call<LikeResponse>
}