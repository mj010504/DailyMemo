package com.example.dailymemo.DailyBoard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dailymemo.databinding.ItemDailyBoardBinding
import com.example.dailymemo.databinding.ItemStreamBinding

class StreamChangeRVADapter: RecyclerView.Adapter<StreamChangeRVADapter.ViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StreamChangeRVADapter.ViewHolder {
        val binding: ItemStreamBinding = ItemStreamBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StreamChangeRVADapter.ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = 4
    inner class ViewHolder(val binding: ItemStreamBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pos : Int) {

        }


    }
}