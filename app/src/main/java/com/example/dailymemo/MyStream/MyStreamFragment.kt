package com.example.dailymemo.MyStream

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.service.voice.VoiceInteractionSession.VisibleActivityCallback
import android.util.Log
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
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBindings
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.dailymemo.DailyBoard.DailyBoardRVAdapter
import com.example.dailymemo.MyStream.Dialog.DeleteCheckDailog
import com.example.dailymemo.MyStream.Dialog.SampleDialog
import com.example.dailymemo.OpenStream.OpenStreamRVAdapter
import com.example.dailymemo.R
import com.example.dailymemo.Setting.StreamSetting.StreamSettingRVAdapter
import com.example.dailymemo.databinding.FragmentMyStreamBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.io.File


class MyStreamFragment : Fragment() {


    lateinit var binding: FragmentMyStreamBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyStreamBinding.inflate(inflater, container, false)

        initRecyclerView()

        binding.apply {
            userProfileIv.setOnClickListener {
                showPopupMenu(userProfileIv)
            }
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 저장된 이미지를 불러와서 이미지뷰에 설정하는 함수 호출
        val savedImagePath = loadSavedImagePath()
        if (savedImagePath.isNotEmpty()) {
            loadImageFromInternalStorage(savedImagePath)
        }

        infiniteScroll()
    }

    private fun initRecyclerView() {
        val myStreamRVAdapter = MyStreamRVAdapter()
        binding.mystreamRv.adapter = myStreamRVAdapter
        binding.mystreamRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        myStreamRVAdapter.seMyItemClickListener(object: MyStreamRVAdapter.MyItemClickListener {
            override fun onMenuClick() {
                showMenu()
            }
        })
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

    private fun showMenu() {

            val bottomSheetView = layoutInflater.inflate(R.layout.mystream_bottom_menu_layout, null)
            val bottomSheetDialog = BottomSheetDialog(requireContext())
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED

            val lockBtn = bottomSheetView.findViewById<ConstraintLayout>(R.id.stream_lock_layout)
            val deleteBtn = bottomSheetView.findViewById<ConstraintLayout>(R.id.stream_delete_layout)

            lockBtn.setOnClickListener {
                val lockTitle = bottomSheetView.findViewById<TextView>(R.id.stream_lock_tv)
                lockTitle.text = "숨기기 해제"
                bottomSheetDialog.dismiss()

                val dialog = SampleDialog(requireContext())
                dialog.show()
            }

            deleteBtn.setOnClickListener {
                bottomSheetDialog.dismiss()
                val dialog = DeleteCheckDailog(requireContext())
                dialog.show()
            }

            bottomSheetDialog.show()


    }


    private fun loadImageFromInternalStorage(filePath: String) {
        Glide.with(this)
            .load(File(filePath))
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(binding.userProfileIv)


    }

    // 저장된 이미지의 파일 경로를 불러오는 함수
    private fun loadSavedImagePath(): String {
        val preferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        return preferences.getString("user_profile_image_path", "") ?: ""
    }

    private fun infiniteScroll() {
        binding.rootView.viewTreeObserver.addOnScrollChangedListener {

            // NestedScrollView의 ScrollY 값과 ScrollableView의 높이를 통해 스크롤 상태를 확인
            if (binding.rootView.scrollY  >= binding.rootView.height) {
                Log.d("layoutTotal", "total succs")

            }
        }
    }
}

