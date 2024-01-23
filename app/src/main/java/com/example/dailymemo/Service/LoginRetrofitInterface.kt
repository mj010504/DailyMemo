package com.example.dailymemo.Service

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginRetrofitInterface {

    @POST("/users/login")
    fun login(@Query("nickName") id : String, @Query("password") pw : String) : Call<LoginResponse>

    @POST("/verification/nickname-exists")
    fun isNicknameExist(@Query("nickName") id : String) : Call<nicknameRepeatedResponse>

    @POST("/verification/email-exists")
    fun isEmailExist(@Query("email") email : String) : Call<emailRepeatedResponse>

    @POST("/users")
    fun register(@Query("name") name : String,
                 @Query("nickName") nickName : String,
                 @Query("password") password : String,
                 @Query("phoneNumber") phoneNumber: String,
                 @Query("email") email: String,
                 @Query("profilePhoto") profilePhoto: String,
                 @Query("emailVerificationToken") emailVerificationToken: String) : Call<registerResponse>
}