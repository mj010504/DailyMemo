package com.example.dailymemo.Service

import android.util.Log
import com.example.dailymemo.Auth.LoginView
import com.example.dailymemo.Auth.SearchingIdFragment
import com.example.dailymemo.Auth.SignUpFragment
import com.example.dailymemo.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginService {

    lateinit var loginView : LoginView

    lateinit var signupView : SignUpFragment

    lateinit var searchingIDView : SearchingIdFragment

    @JvmName("setLoginView2")
    fun setLoginView(loginView: LoginView){
        this.loginView = loginView
    }

    @JvmName("setSignupView2")
    fun setSignupView(signupView : SignUpFragment){
        this.signupView = signupView
    }

    @JvmName("setSearchingIdView2")
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
                    Log.i("LoginService","로그인 성공")
                    loginView.loginSuccess()
                }
                else if(response.code() == 400){
                    Log.i("LoginService", "유효하지않은 응답")
                }
                else if(response.code() == 401){
                    Log.i("LoginService", "아이디 또는 비밀번호가 맞지 않음")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.i("LoginService", "통신상태 확인")
            }
        })
    }
    fun isNicknameExist(id : String){
        val loginService = getRetrofit().create(LoginRetrofitInterface::class.java)

        val body : nicknameRepeatedRequest = nicknameRepeatedRequest(id);
        loginService.isNicknameExist(body).enqueue(object : Callback<nicknameRepeatedResponse>{
            override fun onResponse( call: Call<nicknameRepeatedResponse>,response: Response<nicknameRepeatedResponse> ) {
                Log.i("CheckIDService",response.code().toString())
                if(response.code() == 200){
                    if(response.body()?.result!!.isExists == true){


                if(response.isSuccessful){
                    if(response.code() == 200){
                        if(response.body()?.isExists == true){

                        }
                        else{
                            signupView.chkName = true

                        }

                    }
                    else{
                        signupView.chkName = true
                        Log.i("CheckIDService","사용가능한 아이디 입니다.")
                    }
                }
            }
            override fun onFailure(call: Call<nicknameRepeatedResponse>, t: Throwable) {

            }
        })
    }

    fun isEmailExist(email : String){
        val loginService = getRetrofit().create(LoginRetrofitInterface::class.java)

<<<<<<< HEAD

                val body: emailRepeatedRequest = emailRepeatedRequest(email);
                loginService.isEmailExist(body).enqueue(object : Callback<emailRepeatedResponse> {
                override fun onResponse(
                    call: Call<emailRepeatedResponse>,
                    response: Response<emailRepeatedResponse>
                ) {

                    if (response.isSuccessful) {
                        if (response.code() == 200) {
                            if (response.body()?.isExists == true) {
                                signupView.checkEmailSuccess()
                            } else {

                            }
                        }
                    } else {

                    }
                }

                override fun onFailure(call: Call<emailRepeatedResponse>, t: Throwable) {
=======
        val body : emailRepeatedRequest = emailRepeatedRequest(email);
        loginService.isEmailExist(body).enqueue(object : Callback<emailRepeatedResponse>{
            override fun onResponse( call: Call<emailRepeatedResponse>, response: Response<emailRepeatedResponse>) {

                if(response.code()==200) {
                    if (response.body()?.isExists == false) {
                        signupView.checkEmailSuccess()
                    }
                    else{
>>>>>>> 6a1b383915a8baa08d4d85331ac2a981c48161dc

                    }
                }
            })

    }

<<<<<<< HEAD
            fun resigster(email_verify_token: String) {
                val loginService = getRetrofit().create(LoginRetrofitInterface::class.java)

                val id: String = signupView.binding.signupIdTe.text.toString()
                val nickName: String = signupView.binding.signupNameTe.text.toString()
                val pw: String = signupView.binding.signupPwTe.text.toString()
                val tele: String = ""
                val email: String = signupView.binding.signupEmailTe.text.toString()
                val image_encoded: String = ""

                val body: RegisterRequest = RegisterRequest(
                    id,
                    nickName,
                    pw,
                    tele,
                    email,
                    image_encoded,
                    email_verify_token
                );
                loginService.register(body).enqueue(object : Callback<RegisterResponse> {
                    override fun onResponse(
                        call: Call<RegisterResponse>,
                        response: Response<RegisterResponse>
                    ) {
                        Log.i("RegisterService", response.code().toString())
                        if (response.code() == 200) {
                            signupView.registerSuccess()
                            Log.i("RegisterService", "회원가입 성공")
                        }
                        if (response.code() == 400) {
                            if (response.body() != null)
                                Log.i("RegisterService", response.body()!!.message)
                        }
                    }

                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {

                    }
                })
            }
        }
=======
    fun emailVerificationRequest(email: String){
        val loginService = getRetrofit().create(LoginRetrofitInterface::class.java)

        val body: EmailVerifyRequest = EmailVerifyRequest(email)
        loginService.emailVerificationRequest(body).enqueue(object : Callback<EmailVerifyResponse>{
            override fun onResponse(
                call: Call<EmailVerifyResponse>,
                response: Response<EmailVerifyResponse>
            ) {
                Log.i("EmailVerificationService","성공")
                if(response.code() == 200){
                    Log.i("EmailVerificationService","메일을 성공적으로 전송했습니다.")
                    signupView.emailVerificationSuccess(response.body()!!.result.token)
                }
                else if(response.code() == 400){
                    Log.i("EmailVerficationService","메일 전송에 실패했습니다.")
                }
            }

            override fun onFailure(call: Call<EmailVerifyResponse>, t: Throwable) {
                Log.i("EmailVerificationService","err:(${t.message})")
            }
        })
    }

    fun resigster(email_verify_token: String){
        val loginService = getRetrofit().create(LoginRetrofitInterface::class.java)

        val id: String = signupView.binding.signupIdTe.text.toString()
        val nickName: String = signupView.binding.signupNameTe.text.toString()
        val pw: String = signupView.binding.signupPwTe.text.toString()
        val tele: String = "01057585744"
        val email: String = signupView.binding.signupEmailTe.text.toString()
        val image_encoded: String = ""

        val body : RegisterRequest = RegisterRequest(nickName,id,pw,tele,email, email_verify_token)
        Log.i("RegisterService",body.toString())
        loginService.register(body).enqueue(object : Callback<RegisterResponse>{
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                Log.i("RegisterService", response.code().toString())
                if(response.code() == 200){
                    signupView.registerSuccess()
                    Log.i("RegisterService","회원가입 성공")
                }
                if(response.code() == 400){
                    Log.i("ResgisterService",response.message())
                    if(response.body() != null)
                        Log.i("RegisterService", response.body()!!.result.toString())
                }
            }
            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.i("RegisterService","err:${t.message}")
            }
        })
    }

    fun searchingId(email_verify_token: String){
        val loginService = getRetrofit().create(LoginRetrofitInterface::class.java)

        val name:String = searchingIDView.binding.searchingIdNameTe.text.toString()
        val email:String = ""

        val body : searchingIDRequest = searchingIDRequest(name, email, email_verify_token)
        loginService.searchingIDRequest(body).enqueue(object : Callback<searchingIDResponse>{
            override fun onResponse(
                call: Call<searchingIDResponse>,
                response: Response<searchingIDResponse>
            ) {
                Log.i("SearchingIDService",response.code().toString())
                if(response.code()==404){
                    Log.i("SearchingIDService","사용자를 찾을 수 없음")
                }
                else if(response.code()==200){
                    Log.i("SearchingIDService", "사용자를 찾았습니다.")
                }
                else if(response.code()==400){
                    Log.i("SearchingIDService", "유효한 요청이 아님")
                }
            }

            override fun onFailure(call: Call<searchingIDResponse>, t: Throwable) {

            }
        })
    }
}
>>>>>>> 6a1b383915a8baa08d4d85331ac2a981c48161dc
