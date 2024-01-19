package com.example.dailymemo.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.dailymemo.R
import com.example.dailymemo.databinding.FragmentSplashBinding


class SplashFragment : Fragment() {

    lateinit var binding : FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater,container,false)

        val handler = Handler(Looper.getMainLooper())

        // 1초(1000 milliseconds) 후에 다른 화면으로 이동
        handler.postDelayed({findNavController().navigate(R.id.openStreamFragment)},1000)
        return binding.root
    }

}