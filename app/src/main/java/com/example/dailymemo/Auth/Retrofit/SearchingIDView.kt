package com.example.dailymemo.Auth.Retrofit

interface SearchingIDView {
    fun searchingIdSuccess(id : String)
    fun searchingIdFailed()
    fun isEmailSuccess()
    fun isEmailFailed()
    fun emailVerifySuccess(token: String, code: String)
    fun emailVerifyFailed()
    fun verifySuccess()
    fun verifyFailed()
}