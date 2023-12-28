package com.example.dailymemo.DailyBoard

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.bumptech.glide.Glide
import com.example.dailymemo.R
import com.example.dailymemo.databinding.FragmentDailyBoardBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog


class DailyBoardFragment : Fragment() {

    lateinit var binding:FragmentDailyBoardBinding
    private var photoList = ArrayList<Int>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentDailyBoardBinding.inflate(inflater,container,false)
        initRecyclerView()

        photoList.apply {
            add(R.drawable.daily1)
            add(R.drawable.daily2)
            add(R.drawable.daily3)
        }

        binding.userProfileIv.setOnClickListener {
            uploadPhoto()
        }

        binding.menuBarIv.setOnClickListener {
            showMenu()
        }

        return binding.root
    }

    private fun initRecyclerView() {
        val dailyBoardRVAdapter = DailyBoardRVAdapter(photoList)
        binding.dailyBoardRv.adapter = dailyBoardRVAdapter
        binding.dailyBoardRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

       var snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.dailyBoardRv);

    }

    private fun uploadPhoto() {

    }

    private fun showMenu() {
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_function_layout, null)
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED

        bottomSheetDialog.show()

    }


}