package com.example.dailymemo.DailyBoard

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dailymemo.databinding.ItemDailyBoardBinding

class DailyBoardRVAdapter(private val photoList: ArrayList<Int>) : RecyclerView.Adapter<DailyBoardRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): DailyBoardRVAdapter.ViewHolder {
        val binding: ItemDailyBoardBinding = ItemDailyBoardBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DailyBoardRVAdapter.ViewHolder, position: Int) {
        holder.bind(photoList[position])
    }

    override fun getItemCount(): Int = photoList.size
    inner class ViewHolder(val binding: ItemDailyBoardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(photo : Int) {
            binding.dailyBoardIv.setImageResource(photo!!)
        }
    }

}