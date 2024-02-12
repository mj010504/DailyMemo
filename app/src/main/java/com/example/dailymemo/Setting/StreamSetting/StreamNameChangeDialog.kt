package com.example.dailymemo.Setting.Dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.dailymemo.R
import com.example.dailymemo.databinding.DialogAutoIndexBinding
import com.example.dailymemo.databinding.DialogBuyStreamBinding
import com.example.dailymemo.databinding.DialogStreamNameChangeBinding

class StreamNameChangeDialog(context: Context) : Dialog(context) {
    lateinit var binding: DialogStreamNameChangeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogStreamNameChangeBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        resize(this, 0.85f, 0.5f )

        window?.setBackgroundDrawableResource(R.drawable.white_dialog_layout)

        // 다이얼로그 바깥쪽 클릭시 종료되도록 함 (Cancel the dialog when you touch outside)
        setCanceledOnTouchOutside(true)
        // 취소 가능 유무
        setCancelable(true)

        binding.apply {
            cancleBtn.setOnClickListener {
                dismiss()
            }

            changeBtn.setOnClickListener {
                if(streamNameChangeEt.text.isEmpty()) {
                    val dailog = SampleDialog(context, "변경할 스트림 이름을 입력하세요")
                    dailog.show()
                }
                else {
                    dismiss()
                }

            }

            streamNameChangeEt.addTextChangedListener(object: TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {

                }

            })
        }

    }

    // 사이즈를 조절하고 싶을 때 사용 (use it when you want to resize dialog)
    private fun resize(dialog: Dialog, width: Float, height: Float){
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
