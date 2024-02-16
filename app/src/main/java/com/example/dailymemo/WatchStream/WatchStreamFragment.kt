package com.example.dailymemo.WatchStream



import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.dailymemo.MyStream.Retrofit.Response.post
import com.example.dailymemo.OpenStream.Retrofit.OpenStreamService
import com.example.dailymemo.OpenStream.Retrofit.Response.DiaryResult
import com.example.dailymemo.OpenStream.Retrofit.Response.LikeResult
import com.example.dailymemo.PostViewModel
import com.example.dailymemo.R
import com.example.dailymemo.WatchStream.Comment.CommentFragment
import com.example.dailymemo.databinding.FragmentWatchStreamBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale


class WatchStreamFragment() : Fragment(), WatchStreamView {

    lateinit var binding : FragmentWatchStreamBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWatchStreamBinding.inflate(inflater, container, false)

        val viewModel = ViewModelProvider(requireActivity()).get(PostViewModel::class.java)
        var isMyStream = viewModel.isMyStream
        // postData 사용
        val post = viewModel.post
        var jwt = getMyJwt()

        basicSetting(post, isMyStream)
        showDiary(post.postId)
        initRecyclerView(post)


        binding.apply {

            commentBtnLayout.setOnClickListener {
                showMenu(post.postId)
            }

            backIv.setOnClickListener {
                val fragmentManager = getActivity()?.getSupportFragmentManager();
                fragmentManager?.beginTransaction()?.remove(WatchStreamFragment())
                    ?.commit();
                fragmentManager?.popBackStack();

            }

            likeIc.setOnClickListener {
                onLikeBtnClick(jwt, post.postId)
            }


            return binding.root
        }
    }

    override fun onResume() {
        super.onResume()


    }

    private fun getMyJwt() : String? {
        val spf = requireActivity().getSharedPreferences("auth", Context.MODE_PRIVATE)
        return spf.getString("jwt", null)
    }

    private fun basicSetting(post: post, isMySteram : Boolean) {
        binding.apply {
            dateTv.text = convertDateFormat(post.createdAt)
            likeCountTv.text = post.likes.toString()
            commentCountTv.text = post.comments.toString()

            if(post.isLike) {
                likeIc.setImageResource(R.drawable.like_on_ic)
            }
            else {
                likeIc.setImageResource(R.drawable.like_box_ic)
            }

            if(isMySteram) {
                openStreamTitleIv.setImageResource(R.drawable.mystream_title_ic)
            }

        }
    }

    private fun initRecyclerView(post: post) {
        val watchStreamRVAdapter = WatchStreamRVAdpater(post)
        binding.streamRv.adapter = watchStreamRVAdapter
        binding.streamRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        binding.streamRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                updateCount()
            }
        })

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.streamRv)


    }

    private fun showMenu(postId : Int) {
        val bottomSheetFragment = CommentFragment(requireActivity(),postId)
        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)

    }

    private fun updateCount() {
        var layoutManager = binding.streamRv.layoutManager
        val adapter = binding.streamRv.adapter
        var currentPos: Int =
            (layoutManager as? LinearLayoutManager)!!.findFirstVisibleItemPosition() + 1
        var total = adapter?.itemCount

        binding.countTv.text = "$currentPos/$total"
    }


    fun convertDateFormat(inputDate: String): String {
        val inputFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputFormatter = SimpleDateFormat("yyyy년 M월 d일", Locale.getDefault())

        val date = inputFormatter.parse(inputDate)
        return outputFormatter.format(date!!)
    }

    private fun showDiary(postId : Int) {
        val openStreamService = OpenStreamService()
        openStreamService.setWatchStreamView(this)
        openStreamService.showDiary(postId)
    }

    override fun showDiarySuccess(resp: DiaryResult) {
        val diary = resp.detail
        binding.apply {
            diaryTextTv.text = diary
        }

        isPublicSetting(resp.isPublic)

    }


    private fun isPublicSetting(isPublic: Boolean) {
        if(isPublic == false) {
            binding.apply {
                likeBtnLayout.visibility = INVISIBLE
                commentBtnLayout.visibility = INVISIBLE
                lockIv.visibility = VISIBLE
            }
        }
    }

    private fun onLikeBtnClick(jwt: String?, postId : Int) {
        val openStreamService = OpenStreamService()
        openStreamService.setWatchStreamView(this)
        openStreamService.like(jwt, postId)
    }

    override fun onLikeBtnClickSuccess(resp: LikeResult) {
        val likes = resp.likes.toString()
        val isLike = resp.like_check

        binding.apply {
            likeCountTv.text = likes
            if(isLike) {
                likeIc.setImageResource(R.drawable.like_on_ic)
            }
            else {
                likeIc.setImageResource(R.drawable.like_box_ic)
            }

        }
    }




}




