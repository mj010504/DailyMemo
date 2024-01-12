package com.example.dailymemo.Service

import retrofit2.Call
import retrofit2.http.GET

interface LoginRetrofitInterface {

    @GET("users/login")
    fun login(id : String, pw : String) : Call<LoginResponse>
}