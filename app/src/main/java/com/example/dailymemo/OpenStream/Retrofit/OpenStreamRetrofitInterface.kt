package com.example.dailymemo.OpenStream.Retrofit


import com.example.dailymemo.OpenStream.Retrofit.Response.ChangeCommentResponse
import com.example.dailymemo.OpenStream.Retrofit.Response.DiaryResponse
import com.example.dailymemo.OpenStream.Retrofit.Response.LikeResponse
import com.example.dailymemo.OpenStream.Retrofit.Response.OpenStreamResponse
import com.example.dailymemo.OpenStream.Retrofit.Response.RemoveCommentResponse
import com.example.dailymemo.OpenStream.Retrofit.Response.ShowCommentResponse
import com.example.dailymemo.OpenStream.Retrofit.Response.WriteCommentResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface OpenStreamRetrofitInterface {

    @GET("stream/public/{usedrId}?page={page}")
    fun showOpenStream(@Query("userId") userId : Int, @Query("page") page : Int) : Call<OpenStreamResponse>

    @GET("stream/public/search/{userId}?query={query}&page={page}")
    fun search(@Query("userId") userId : Int, @Query("query") query : String, @Query("page") page : Int) : Call<OpenStreamResponse>

    @GET("stream/public/posts/{post_id}")
    fun showDiary(@Query("post_id") post_id : Int) : Call<DiaryResponse>

    @POST("stream/public/{post_id}/like/{userId}")
    fun like(@Query("post_id") post_id : Int, @Query("userId") userId: Int) : Call<LikeResponse>

    @POST("comments/{postId}")
    fun writeComment(@Query("postId") postId : Int, @Body userId :Int, @Body detail : String) : Call<WriteCommentResponse>

    @GET("comments/{postId}/list/{userId}")
    fun showComment(@Query("postId") postId : Int, @Query("userId") userId : Int) : Call<ShowCommentResponse>

    @DELETE("comments/{commentId}")
    fun removeComment(@Query("commentId") commentId : Int) : Call<RemoveCommentResponse>

    @PUT("comments/{commentId}")
    fun changeComment(@Query("commentId") commentId : Int, @Body userId : Int, @Body editDetail : String) : Call<ChangeCommentResponse>
}