package com.example.dailymemo.Auth.Service

import retrofit2.Call
import retrofit2.http.GET

interface LoginRetrofitInterface {

    @GET("users/login")
    fun login(id : String, pw : String) : Call<LoginResponse>
}