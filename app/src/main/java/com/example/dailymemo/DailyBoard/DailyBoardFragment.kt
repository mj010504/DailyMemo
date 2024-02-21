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
import android.media.Image
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
import com.example.dailymemo.DailyBoard.Retrofit.Response.ImageRequest
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
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale


class DailyBoardFragment : Fragment(), DailyBoardView {

    lateinit var binding: FragmentDailyBoardBinding
    private var imageList = ArrayList<Uri>()
    private var isDiaryPreview = false
    private var pageNum = 1
    private var hasNext = false
    lateinit var adapter : DailyBoardRVAdapter

    var isEmpty = true

    lateinit var diaryList : List<Diary>


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
                    val streamName = binding.streamNameTv.text.toString()
                    val spf = requireActivity().getSharedPreferences("Streams",Context.MODE_PRIVATE)
                    val streamId = spf.getInt(streamName, 1)

                    showDiaryPreview(streamId)
                }
                else {
                    DiaryPreviewClose()
                }
             }

            deleteLayout.setOnClickListener {
                var layoutManager = binding.dailyBoardRv.layoutManager
                var pos: Int = (layoutManager as? LinearLayoutManager)!!.findFirstVisibleItemPosition()
                val diaryPhotoId = adapter.getdiaryPhotoIdByPosition(pos)
                onDailyBoardRemoveBtnClick(diaryPhotoId)
                adapter.udpateStatus(pos)
               if( adapter.getStatus(pos)) {
                   removePhoto()
               }
                else {
                    nonRemovePhoto()
               }
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

        val sharedPreferences = requireActivity().getSharedPreferences("time", Context.MODE_PRIVATE)
        val lastTime = sharedPreferences.getLong("lastTime", 0)


        getImage(lastTime)

        val jwt = getMyJwt()
        val userId = getMyUserId()


        if(imageList.size > 0) {
            Log.d("lastTime", imageList.size.toString() )
            val base64List : ImageRequest = getBase64List(imageList)
            storeImage(jwt, base64List)
        }
        else {
            showDailyBoard(jwt, userId, pageNum)
        }



    }


    override fun onResume() {
        super.onResume()

        }


    fun saveBase64ListToFile(context: Context, base64List: List<String>, fileName: String) {
        try {
            val fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)
            val outputStreamWriter = OutputStreamWriter(fileOutputStream)

            for (base64String in base64List) {
                outputStreamWriter.write("$base64String\n")
            }

            outputStreamWriter.close()
            fileOutputStream.close()
            Log.d("FileSave", "Base64 List saved to file: $fileName")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }



    private fun getBase64List(images: List<Uri>) : ImageRequest{
        val cl : List<Bitmap> = compressImages(images)
        val resizeBitmapList = resizeBitmapList(cl, 100, 100)
        var base64List = resizeBitmapList.map{ bitmapToBase64(it)}
        val imageRequest = ImageRequest(base64List)


        saveBase64ListToFile(requireContext(), base64List, "base64_list.txt")

        return imageRequest
    }



    private fun getMyUserId() : Int {
        val spf = requireActivity().getSharedPreferences("auth", Context.MODE_PRIVATE)
        return spf.getInt("userId", 1)
    }


    private fun getMyJwt() : String? {
        val spf = requireActivity().getSharedPreferences("auth", Context.MODE_PRIVATE)
        return spf.getString("jwt", null)
    }


    private fun initRecyclerView(diaryList : ArrayList<Diary>) {

        val dailyBoardRVAdapter = DailyBoardRVAdapter(requireContext(), diaryList)
        adapter = dailyBoardRVAdapter
        binding.dailyBoardRv.adapter = dailyBoardRVAdapter
        binding.dailyBoardRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        binding.dailyBoardRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                updateCount()
                var layoutManager = binding.dailyBoardRv.layoutManager
                var pos: Int = (layoutManager as? LinearLayoutManager)!!.findFirstVisibleItemPosition()
                val status = adapter.getStatus(pos)

                if(status) {
                    removePhoto()
                }
                else {
                    nonRemovePhoto()
                }
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

        for(diary in diaryList) {
           if (diary.diaryPhotoList.isNotEmpty() ) {
               isEmpty = false
               break
           }

        }

        if (isEmpty == false) {
            binding.basicIv.visibility = INVISIBLE
            binding.basicTv.visibility = INVISIBLE
            binding.countLayout.visibility = VISIBLE
        }
        else {
            binding.basicIv.visibility = VISIBLE
            binding.basicTv.visibility = VISIBLE
            binding.countLayout.visibility = INVISIBLE
        }
    }

    private fun showStreamDiaryMenu() {
        if (diaryList.isNotEmpty()) {
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
                override fun onStreamDiaryClick(streamName: String) {
                    val sharedPreferences = requireActivity().getSharedPreferences("Streams", Context.MODE_PRIVATE)
                    val streamId = sharedPreferences.getInt(streamName, 1)
                    showDiaryMenu(streamId)
                }

            } )


            bottomSheetDialog.show()
        }
        else {
            val dialog = SampleDialog(requireContext(),"사진을 등록해야 일기 작성이 가능합니다.")
            dialog.show()
        }
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

        val sharedPreferences = requireActivity().getSharedPreferences("time", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong("lastTime", today.timeInMillis)
        editor.apply()

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

    private fun getImage(lasttime: Long) {

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




                            // lasttime 이후의 이미지만 추가
                            if (dateTaken > lasttime) {

                                val imageUri =
                                    ContentUris.withAppendedId(
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                        id
                                    )

                                imageList.add(imageUri)

                            }
                        }
                        cursor.close()
                    }
                }
            } catch (e: Exception) {
                // 에러 대응 코드 작성
                Log.d("cursor", "cursor Error")
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


    fun resizeBitmapList(bitmapList: List<Bitmap>, targetWidth: Int, targetHeight: Int): List<Bitmap> {
        val resizedBitmapList = mutableListOf<Bitmap>()

        for (bitmap in bitmapList) {
            val resizedBitmap = resizeBitmap(bitmap, targetWidth, targetHeight)
            resizedBitmap?.let {
                resizedBitmapList.add(it)
            }
        }

        return resizedBitmapList
    }

    fun resizeBitmap(bitmap: Bitmap, targetWidth: Int, targetHeight: Int): Bitmap? {
        try {
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
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
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

    private fun showDiaryMenu(streamId : Int) {
        val bottomSheetFragment = DiaryFragment(streamId)
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
        dailyBoardStreamRVAdapter.setMyItemClickListener(object: DailyBoardStreamRVAdapter.MyItemClickListener{
            override fun onStreamTypeClick(streamName: String) {
                var layoutManager = binding.dailyBoardRv.layoutManager
                var pos: Int = (layoutManager as? LinearLayoutManager)!!.findFirstVisibleItemPosition()
                val diaryPhotoId = adapter.getdiaryPhotoIdByPosition(pos)

                val spf = requireActivity().getSharedPreferences("Streams", Context.MODE_PRIVATE)
                val streamId = spf.getInt(streamName, 1)

                changeDailyBoardStream(diaryPhotoId, streamId)

                popupWindow.dismiss()
            }

        })
        val upBtn = customMenuView.findViewById<ImageView>(R.id.fold_up_btn_iv)
        upBtn.setOnClickListener {
            popupWindow.dismiss()
        }
    }

    private fun showDailyBoard(jwt: String?,userId : Int, page : Int) {
        val dailyBoardService = DailyBoardService()
        dailyBoardService.setDailyBoardView(this)
        dailyBoardService.showDailyBoard(jwt,userId, page)

    }

    override fun onShowDailyBoardSuccess(resp: showDailyBoardResponse) {
        if(resp.result.hasNext) {
            pageNum += 1
            hasNext = true
        }
        else {
            hasNext = false
        }

        diaryList = resp.result.diaryList
        val diaryList = ArrayList(diaryList)
        initRecyclerView(diaryList)

        basicSetting()
    }

   private fun storeImage(jwt: String?, images : ImageRequest) {
        val dailyBoardService = DailyBoardService()
        dailyBoardService.setDailyBoardView(this)
        dailyBoardService.storeImage(jwt, images)
    }

    override fun storeImageSuccess(resp: storeImageResponse) {
        val jwt = getMyJwt()
        val userId = getMyUserId()
        showDailyBoard(jwt, userId, pageNum)
    }

    override fun storeImageFailed() {
        val jwt = getMyJwt()
        val userId = getMyUserId()
        showDailyBoard(jwt, userId, pageNum)
    }


    fun convertDateFormat(inputDate: String): String {
        val inputFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputFormatter = SimpleDateFormat("yyyy년 M월 d일", Locale.getDefault())

        val date = inputFormatter.parse(inputDate)
        return outputFormatter.format(date!!)
    }

    private fun changeDailyBoardStream(diaryphotoId: Int, streamId: Int) {
        val dailyBoardService = DailyBoardService()
        dailyBoardService.setDailyBoardView(this)
        dailyBoardService.changeDailyBoardStream(diaryphotoId, streamId)
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

    private fun nonRemovePhoto() {
        binding.deleteTv.visibility = VISIBLE
        binding.deletedTv.visibility = INVISIBLE
    }

    private fun removePhoto() {
        binding.deleteTv.visibility = INVISIBLE
        binding.deletedTv.visibility = VISIBLE
    }

    private fun showDiaryPreview(streamId : Int) {
        val dailyBoardService = DailyBoardService()
        dailyBoardService.setDailyBoardView(this)
        dailyBoardService.showDiaryPreview(streamId)
    }

    override fun showDiaryPreviewSuccess(resp: showDiaryPreviewResponse) {
        val detail = resp.result.detail
        val date = resp.result.createdAt
        val dateString = convertDateFormat(date)
        val imageList = resp.result.imageList

        DiaryPreviewOpen(detail, dateString)
    }

}



