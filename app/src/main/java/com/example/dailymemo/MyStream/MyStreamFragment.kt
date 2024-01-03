package com.example.dailymemo.MyStream

import android.os.Bundle
import android.service.voice.VoiceInteractionSession.VisibleActivityCallback
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.PopupWindow
import android.widget.Space
import android.widget.Spinner
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.viewbinding.ViewBindings
import com.example.dailymemo.DailyBoard.DailyBoardRVAdapter
import com.example.dailymemo.R
import com.example.dailymemo.databinding.FragmentMyStreamBinding


class MyStreamFragment : Fragment() {

    lateinit var binding: FragmentMyStreamBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyStreamBinding.inflate(inflater, container, false)

        initRecyclerView()


        return binding.root
    }

    private fun initRecyclerView() {
        val myStreamRVAdapter = MyStreamRVAdapter()
        binding.mystreamRv.adapter = myStreamRVAdapter
        binding.mystreamRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        myStreamRVAdapter.seMyItemClickListener(object: MyStreamRVAdapter.MyItemClickListener {
            override fun onMenuClick(menu: ImageView) {
                showPopupMenu(menu)
            }

        })

    }

    private fun showPopupMenu(anchorView: View) {
        val inflater = LayoutInflater.from(requireContext())
        val customMenuView = inflater.inflate(R.layout.mystream_popup_menu_layout, null)

        // Customize PopupWindow
       val popupWindow = PopupWindow(
            customMenuView,
            350,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )

        // Get the location of the anchorView on the screen
        val location = IntArray(2)
        anchorView.getLocationOnScreen(location)

        // Calculate xOffset and yOffset to align top-right of popup with top-right of anchorView
        val xOffset = location[0] + anchorView.width - 350
        val yOffset = location[1]

        // Set location
        popupWindow?.showAtLocation(anchorView, Gravity.NO_GRAVITY, xOffset, yOffset)

        val publicBtn = customMenuView.findViewById<ConstraintLayout>(R.id.public_btn)
        val modifyBtn = customMenuView.findViewById<ConstraintLayout>(R.id.modify_btn)
        val deleteBtn = customMenuView.findViewById<ConstraintLayout>(R.id.delete_btn)

        publicBtn.setOnClickListener {

            popupWindow?.dismiss()
        }

        modifyBtn.setOnClickListener {

            popupWindow?.dismiss()
        }


        deleteBtn.setOnClickListener {

            popupWindow?.dismiss()
        }


    }
}

