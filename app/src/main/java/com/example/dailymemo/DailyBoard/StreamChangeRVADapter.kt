package com.example.dailymemo.DailyBoard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.dailymemo.databinding.ItemDailyBoardBinding
import com.example.dailymemo.databinding.ItemStreamBinding
import java.io.File


class StreamChangeRVADapter(activity : FragmentActivity): RecyclerView.Adapter<StreamChangeRVADapter.ViewHolder>(){

    val act = activity
    val streams = arrayOf("일상", "여행", " 맛집")

    interface MyItemClickListener {
        fun onStreamDiaryClick()
    }

    private lateinit var mitemClickListener : MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        mitemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StreamChangeRVADapter.ViewHolder {
        val binding: ItemStreamBinding = ItemStreamBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StreamChangeRVADapter.ViewHolder, position: Int) {
        holder.bind(position)
        holder.binding.streamDiaryLayout.setOnClickListener { mitemClickListener.onStreamDiaryClick() }
    }

    override fun getItemCount(): Int = 3
    inner class ViewHolder(val binding: ItemStreamBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pos : Int) {
            val preferences = act.getPreferences(Context.MODE_PRIVATE)
            val filePath = preferences.getString("user_profile_image_path", "") ?: ""
            Glide.with(binding.root.context)
                .load(File(filePath))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(binding.userProfileIv)

            binding.streamNameTv.text = streams[pos]
        }


    }
}