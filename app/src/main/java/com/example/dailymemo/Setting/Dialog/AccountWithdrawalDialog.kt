package com.example.dailymemo.MyStream.Dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.dailymemo.R
import com.example.dailymemo.databinding.DialogAccountWithdrawalBinding
import com.example.dailymemo.databinding.DialogMystreamDeleteBinding
import com.example.dailymemo.databinding.DialogSampleBinding

class AccountWithdrawalDialog(context: Context, navController: NavController) : Dialog(context) {
    lateinit var binding: DialogAccountWithdrawalBinding
    private  var navController = navController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogAccountWithdrawalBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        resize(this, 0.7f, 0.17f)

        window?.setBackgroundDrawableResource(R.drawable.white_dialog_layout)

        // 다이얼로그 바깥쪽 클릭시 종료되도록 함 (Cancel the dialog when you touch outside)
        setCanceledOnTouchOutside(true)
        // 취소 가능 유무
        setCancelable(true)

        binding.apply {
            checkBtn.setOnClickListener {
                dismiss()
                navController.navigate(R.id.loginFragment)
            }

        }
    }

    // 사이즈를 조절하고 싶을 때 사용 (use it when you want to resize dialog)
    private fun resize(dialog: Dialog, width: Float, height: Float) {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        if (Build.VERSION.SDK_INT < 30) {
            val size = Point()
            windowManager.defaultDisplay.getSize(size)

            val x = (size.x * width).toInt()
            val y = (size.y * height).toInt()
            dialog.window?.setLayout(x, y)
        } else {
            val rect = windowManager.currentWindowMetrics.bounds

            val x = (rect.width() * width).toInt()
            val y = (rect.height() * height).toInt()
            dialog.window?.setLayout(x, y)
        }
    }
}