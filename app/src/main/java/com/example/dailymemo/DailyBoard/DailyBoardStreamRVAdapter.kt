package com.example.dailymemo.DailyBoard

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.dailymemo.databinding.ItemDailyBoardStreamBinding
import com.example.dailymemo.databinding.ItemStreamBinding
import java.io.File

class DailyBoardStreamRVAdapter(activity : FragmentActivity,  currentStream: String) : RecyclerView.Adapter<DailyBoardStreamRVAdapter.ViewHolder>() {

    val act = activity
    val streams = arrayOf("일상", "맛집", "여행")
    val currentStream = currentStream
    private var isDuplicated = false



    interface MyItemClickListener {
        fun onStreamTypeClick()
    }

    private lateinit var mitemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        mitemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DailyBoardStreamRVAdapter.ViewHolder {
        val binding: ItemDailyBoardStreamBinding =
            ItemDailyBoardStreamBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = 2

    override fun onBindViewHolder(holder: DailyBoardStreamRVAdapter.ViewHolder, position: Int) {
        holder.bind(position)
        holder.binding.streamTypeLayout.setOnClickListener { mitemClickListener.onStreamTypeClick() }
    }

    inner class ViewHolder(val binding: ItemDailyBoardStreamBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(pos: Int) {
            val preferences = act.getPreferences(Context.MODE_PRIVATE)
            val filePath = preferences.getString("user_profile_image_path", "") ?: ""

            Glide.with(binding.root.context)
                .load(File(filePath))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(binding.userProfileIv)

            if(isDuplicated == false) {
                if (currentStream == streams[pos]) {
                    isDuplicated = true
                    binding.streamNameTv.text = streams[pos + 1]
                } else {
                    binding.streamNameTv.text = streams[pos]
                }
            }
            else {
                binding.streamNameTv.text = streams[pos + 1]
            }
        }


    }

}