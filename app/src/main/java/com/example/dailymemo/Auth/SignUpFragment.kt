package com.example.dailymemo.Auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.dailymemo.R
import com.example.dailymemo.Service.LoginService
import com.example.dailymemo.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    private var email_verify_token: String = ""

    lateinit var binding: FragmentSignUpBinding

    var chkName: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater)

        binding.signupIdchkBtn.setOnClickListener {
            checkNickname()
        }

        binding.signupEmailBtn.setOnClickListener {
            checkEmail()
        }

        binding.signupRegisterBtn.setOnClickListener {
            register()
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    fun checkNickname() {
        val loginService: LoginService = LoginService()
        loginService.setSignupView(this)

        val nickname_input = binding.signupNameTe.text.toString()
        loginService.isNicknameExist(nickname_input)
    }

    fun checkEmail() {
        val loginService: LoginService = LoginService()
        loginService.setSignupView(this)

        val email_input = binding.signupEmailTe.text.toString()
        loginService.isEmailExist(email_input)
    }

    fun checkEmailSuccess() {
    }

    fun register() {
        val loginService: LoginService = LoginService()
        loginService.setSignupView(this)

        loginService.resigster(email_verify_token)
    }

    fun registerSuccess() {
        findNavController().navigate(R.id.loginFragment)
    }


}