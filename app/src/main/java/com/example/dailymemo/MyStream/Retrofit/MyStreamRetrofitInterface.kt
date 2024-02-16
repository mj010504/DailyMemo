package com.example.dailymemo.MyStream.Retrofit


import com.example.dailymemo.MyStream.Retrofit.Response.diaryPublicTypeResponse
import com.example.dailymemo.MyStream.Retrofit.Response.makeMyStreamResponse
import com.example.dailymemo.MyStream.Retrofit.Response.modifyMyStreamRequest
import com.example.dailymemo.MyStream.Retrofit.Response.modifyMyStreamResponse
import com.example.dailymemo.MyStream.Retrofit.Response.openMyStreamResponse
import com.example.dailymemo.MyStream.Retrofit.Response.removeMyStreamDiaryResponse
import com.example.dailymemo.MyStream.Retrofit.Response.removeMyStreamResponse
import com.example.dailymemo.MyStream.Retrofit.Response.searchMyStreamResponse
import com.example.dailymemo.MyStream.Retrofit.Response.showWatchStreamResponse
import com.example.dailymemo.MyStream.Retrofit.Response.streamPublicTypeResponse
import com.example.dailymemo.OpenStream.Retrofit.Response.OpenStreamResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface MyStreamRetrofitInterface {
    @GET("stream/private/my/{streamId}")
    fun showMyStream(@Path("streamId", encoded = true) streamId : Int, @Query("userId") userId : Int, @Query("page") page : Int ) : Call<openMyStreamResponse>

    @GET("stream/private/search/{userId}")
    fun searchMystream(@Path("userId", encoded = true) userId : Int, @Query("query") query : String, @Query("page") page : Int) : Call<searchMyStreamResponse>

    @GET("stream/private/my")
    fun showWatchStream(@Query("userId") userId: Int, @Query("page") page: Int) : Call<showWatchStreamResponse>

    @POST("stream/private/my")
    fun makeMyStream(@Query("userId") userId : Int, @Query("streamName") streamName : String) : Call<makeMyStreamResponse>

    @PUT("stream/private/posts/{postId}")
    fun modifyMyStream(@Query("postId") postId : Int, @Body modifyMyStreamRequest : modifyMyStreamRequest): Call<modifyMyStreamResponse>

    @PUT("stream/private/post-visible/{userId}/{postId}")
    fun diaryPublicType(@Path("userId", encoded = true) userId : Int, @Path("postId", encoded = true) postId :Int, @Body isPublic : Boolean) : Call<diaryPublicTypeResponse>

    @PUT("stream/private/post-visible/{userId}/{streamId}")
    fun streamPublicType(@Path("userId", encoded = true) userId : Int, @Path("streamId", encoded = true) streamId :Int, @Body isPublic : Boolean) : Call<streamPublicTypeResponse>

    @DELETE("stream/private/my/{streamId}")
    fun removeMyStream(@Path("streamId", encoded = true) streamId : Int) : Call<removeMyStreamResponse>

    @DELETE("stream/private/my/post/{postId}")
    fun removeMyStreamDiary(@Path("postId", encoded = true) postId : Int) : Call<removeMyStreamDiaryResponse>
}