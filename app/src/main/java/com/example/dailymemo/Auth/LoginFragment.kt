package com.example.dailymemo.Auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.dailymemo.R
import com.example.dailymemo.Service.LoginService
import com.example.dailymemo.databinding.FragmentLoginBinding


class LoginFragment : Fragment(),LoginView {

    lateinit var binding: FragmentLoginBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)

        binding.loginfragLoginBtn.setOnClickListener{
            login()
        }
        binding.loginfragRegisterTv.setOnClickListener{
            findNavController().navigate(R.id.signUpFragment)
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    fun login(){
        val loginService = LoginService()
        loginService.setLoginView(this)

        val id_text = binding.loginfragIdEt.text.toString()
        val pw_text = binding.loginfragPwEt.text.toString()

        loginService.login(id_text,pw_text)
    }

    override fun loginSuccess(){
        findNavController().navigate(R.id.openStreamFragment)
    }

}