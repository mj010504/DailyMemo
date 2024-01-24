package com.example.dailymemo.Setting.StreamSetting

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.dailymemo.R
import com.example.dailymemo.databinding.FragmentStreamSettingDetailBinding
import java.io.File
import java.io.FileOutputStream


class StreamSettingDetailFragment : Fragment() {

    lateinit var binding : FragmentStreamSettingDetailBinding

    private val pickImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->

                val savedImagePath = saveImageToInternalStorage(uri)

                // 저장된 이미지를 불러와서 이미지뷰에 설정
                loadImageFromInternalStorage(savedImagePath)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStreamSettingDetailBinding.inflate(inflater,container,false)

        binding.apply {
            settingIcIv.setOnClickListener {
                openGallery()
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

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImage.launch(galleryIntent)
    }

    // 이미지 저장을 위한 함수
    private fun saveImageToInternalStorage(uri: Uri): String {
        val inputStream = requireActivity().contentResolver.openInputStream(uri)
        val fileName = "user_profile_image.jpg"
        val file = File(requireContext().filesDir, fileName)

        val outputStream = FileOutputStream(file)

        inputStream?.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }

        // 저장된 파일 경로를 SharedPreferences에 저장
        val preferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString("user_profile_image_path", file.absolutePath)
        editor.apply()

        return file.absolutePath
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