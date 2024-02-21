package com.example.dailymemo.Auth

import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.dailymemo.R
import com.example.dailymemo.Auth.Retrofit.LoginService
import com.example.dailymemo.Auth.Retrofit.SignUpView
import com.example.dailymemo.Setting.Dialog.SampleDialog
import com.example.dailymemo.WatchStream.WatchStreamFragment
import com.example.dailymemo.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment(), SignUpView {

    private lateinit var binding: FragmentSignUpBinding

    private var emailToken : String? = null

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

        binding.signupCertiBtn.setOnClickListener {
            verify()
        }

        binding.signupRegisterBtn.setOnClickListener {
            register()
        }

        binding.apply {
            backIv.setOnClickListener {
                val fragmentManager = getActivity()?.getSupportFragmentManager();
                fragmentManager?.beginTransaction()?.remove(SignUpFragment())?.commit();
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

            signupPwchkTe.addTextChangedListener(object: TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val pwText = signupPwTe.text.toString()
                    val pwchkText = signupPwchkTe.text.toString()

                    if(pwchkText.isNotEmpty()) {
                        signupPwwarnTv.visibility = INVISIBLE
                        signupPwMessageTv.visibility = VISIBLE
                        if(pwchkText != pwText) {
                            signupPwMessageTv.text = "비밀번호가 일치하지 않습니다."
                        }
                        else {
                            signupPwMessageTv.text = "비밀번호가 일치합니다."
                        }
                    }
                    else {
                        signupPwwarnTv.visibility = VISIBLE
                        signupPwMessageTv.visibility = INVISIBLE
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
        val loginService = LoginService()
        loginService.setSignupView(this)

        val nickname_input = binding.signupIdTe.text.toString()
        loginService.isNicknameExist(nickname_input)
    }

    fun checkEmail() {
        val loginService = LoginService()
        loginService.setSignupView(this)

        val email_input = binding.signupEmailTe.text.toString()
        loginService.isEmailExist(email_input)
    }



    fun emailVerificationRequest(){
        val loginService = LoginService()
        loginService.setSignupView(this)

        val email_input = binding.signupEmailTe.text.toString()
        loginService.emailVerificationRequest(email_input)
    }

    private fun verify() {
        val loginService = LoginService()
        loginService.setSignupView(this)

        loginService.verifyEmail(emailToken, binding.signupCertiTe.text.toString())
    }

    fun register() {
        val loginService = LoginService()
        loginService.setSignupView(this)
            val name = binding.signupNameTe.text.toString()
            val email = binding.signupEmailTe.text.toString()
            val nickName = binding.signupIdTe.text.toString()
            val password = binding.signupPwTe.text.toString()
        loginService.resigster(name, nickName, password, email, emailToken)
    }

    override fun registerSuccess() {
        findNavController().navigate(R.id.loginFragment)
        val dialog = SampleDialog(requireContext(), "회원가입이 완료되었습니다!")
        dialog.show()
    }

    override fun registerFailed() {
        val dialog = SampleDialog(requireContext(), "회원가입에 실패하였습니다.")
        dialog.show()
    }

    override fun isNickNameSuccess() {
        val dialog = SampleDialog(requireContext(), "사용가능한 아이디입니다.")
        dialog.show()
    }

    override fun isNickNameFailed() {
        val dialog = SampleDialog(requireContext(), "사용중인 아이디입니다.")
        dialog.show()
    }

    override fun isEmailSuccess() {
        val dialog = SampleDialog(requireContext(), "인증번호를\n발송하였습니다.")
        dialog.show()
        emailVerificationRequest()
    }

    override fun isEmailFailed() {
        val dialog = SampleDialog(requireContext(), "존재하지 않는 이메일 주소입니다.")
        dialog.show()
    }

    override fun emailVerifySuccess(token: String, code: String) {
        emailToken = token
        binding.signupCertiTe.text = code.toEditable()
    }

    fun String.toEditable(): Editable {
        return SpannableStringBuilder(this)
    }

    override fun emailVerifyFailed() {
        val dialog = SampleDialog(requireContext(), "인증 요청에 실패했습니다.")
        dialog.show()
    }

    override fun verifySuccess() {
        val dialog = SampleDialog(requireContext(), "인증번호가 확인되었습니다.")
        dialog.show()
    }

    override fun verifyFailed() {
        val dialog = SampleDialog(requireContext(), "인증번호가 올바르지\n않습니다.")
        dialog.show()
    }


}