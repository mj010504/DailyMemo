package com.example.dailymemo.Auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.dailymemo.R
import com.example.dailymemo.Service.LoginService
import com.example.dailymemo.WatchStream.WatchStreamFragment
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

        binding.apply {
            backIv.setOnClickListener {
                val fragmentManager = getActivity()?.getSupportFragmentManager();
                fragmentManager?.beginTransaction()?.remove(WatchStreamFragment())?.commit();
                fragmentManager?.popBackStack();

            }
            signupIdTe.addTextChangedListener(object: TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val inputText = s.toString()
                    if(inputText.isNotEmpty()) {
                        signupIdchkBtn.setBackgroundDrawable(resources.getDrawable(R.drawable.point_btn_active_layout))
                    }
                    else {
                        signupIdchkBtn.setBackgroundDrawable(resources.getDrawable(R.drawable.point_color_25_5_btn_layout))
                    }
                }

                override fun afterTextChanged(s: Editable?) {

                }

            })

            signupEmailTe.addTextChangedListener(object: TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val inputText = s.toString()
                    if(inputText.isNotEmpty()) {
                        signupEmailBtn.setBackgroundDrawable(resources.getDrawable(R.drawable.point_btn_active_layout))
                    }
                    else {
                        signupEmailBtn.setBackgroundDrawable(resources.getDrawable(R.drawable.point_color_25_5_btn_layout))
                    }
                }

                override fun afterTextChanged(s: Editable?) {

                }

            })

            signupCertiTe.addTextChangedListener(object: TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val inputText = s.toString()
                    if(inputText.isNotEmpty()) {
                        signupCertiBtn.setBackgroundDrawable(resources.getDrawable(R.drawable.point_btn_active_layout))
                        signupRegisterBtn.setBackgroundDrawable(resources.getDrawable(R.drawable.point_btn_active_layout))
                    }
                    else {
                        signupCertiBtn.setBackgroundDrawable(resources.getDrawable(R.drawable.point_color_25_5_btn_layout))
                        signupRegisterBtn.setBackgroundDrawable(resources.getDrawable(R.drawable.point_color_25_5_btn_layout))
                    }
                }

                override fun afterTextChanged(s: Editable?) {

                }

            })
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    fun checkNickname() {
        val loginService: LoginService = LoginService()
        loginService.setSignupView(this)

        val nickname_input = binding.signupIdTe.text.toString()
        loginService.isNicknameExist(nickname_input)
    }

    fun checkEmail() {
        val loginService: LoginService = LoginService()
        loginService.setSignupView(this)

        val email_input = binding.signupEmailTe.text.toString()
        loginService.isEmailExist(email_input)
    }


    fun checkEmailSuccess(){
        emailVerificationRequest()
    }

    fun emailVerificationRequest(){
        val loginService: LoginService = LoginService()
        loginService.setSignupView(this)

        val email_input = binding.signupEmailTe.text.toString()
        loginService.emailVerificationRequest(email_input)
    }

    fun emailVerificationSuccess(token : String){
        this.email_verify_token = token
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