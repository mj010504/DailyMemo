package com.example.dailymemo.Auth.Retrofit

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("nickName") val nickName : String,
    @SerializedName("password") val password : String
)
data class LoginResponse(
    @SerializedName("isSuccess") val isSuccess :Boolean,
    @SerializedName("code") val code : String,
    @SerializedName("message") val message : String,
    @SerializedName("result") val result : loginResult
)

data class loginResult(
    @SerializedName("token") val token : String,
    @SerializedName("nickName") val nickName : String,
    @SerializedName("userId") val userId : Int
)


data class nicknameRepeatedResponse(
    @SerializedName("isSuccess") val isSuccess :Boolean,
    @SerializedName("code") val code : String,
    @SerializedName("message") val message : String,
    @SerializedName("result") val result : nicknameRepeated_result
)
data class nicknameRepeated_result(
    @SerializedName("exists") val exsists: Boolean
)



data class emailRepeatedResponse(
    @SerializedName("isSuccess") val isSuccess :Boolean,
    @SerializedName("code") val code : String,
    @SerializedName("message") val message : String,
    @SerializedName("isExists") val isExists : Boolean
)



data class EmailVerify_result(
    @SerializedName("token") val token: String,
    @SerializedName("expireAt") val expireAt: String,
    @SerializedName("code") val code : String
)
data class EmailVerifyResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: EmailVerify_result?
)

data class RegisterRequest(
    @SerializedName("name") val name : String,
    @SerializedName("nickName") val nickName : String,
    @SerializedName("password") val password : String,
//    @SerializedName("phoneNumber") val phoneNumber: String,
    @SerializedName("email") val email: String,
    //@SerializedName("profilePhoto") val profilePhoto: String,
    @SerializedName("emailVerificationToken") val emailVerificationToken: String?
)
data class Register_result(
    @SerializedName("id") val id : Int?,
    @SerializedName("createdAt") val date : String?,
    @SerializedName("phoneNumber") val phoneErr : String?,
    @SerializedName("name") val nameErr : String?,
    @SerializedName("password") val pwErr : String?,
    @SerializedName("email") val emailErr : String?,
    @SerializedName("nickName") val nickErr : String?
)
data class RegisterResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Register_result
)

data class searchingIDRequest(
    @SerializedName("name") val name : String,
    @SerializedName("email") val email : String,
    @SerializedName("emailVerificationToken") val emailVerificationToken: String
)

data class searchingIDResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result : searchingIDResult
)

data class searchingIDResult(
        @SerializedName("email") val email : String?,
        @SerializedName("name") val name : String?,
        @SerializedName("nickName") val nickName : String?
)

data class verifyEmailResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result : verifyEmailResult
)

data class verifyEmailResult(
    @SerializedName("token") val token : String,
    @SerializedName("verified") val verified : Boolean
)

data class verifyRequest(
    @SerializedName("token") val token : String?,
    @SerializedName("code") val code : String
)

data class AccountWithdrawalResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result : Boolean

)

data class ResetPasswordRequest(
    @SerializedName("name") val name : String,
    @SerializedName("nickName") val nickName : String,
    @SerializedName("password") val password : String,
    @SerializedName("email") val email: String,
    @SerializedName("emailVerificationToken") val emailVerificationToken: String?
)

data class ChangePasswordRequest(
    @SerializedName("existingPassword") val existingPassword: String,
    @SerializedName("newPasword") val newPassword: String
)
