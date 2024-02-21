package com.example.dailymemo.MyStream.Dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import com.example.dailymemo.Auth.Retrofit.LoginService
import com.example.dailymemo.MyStream.Retrofit.MyStreamService
import com.example.dailymemo.R
import com.example.dailymemo.Setting.AccountWithdrawalView
import com.example.dailymemo.Setting.Dialog.BuySuccessDialog
import com.example.dailymemo.databinding.DialogAccountWithdrawalCheckBinding
import com.example.dailymemo.databinding.DialogMystreamDeleteBinding
import com.example.dailymemo.databinding.DialogMystreamDeleteCheckBinding
import com.example.dailymemo.databinding.DialogSampleBinding
import kotlin.math.log

class AccountWithdrawalCheckDialog(context: Context, navController: NavController, activity: FragmentActivity) : Dialog(context), AccountWithdrawalView {
    lateinit var binding: DialogAccountWithdrawalCheckBinding
    private var navController = navController
    val act = activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogAccountWithdrawalCheckBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        resize(this, 0.85f, 0.2f)

        window?.setBackgroundDrawableResource(R.drawable.white_dialog_layout)

        // 다이얼로그 바깥쪽 클릭시 종료되도록 함 (Cancel the dialog when you touch outside)
        setCanceledOnTouchOutside(true)
        // 취소 가능 유무
        setCancelable(true)

        binding.apply {
            checkBtn.setOnClickListener {
                dismiss()

                var jwt = getMyJwt()
                accountWithdrawal(jwt)
            }

            cancleBtn.setOnClickListener {
                dismiss()
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

    private fun getMyJwt() : String? {
        val spf = act.getSharedPreferences("auth", Context.MODE_PRIVATE)
        return spf.getString("jwt", null)
    }


    private fun showAccountWithdrawalSuccessDialog() {
        val dialog = AccountWithdrawalDialog(context, navController)
        dialog.show()
    }

    private fun accountWithdrawal(jwt : String?) {
        val loginService = LoginService()
        loginService.setAccountWithdrawalView(this)
        loginService.accountWithdrawal(jwt)
    }

    override fun success() {
        showAccountWithdrawalSuccessDialog()
    }
}