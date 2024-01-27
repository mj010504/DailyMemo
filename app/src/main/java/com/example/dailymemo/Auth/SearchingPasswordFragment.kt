package com.example.dailymemo.Auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dailymemo.R
import com.example.dailymemo.databinding.FragmentSearchingPasswordBinding


class SearchingPasswordFragment : Fragment() {

    lateinit var binding : FragmentSearchingPasswordBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchingPasswordBinding.inflate(inflater,container,false)

        binding.apply {
            searchingPwIdTe.addTextChangedListener(object: TextWatcher {
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
                        searchingPwIdBtn.setBackgroundDrawable(resources.getDrawable(R.drawable.point_btn_active_layout))
                    }
                    else {
                        searchingPwIdBtn.setBackgroundDrawable(resources.getDrawable(R.drawable.point_color_25_5_btn_layout))
                    }
                }

                override fun afterTextChanged(s: Editable?) {

                }

            })
        }

        return binding.root
    }


}