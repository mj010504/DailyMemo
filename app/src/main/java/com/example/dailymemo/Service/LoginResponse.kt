package com.example.dailymemo.Service

import com.google.gson.annotations.SerializedName
import java.io.Serial
import java.time.LocalDateTime

data class LoginResponse(
    @SerializedName("token") val token : String,
    @SerializedName("nickName") val nickName : String
)

data class nicknameRepeatedResponse(
    @SerializedName("isSuccess") val isSuccess :Boolean,
    @SerializedName("code") val code : String,
    @SerializedName("message") val message : String,
    @SerializedName("isExists") val isExists : Boolean
)

data class emailRepeatedResponse(
    @SerializedName("isSuccess") val isSuccess :Boolean,
    @SerializedName("code") val code : String,
    @SerializedName("message") val message : String,
    @SerializedName("isExists") val isExists : Boolean
)

data class registerResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result.id") val id: Int,
    @SerializedName("result.createdAt") val date: LocalDateTime
)
