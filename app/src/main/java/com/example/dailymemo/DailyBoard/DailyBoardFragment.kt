package com.example.dailymemo.DailyBoard

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.INVISIBLE
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.RecyclerView.VISIBLE
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.dailymemo.R
import com.example.dailymemo.Setting.StreamSetting.StreamSettingRVAdapter
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


        binding.apply {
            // 사진 업로드(미완성)
            userProfileIv.setOnClickListener {
                uploadPhoto()
            }

            // 바텀 다이얼로그
            menuBarIv.setOnClickListener {
                showMenu()
            }

            sendBtnIv.setOnClickListener {
                diaryEt.visibility = INVISIBLE
                sendBtnIv.visibility = INVISIBLE
                diaryTextTv.text = diaryEt.text
                diaryTextTv.visibility = VISIBLE
            }

            topUserProfileIv.setOnClickListener {
                showPopupMenu(topUserProfileIv)
            }

        }


        return binding.root
    }

    private fun initRecyclerView() {
        val dailyBoardRVAdapter = DailyBoardRVAdapter(photoList)
        binding.dailyBoardRv.adapter = dailyBoardRVAdapter
        binding.dailyBoardRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

       val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.dailyBoardRv)

    }

    private fun uploadPhoto() {
        if(photoList.size > 0) {
            binding.basicIv.visibility = INVISIBLE
            binding.basicTv.visibility = INVISIBLE
        }
    }

    private fun showMenu() {
        if(photoList.size > 0) {
            val bottomSheetView = layoutInflater.inflate(R.layout.bottom_menu_layout, null)
            val bottomSheetDialog = BottomSheetDialog(requireContext())
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED

            val deleteBtn = bottomSheetView.findViewById<ConstraintLayout>(R.id.delete_layout)
            deleteBtn.setOnClickListener {
                removePhoto()

                val deleteTitle = bottomSheetView.findViewById<TextView>(R.id.delete_tv)
                deleteTitle.text = "삭제 취소"
            }

            bottomSheetDialog.show()

        }
    }

    private fun removePhoto() {
        var layoutManager = binding.dailyBoardRv.layoutManager
//        var visibleItemCount = layoutManager?.childCount
        var pos : Int = (layoutManager as? LinearLayoutManager)!!.findFirstVisibleItemPosition()

        val viewHolder = binding.dailyBoardRv.findViewHolderForAdapterPosition(pos) as? DailyBoardRVAdapter.ViewHolder
        viewHolder?.removeItem()
    }

    private fun showPopupMenu(anchorView: View) {
        val inflater = LayoutInflater.from(requireContext())
        val customMenuView = inflater.inflate(R.layout.stream_setting_popup_menu_layout, null)

        // Customize PopupWindow
        val popupWindow = PopupWindow(
            customMenuView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )

        // Get the location of the anchorView on the screen
        val location = IntArray(2)
        anchorView.getLocationOnScreen(location)

        // Calculate xOffset and yOffset to align top-right of popup with top-right of anchorView
        val xOffset = location[0]
        val yOffset = location[1]

        // Set location
        popupWindow?.showAtLocation(anchorView, Gravity.NO_GRAVITY, xOffset, yOffset)
        popupWindow.setBackgroundDrawable(ColorDrawable(Color.BLACK))
        popupWindow.elevation = resources.getDimension(R.dimen.popup_card_elevation)

        val recyclerView = customMenuView.findViewById<RecyclerView>(R.id.stream_setting_rv)
        val streamSettingRVAdapter = StreamSettingRVAdapter()
        recyclerView.adapter = streamSettingRVAdapter
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)


    }

}