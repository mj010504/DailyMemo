package com.example.dailymemo.MyStream

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dailymemo.DailyBoard.DailyBoardRVAdapter
import com.example.dailymemo.databinding.ItemDailyBoardBinding
import com.example.dailymemo.databinding.ItemMystreamLayoutBinding

class MyStreamRVAdapter : RecyclerView.Adapter<MyStreamRVAdapter.ViewHolder>() {



    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): MyStreamRVAdapter.ViewHolder {
        val binding: ItemMystreamLayoutBinding = ItemMystreamLayoutBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyStreamRVAdapter.ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = 4

    inner class ViewHolder(val binding: ItemMystreamLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pos : Int) {
            binding.dateTv.text = "2024년 1월 2일"
            binding.likeCountTv.text = "5"
            binding.mystreamRv.apply {
                adapter = MyPhotoRVAdapter(pos)
                layoutManager = LinearLayoutManager(binding.mystreamRv.context, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
            }
        }

    }
}