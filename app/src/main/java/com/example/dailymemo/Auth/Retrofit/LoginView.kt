package com.example.dailymemo.Auth.Retrofit

interface LoginView {
    fun loginSuccess(token : String, userId : Int)
    fun loginFailed()
}