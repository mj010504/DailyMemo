package com.example.dailymemo.Setting.StreamSetting

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.dailymemo.OpenStream.Retrofit.OpenStreamService
import com.example.dailymemo.OpenStream.Retrofit.Response.ChangeProfileRequest
import com.example.dailymemo.R
import com.example.dailymemo.Setting.Dialog.StreamNameChangeDialog
import com.example.dailymemo.Setting.SettingView
import com.example.dailymemo.WatchStream.WatchStreamFragment
import com.example.dailymemo.databinding.FragmentStreamSettingDetailBinding
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


class StreamSettingDetailFragment : Fragment(), SettingView {

    lateinit var binding : FragmentStreamSettingDetailBinding



    private val pickImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                val base64 = imageTobase64(uri)
                changeProfile(base64)

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

        // 초기값 설정,
        val bundle = arguments
        if (bundle != null) {
            val streamName = bundle.getString("streamName")
            binding.streamNameTv.text = streamName
            binding.streamTypeTv.text = streamName
        }

        binding.apply {
            settingIcIv.setOnClickListener {
                openGallery()
            }

            backIv.setOnClickListener {
                val fragmentManager = getActivity()?.getSupportFragmentManager();
                fragmentManager?.beginTransaction()?.remove(StreamSettingDetailFragment())?.commit();
                fragmentManager?.popBackStack();

            }

            streamNameChangeBtn.setOnClickListener {
                val streamName = bundle!!.getString("streamName")
                val dialog = StreamNameChangeDialog(requireContext(), requireActivity(), streamName)
                dialog.show()
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

    private fun getMyJwt() : String? {
        val spf = requireActivity().getSharedPreferences("auth", Context.MODE_PRIVATE)
        return spf.getString("jwt", null)
    }

    private fun changeProfile(base64 : String) {
        val jwt = getMyJwt()
        val openStreamService = OpenStreamService()
        openStreamService.setSettingView(this)
        val request  = ChangeProfileRequest(base64)
        openStreamService.changeProfileImage(jwt, request)
    }

    override fun changeProfileSuccess() {
        Log.d("changeProfile", "success")
    }


    private fun imageTobase64(imageUri : Uri) : String {
        var bitmap = getBitmapFromUri(imageUri)
        bitmap = compressBitmap(bitmap, 50)
        bitmap = resizeBitmap(bitmap, 170, 170)
        val base64 = bitmapToBase64(bitmap)

        return base64
    }


    // 이미지 Uri를 Bitmap으로 변환하는 함수들
    private fun getBitmapFromUri(uri: Uri): Bitmap {
        val source: ImageDecoder.Source = ImageDecoder.createSource(requireContext().contentResolver, uri)
        val bitmap: Bitmap = ImageDecoder.decodeBitmap(source)

        return bitmap
    }

    private fun compressBitmap(bitmap: Bitmap, compressionQuality: Int): Bitmap {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressionQuality, stream)
        return BitmapFactory.decodeStream(ByteArrayInputStream(stream.toByteArray()))
    }

    fun resizeBitmap(bitmap: Bitmap, targetWidth: Int, targetHeight: Int): Bitmap {

            // Calculate the inSampleSize
            val options = BitmapFactory.Options().apply {
                inJustDecodeBounds = true
            }
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size, options)
            options.inSampleSize = calculateInSampleSizes(options, targetWidth, targetHeight)

            // Decode the stream again, using the calculated inSampleSize
            options.inJustDecodeBounds = false
            val resizedBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size, options)

            return resizedBitmap
    }

    private fun calculateInSampleSizes(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }

    // Bitmap을 Base64로 인코딩하는 함수
    private fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.NO_WRAP)
    }



}