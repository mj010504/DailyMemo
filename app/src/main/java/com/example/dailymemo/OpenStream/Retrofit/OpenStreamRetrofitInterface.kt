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
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface OpenStreamRetrofitInterface {



    @GET("stream/public")
    fun showOpenStream(@Header("Authorization") jwt : String?, @Query("page") page : Int) : Call<OpenStreamResponse>

    @GET("stream/public/search")
    fun search(@Header("Authorization") jwt : String?, @Query("query") query : String, @Query("page") page : Int) : Call<OpenStreamResponse>

    @GET("stream/public/posts/{postId}")
    fun showDiary(@Path("postId", encoded = true) post_id : Int) : Call<DiaryResponse>

    @POST("likes/{postId}")
    fun like(@Header("Authorization") jwt : String?, @Path("postId", encoded = true) postId : Int) : Call<LikeResponse>

    @POST("comments/{postId}")
    fun writeComment(@Header("Authorization") jwt : String?,@Path("postId", encoded = true) postId : Int, @Body detail : String) : Call<WriteCommentResponse>

    @GET("comments/list/{postId}")
    fun showComment(@Header("Authorization") jwt : String?,@Path("postId", encoded = true) postId : Int, @Query("page") page : Int) : Call<ShowCommentResponse>

    @DELETE("comments/{commentId}")
    fun removeComment(@Header("Authorization") jwt : String?,@Path("commentId", encoded = true) commentId : Int) : Call<RemoveCommentResponse>

    @PUT("comments/{commentId}")
    fun changeComment(@Header("Authorization") jwt : String?,@Path("commentId", encoded = true) commentId : Int, @Body detail : String) : Call<ChangeCommentResponse>
}