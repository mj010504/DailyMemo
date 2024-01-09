package com.example.dailymemo.WatchStream

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dailymemo.OpenStream.PhotoRVAdapter
import com.example.dailymemo.databinding.ItemMystreamLayoutBinding
import com.example.dailymemo.databinding.ItemOpenstreamLayoutBinding
import com.example.dailymemo.databinding.ItemWatchStreamLayoutBinding

class WatchStreamRVAdpater : RecyclerView.Adapter<WatchStreamRVAdpater.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WatchStreamRVAdpater.ViewHolder {
        val binding: ItemWatchStreamLayoutBinding = ItemWatchStreamLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WatchStreamRVAdpater.ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = 3

    inner class ViewHolder(val binding: ItemWatchStreamLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {

            }
        }

}
