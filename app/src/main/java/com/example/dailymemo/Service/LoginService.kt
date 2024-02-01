package com.example.dailymemo.Service

import android.util.Log
import com.example.dailymemo.Auth.LoginView
import com.example.dailymemo.Auth.SearchingIDView
import com.example.dailymemo.Auth.SearchingIdFragment
import com.example.dailymemo.Auth.SignUpFragment
import com.example.dailymemo.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.sign

class LoginService {

    private lateinit var loginView : LoginView

    private lateinit var signupView : SignUpView

    private lateinit var searchingIDView : SearchingIDView


    fun setLoginView(loginView: LoginView){
        this.loginView = loginView
    }


    fun setSignupView(signupView : SignUpView){
        this.signupView = signupView
    }

    fun setSearchingIdView(searchingIdView: SearchingIdFragment){
        this.searchingIDView = searchingIdView
    }

    fun login(id : String, pw : String){
        val loginService = getRetrofit().create(LoginRetrofitInterface::class.java)

        val body : LoginRequest = LoginRequest(id,pw)
        loginService.login(body).enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                Log.i("LoginService", response.toString())
                if(response.code() == 200){
                    loginView.loginSuccess()
                }
                else {
                    loginView.loginFailed()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

            }
        })
    }
    fun isNicknameExist(id : String){
        val loginService = getRetrofit().create(LoginRetrofitInterface::class.java)

        loginService.isNicknameExist(id).enqueue(object : Callback<nicknameRepeatedResponse>{
            override fun onResponse( call: Call<nicknameRepeatedResponse>,response: Response<nicknameRepeatedResponse> ) {
                if(response.code() == 200){
                    if(response.body()?.result!!.exsists == false){
                        signupView.isNickNameSuccess()
                    }
                    else{
                        signupView.isNickNameFailed()
                    }
                }
            }
            override fun onFailure(call: Call<nicknameRepeatedResponse>, t: Throwable) {
                Log.d("failed", "404 nickName")
            }
        })
    }

    fun isEmailExist(email : String){
        val loginService = getRetrofit().create(LoginRetrofitInterface::class.java)

        loginService.isEmailExist(email).enqueue(object : Callback<emailRepeatedResponse>{
            override fun onResponse( call: Call<emailRepeatedResponse>, response: Response<emailRepeatedResponse>) {

                if(response.code()==200) {
                    if (response.body()?.isExists == false) {
                        signupView.isEmailSuccess()
                    }
                    else{
                        signupView.isEmailFailed()
                    }
                }
            }

            override fun onFailure(call: Call<emailRepeatedResponse>, t: Throwable) {

            }
        })
    }

    fun searchingIdIsEmailExist(email : String){
        val loginService = getRetrofit().create(LoginRetrofitInterface::class.java)

        loginService.isEmailExist(email).enqueue(object : Callback<emailRepeatedResponse>{
            override fun onResponse( call: Call<emailRepeatedResponse>, response: Response<emailRepeatedResponse>) {

                if(response.code()==200) {
                    if (response.body()?.isExists == false) {
                        searchingIDView.isEmailSuccess()
                    }
                    else{
                        searchingIDView.isEmailFailed()
                    }
                }
            }

            override fun onFailure(call: Call<emailRepeatedResponse>, t: Throwable) {

            }
        })
    }


    // 이메일 인증 요청
    fun emailVerificationRequest(email: String){
        val loginService = getRetrofit().create(LoginRetrofitInterface::class.java)

        loginService.emailVerificationRequest(email).enqueue(object : Callback<EmailVerifyResponse>{
            override fun onResponse(
                call: Call<EmailVerifyResponse>,
                response: Response<EmailVerifyResponse>
            ) {
                if(response.code() == 200){
                    signupView.emailVerifySuccess(response.body()!!.result!!.token, response.body()!!.result!!.code)
                }
                else {
                    signupView.emailVerifyFailed()
                }
            }

            override fun onFailure(call: Call<EmailVerifyResponse>, t: Throwable) {

            }
        })
    }

    fun searchingIdEmailVerificationRequest(email: String){
        val loginService = getRetrofit().create(LoginRetrofitInterface::class.java)

        loginService.emailVerificationRequest(email).enqueue(object : Callback<EmailVerifyResponse>{
            override fun onResponse(
                call: Call<EmailVerifyResponse>,
                response: Response<EmailVerifyResponse>
            ) {
                if(response.code() == 200){
                    searchingIDView.emailVerifySuccess(response.body()!!.result!!.token, response.body()!!.result!!.code)
                }
                else {
                    searchingIDView.emailVerifyFailed()
                }
            }

            override fun onFailure(call: Call<EmailVerifyResponse>, t: Throwable) {

            }
        })
    }


    fun verifyEmail(token : String?, code : String) {
        val loginService = getRetrofit().create(LoginRetrofitInterface::class.java)
        val verifyRequest = verifyRequest(token, code)
        loginService.verifyEmail(verifyRequest).enqueue(object : Callback<verifyEmailResponse> {
            override fun onResponse(
                call: Call<verifyEmailResponse>,
                response: Response<verifyEmailResponse>
            ) {
                if(response.code() == 200){
                    signupView.verifySuccess()
                }
                else {
                    signupView.verifyFailed()
                }
            }

            override fun onFailure(call: Call<verifyEmailResponse>, t: Throwable) {

            }


        })
    }

    fun searchingIdVerifyEmail(token : String, code : String) {
        val loginService = getRetrofit().create(LoginRetrofitInterface::class.java)
        val verifyRequest = verifyRequest(token, code)
        loginService.verifyEmail(verifyRequest).enqueue(object : Callback<verifyEmailResponse> {
            override fun onResponse(
                call: Call<verifyEmailResponse>,
                response: Response<verifyEmailResponse>
            ) {
                if(response.code() == 200){
                    searchingIDView.verifySuccess()
                }
                else {
                    searchingIDView.verifyFailed()
                }
            }

            override fun onFailure(call: Call<verifyEmailResponse>, t: Throwable) {

            }


        })
    }

    fun resigster(name : String,  nickName: String, password : String, phoneNumber : String, email : String, emailVerificationToken : String?) {
        val loginService = getRetrofit().create(LoginRetrofitInterface::class.java)

        val registerRequest = RegisterRequest(name, nickName, password, phoneNumber, email, emailVerificationToken)
        loginService.register(registerRequest).enqueue(object: Callback<RegisterResponse>{
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if(response.code() == 200) {
                    signupView.registerSuccess()
                }
                else {
                    signupView.registerFailed()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {

            }

        })
    }

        fun searchingId(name: String, email : String, emailVerificationToken: String){
        val loginService = getRetrofit().create(LoginRetrofitInterface::class.java)

        val body = searchingIDRequest(name, email, emailVerificationToken)
        loginService.searchingIDRequest(body).enqueue(object : Callback<searchingIDResponse>{
            override fun onResponse(
                call: Call<searchingIDResponse>,
                response: Response<searchingIDResponse>
            ) {
                if(response.code() == 200) {
                    searchingIDView.searchingIdSuccess(response.body()!!.result.nickName!!)
                }
                else {
                    searchingIDView.searchingIdFailed()
                }
            }

            override fun onFailure(call: Call<searchingIDResponse>, t: Throwable) {

            }
        })
    }
}