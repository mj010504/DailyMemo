package com.example.dailymemo.Setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.dailymemo.R
import com.example.dailymemo.databinding.FragmentSettingBinding


class SettingFragment : Fragment() {

    lateinit var binding : FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentSettingBinding.inflate(inflater,container,false)

        binding.apply {
            premeumSettingTv.setOnClickListener {
                findNavController().navigate(R.id.premiumSettingFragment)
            }
        }

        return binding.root
    }

}