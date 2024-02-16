package com.example.dailymemo.DailyBoard

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.ImageDecoder
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.INVISIBLE
import androidx.recyclerview.widget.RecyclerView.VISIBLE
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.dailymemo.DailyBoard.Retrofit.DailyBoardService
import com.example.dailymemo.DailyBoard.Retrofit.DailyBoardView
import com.example.dailymemo.DailyBoard.Retrofit.Response.Diary
import com.example.dailymemo.DailyBoard.Retrofit.Response.DiaryPhoto
import com.example.dailymemo.DailyBoard.Retrofit.Response.showDailyBoardResponse
import com.example.dailymemo.DailyBoard.Retrofit.Response.showDiaryPreviewResponse
import com.example.dailymemo.DailyBoard.Retrofit.Response.storeImageResponse
import com.example.dailymemo.DailyBoard.Retrofit.Response.writeDiaryRequest
import com.example.dailymemo.MyStream.StreamSettingRVAdapter
import com.example.dailymemo.R
import com.example.dailymemo.Setting.Dialog.SampleDialog
import com.example.dailymemo.databinding.DailyboardStreamPopupMenuLayoutBinding
import com.example.dailymemo.databinding.FragmentDailyBoardBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar


class DailyBoardFragment : Fragment(), DailyBoardView {

    lateinit var binding: FragmentDailyBoardBinding
    private var imageList = ArrayList<Uri>()
    private var isDiaryPreview = false
    private var pageNum = 1
    private var hasNext = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDailyBoardBinding.inflate(inflater, container, false)

        // 키보드 자동으로 올라오는 것 방지
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        binding.apply {

            diaryInputBtnLayout.setOnClickListener {
                showStreamDiaryMenu()
            }

            diaryPreviewBtnLayout.setOnClickListener {
                if(isDiaryPreview == false) {
                    showDiaryPreview(diaryphotoId = 1, streamId = 1)
                }
                else {
                    DiaryPreviewClose()
                }
             }

            deleteLayout.setOnClickListener {
                removePhoto()
            }

            streamLayout.setOnClickListener {
                showStreamPopupMenu(streamProfileIv, binding.streamProfileIv.drawable, binding.streamNameTv.text.toString())
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

    override fun onResume() {
        super.onResume()
//        if(imageList.size > 0) {
//            val bitmapList = compressImages(imageList)
//            val images = bitmapList.map { bitmapToBase64(it)}
//            storeImage(diaryId, images)
//
//        }

//
//        var cl =  compressImages(imageList)
//        Log.d("bittmap1", cl[0].toString() )
//        // compressedBitmapList의 각 Bitmap을 Base64로 인코딩하여 문자열로 변환
//        val bitmapStringList = cl.map { bitmapToBase64(it) }
//
//        saveBase64ListToFile(requireContext(), bitmapStringList, "base64_list.txt")
//
//// 파일에서 Base64 문자열 리스트를 불러오기
//        val loadedBase64List = readBase64ListFromFile(requireContext(), "base64_list.txt")
//
//
//        val bitmap = stringToBitmap(loadedBase64List[0])
//        binding.streamProfileIv.setImageBitmap(bitmap)
    }



    private fun initRecyclerView() {
        val dailyBoardRVAdapter = DailyBoardRVAdapter(requireContext(),imageList)
        binding.dailyBoardRv.adapter = dailyBoardRVAdapter
        binding.dailyBoardRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        binding.dailyBoardRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                updateCount()
                updateDeleteText()
            }
        })

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.dailyBoardRv)

    }


    private fun updateCount() {
        var layoutManager = binding.dailyBoardRv.layoutManager
        val adapter = binding.dailyBoardRv.adapter
        var currentPos: Int =
            (layoutManager as? LinearLayoutManager)!!.findFirstVisibleItemPosition() + 1
        var total = adapter?.itemCount

        binding.countTv.text = "$currentPos/$total"
    }

    private fun basicSetting() {
        if (imageList.size > 0) {
            binding.basicIv.visibility = INVISIBLE
            binding.basicTv.visibility = INVISIBLE
            binding.countLayout.visibility = VISIBLE
        }
    }

    private fun showStreamDiaryMenu() {
        if (imageList.size > 0) {
            val bottomSheetView = layoutInflater.inflate(R.layout.bottom_stream_diary_menu_layout, null)
            val bottomSheetDialog = BottomSheetDialog(requireContext())
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED

            val streamRecyclerView = bottomSheetView.findViewById<RecyclerView>(R.id.stream_change_rv)
            val streamChangeRVAdpater = StreamChangeRVADapter(requireActivity())
            streamRecyclerView.adapter = streamChangeRVAdpater
            streamRecyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            streamChangeRVAdpater.setMyItemClickListener(object : StreamChangeRVADapter.MyItemClickListener{
                override fun onStreamDiaryClick() {
                    showDiaryMenu()
                }

            } )


            bottomSheetDialog.show()
        }
        else {
            val dialog = SampleDialog(requireContext(),"사진을 등록해야 일기 작성이 가능합니다.")
            dialog.show()
        }
    }


