package com.example.dailymemo.Setting.StreamSetting

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.dailymemo.R
import com.example.dailymemo.WatchStream.WatchStreamFragment
import com.example.dailymemo.databinding.FragmentStreamSettingBinding
import java.io.File


class StreamSettingFragment : Fragment() {

    lateinit var binding : FragmentStreamSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStreamSettingBinding.inflate(inflater,container,false)

        binding.apply {
            settingIcIv.setOnClickListener {
                findNavController().navigate(R.id.streamSettingDetailFragment)
            }

            backIv.setOnClickListener {
                val fragmentManager = getActivity()?.getSupportFragmentManager();
                fragmentManager?.beginTransaction()?.remove(WatchStreamFragment())?.commit();
                fragmentManager?.popBackStack();

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