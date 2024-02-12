package com.example.dailymemo.DailyBoard

import android.content.Context
import android.os.Bundle
import android.service.voice.VoiceInteractionSession.VisibleActivityCallback
import android.text.Editable
import android.text.SpannableStringBuilder
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.PopupWindow
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.dailymemo.R
import com.example.dailymemo.Setting.Dialog.SampleDialog
import com.example.dailymemo.WatchStream.Comment.CommentRVAdapter
import com.example.dailymemo.databinding.FragmentDiaryBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.File

class DiaryFragment : BottomSheetDialogFragment() {


    lateinit var binding: FragmentDiaryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiaryBinding.inflate(inflater,container, false)

        binding.apply {
            diaryBasicTextTv.setOnClickListener {
                diaryBasicTextTv.visibility = INVISIBLE
                diaryEt.visibility = VISIBLE
            }

            sendBtnIv.setOnClickListener {
                if(diaryEt.text.isNotEmpty()) {
                    diaryEt.visibility = INVISIBLE
                    diaryTextTv.text = diaryEt.text.toString()
                    diaryTextTv.visibility = VISIBLE
                    sendBtnIv.visibility = INVISIBLE
                    modifyDiaryBtn.visibility = VISIBLE
                }
                else {
                    val dailog = SampleDialog(requireContext(), "일기를 작성해주세요.")
                    dailog.show()
                }
            }

            modifyDiaryBtn.setOnClickListener {
               diaryTextTv.visibility = INVISIBLE
                diaryEt.visibility = VISIBLE
                diaryEt.text = diaryTextTv.text.toString().toEditable()
                sendBtnIv.visibility = VISIBLE
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

        val dialog = dialog as? BottomSheetDialog

        dialog?.let {
            it.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

    }

    fun String.toEditable(): Editable {
        return SpannableStringBuilder(this)
    }


    private fun hideKeyboard(view: View) {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


    // 저장된 이미지를 불러와서 이미지뷰에 설정하는 함수
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


}