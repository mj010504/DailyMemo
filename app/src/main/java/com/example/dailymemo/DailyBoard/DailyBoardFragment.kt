package com.example.dailymemo.DailyBoard

import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.ImageDecoder
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.marginTop
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.INVISIBLE
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.RecyclerView.VISIBLE
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.dailymemo.MyStream.MyStreamRVAdapter
import com.example.dailymemo.OpenStream.OpenStreamRVAdapter
import com.example.dailymemo.R
import com.example.dailymemo.Setting.StreamSetting.StreamSettingRVAdapter
import com.example.dailymemo.databinding.FragmentDailyBoardBinding
import com.example.dailymemo.databinding.ItemDailyBoardBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.internal.ViewUtils.hideKeyboard
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class DailyBoardFragment : Fragment() {

    lateinit var binding: FragmentDailyBoardBinding
    private var isPhoto = false
    private var imageList = ArrayList<Uri>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDailyBoardBinding.inflate(inflater, container, false)


        val editText = binding.diaryEt
        val rootView = binding.rootView

        // 키보드가 나타날 때의 리스너 등록
        rootView.viewTreeObserver.addOnGlobalLayoutListener {
            val r = Rect()
            rootView.getWindowVisibleDisplayFrame(r)
            val screenHeight = rootView.height
            val keypadHeight = screenHeight - r.bottom

            if (keypadHeight > screenHeight * 0.15) {
                // 키보드가 열려있는 상태에서의 동작 (올리기)
                val location = IntArray(2)
                editText.getLocationOnScreen(location)
                val editTextBottom = location[1] + editText.height + 35
                val margin = editTextBottom - r.bottom
                rootView.scrollTo(0, margin)
            } else {
                // 키보드가 닫혀있는 상태에서의 동작 (내리기)
                rootView.scrollTo(0, 0)
            }
        }

        // 키보드 자동으로 올라오는 것 방지
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        binding.apply {

            // 바텀 다이얼로그
            menuBarIv.setOnClickListener {
                showMenu()
            }

            sendBtnIv.setOnClickListener {
                diaryEt.visibility = INVISIBLE
                sendBtnIv.visibility = INVISIBLE
                diaryTextTv.text = diaryEt.text
                diaryTextTv.visibility = VISIBLE

                hideKeyboard(it)
            }

            diaryBasicTextTv.setOnClickListener {
                diaryEt.visibility = VISIBLE
                sendBtnIv.visibility = VISIBLE
                diaryBasicTextTv.visibility = INVISIBLE
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

        getImage()
        basicSetting()
        initRecyclerView()

    }

    private fun initRecyclerView() {
        val dailyBoardRVAdapter = DailyBoardRVAdapter(requireContext(),imageList)
        binding.dailyBoardRv.adapter = dailyBoardRVAdapter
        binding.dailyBoardRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        dailyBoardRVAdapter.setITemClickListener(object : DailyBoardRVAdapter.ItemClickListener {
            override fun onPhotoClick() {
                showInfo(isPhoto)
                isPhoto = !isPhoto
            }

        })
        binding.dailyBoardRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                updateCount()
            }
        })

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.dailyBoardRv)

    }

    private fun basicSetting() {
        if (imageList.size > 0) {
            binding.basicIv.visibility = INVISIBLE
            binding.basicTv.visibility = INVISIBLE
        }
    }

    private fun showMenu() {
        if (imageList.size > 0) {
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

            val streamChangeBtn =
                bottomSheetView.findViewById<ConstraintLayout>(R.id.stream_change_layout)
            val streamRecyclerView =
                bottomSheetView.findViewById<RecyclerView>(R.id.stream_change_rv)
            streamChangeBtn.setOnClickListener {
                streamChangeBtn.setBackgroundResource(R.drawable.menu_box_selected)
                deleteBtn.visibility = INVISIBLE
                streamRecyclerView.visibility = VISIBLE
                val streamChangeRVAdpater = StreamChangeRVADapter(requireActivity())
                streamRecyclerView.adapter = streamChangeRVAdpater
                streamRecyclerView.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            }

            bottomSheetDialog.show()

        }
    }

    private fun removePhoto() {
        var layoutManager = binding.dailyBoardRv.layoutManager
//        var visibleItemCount = layoutManager?.childCount
        var pos: Int = (layoutManager as? LinearLayoutManager)!!.findFirstVisibleItemPosition()

        val viewHolder =
            binding.dailyBoardRv.findViewHolderForAdapterPosition(pos) as? DailyBoardRVAdapter.ViewHolder
        viewHolder?.removeItem()
    }


    private fun showInfo(isPhoto: Boolean) {

        if (isPhoto == true) {
            binding.infoView.visibility = View.INVISIBLE
            binding.clickUserProfileIv.visibility = View.INVISIBLE
            binding.streamNameTv.visibility = View.INVISIBLE
            binding.countLayout.visibility = View.INVISIBLE
        }


        if (isPhoto == false) {
            binding.infoView.visibility = View.VISIBLE
            binding.clickUserProfileIv.visibility = View.VISIBLE
            binding.streamNameTv.visibility = View.VISIBLE
            binding.countLayout.visibility = View.VISIBLE

            updateCount()

        }
    }

    private fun updateCount() {
        var layoutManager = binding.dailyBoardRv.layoutManager
        val adapter = binding.dailyBoardRv.adapter
        var currentPos: Int =
            (layoutManager as? LinearLayoutManager)!!.findFirstVisibleItemPosition() + 1
        var total = adapter?.itemCount

        binding.countTv.text = "$currentPos/$total"
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

        Glide.with(this)
            .load(File(filePath))
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(binding.topUserProfileIv)

    }

    // 저장된 이미지의 파일 경로를 불러오는 함수
    private fun loadSavedImagePath(): String {
        val preferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        return preferences.getString("user_profile_image_path", "") ?: ""
    }

    private fun getCursor(): Cursor? {


        val projection = arrayOf(
            MediaStore.Images.ImageColumns._ID,
            MediaStore.Images.ImageColumns.DATE_TAKEN
        )

        val today = Calendar.getInstance()
        val midnight = today.apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        val selection = "${MediaStore.Images.Media.DATE_TAKEN} >= ?"
        val selectionArgs = arrayOf(midnight.timeInMillis.toString())
        val sortOrder = "${MediaStore.Images.ImageColumns.DATE_TAKEN} DESC" //내림차순

        val cursor = requireContext().contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )



        return cursor

    }

    private fun getImage() {
        //내부 오류가 발생하는 경우, 쿼리 결과는 특정 제공자에 따라 달라집니다. null을 반환하기로 선택할 수도 있고, Exception을 발생시킬 수도 있습니다.
        //따라서 try catch & try 내에서도 cursor이 null로 반환되는 경우를 모두 처리해줌.
        lifecycleScope.launch { //비동기 처리
            try {
                val cursor = getCursor()
                when (cursor?.count) {
                    null -> {
                        Log.d("cursor", "cursor count = null")
                    }

                    0 -> {
                        Log.d("cursor", "cursor count = 0")

                    }

                    else -> {

                        while (cursor.moveToNext()) {

                            val idColNum =
                                cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns._ID)
                            val dateTakenColNum =
                                cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATE_TAKEN)


                            val id = cursor.getLong(idColNum)
                            val dateTaken = cursor.getLong(dateTakenColNum)


                            val imageUri =
                                ContentUris.withAppendedId(
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                    id
                                )

                            imageList.add(imageUri)

                            Log.d(
                                "cursor",
                                "id: ${id}, dateTaken : $dateTaken, imageUri : $imageUri"
                            )

                        }
                        cursor.close() //사용한 cursor는 꼭 close 해줘야함

                    }
                }

            } catch (e: Exception) {
                //에러 대응 코드 작성
                Log.d("cursor","cusor Error")
            }
        }
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

    private fun compressImages(imageList: List<Uri>, compressionQuality: Int = 50): List<Bitmap> {
        val compressedBitmapList = mutableListOf<Bitmap>()

        for (imageUri in imageList) {
            val bitmap = getBitmapFromUri(imageUri) // URI에서 Bitmap으로 변환
            val compressedBitmap = compressBitmap(bitmap, compressionQuality)
            compressedBitmapList.add(compressedBitmap)
        }

        return compressedBitmapList
    }

}



