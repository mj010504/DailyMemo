package com.example.dailymemo.OpenStream

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.PopupWindow
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dailymemo.MyStream.MyStreamRVAdapter
import com.example.dailymemo.R
import com.example.dailymemo.databinding.FragmentOpenStreamBinding


class OpenStreamFragment : Fragment() {

    lateinit var binding : FragmentOpenStreamBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOpenStreamBinding.inflate(inflater,container,false)

        binding.apply {
            searchingIv.setOnClickListener {
                searchEt.visibility = VISIBLE
                searchEt.addTextChangedListener(object: TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {

                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        searchingIv.visibility = INVISIBLE
                        searchTv.visibility = VISIBLE

                        val inputText = s.toString()
                        if(inputText.isEmpty()) {
                            searchTv.text = "취소"

                            searchTv.setOnClickListener {
                                hideKeyboard(it)
                                searchTv.visibility = INVISIBLE
                                searchingIv.visibility = VISIBLE
                            }
                        }
                        else {
                            searchTv.text = "검색"
                            searchTv.setOnClickListener {
                                searchTv.visibility = INVISIBLE
                                searchingIv.visibility = VISIBLE

                                val keyword = searchEt.text.toString()
                                searchEt.hint = "'$keyword'에 대한 검색결과"
                                searchEt.setText("")
                            }
                        }
                    }

                    override fun afterTextChanged(s: Editable?) {

                    }

                })
            }
        }

        return binding.root
    }

    private fun initRecyclerView() {
        val openStreamRVAdapter = OpenStreamRVAdapter()
        binding.openstreamRv.adapter = openStreamRVAdapter
        binding.openstreamRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        openStreamRVAdapter.setMyItemClickListener(object: OpenStreamRVAdapter.MyItemClickListener {

            override fun onStreamClick() {
               moveToStream()
            }
        })

    }


    private fun moveToStream() {
        findNavController().navigate(R.id.watchStreamFragment)
    }

    private fun hideKeyboard(view: View) {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


}