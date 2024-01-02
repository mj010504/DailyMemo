package com.example.dailymemo.MyStream

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.dailymemo.DailyBoard.DailyBoardRVAdapter
import com.example.dailymemo.R
import com.example.dailymemo.databinding.FragmentMyStreamBinding


class MyStreamFragment : Fragment() {

    lateinit var binding : FragmentMyStreamBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyStreamBinding.inflate(inflater,container,false)

        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView() {
        val myStreamRVAdapter = MyStreamRVAdapter()
        binding.mystreamRv.adapter = myStreamRVAdapter
        binding.mystreamRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    }

}