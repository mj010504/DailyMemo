package com.example.dailymemo.Auth.Retrofit

interface ResetPwView {
    fun emailVerifySuccess(token: String, code: String)
    fun emailVerifyFailed()
    fun verifySuccess()
    fun verifyFailed()
}