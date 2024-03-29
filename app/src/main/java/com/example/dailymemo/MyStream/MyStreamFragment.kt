package com.example.dailymemo.MyStream

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.dailymemo.DailyBoard.DailyBoardRVAdapter
import com.example.dailymemo.MainActivity
import com.example.dailymemo.MyStream.Dialog.DeleteCheckDailog
import com.example.dailymemo.MyStream.Dialog.KeywordCheckDialog
import com.example.dailymemo.MyStream.Retrofit.MyStreamService
import com.example.dailymemo.MyStream.Retrofit.MyStreamView
import com.example.dailymemo.MyStream.Retrofit.Response.openMyStreamResponse
import com.example.dailymemo.MyStream.Retrofit.Response.post
import com.example.dailymemo.MyStream.Retrofit.Response.searchMyStreamResponse
import com.example.dailymemo.R
import com.example.dailymemo.Setting.Dialog.SampleDialog
import com.example.dailymemo.databinding.FragmentMyStreamBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.io.File


class MyStreamFragment : Fragment(), MyStreamView {


    lateinit var binding: FragmentMyStreamBinding
    var page : Int = 1
    var hasNext : Boolean = false
    var searchPage : Int = 1
    var searchHasNext = false

    lateinit var postList : List<post>
    var listSize : Int  = 0
    var userId = 1

    lateinit var adapter : MyStreamRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyStreamBinding.inflate(inflater, container, false)


