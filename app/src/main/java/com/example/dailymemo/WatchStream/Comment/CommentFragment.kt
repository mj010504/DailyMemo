    package com.example.dailymemo.WatchStream.Comment

    import android.content.Context
    import android.content.Context.INPUT_METHOD_SERVICE
    import android.os.Bundle
    import android.text.Editable
    import android.text.SpannableStringBuilder
    import android.util.Log
    import android.view.Gravity
    import android.view.LayoutInflater
    import android.view.View
    import android.view.View.INVISIBLE
    import android.view.View.VISIBLE
    import android.view.ViewGroup
    import android.view.inputmethod.InputMethodManager
    import android.widget.PopupWindow
    import androidx.constraintlayout.widget.ConstraintLayout
    import androidx.fragment.app.FragmentActivity
    import androidx.recyclerview.widget.LinearLayoutManager
    import com.bumptech.glide.Glide
    import com.bumptech.glide.load.engine.DiskCacheStrategy
    import com.example.dailymemo.MyStream.Retrofit.Response.post
    import com.example.dailymemo.OpenStream.Retrofit.OpenStreamService
    import com.example.dailymemo.OpenStream.Retrofit.Response.CommentResult
    import com.example.dailymemo.OpenStream.Retrofit.Response.ShowCommentResponse
    import com.example.dailymemo.OpenStream.Retrofit.Response.ShowCommentResult
    import com.example.dailymemo.R
    import com.example.dailymemo.databinding.FragmentCommentBinding
    import com.google.android.material.bottomsheet.BottomSheetBehavior
    import com.google.android.material.bottomsheet.BottomSheetDialog
    import com.google.android.material.bottomsheet.BottomSheetDialogFragment
    import com.google.android.material.internal.ViewUtils.hideKeyboard
    import java.io.File

    class CommentFragment(activity: FragmentActivity, postId : Int) : BottomSheetDialogFragment(), CommentView  {

        val act = activity

        lateinit var binding : FragmentCommentBinding
        var hasNext = false
        var page : Int = 1
        val postId = postId
        var jwt = getMyJwt()
        private lateinit var adapter : CommentRVAdapter

        lateinit var commentList: List<CommentResult>
        var listSize: Int = 0

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            binding = FragmentCommentBinding.inflate(inflater, container, false)



            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)


            // 저장된 이미지를 불러와서 이미지뷰에 설정하는 함수 호출
            val savedImagePath = loadSavedImagePath()
            if (savedImagePath.isNotEmpty()) {
                loadImageFromInternalStorage(savedImagePath)
            }

            val dialog = dialog as? BottomSheetDialog

            dialog?.let {
                it.behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }

        }

        override fun onResume() {
            super.onResume()

            showComment(jwt, postId, page)
        }

        private fun getMyJwt() : String? {
            val spf = act.getSharedPreferences("auth", Context.MODE_PRIVATE)
            return spf.getString("jwt", null)
        }


        private fun initRecyclerView(commentList : ArrayList<CommentResult>, listSize: Int) : CommentRVAdapter {
            val commentRVAdapter = CommentRVAdapter(requireActivity(), commentList, listSize)
            binding.commentRv.adapter = commentRVAdapter
            adapter = commentRVAdapter
            binding.commentRv.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            if(adapter.itemCount == 0 ) {
                showBasic()
            }
            else {
                removeBasic()
            }

            binding.sendBtnIv.setOnClickListener {
                if(binding.commentEt.text.toString().length > 0) {
                    writeComment(jwt, postId, binding.commentEt.text.toString())
                    hideKeyboard(it)
                    removeBasic()
                }
            }

            commentRVAdapter.setMyItemClickListener(object : CommentRVAdapter.MyItemClickListener {
                override fun onMenuClick(menu: ConstraintLayout, position: Int, commentText : String) {
                    showPopupMenu(commentRVAdapter,menu, position, commentText)
                }
            })

            return commentRVAdapter

        }



        private fun showPopupMenu(adapter : CommentRVAdapter, anchorView: View, position : Int, commentText : String) {
            val inflater = LayoutInflater.from(requireContext())
            val customMenuView = inflater.inflate(R.layout.my_comment_popup_menu_layout, null)

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
            val xOffset = location[0] + 600
            val yOffset = location[1]

            // Set location
            popupWindow?.showAtLocation(anchorView, Gravity.NO_GRAVITY, xOffset, yOffset)

            val deleteBtn = customMenuView.findViewById<ConstraintLayout>(R.id.delete_btn)
            val modifyBtn = customMenuView.findViewById<ConstraintLayout>(R.id.comment_modify_btn)

            // 댓글 수정
            modifyBtn.setOnClickListener {
                popupWindow.dismiss()
                binding.commentEt.text = commentText.toEditable()

                // 키보드 올리기
                binding.commentEt.requestFocus()
                val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                binding.commentEt.postDelayed({inputMethodManager.showSoftInput(binding.commentEt, 0)}, 100)

                binding.sendBtnIv.setOnClickListener {
                    if(binding.commentEt.text.toString().length > 0) {
                        val commentId = adapter.getCommentId(position)
                        changeComment(jwt, commentId, binding.commentEt.text.toString())
                        binding.commentEt.text = null
                        hideKeyboard(it)

                        binding.sendBtnIv.setOnClickListener {
                            if(binding.commentEt.text.toString().length > 0) {
                               writeComment(jwt, postId,binding.commentEt.text.toString())
                                hideKeyboard(it)
                                removeBasic()
                            }
                        }
                    }
                }
            }

            //댓글 삭제
            deleteBtn.setOnClickListener {
                val commentId = adapter.getCommentId(position)
                removeComment(jwt, commentId)
                popupWindow.dismiss()
                if(adapter.itemCount == 0 ) {
                    showBasic()
                }
            }

        }

        fun String.toEditable(): Editable {
            return SpannableStringBuilder(this)
        }

        private fun removeBasic() {
            binding.apply {
                basicCommentLayout.visibility = INVISIBLE
                basicComment1Iv.visibility = INVISIBLE
                basicComment2Iv.visibility = INVISIBLE
                basicCommentIcIv.visibility = INVISIBLE
            }
        }

        private fun showBasic() {
            binding.apply {
                basicCommentLayout.visibility = VISIBLE
                basicComment1Iv.visibility = VISIBLE
                basicComment2Iv.visibility = VISIBLE
                basicCommentIcIv.visibility = VISIBLE
            }
        }

        private fun hideKeyboard(view: View) {
            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

//        private fun setupRatio(bottomSheetDialog: BottomSheetDialog) {
//
//            val bottomSheet = binding.parent
//            val layoutParams = bottomSheet.layoutParams
//            layoutParams.height = getBottomSheetDialogDefaultHeight()
//            bottomSheet.layoutParams = layoutParams
//        }
//
//        private fun getBottomSheetDialogDefaultHeight(): Int {
//            return getWindowHeight() * 80 / 100
//        }
//
//        private fun getWindowHeight(): Int {
//            // Calculate window height for fullscreen use
//            val displayMetrics = DisplayMetrics()
//            (context as? Activity)?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
//            return displayMetrics.heightPixels
//        }

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

        private fun showComment(jwt: String?, postId : Int, page : Int) {
            val openStreamService = OpenStreamService()
            openStreamService.setCommentView(this)
            openStreamService.showComment(jwt, postId, page)
        }

        override fun showCommentSuccess(resp: ShowCommentResponse) {
            if (resp.result.hasNext) {
                hasNext = true
                page += 1
            } else {
                hasNext = false
            }

            commentList = resp.result.commentList
            listSize = resp.result.listSize


            val commentArrayList = ArrayList(commentList)
            initRecyclerView(commentArrayList, listSize)
        }

        private fun writeComment(jwt: String?, postId: Int, detail : String) {
            val openStreamService = OpenStreamService()
            openStreamService.setCommentView(this)
            openStreamService.writeComment(jwt, postId, detail)
        }

        override fun writeCommentSuccess(resp: CommentResult) {
            adapter.addItem(resp)
            binding.commentEt.text = null
        }

        private fun changeComment(jwt : String?, commentId: Int, detail : String) {
            val openStreamService = OpenStreamService()
            openStreamService.setCommentView(this)
            openStreamService.changeComment(jwt, commentId, detail)
        }

        override fun changeCommentSuccess(resp: CommentResult, detail : String) {
            adapter.updateItem(resp.commentId ,detail)
            binding.commentEt.text = null
        }

        private fun removeComment(jwt: String?, commentId : Int) {
            val openStreamService = OpenStreamService()
            openStreamService.setCommentView(this)
            openStreamService.removeComment(jwt, commentId)
        }


        override fun removeCommentSuccess(commentId: Int) {
          adapter.removeItem(commentId)
            if(adapter.itemCount == 0) {
                showBasic()
            }
        }
    }