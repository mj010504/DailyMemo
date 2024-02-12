package com.example.dailymemo.Setting.StreamSetting

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.dailymemo.R
import com.example.dailymemo.databinding.ItemStreamSettingBinding
import com.example.dailymemo.databinding.ItemWatchStreamLayoutBinding
import java.io.File

class SettingStreamSettingRVAdapter(activity: FragmentActivity) : RecyclerView.Adapter<SettingStreamSettingRVAdapter.ViewHolder>() {

    var act = activity
    val userProfile = getProfieImage()
    val streams = arrayOf("일상", "여행", "맛집")

    interface MyItemClickListener {
        fun onSettingClick(streamName: String)
    }

    private lateinit var mitemClickListener : MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        mitemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SettingStreamSettingRVAdapter.ViewHolder {
       val binding : ItemStreamSettingBinding = ItemStreamSettingBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SettingStreamSettingRVAdapter.ViewHolder, position: Int) {
       holder.bind(position)
        holder.binding.settingIcIv.setOnClickListener { mitemClickListener.onSettingClick(holder.binding.streamNameTv.text.toString()) }
    }

    override fun getItemCount(): Int = 3
    inner class ViewHolder(val binding: ItemStreamSettingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            binding.streamNameTv.text = streams[pos]

            if(userProfile != null) {
                binding.userProfileIv.setImageDrawable(userProfile)
            }
            else {
                binding.userProfileIv.setImageResource(R.drawable.basic_user_profile)
            }
        }
        }

    private fun getProfieImage() : Drawable? {

        val preferences = act.getPreferences(Context.MODE_PRIVATE)
        val filePath = preferences.getString("user_profile_image_path", "") ?: ""

        if (filePath.isNotEmpty()) {
            // 파일 경로가 비어 있지 않은 경우에만 실행
            val file = File(filePath)
            if (file.exists()) {
                // 파일이 존재하는 경우에만 실행
                val drawable = Drawable.createFromPath(filePath)
                return drawable
            } else {
                return null
            }
        } else {
            return null
        }
    }

}
