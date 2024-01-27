package com.example.dailymemo.Setting.StreamSetting

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.dailymemo.DailyBoard.DailyBoardRVAdapter
import com.example.dailymemo.databinding.ItemDailyBoardBinding
import com.example.dailymemo.databinding.ItemStreamTypeBinding
import java.io.File

class StreamSettingRVAdapter : RecyclerView.Adapter<StreamSettingRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StreamSettingRVAdapter.ViewHolder {
        val binding: ItemStreamTypeBinding = ItemStreamTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StreamSettingRVAdapter.ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = 4

    inner class ViewHolder(val binding: ItemStreamTypeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pos : Int) {

        }


    }


}