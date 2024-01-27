package com.example.dailymemo.Auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.dailymemo.DailyBoard.DailyBoardFragment
import com.example.dailymemo.R
import com.example.dailymemo.Service.LoginService
import com.example.dailymemo.databinding.FragmentSearchingBinding
import com.example.dailymemo.databinding.FragmentSearchingIdBinding


class SearchingIdFragment : Fragment() {

    lateinit var binding : FragmentSearchingIdBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         val searchingBinding = FragmentSearchingBinding.inflate(inflater,container,false)
        binding = FragmentSearchingIdBinding.inflate(inflater)
        binding.searchingIdCertiBtn.setOnClickListener{
            searchID()
        }

        binding.apply {
            searchingIdCertiTv.setOnClickListener {
               moveToFragment(SearchingIdSuccessFragment())
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
        val loginService: LoginService = LoginService()
        loginService.setSearchingIdView(this)

//        loginService.searchingId(email_verify_token)
    }

    fun success(){
    }

    private fun moveToFragment(fragment: Fragment) {
        val newFragment = fragment

        parentFragmentManager.beginTransaction()
            .replace(R.id.searchingIdLayout, newFragment)
            .commit()
    }
}