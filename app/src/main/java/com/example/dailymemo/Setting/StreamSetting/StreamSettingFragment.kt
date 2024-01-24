package com.example.dailymemo.Setting.StreamSetting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.dailymemo.R
import com.example.dailymemo.databinding.FragmentStreamSettingBinding


class StreamSettingFragment : Fragment() {

    lateinit var binding : FragmentStreamSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStreamSettingBinding.inflate(inflater,container,false)

        binding.apply {
            settingIcIv.setOnClickListener {
                findNavController().navigate(R.id.streamSettingDetailFragment)
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}