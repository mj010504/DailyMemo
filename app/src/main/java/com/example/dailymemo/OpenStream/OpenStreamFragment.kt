package com.example.dailymemo.OpenStream

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dailymemo.MainView
import com.example.dailymemo.MyStream.Dialog.DeleteDailog
import com.example.dailymemo.MyStream.Dialog.KeywordCheckDialog
import com.example.dailymemo.MyStream.MyStreamRVAdapter
import com.example.dailymemo.MyStream.Retrofit.MyStreamService
import com.example.dailymemo.MyStream.Retrofit.Response.post
import com.example.dailymemo.MyStream.Retrofit.Response.stream
import com.example.dailymemo.OpenStream.Retrofit.OpenStreamService
import com.example.dailymemo.OpenStream.Retrofit.OpenStreamView
import com.example.dailymemo.OpenStream.Retrofit.Response.OpenStreamResponse
import com.example.dailymemo.R
import com.example.dailymemo.databinding.FragmentOpenStreamBinding
import com.google.android.material.internal.ViewUtils.hideKeyboard


class OpenStreamFragment : Fragment(), OpenStreamView, MainView {

    lateinit var binding: FragmentOpenStreamBinding

    var page: Int = 1
    var hasNext: Boolean = false
    var searchPage: Int = 1
    var searchHasNext = false

    lateinit var postList: List<post>
    var listSize: Int = 0
    var userId = 1
    var jwt : String? = null

    lateinit var adapter: OpenStreamRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOpenStreamBinding.inflate(inflater, container, false)


        binding.apply {
            openstreamRootView.isEnabled = false

            searchingIv.setOnClickListener {
                searchEt.visibility = VISIBLE
                searchEt.addTextChangedListener(object : TextWatcher {
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
                        searchingIv.visibility = INVISIBLE
                        searchTv.visibility = VISIBLE

                        val inputText = s.toString()
                        if (inputText.isEmpty()) {
                            searchTv.text = "취소"

                            searchTv.setOnClickListener {
                                hideKeyboard(it)
                                searchTv.visibility = INVISIBLE
                                searchingIv.visibility = VISIBLE
                            }
                        } else {
                            searchTv.text = "검색"
                            searchTv.setOnClickListener {
                                val keyword = searchEt.text.toString()
                                if (keyword.length < 2) {
                                    showKeywordCheckDialog()
                                } else {
                                    searchOpenStream(jwt, keyword)

                                    searchTv.visibility = INVISIBLE
                                    searchingIv.visibility = VISIBLE

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


        userId = getMyUserId()
        jwt = getMyJwt()

    }

    override fun onResume() {
        super.onResume()
        showWatchStream(userId, 1)

        showOpenStream(jwt, page)

        infiniteScroll()
    }

    private fun getMyUserId(): Int {
        val spf = requireActivity().getSharedPreferences("auth", Context.MODE_PRIVATE)
        return spf.getInt("userId", 1)
    }

    private fun getMyJwt() : String? {
        val spf = requireActivity().getSharedPreferences("auth", Context.MODE_PRIVATE)
        return spf.getString("jwt", null)
    }

    private fun initRecyclerView(postList: ArrayList<post>, listSize: Int) {
        val openStreamRVAdapter = OpenStreamRVAdapter(requireActivity(),listSize, postList, parentFragmentManager)
        binding.openstreamRv.adapter = openStreamRVAdapter
        binding.openstreamRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)


    }


    private fun hideKeyboard(view: View) {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun showKeywordCheckDialog() {
        val dialog = KeywordCheckDialog(requireContext())
        dialog.show()
    }


    private fun infiniteScroll() {
        binding.openstreamRootView.viewTreeObserver.addOnScrollChangedListener {

            // NestedScrollView의 ScrollY 값과 ScrollableView의 높이를 통해 스크롤 상태를 확인
            if (binding.openstreamRootView.scrollY >= binding.openstreamRootView.height) {

            }
        }
    }


    private fun showWatchStream(userId: Int, page: Int) {
        val myStreamService = MyStreamService()
        myStreamService.setMainView(this)
        myStreamService.showWatchStream(userId, page)
    }

    override fun showWatchStreamSuccess(streamList: List<stream>) {
        if (streamList.isEmpty()) {
            makeMyStream(userId, "일상")
            makeMyStream(userId, "여행")
            makeMyStream(userId, "맛집")
        } else {
            val sharedPreferences =
                requireActivity().getSharedPreferences("Streams", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            var index = 1
            for (stream in streamList) {
                editor.putString("stream" + index, stream.streamName)
                editor.putInt(stream.streamName, stream.streamId)
                editor.apply()
                index += 1
            }
        }
    }

    private fun makeMyStream(userId: Int, streamName: String) {
        val myStreamService = MyStreamService()
        myStreamService.setMainView(this)
        myStreamService.makeMyStream(userId, streamName)
    }

    override fun makeMyStreamSuccess(streamId: Int, streamName: String) {
        val sharedPreferences =
            requireActivity().getSharedPreferences("Streams", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // 데이터 저장
        editor.putInt(streamName, streamId)

        // 저장
        editor.apply()
    }

    private fun showOpenStream(jwt: String?, page : Int) {
        val openStreamService = OpenStreamService()
        openStreamService.setOpenStreamView(this)
        openStreamService.showOpenStream(jwt, page)
    }

    override fun showOpenStreamSuccess(resp: OpenStreamResponse) {
        if (resp.result.hasNext) {
            hasNext = true
            page += 1
        } else {
            hasNext = false
        }

        postList = resp.result.postList
        listSize = resp.result.listSize


            val photoArrayList = ArrayList(postList)
            initRecyclerView(photoArrayList, listSize)

    }

    private fun searchOpenStream(jwt: String?, query: String) {
        val openStreamService = OpenStreamService()
        openStreamService.setOpenStreamView(this)
        openStreamService.search(jwt, query, searchPage)
    }

    override fun searchOpenStreamSuccess(resp: OpenStreamResponse) {
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

}