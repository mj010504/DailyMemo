    package com.example.dailymemo.WatchStream.Comment

    import android.content.Context
    import android.os.Bundle
    import android.view.Gravity
    import android.view.LayoutInflater
    import android.view.View
    import android.view.View.INVISIBLE
    import android.view.View.VISIBLE
    import android.view.ViewGroup
    import android.view.inputmethod.InputMethodManager
    import android.widget.PopupWindow
    import androidx.constraintlayout.widget.ConstraintLayout
    import androidx.recyclerview.widget.LinearLayoutManager
    import com.bumptech.glide.Glide
    import com.bumptech.glide.load.engine.DiskCacheStrategy
    import com.example.dailymemo.R
    import com.example.dailymemo.databinding.FragmentCommentBinding
    import com.google.android.material.bottomsheet.BottomSheetBehavior
    import com.google.android.material.bottomsheet.BottomSheetDialog
    import com.google.android.material.bottomsheet.BottomSheetDialogFragment
    import java.io.File

    class CommentFragment : BottomSheetDialogFragment(){

        lateinit var binding : FragmentCommentBinding

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            binding = FragmentCommentBinding.inflate(inflater, container, false)
            // 키보드 자동으로 올라오는 것 방지

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

            initRecyclerView()

//            val editText = binding.commentEt
//
//            // 키보드가 나타날 때의 리스너 등록
//            editText.viewTreeObserver.addOnGlobalLayoutListener {
//                val r = Rect()
//                editText.getWindowVisibleDisplayFrame(r)
//                val screenHeight = editText.height
//                val keypadHeight = screenHeight - r.bottom
//
//                if (keypadHeight > screenHeight * 0.15) {
//                    // 키보드가 열려있는 상태에서의 동작 (올리기)
//                    val location = IntArray(2)
//                    editText.getLocationOnScreen(location)
//                    val editTextBottom = location[1] + editText.height
//                    val margin = editTextBottom - r.bottom
//                    editText.scrollBy(0, margin)
//                } else {
//                    // 키보드가 닫혀있는 상태에서의 동작 (내리기)
//                    editText.scrollBy(0, 0)
//                }
//            }
//
//            // 키보드 자동으로 올라오는 것 방지
//            activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        }

//        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//            val dialog = super.onCreateDialog(savedInstanceState)
//
//            dialog.setOnShowListener { dialogInterface ->
//                val bottomSheetDialog = dialogInterface as BottomSheetDialog
//                setupRatio(bottomSheetDialog)
//            }
//
//            return dialog
//        }

        private fun initRecyclerView() {
            val commentRVAdapter = CommentRVAdapter(requireActivity())
            binding.commentRv.adapter = commentRVAdapter
            binding.commentRv.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            binding.sendBtnIv.setOnClickListener {
                if(binding.commentEt.text.toString().length > 0) {
                    commentRVAdapter.addItem(binding.commentEt.text.toString())
                    binding.commentEt.text = null
                    hideKeyboard(it)
                    removeBasic()
                }
            }

            commentRVAdapter.setMyItemClickListener(object : CommentRVAdapter.MyItemClickListener {
                override fun onMenuClick(menu: ConstraintLayout, position: Int) {
                    showPopupMenu(commentRVAdapter,menu, position)
                }
            })


        }



        private fun showPopupMenu(adapter : CommentRVAdapter, anchorView: View, position : Int) {
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
            }

            //댓글 삭제
            deleteBtn.setOnClickListener {
               adapter.removeItem(position)
                popupWindow.dismiss()
                if(adapter.itemCount == 0 ) {
                    showBasic()
                }
            }

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
            val imm =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
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


    }