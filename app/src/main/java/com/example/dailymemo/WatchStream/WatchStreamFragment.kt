package com.example.dailymemo.WatchStream

import InsetsWithKeyboardCallback
import android.graphics.Rect
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
import kotlinx.coroutines.NonDisposableHandle.parent


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
            wsCommentLayout.setOnClickListener {
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
            val bottomSheetView = layoutInflater.inflate(R.layout.comment_layout, null)
            val bottomSheetDialog = BottomSheetDialog(requireContext())
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED


        bottomSheetDialog.show()

        val editText = bottomSheetView.findViewById<EditText>(R.id.comment_et)

        // 키보드가 나타날 때의 리스너 등록
        editText.viewTreeObserver.addOnGlobalLayoutListener {
            val r = Rect()
            editText.getWindowVisibleDisplayFrame(r)
            val screenHeight = editText.height
            val keypadHeight = screenHeight - r.bottom

            if (keypadHeight > screenHeight * 0.15) {
                // 키보드가 열려있는 상태에서의 동작 (올리기)
                val location = IntArray(2)
                editText.getLocationOnScreen(location)
                val editTextBottom = location[1] + editText.height
                val margin = editTextBottom - r.bottom
                editText.scrollBy(0, margin)
            } else {
                // 키보드가 닫혀있는 상태에서의 동작 (내리기)
                editText.scrollBy(0, 0)
            }
        }

        // 키보드 자동으로 올라오는 것 방지
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }

}




