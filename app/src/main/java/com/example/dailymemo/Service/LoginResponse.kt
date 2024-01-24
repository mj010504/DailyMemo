package com.example.dailymemo.Service

import com.google.gson.annotations.SerializedName
import retrofit2.http.Query
import java.io.Serial
import java.time.LocalDateTime

data class LoginRequest(
    @SerializedName("nickName") val id : String,
    @SerializedName("password") val pw : String
)
data class LoginResponse(
    @SerializedName("token") val token : String,
    @SerializedName("nickName") val nickName : String
)

data class nicknameRepeatedRequest(
    @SerializedName("nickName") val nickname : String
)
data class nicknameRepeatedResponse(
    @SerializedName("isSuccess") val isSuccess :Boolean,
    @SerializedName("code") val code : String,
    @SerializedName("message") val message : String,
    @SerializedName("isExists") val isExists : Boolean
)

data class emailRepeatedRequest(
    @SerializedName("email") val email: String
)

data class emailRepeatedResponse(
    @SerializedName("isSuccess") val isSuccess :Boolean,
    @SerializedName("code") val code : String,
    @SerializedName("message") val message : String,
    @SerializedName("isExists") val isExists : Boolean
)

data class EmailVerifyRequest(
    @SerializedName("email") val email: String
)

data class EmailVerify_result(
    @SerializedName("token") val token: String,
    @SerializedName("expireAt") val expireAt: LocalDateTime
)
data class EmailVerifyResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: EmailVerify_result
)

data class RegisterRequest(
    @SerializedName("name") val name : String,
    @SerializedName("nickName") val nickName : String,
    @SerializedName("password") val password : String,
    @SerializedName("phoneNumber") val phoneNumber: String,
    @SerializedName("email") val email: String,
    @SerializedName("profilePhoto") val profilePhoto: String,
    @SerializedName("emailVerificationToken") val emailVerificationToken: String
)
data class RegisterResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result.id") val id: Int,
    @SerializedName("result.createdAt") val date: LocalDateTime
)

data class searchingIDRequest(
    @SerializedName("name") val name : String,
    @SerializedName("email") val email : String,
    @SerializedName("emailVerificationToken") val emailVerificationToken: String
)

data class searchingIDResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String
)
