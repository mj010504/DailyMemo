package com.example.dailymemo.Auth

import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.fragment.findNavController
import com.example.dailymemo.Auth.Retrofit.LoginService
import com.example.dailymemo.Auth.Retrofit.ResetPasswordRequest
import com.example.dailymemo.Auth.Retrofit.ResetPwView
import com.example.dailymemo.R
import com.example.dailymemo.Setting.Dialog.SampleDialog
import com.example.dailymemo.databinding.FragmentSearchingPasswordInputBinding

class SearchingPasswordInputFragment : Fragment(), ResetPwView {

    lateinit var binding : FragmentSearchingPasswordInputBinding

    private var emailToken : String? = null
    private var isCerti : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentSearchingPasswordInputBinding.inflate(inflater,container,false)

        binding.apply {

            searchingPwCertiEmailBtn.setOnClickListener {
                emailVerificationRequest()
            }

            searchingPwCertiCertiBtn.setOnClickListener {
                verify()
            }


                searchingPwCertiConfirmBtn.setOnClickListener {
                    if(isCerti) {
                        findNavController().navigate(R.id.searchingPasswordResetFragment)
                    }
                }


            searchingPwCertiEmailTe.addTextChangedListener(object: TextWatcher {
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
                        searchingPwCertiEmailBtn.setBackgroundDrawable(resources.getDrawable(R.drawable.point_btn_active_layout))

                    }
                    else {
                        searchingPwCertiEmailBtn.setBackgroundDrawable(resources.getDrawable(R.drawable.point_color_25_5_btn_layout))
                    }
                }

                override fun afterTextChanged(s: Editable?) {

                }

            })



            searchingPwCertiCertiTe.addTextChangedListener(object: TextWatcher {
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
                        searchingPwCertiCertiBtn.setBackgroundDrawable(resources.getDrawable(R.drawable.point_btn_active_layout))
                        searchingPwCertiConfirmBtn.setBackgroundDrawable(resources.getDrawable(R.drawable.point_btn_active_layout))
                    }
                    else {
                        searchingPwCertiCertiBtn.setBackgroundDrawable(resources.getDrawable(R.drawable.point_color_25_5_btn_layout))
                        searchingPwCertiConfirmBtn.setBackgroundDrawable(resources.getDrawable(R.drawable.point_color_25_5_btn_layout))
                    }
                }

                override fun afterTextChanged(s: Editable?) {

                }

            })



        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }



    private fun emailVerificationRequest(){
        val loginService = LoginService()
        loginService.setResetPwView(this)
        val email_input = binding.searchingPwCertiEmailTe.text.toString()
        loginService.emailVerificationRequest2(email_input)
    }

    private fun verify() {
        val loginService = LoginService()
        loginService.setResetPwView(this)

        loginService.verifyEmail2(emailToken, binding.searchingPwCertiCertiTe.text.toString())
    }

    override fun emailVerifySuccess(token: String, code: String) {
        emailToken = token
        binding.searchingPwCertiCertiTe.text = code.toEditable()
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

        isCerti = true
    }

    override fun verifyFailed() {
        val dialog = SampleDialog(requireContext(), "인증번호가 올바르지\n않습니다.")
        dialog.show()

        isCerti = false
    }



}