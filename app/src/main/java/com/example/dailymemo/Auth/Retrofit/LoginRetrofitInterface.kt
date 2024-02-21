package com.example.dailymemo.Auth.Retrofit

import retrofit2.Call

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface LoginRetrofitInterface {

    @POST("users/login")
    fun login(@Body loginRequest: LoginRequest) : Call<LoginResponse>

    @POST("verification/nickname-exists")
    fun isNicknameExist(@Body nickName : String) : Call<nicknameRepeatedResponse>

    @POST("verification/email-exists")
    fun isEmailExist(@Body email : String) : Call<emailRepeatedResponse>

    @POST("verification/email-verification-request")
    fun emailVerificationRequest(@Body email: String) : Call<EmailVerifyResponse>

    @POST("verification/verify-email")
    fun verifyEmail(@Body verifyRequest: verifyRequest) : Call<verifyEmailResponse>


    @POST("users/findmyid")
    fun searchingIDRequest(@Body searchingIDRequest: searchingIDRequest) : Call<searchingIDResponse>

    @POST("users")
    fun register(@Body registerRequest: RegisterRequest) : Call<RegisterResponse>

    @PUT("users/resetpassword")
    fun resetPassword(@Body resetPasswordRequest : ResetPasswordRequest) : Call<AccountWithdrawalResponse>

    @PUT("users/changepassword")
    fun changePassword(@Header("Authroization") jwt : String?, @Body changePasswordRequest : ChangePasswordRequest) : Call<AccountWithdrawalResponse>

    @DELETE("users/leave")
    fun accountWithdrawal(@Header("Authorization") jwt : String?) : Call<AccountWithdrawalResponse>
}