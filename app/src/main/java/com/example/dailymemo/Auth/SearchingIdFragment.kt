package com.example.dailymemo.Auth

import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.dailymemo.DailyBoard.DailyBoardFragment
import com.example.dailymemo.R
import com.example.dailymemo.Service.LoginService
import com.example.dailymemo.Setting.Dialog.SampleDialog
import com.example.dailymemo.databinding.FragmentSearchingBinding
import com.example.dailymemo.databinding.FragmentSearchingIdBinding


class SearchingIdFragment : Fragment(), SearchingIDView {

    lateinit var binding : FragmentSearchingIdBinding
    lateinit var emailToken : String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchingIdBinding.inflate(inflater)


        binding.apply {
            searchingIdTelephoneBtn.setOnClickListener {
                checkEmail()
            }

            searchingIdCertiBtn.setOnClickListener {
                verify()
            }

            serachingIdBtn.setOnClickListener {
                searchID()
            }
        }

        binding.searchingIdTelephoneTe.addTextChangedListener(object: TextWatcher {
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
                    binding.searchingIdTelephoneBtn.setBackgroundDrawable(resources.getDrawable(R.drawable.point_btn_active_layout))
                }
                else {
                    binding.searchingIdTelephoneBtn.setBackgroundDrawable(resources.getDrawable(R.drawable.point_color_25_5_btn_layout))
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        binding.searchingIdCertiTe.addTextChangedListener(object: TextWatcher {
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
                    binding.searchingIdCertiBtn.setBackgroundDrawable(resources.getDrawable(R.drawable.point_btn_active_layout))
                }
                else {
                    binding.searchingIdCertiBtn.setBackgroundDrawable(resources.getDrawable(R.drawable.point_color_25_5_btn_layout))
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        return binding.root
    }



    fun searchID(){
        val loginService= LoginService()
        loginService.setSearchingIdView(this)
        val name = binding.searchingIdNameTe.text.toString()
        val email = binding.searchingIdTelephoneTe.text.toString()

     loginService.searchingId(name, email, emailToken)
    }



    private fun moveToFragment(fragment: Fragment) {
        val newFragment = fragment

        parentFragmentManager.beginTransaction()
            .replace(R.id.searchingIdLayout, newFragment)
            .commit()
    }

    override fun searchingIdSuccess(id : String) {
        moveToFragment(SearchingIdSuccessFragment(id))
    }

    override fun searchingIdFailed() {
        moveToFragment(SearchingIdFailedFragment())
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
        binding.searchingIdCertiTe.text = code.toEditable()
    }

    fun String.toEditable(): Editable {
        return SpannableStringBuilder(this)
    }

    override fun emailVerifyFailed() {
        val dialog = SampleDialog(requireContext(), "인증 요청에 실패했습니다.")
        dialog.show()
    }
    fun checkEmail() {
        val loginService= LoginService()
        loginService.setSearchingIdView(this)

        val email_input = binding.searchingIdTelephoneTe.text.toString()
        loginService.searchingIdIsEmailExist(email_input)
    }

    fun emailVerificationRequest(){
        val loginService = LoginService()
        loginService.setSearchingIdView(this)

        val email_input = binding.searchingIdTelephoneTe.text.toString()
        loginService.searchingIdEmailVerificationRequest(email_input)
    }

    override fun verifySuccess() {
        val dialog = SampleDialog(requireContext(), "인증번호가 확인되었습니다.")
        dialog.show()
        binding.serachingIdBtn.setBackgroundDrawable(resources.getDrawable(R.drawable.point_btn_active_layout))
    }

    override fun verifyFailed() {
        val dialog = SampleDialog(requireContext(), "인증번호가 올바르지\n않습니다.")
        dialog.show()
    }

    private fun verify() {
        val loginService = LoginService()
        loginService.setSearchingIdView(this)

        loginService.searchingIdVerifyEmail(emailToken, binding.searchingIdCertiTe.text.toString())
    }


}