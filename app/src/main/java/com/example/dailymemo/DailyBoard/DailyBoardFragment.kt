package com.example.dailymemo.DailyBoard

import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Color
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
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class DailyBoardFragment : Fragment() {

    lateinit var binding: FragmentDailyBoardBinding
    private var photoList = ArrayList<Int>()
    private var isPhoto = false
    private var imageList = ArrayList<Uri>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDailyBoardBinding.inflate(inflater, container, false)
        initRecyclerView()

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


        photoList.apply {
            add(R.drawable.daily1)
            add(R.drawable.daily2)
            add(R.drawable.daily3)
        }



        binding.apply {
            // 사진 업로드(미완성)
            userProfileIv.setOnClickListener {
                uploadPhoto()
            }

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

        val yesterday = Calendar.getInstance()
        yesterday.add(Calendar.DATE, -1) // 오늘 날짜에서 1일을 빼서 어제의 날짜를 얻음

        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formattedDateYesterday = formatter.format(yesterday.time)
        Log.d("yesterday", formattedDateYesterday)

        getImage()
        if(imageList.isNotEmpty()) {
            val uri = imageList[3]

            Glide.with(this)
                .load(uri)
                .into(binding.basicIv)
        }
        else {
            Log.d("cursor", "Empty Empty Empty")
        }

    }

    private fun initRecyclerView() {
        val dailyBoardRVAdapter = DailyBoardRVAdapter(photoList)
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

    private fun uploadPhoto() {
        if (photoList.size > 0) {
            binding.basicIv.visibility = INVISIBLE
            binding.basicTv.visibility = INVISIBLE
        }
    }

    private fun showMenu() {
        if (photoList.size > 0) {
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

                val streamChangeRVAdpater = StreamChangeRVADapter()
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
        //커서란?
        //ContentResolver.query() 클라이언트 메서드는 언제나 쿼리 선택 기준과 일치하는 행에 대해 쿼리 프로젝션이 지정한 열을 포함하는 Cursor를 반환합니다.
        //데이터베이스 쿼리에서 반환된 결과 테이블의 행들을 가르키는 것
        //이 인터페이스는 데이터베이스 쿼리에서 반환된 결과 집합에 대한 임의의 읽기-쓰기 액세스를 제공합니다.

        val projection = arrayOf(
            MediaStore.Images.ImageColumns._ID,
            MediaStore.Images.ImageColumns.TITLE,
            MediaStore.Images.ImageColumns.DATE_TAKEN

        ) //mediaStore provider의 사진의 id, title, date_taken을 가져오겠다.

        //가져오고 싶은 행 Filter 하는 법
        //val selection = "${MediaStore.Images.ImageColumns.DATE_TAKEN} >= ?"
        //? 이후에 찍힌 것만
        //val selectionArgs = arrayOf(
        //dateToTimestamp(day = 1, month = 1, year = 1970).toString()) //?는 1970년 1월 1일

        //모두 가져오고 싶으면 selection과 selectionArgs에 null을 넣어주면 된다.
        val selection = null
        val selectionArgs = null
        val sortOrder = "${MediaStore.Images.ImageColumns.DATE_TAKEN} DESC" //내림차순
        //"${MediaStore.Images.ImageColumns.DATE_TAKEN} ASC" //오름차순

        val cursor = requireContext().contentResolver.query(
            //Uri: 찾고자하는 데이터의 Uri입니다. 접근할 앱에서 정의됨. 내 앱에서 만들고 싶다면 manifest에서 만들 수 있음.
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            //Projection: 일반적인 DB의 column과 같습니다. 결과로 받고 싶은 데이터의 종류를 알려줍니다. (표1.에서는 각 행에 포함 되어야 하는 열의 배열이다.)
            projection,
            //Selection: DB의 where 키워드와 같습니다. 어떤 조건으로 필터링된 결과를 받을 때 사용합니다. (표1. 에서는 행을 선택하는 기준)
            selection,
            //Selection args: Selection과 함께 사용됩니다. SELECT 절에 있는 ? 자리표시자를 대체합니다.
            selectionArgs,
            //SortOrder: 쿼리 결과 데이터를 sorting할 때 사용합니다.(반환된 Cursor 내에 행이 나타나는 순서를 지정합니다.)
            null
        )

        //1건만 가져오려면?
        //Uri 및 Uri.Builder 클래스에는 문자열에서 올바른 형식의 URI 객체를 구성하기 위한 편의 메서드가 포함되어 있습니다.
        //Uri.Builder는 URI 참조를 빌드하거나 조작하기 위한 도우미 클래스입니다.
        //appendQueryParameter : Encodes the key and value and then appends the parameter to the query string.
        //val queryUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        //queryUri.buildUpon().appendQueryParameter("limit", "1").build()

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
                        /*
                         * 에러 대응 코드 작성. cursor 사용하지 말것!!
                         * You may want to call android.util.Log.e() to log this error.
                         *
                         */
                    }

                    0 -> {
                        Log.d("cursor", "cursor count = 0")
                        /*
                         *사용자에게 검색이 실패했음을 알리려면 여기에 코드를 삽입하십시오.
                         * 무조건 에러는 아니다. 테이블을 못찾은게 아니라 말 그대로 테이블에 행이 0개 일 수도.
                         * 사용자에게 새 항목을 삽입할 수 있는 옵션을 제공할 수 있습니다.
                         * 행 또는 검색어를 다시 입력하십시오.
                         */
                    }

                    else -> {
                        //결과가 1개이상 검색 됨
                        //커서를 맨 앞으로 이동.
                        //true를 반환해야 데이터가 있는 것임.
                        while (cursor.moveToNext()) {
                            //1. 각 컬럼의 열 인덱스를 취득한다.
                            val idColNum =
                                cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns._ID)
                            val titleColNum =
                                cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.TITLE)
                            val dateTakenColNum =
                                cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATE_TAKEN)

                            //2. 인덱스를 바탕으로 각 행의 열 값(마지막 행에 도달할 때 까지 1행의 id,title,dateTaken, 2행의 id,title,dateTaken...)을 Cursor로부터 취득하기
                            val id = cursor.getLong(idColNum)
                            val title = cursor.getString(titleColNum)
                            val dateTaken = cursor.getLong(dateTakenColNum)
                            /*Cursor를 통해 얻은 ID로 Uri 정보를 얻을 수 있습니다.
                            쿼리를 요청한 Uri와 파일의 ID가 다음과 같이 주어졌다면,

                            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI : content://media/external/audio/media
                            File ID : 13492
                            이 파일의 Uri는 다음처럼 두개의 스트링을 합친 값이 됩니다.
                            content://media/external/audio/media/13492
                            String이 아닌 Uri 객체로 얻으려면 다음처럼 Uri.withAppendedPath()를 이용하시면 됩니다.*/
                            val imageUri =
                                ContentUris.withAppendedId(
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                    id
                                )

                            imageList.add(imageUri)//recylcerview에 넣기 위한 uri list

                            Log.d(
                                "cursor",
                                "id: ${id}, title:$title, dateTaken : $dateTaken, imageUri : $imageUri"
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
}