//    private fun removePhoto() {
//        var layoutManager = binding.dailyBoardRv.layoutManager
////        var visibleItemCount = layoutManager?.childCount
//        var pos: Int = (layoutManager as? LinearLayoutManager)!!.findFirstVisibleItemPosition()
//
//        val viewHolder = binding.dailyBoardRv.findViewHolderForAdapterPosition(pos) as? DailyBoardRVAdapter.ViewHolder
//        viewHolder?.removeItem(pos)
//        updateDeleteText()
//    }
//
//    private fun updateDeleteText() {
//        var layoutManager = binding.dailyBoardRv.layoutManager
//        var pos: Int = (layoutManager as? LinearLayoutManager)!!.findFirstVisibleItemPosition()
//        val viewHolder = binding.dailyBoardRv.findViewHolderForAdapterPosition(pos) as? DailyBoardRVAdapter.ViewHolder
//        var isDelete = viewHolder!!.getIsDelete(pos)
//        if(isDelete) {
//            binding.deleteTv.visibility = VISIBLE
//            binding.deletedTv.visibility = INVISIBLE
//        }
//        else {
//            binding.deleteTv.visibility = INVISIBLE
//            binding.deletedTv.visibility = VISIBLE
//        }
//    }




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
            .into(binding.streamProfileIv)
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
        val sortOrder = "${MediaStore.Images.ImageColumns.DATE_TAKEN} ASC" //오름차순

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

    // Bitmap을 Base64로 인코딩하는 함수
    private fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.NO_WRAP)
    }

    private fun stringToBitmap(base64: String) : Bitmap {
        val encodeByte = Base64.decode(base64, Base64.NO_WRAP)

        return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
    }



    private fun showDiaryMenu() {
        val bottomSheetFragment = DiaryFragment()
        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)

    }


    private fun DiaryPreviewClose() {
        isDiaryPreview = !isDiaryPreview
        ChangeDownHeight()
        binding.apply {
            diaryPreviewBtnLayout.setBackgroundResource(R.drawable.menu_box)

            streamLayout.visibility = VISIBLE
            deleteLayout.visibility = VISIBLE

            diaryPreviewActiveTv.visibility = INVISIBLE
            dateTv.visibility = INVISIBLE
            diaryTextPreviewLayout.visibility = INVISIBLE

            diaryPreviewTv.text = "일기 미리보기"
        }
    }

    private fun DiaryPreviewOpen(detail : String, dateString : String) {
        isDiaryPreview = !isDiaryPreview
        ChangeUpHeight()

        binding.apply {
            diaryPreviewBtnLayout.setBackgroundResource(R.drawable.menu_box_selected)

            streamLayout.visibility = INVISIBLE
            deleteLayout.visibility = INVISIBLE

            diaryPreviewActiveTv.visibility = VISIBLE
            dateTv.visibility = VISIBLE
            diaryTextPreviewLayout.visibility = VISIBLE

            diaryPreviewDetailTv.text = detail
            dateTv.text = dateString

            diaryPreviewTv.text = "미리보기 취소"
        }
    }


    private fun ChangeUpHeight() {
        val newHeight = resources.getDimensionPixelSize(R.dimen.diary_preview) // 예시로 리소스에서 값을 가져옴
        // 기존 LayoutParams를 가져오기
        val layoutParams = binding.menuLayout.layoutParams as ConstraintLayout.LayoutParams
        // LayoutParams의 height 속성 변경
        layoutParams.height = newHeight
        // 변경된 LayoutParams를 설정
        binding.menuLayout.layoutParams = layoutParams
    }

    private fun ChangeDownHeight() {
        val newHeight = resources.getDimensionPixelSize(R.dimen.diary_preview_cancle) // 예시로 리소스에서 값을 가져옴
        // 기존 LayoutParams를 가져오기
        val layoutParams = binding.menuLayout.layoutParams as ConstraintLayout.LayoutParams
        // LayoutParams의 height 속성 변경
        layoutParams.height = newHeight
        // 변경된 LayoutParams를 설정
        binding.menuLayout.layoutParams = layoutParams
    }

    private fun showStreamPopupMenu(anchorView: View, streamProfile: Drawable, streamName : String) {
        val inflater = LayoutInflater.from(requireContext())
        val customMenuView = inflater.inflate(R.layout.dailyboard_stream_popup_menu_layout, null)

        val popupStreamProfile = customMenuView.findViewById<ImageView>(R.id.popup_stream_profile_iv)
        val popupStreamName = customMenuView.findViewById<TextView>(R.id.popup_stream_name_tv)



        popupStreamProfile.setImageDrawable(streamProfile)
        popupStreamName.text = streamName


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

        val recyclerView = customMenuView.findViewById<RecyclerView>(R.id.stream_list_rv)
        val dailyBoardStreamRVAdapter = DailyBoardStreamRVAdapter(requireActivity(),binding.streamNameTv.text.toString())
        recyclerView.adapter = dailyBoardStreamRVAdapter
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)


        val upBtn = customMenuView.findViewById<ImageView>(R.id.fold_up_btn_iv)
        upBtn.setOnClickListener {
            popupWindow.dismiss()
        }
    }

    private fun showDailyBoard(userId : Int, page : Int) {
        val dailyBoardService = DailyBoardService()
        dailyBoardService.setDailyBoardView(this)
        dailyBoardService.showDailyBoard(userId, page)
    }

    override fun onShowDailyBoardSuccess(resp: showDailyBoardResponse) {
        if(resp.result.hasNext) {
            pageNum += 1
            hasNext = true
        }
        else {
            hasNext = false
        }


    }

    private fun storeImage(diaryId : Int, images : List<String>) {
        val dailyBoardService = DailyBoardService()
        dailyBoardService.setDailyBoardView(this)
        dailyBoardService.storeImage(diaryId, images)
    }

    override fun storeImageSuccess(resp: storeImageResponse) {
        Log.d("storeImage", "succcess")
    }

    private fun showDiaryPreview(diaryphotoId : Int, streamId : Int) {
        val dailyBoardService = DailyBoardService()
        dailyBoardService.setDailyBoardView(this)
        dailyBoardService.showDiaryPreview(diaryphotoId, streamId)
    }

    override fun showDiaryPreviewSuccess(resp: showDiaryPreviewResponse) {
        val detail = resp.result.detail
        val date = resp.result.createdAt
        val dateString = convertDateFormat(date)
        DiaryPreviewOpen(detail, dateString)
    }

    fun convertDateFormat(inputDate: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
        val dateTime = LocalDateTime.parse(inputDate, formatter)
        val outputFormatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일")
        return dateTime.format(outputFormatter)
    }

    private fun changeDailyBoardStream(diaryphotoId: Int, streamName: String) {
        val dailyBoardService = DailyBoardService()
        dailyBoardService.setDailyBoardView(this)
        dailyBoardService.changeDailyBoardStream(diaryphotoId, streamName)
    }

    override fun changeDailyBoardStreamSuccess() {
        val dialog = SampleDialog(requireContext(),"스트림이 변경되었습니다!")
        dialog.show()
    }

    private fun onDailyBoardRemoveBtnClick(dirayPhotoId : Int) {
        val dailyBoardService = DailyBoardService()
        dailyBoardService.setDailyBoardView(this)
        dailyBoardService.onDailyBoardRemoveBtnClick(dirayPhotoId)
    }

    override fun onDailyBoardRemoveBtnClick(status: Boolean) {
        if(status) {
            removePhoto()
        }
    }

    private fun removePhoto() {
        binding.deleteTv.visibility = VISIBLE
        binding.deletedTv.visibility = INVISIBLE
    }

    private fun updateDeleteText() {

    }

}



