package com.example.dailymemo.Setting

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.dailymemo.R
import com.example.dailymemo.databinding.FragmentSettingBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.nio.file.Files.delete


class SettingFragment : Fragment() {

    lateinit var binding : FragmentSettingBinding





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentSettingBinding.inflate(inflater,container,false)

        binding.apply {
            premeumSettingTv.setOnClickListener {
                findNavController().navigate(R.id.premiumSettingFragment)
            }


            accountWithdrawalTv.setOnClickListener {
                findNavController().navigate(R.id.accountWithdrawalFragment)
            }

            settingIcIv.setOnClickListener {
                findNavController().navigate(R.id.streamSettingFragment)
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