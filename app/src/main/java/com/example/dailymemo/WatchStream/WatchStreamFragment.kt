package com.example.dailymemo.WatchStream



import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.dailymemo.R
import com.example.dailymemo.WatchStream.Comment.CommentFragment
import com.example.dailymemo.databinding.FragmentWatchStreamBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog


class WatchStreamFragment : Fragment() {

    lateinit var binding : FragmentWatchStreamBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWatchStreamBinding.inflate(inflater,container,false)

        initRecyclerView()

        binding.apply{
            commentBtnLayout.setOnClickListener {
                showMenu()
            }

            backIv.setOnClickListener {
                val fragmentManager = getActivity()?.getSupportFragmentManager();
                fragmentManager?.beginTransaction()?.remove(WatchStreamFragment())?.commit();
                fragmentManager?.popBackStack();

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
        val bottomSheetFragment = CommentFragment()
        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)

    }

}




