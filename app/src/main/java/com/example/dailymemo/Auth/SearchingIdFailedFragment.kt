package com.example.dailymemo.Auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dailymemo.R
import com.example.dailymemo.databinding.FragmentSearchingIdFailedBinding

class SearchingIdFailedFragment : Fragment() {

    lateinit var binding : FragmentSearchingIdFailedBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchingIdFailedBinding.inflate(inflater,container,false)
        binding.apply {
            searchingIdFailedConfirmBtn.setOnClickListener {
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