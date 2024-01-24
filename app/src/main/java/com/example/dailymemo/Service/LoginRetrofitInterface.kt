package com.example.dailymemo.Service

import retrofit2.Call

import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginRetrofitInterface {

    @POST("users/login")
    fun login(@Body loginRequest: LoginRequest) : Call<LoginResponse>

    @POST("verification/nickname-exists")
    fun isNicknameExist(@Body nickname : nicknameRepeatedRequest) : Call<nicknameRepeatedResponse>

    @POST("verification/email-exists")
    fun isEmailExist(@Body email : emailRepeatedRequest) : Call<emailRepeatedResponse>

    @POST("email-verification-request")
    fun emailVerificationRequest(@Body emailVerifyRequest: EmailVerifyRequest) : Call<EmailVerifyResponse>

    @POST("users/findmyid")
    fun searchingIDRequest(@Body searchingIDRequest: searchingIDRequest) : Call<searchingIDResponse>

    @POST("users")
    fun register(@Body registerRequest: RegisterRequest) : Call<RegisterResponse>
}