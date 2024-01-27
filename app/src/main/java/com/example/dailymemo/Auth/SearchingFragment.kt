package com.example.dailymemo.Auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.dailymemo.R
import com.example.dailymemo.WatchStream.WatchStreamFragment
import com.example.dailymemo.databinding.FragmentSearchingBinding
import com.google.android.material.tabs.TabLayoutMediator


class SearchingFragment(idx : Int) : Fragment() {

    lateinit var binding : FragmentSearchingBinding
    private val info = arrayListOf("아이디 찾기", "비밀번호 찾기")
    private var tabIdx = idx
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchingBinding.inflate(inflater,container,false)

        val searchingVPAdapter = SearchingVPAdapter(this)
        binding.searchingVp.adapter = searchingVPAdapter

        binding.searchingVp.currentItem = tabIdx

        TabLayoutMediator(binding.searchingTb, binding.searchingVp) {
            tab, position ->
            tab.text = info[position]
        }.attach()

        binding.apply {
            backIv.setOnClickListener {
              findNavController().navigate(R.id.loginFragment)
            }
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}