package com.example.dailymemo.Auth

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.dailymemo.Auth.Retrofit.ChangePwView
import com.example.dailymemo.Auth.Retrofit.LoginService
import com.example.dailymemo.Auth.Retrofit.ResetPwView
import com.example.dailymemo.R
import com.example.dailymemo.Setting.Dialog.SampleDialog
import com.example.dailymemo.WatchStream.WatchStreamFragment
import com.example.dailymemo.databinding.FragmentSearchingPasswordResetBinding



class SearchingPasswordResetFragment : Fragment(), ChangePwView {

    lateinit var binding : FragmentSearchingPasswordResetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchingPasswordResetBinding.inflate(inflater,container,false)

        binding.apply {

            backIv.setOnClickListener {
                val fragmentManager = getActivity()?.getSupportFragmentManager();
                fragmentManager?.beginTransaction()?.remove(SearchingPasswordResetFragment())?.commit();
                fragmentManager?.popBackStack();

            }

            searchingPwResetRepwTe.addTextChangedListener(object: TextWatcher {
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
                        searchingPwResetBtn.setBackgroundDrawable(resources.getDrawable(R.drawable.point_btn_active_layout))
                    }
                    else {
                        searchingPwResetBtn.setBackgroundDrawable(resources.getDrawable(R.drawable.point_color_25_5_btn_layout))
                    }
                }

                override fun afterTextChanged(s: Editable?) {

                }

            })

            searchingPwResetBtn.setOnClickListener {
                var jwt = getMyJwt()
                val expw = binding.searchingPwResetRepwTe.text.toString()
                val newpw = binding.searchingPwResetNewpwTe.text.toString()
                changePassword(jwt, expw, newpw)
            }

        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun getMyJwt() : String? {
        val spf = requireActivity().getSharedPreferences("auth", Context.MODE_PRIVATE)
        return spf.getString("jwt", null)
    }


    private fun changePassword(jwt : String?, expw: String, newpw: String) {
        val loginService = LoginService()
        loginService.setChangePwView(this)
        loginService.changePassword(jwt, expw, newpw)
    }

    override fun success() {
       val dialog = SampleDialog(requireContext(), "비밀번호가 초기화 되었습니다!")
        dialog.show()

        findNavController().navigate(R.id.loginFragment)
    }


}