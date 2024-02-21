package com.example.dailymemo.Auth.Retrofit

import android.util.Log
import com.example.dailymemo.Auth.SearchingIdFragment
import com.example.dailymemo.Setting.AccountWithdrawalView
import com.example.dailymemo.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginService {

    private lateinit var loginView : LoginView

    private lateinit var signupView : SignUpView

    private lateinit var searchingIDView : SearchingIDView
    private lateinit var accountWithdrawalView : AccountWithdrawalView
    private lateinit var searchingPasswordView : SearchingPasswordView
    private lateinit var resetPwView: ResetPwView
    private lateinit var changePwView : ChangePwView



    fun setLoginView(loginView: LoginView){
        this.loginView = loginView
    }


    fun setSignupView(signupView : SignUpView){
        this.signupView = signupView
    }

    fun setSearchingIdView(searchingIdView: SearchingIdFragment){
        this.searchingIDView = searchingIdView
    }

    fun setAccountWithdrawalView(accountWithdrawalView: AccountWithdrawalView) {
        this.accountWithdrawalView = accountWithdrawalView
    }

    fun setSearchingPasswordView(searchingPasswordView: SearchingPasswordView) {
        this.searchingPasswordView = searchingPasswordView
    }

    fun setResetPwView(resetPwView: ResetPwView) {
        this.resetPwView = resetPwView
    }

    fun setChangePwView(changePwView: ChangePwView) {
        this.changePwView = changePwView
    }

    fun login(id : String, pw : String){
        val loginService = getRetrofit().create(LoginRetrofitInterface::class.java)

        val body : LoginRequest = LoginRequest(id,pw)
        loginService.login(body).enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                if(response.code() == 200){
                    val token = response.body()!!.result.token
                    val userId = response.body()!!.result.userId
                    loginView.loginSuccess(token, userId)
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
            override fun onResponse(call: Call<nicknameRepeatedResponse>, response: Response<nicknameRepeatedResponse> ) {
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

            }
        })
    }

    fun isNicknameExist2(id : String){
        val loginService = getRetrofit().create(LoginRetrofitInterface::class.java)

        loginService.isNicknameExist(id).enqueue(object : Callback<nicknameRepeatedResponse>{
            override fun onResponse(call: Call<nicknameRepeatedResponse>, response: Response<nicknameRepeatedResponse> ) {
                if(response.code() == 200){
                  searchingPasswordView.success()
                }
                else {
                    searchingPasswordView.failed()
                }
            }
            override fun onFailure(call: Call<nicknameRepeatedResponse>, t: Throwable) {

            }
        })
    }

    fun isEmailExist(email : String){
        val loginService = getRetrofit().create(LoginRetrofitInterface::class.java)

        loginService.isEmailExist(email).enqueue(object : Callback<emailRepeatedResponse>{
            override fun onResponse(call: Call<emailRepeatedResponse>, response: Response<emailRepeatedResponse>) {

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
            override fun onResponse(call: Call<emailRepeatedResponse>, response: Response<emailRepeatedResponse>) {

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

    fun emailVerificationRequest2(email: String){
        val loginService = getRetrofit().create(LoginRetrofitInterface::class.java)

        loginService.emailVerificationRequest(email).enqueue(object : Callback<EmailVerifyResponse>{
            override fun onResponse(
                call: Call<EmailVerifyResponse>,
                response: Response<EmailVerifyResponse>
            ) {
                if(response.code() == 200){
                    resetPwView.emailVerifySuccess(response.body()!!.result!!.token, response.body()!!.result!!.code)
                }
                else {
                    resetPwView.emailVerifyFailed()
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

    fun verifyEmail2(token : String?, code : String) {
        val loginService = getRetrofit().create(LoginRetrofitInterface::class.java)
        val verifyRequest = verifyRequest(token, code)
        loginService.verifyEmail(verifyRequest).enqueue(object : Callback<verifyEmailResponse> {
            override fun onResponse(
                call: Call<verifyEmailResponse>,
                response: Response<verifyEmailResponse>
            ) {
                if(response.code() == 200){
                    resetPwView.verifySuccess()
                }
                else {
                    resetPwView.verifyFailed()
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

    fun resigster(name : String,  nickName: String, password : String,email : String, emailVerificationToken : String?) {
        val loginService = getRetrofit().create(LoginRetrofitInterface::class.java)

        val registerRequest = RegisterRequest(name, nickName, password,email, emailVerificationToken)
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

    fun accountWithdrawal(jwt : String?) {
        val loginService = getRetrofit().create(LoginRetrofitInterface::class.java)
        loginService.accountWithdrawal("Bearer +"+jwt).enqueue(object: Callback<AccountWithdrawalResponse>{
            override fun onResponse(
                call: Call<AccountWithdrawalResponse>,
                response: Response<AccountWithdrawalResponse>
            ) {
                if(response.code() == 200) {
                    accountWithdrawalView.success()
                }
            }

            override fun onFailure(call: Call<AccountWithdrawalResponse>, t: Throwable) {

            }
        })
    }


    fun changePassword(jwt: String?, expw: String, newpw: String) {
        val loginService = getRetrofit().create(LoginRetrofitInterface::class.java)

        val request = ChangePasswordRequest(expw, newpw)
        loginService.changePassword(jwt,request).enqueue(object: Callback<AccountWithdrawalResponse>{
            override fun onResponse(
                call: Call<AccountWithdrawalResponse>,
                response: Response<AccountWithdrawalResponse>
            ) {
                if(response.code() == 200) {
                    changePwView.success()
                }
            }

            override fun onFailure(call: Call<AccountWithdrawalResponse>, t: Throwable) {

            }

        })

    }
}