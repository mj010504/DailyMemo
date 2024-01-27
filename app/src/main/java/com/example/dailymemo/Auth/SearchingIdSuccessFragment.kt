package com.example.dailymemo.Auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dailymemo.R
import com.example.dailymemo.databinding.FragmentSearchingIdBinding
import com.example.dailymemo.databinding.FragmentSearchingIdSuccessBinding

class SearchingIdSuccessFragment : Fragment() {

    lateinit var binding : FragmentSearchingIdSuccessBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchingIdSuccessBinding.inflate(inflater,container,false)

        binding.apply {
            checkBtn.setOnClickListener {
                moveToFragment(SearchingIdFragment())
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun moveToFragment(fragment: Fragment) {
        val newFragment = fragment

        parentFragmentManager.beginTransaction()
            .replace(R.id.searchingIdLayout, newFragment)
            .commit()
    }
}