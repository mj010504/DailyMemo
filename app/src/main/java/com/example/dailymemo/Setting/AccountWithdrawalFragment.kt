package com.example.dailymemo.Setting

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.dailymemo.MyStream.Dialog.AccountWithdrawalCheckDialog
import com.example.dailymemo.R
import com.example.dailymemo.Setting.Dialog.DeleteFailureDialog
import com.example.dailymemo.databinding.FragmentAccountWithdrawalBinding


class AccountWithdrawalFragment : Fragment() {

    lateinit var binding : FragmentAccountWithdrawalBinding
    var isButton = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAccountWithdrawalBinding.inflate(inflater,container,false)
        showText()

        val editText = binding.withdrawalEt
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
                val editTextBottom = location[1] + editText.height + 60
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
            withdrawalEt.addTextChangedListener(object: TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val inputText = s.toString()
                    if(inputText.isNotEmpty()) {
                        isButton = true
                        withdrawalBtn.background = ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.withdrawal_btn_layout
                        )
                        withdrawalBtn.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.white
                            )
                        )
                    }
                    else {
                        isButton = false
                        withdrawalBtn.background = ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.setting_layout
                        )


                    withdrawalBtn.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.half_black
                        )
                    )
                }
                }

                override fun afterTextChanged(s: Editable?) {

                }

            })

            withdrawalBtn.setOnClickListener {
                if(isButton) {
                    if(withdrawalEt.text.toString() == "탈퇴") {
                        showAccountWithdrawalCheckDialog()
                    }
                    else {
                        showDeleteFailureDialog()
                    }
                }
            }
        }

        return binding.root
    }

    private fun showText() {
        val span  = SpannableString(binding.accountWithdrawalTv.text)
        span.setSpan(R.color.delete, 33, 35, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        span.setSpan(R.color.impossible,47, 50,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        span.setSpan(StyleSpan(Typeface.BOLD), 92, 97, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.accountWithdrawalTv.text  = span
    }

   private fun showDeleteFailureDialog() {
       val dialog = DeleteFailureDialog(requireContext())
       dialog.show()
   }

    private fun showAccountWithdrawalCheckDialog() {
        val dialog = AccountWithdrawalCheckDialog(requireContext(), findNavController())
        dialog.show()
    }
}