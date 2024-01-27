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
import com.example.dailymemo.databinding.FragmentSearchingPasswordInputBinding

class SearchingPasswordInputFragment : Fragment() {

    lateinit var binding : FragmentSearchingPasswordInputBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentSearchingPasswordInputBinding.inflate(inflater,container,false)

        binding.apply {
            searchingPwCertiConfirmBtn.setOnClickListener {
                findNavController().navigate(R.id.searchingPasswordResetFragment)
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}