package com.example.dailymemo.WatchStream

import InsetsWithKeyboardCallback
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.dailymemo.MyStream.MyStreamRVAdapter
import com.example.dailymemo.R
import com.example.dailymemo.databinding.FragmentWatchStreamBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog


class WatchStreamFragment : Fragment() {

    lateinit var binding : FragmentWatchStreamBinding
    private lateinit var callback: OnBackPressedCallback


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWatchStreamBinding.inflate(inflater,container,false)

        initRecyclerView()

        binding.apply{
            commentIv.setOnClickListener {
                showMenu()
            }

            backIv.setOnClickListener {
                requireActivity().onBackPressedDispatcher
            }
        }

        return binding.root
    }

    private fun initRecyclerView() {
        val watchStreamRVAdapter = WatchStreamRVAdpater()
        binding.streamRv.adapter = watchStreamRVAdapter
        binding.streamRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.streamRv)

    }
    private fun showMenu() {
            val bottomSheetView = layoutInflater.inflate(R.layout.comment_layout, null)
            val bottomSheetDialog = BottomSheetDialog(requireContext())
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED


        bottomSheetDialog.show()
        }


}

