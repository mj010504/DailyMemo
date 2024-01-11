package com.example.dailymemo.Service

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("token") val token : String,
    @SerializedName("nickName") val nickName : String
)
