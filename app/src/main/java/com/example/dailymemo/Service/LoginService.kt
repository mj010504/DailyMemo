package com.example.dailymemo.Service

import android.util.Log
import com.example.dailymemo.Auth.LoginView
import com.example.dailymemo.Auth.SignUpFragment
import com.example.dailymemo.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginService {

    lateinit var loginView : LoginView

    lateinit var signupView : SignUpFragment

    @JvmName("setLoginView2")
    fun setLoginView(loginView: LoginView){
        this.loginView = loginView
    }

    @JvmName("setSignupView2")
    fun setSignupView(signupView : SignUpFragment){
        this.signupView = signupView
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


        loginService.isNicknameExist(id).enqueue(object : Callback<nicknameRepeatedResponse>{
            override fun onResponse(call: Call<nicknameRepeatedResponse>, response: Response<nicknameRepeatedResponse> ) {

        val body : nicknameRepeatedRequest = nicknameRepeatedRequest(id);
        loginService.isNicknameExist(body).enqueue(object : Callback<nicknameRepeatedResponse>{
            override fun onResponse( call: Call<nicknameRepeatedResponse>,response: Response<nicknameRepeatedResponse> ) {

                if(response.isSuccessful){
                    if(response.code() == 200){
                        if(response.body()?.isExists == true){

                        }
                        else{
                            signupView.chkName = true
                        }
                    }
                }
                else{

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

        val body : emailRepeatedRequest = emailRepeatedRequest(email);
        loginService.isEmailExist(body).enqueue(object : Callback<emailRepeatedResponse>{
            override fun onResponse( call: Call<emailRepeatedResponse>, response: Response<emailRepeatedResponse>) {

                if(response.isSuccessful){
                    if(response.code()==200){
                        if(response.body()?.isExists == true){
                            signupView.checkEmailSuccess()
                        }
                        else{

                        }
                    }
                }
                else{

                }
            }

            override fun onFailure(call: Call<emailRepeatedResponse>, t: Throwable) {

            }
        })
    }

    fun resigster(email_verify_token: String){
        val loginService = getRetrofit().create(LoginRetrofitInterface::class.java)

        val id: String = signupView.binding.signupIdTe.text.toString()
        val nickName: String = signupView.binding.signupNameTe.text.toString()
        val pw: String = signupView.binding.signupPwTe.text.toString()
        val tele: String = ""
        val email: String = signupView.binding.signupEmailTe.text.toString()
        val image_encoded: String = ""

        val body : RegisterRequest = RegisterRequest(id,nickName,pw,tele,email,image_encoded, email_verify_token);
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
                    if(response.body() != null)
                        Log.i("RegisterService", response.body()!!.message)
                }
            }
            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {

            }
        })
    }
}