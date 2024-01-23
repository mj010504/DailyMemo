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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SignUpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignUpFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var email_verify_token: String = ""

    lateinit var binding : FragmentSignUpBinding

    var chkName : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater)

        binding.signupIdchkBtn.setOnClickListener{
            checkNickname()
        }

        binding.signupEmailBtn.setOnClickListener{
            checkEmail()
        }

        binding.signupRegisterBtn.setOnClickListener{
            register()
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    fun checkNickname(){
        val loginService : LoginService = LoginService()
        loginService.setSignupView(this)

        val nickname_input = binding.signupNameTe.text.toString()
        loginService.isNicknameExist(nickname_input)
    }

    fun checkEmail(){
        val loginService : LoginService = LoginService()
        loginService.setSignupView(this)

        val email_input = binding.signupEmailTe.text.toString()
        loginService.isEmailExist(email_input)
    }

    fun checkEmailSuccess(){
    }

    fun register(){
        val loginService : LoginService = LoginService()
        loginService.setSignupView(this)

        loginService.resigster(email_verify_token)
    }

    fun registerSuccess(){
        findNavController().navigate(R.id.loginFragment)
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SignUpFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignUpFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}