        binding.apply {
            userProfileIv.setOnClickListener {
                showPopupMenu(userProfileIv)
            }

            searchingIv.setOnClickListener {
                searchEt.visibility = View.VISIBLE
                searchEt.addTextChangedListener(object: TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {

                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        searchingIv.visibility = View.INVISIBLE
                        searchTv.visibility = View.VISIBLE

                        val inputText = s.toString()
                        if(inputText.isEmpty()) {
                            searchTv.text = "취소"

                            searchTv.setOnClickListener {
                                hideKeyboard(it)
                                searchTv.visibility = View.INVISIBLE
                                searchingIv.visibility = View.VISIBLE
                            }
                        }
                        else {
                            searchTv.text = "검색"
                            searchTv.setOnClickListener {
                                val keyword = searchEt.text.toString()
                                if(keyword.length < 2) {
                                    showKeywordCheckDialog()
                                }
                                else {
                                    searchMyStream(userId, keyword, searchPage )

                                    searchTv.visibility = View.INVISIBLE
                                    searchingIv.visibility = View.VISIBLE

                                    searchEt.hint = "'$keyword'에 대한 검색결과"
                                    searchEt.setText("")
                                }
                            }
                        }
                    }

                    override fun afterTextChanged(s: Editable?) {

                    }

                })


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

    override fun onResume() {
        super.onResume()
        val spf = requireActivity().getSharedPreferences("Streams", Context.MODE_PRIVATE)
        val streamName = spf.getString("stream"+1, "일상")
        val streamId = spf.getInt(streamName, 1)
        userId = getMyUserId()


        showMyStream(streamId, userId, page)

        infiniteScroll()
    }

    private fun getMyUserId() : Int {
        val spf = requireActivity().getSharedPreferences("auth", Context.MODE_PRIVATE)
        return spf.getInt("userId", 1)
    }

    private fun initRecyclerView(postList: ArrayList<post>, listSize : Int) {
        val myStreamRVAdapter = MyStreamRVAdapter(requireActivity(),listSize, postList, parentFragmentManager)
        binding.mystreamRv.adapter = myStreamRVAdapter
        adapter = myStreamRVAdapter
        binding.mystreamRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        myStreamRVAdapter.seMyItemClickListener(object: MyStreamRVAdapter.MyItemClickListener {
            override fun onMenuClick(isPublic: Boolean) {
                showMenu(isPublic)
            }
        })
    }

    private fun hideKeyboard(view: View) {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun showKeywordCheckDialog() {
        val dialog = KeywordCheckDialog(requireContext())
        dialog.show()
    }


    private fun showPopupMenu(anchorView: View) {
        val inflater = LayoutInflater.from(requireContext())
        val customMenuView = inflater.inflate(R.layout.stream_setting_popup_menu_layout, null)

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
        popupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY, xOffset, yOffset)
        popupWindow.setBackgroundDrawable(ColorDrawable(Color.BLACK))
        popupWindow.elevation = resources.getDimension(R.dimen.popup_card_elevation)

        val recyclerView = customMenuView.findViewById<RecyclerView>(R.id.stream_setting_rv)
        val streamSettingRVAdapter = StreamSettingRVAdapter(requireActivity())
        recyclerView.adapter = streamSettingRVAdapter
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        streamSettingRVAdapter.seMyItemClickListener(object: StreamSettingRVAdapter.MyItemClickListener {
            override fun onStreamClick(streamName: String) {
                val sharedPreferences = requireActivity().getSharedPreferences("Streams", Context.MODE_PRIVATE)
                val streamId = sharedPreferences.getInt(streamName, 1)

                showMyStream(streamId, userId, page)

                popupWindow.dismiss()
            }

        })
    }

    private fun showMenu(isPublic: Boolean) {

            val bottomSheetView = layoutInflater.inflate(R.layout.mystream_bottom_menu_layout, null)
            val bottomSheetDialog = BottomSheetDialog(requireContext())
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED

            val lockBtn = bottomSheetView.findViewById<ConstraintLayout>(R.id.stream_lock_layout)
            val deleteBtn = bottomSheetView.findViewById<ConstraintLayout>(R.id.stream_delete_layout)

        var layoutManager = binding.mystreamRv.layoutManager
        var pos: Int = (layoutManager as? LinearLayoutManager)!!.findFirstVisibleItemPosition()

        val lockTitle = bottomSheetView.findViewById<TextView>(R.id.stream_lock_tv)
        if(isPublic) {
            lockTitle.text = "숨기기"
        }
        else {
            lockTitle.text = "공개하기"
        }


        val postId = adapter.getPostId(pos)


        lockBtn.setOnClickListener {
                diaryPublicType(userId, postId, !isPublic)
                adapter.updateIsPublic(pos, !isPublic)
                bottomSheetDialog.dismiss()
            }

            deleteBtn.setOnClickListener {

                removeMyStreamDiary(postId)
                bottomSheetDialog.dismiss()
            }

            bottomSheetDialog.show()


    }


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

    private fun infiniteScroll() {
        if(searchHasNext == true) {
            binding.mystreamRootView.viewTreeObserver.addOnScrollChangedListener {
                // NestedScrollView의 ScrollY 값과 ScrollableView의 높이를 통해 스크롤 상태를 확인
                if (binding.mystreamRootView.scrollY >= binding.mystreamRootView.height) {

                }
            }
        }
        else if(hasNext == true) {
            binding.mystreamRootView.viewTreeObserver.addOnScrollChangedListener {
                // NestedScrollView의 ScrollY 값과 ScrollableView의 높이를 통해 스크롤 상태를 확인
                if (binding.mystreamRootView.scrollY >= binding.mystreamRootView.height) {


                }
            }
        }
    }

    private fun showMyStream(streamId: Int, userId : Int, page: Int) {
        val myStreamService = MyStreamService()
        myStreamService.setMyStreamView(this)
        myStreamService.showMystream(streamId, userId, page)
    }

    override fun showMyStreamSuccess(resp: openMyStreamResponse) {

        if(resp.result.hasNext) {
            hasNext = true
            page += 1
        }
        else {
            hasNext = false
        }

        postList = resp.result.postList
        listSize = resp.result.listSize


            val postArrayList = ArrayList(postList)
            initRecyclerView(postArrayList, listSize)

    }

    private fun searchMyStream(userId : Int, query : String, page : Int) {
        val myStreamService = MyStreamService()
        myStreamService.setMyStreamView(this)
        myStreamService.searchMyStream(userId, query, page)
    }

    override fun searchMyStreamSuccess(resp: searchMyStreamResponse) {
        if(resp.result.hasNext) {
            searchHasNext = true
            searchPage += 1
        }
        else {
            searchHasNext = false
        }

        postList = resp.result.postList
        listSize = resp.result.listSize

        val postArrayList = ArrayList(postList)

        initRecyclerView(postArrayList, listSize)
    }

    private fun diaryPublicType(userId : Int, postId: Int, isPublic: Boolean) {
        val myStreamService = MyStreamService()
        myStreamService.setMyStreamView(this)
        myStreamService.diaryPublicType(userId, postId, isPublic)
    }

    override fun diaryPublicTypeSuccess(isPublic: Boolean) {
        if(isPublic == false) {
            val dialog = SampleDialog(requireContext(), "일기가 비공개되었습니다.")
            dialog.show()
        }
        else {
            val dialog = SampleDialog(requireContext(), "일기가 공개되었습니다.")
            dialog.show()
        }
    }

    private fun removeMyStreamDiary(postId : Int) {
        val myStreamService = MyStreamService()
        myStreamService.setMyStreamView(this)
        myStreamService.removeMyStreamDiary(postId)

    }



    override fun removeMyStreamDiarySuccess() {

        var layoutManager = binding.mystreamRv.layoutManager
        var pos: Int = (layoutManager as? LinearLayoutManager)!!.findFirstVisibleItemPosition()


        adapter.removeItem(pos)
        val dialog = SampleDialog(requireContext(), "일기가 삭제되었습니다.")
        dialog.show()
    }
}

