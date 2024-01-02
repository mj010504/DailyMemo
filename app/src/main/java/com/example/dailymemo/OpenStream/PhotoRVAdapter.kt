package com.example.dailymemo.OpenStream

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dailymemo.databinding.ItemMystreamPhotoLayoutBinding
import com.example.dailymemo.databinding.ItemOpenstreamLayoutBinding
import com.example.dailymemo.databinding.ItemOpenstreamPhotoLayoutBinding

class PhotoRVAdapter: RecyclerView.Adapter<PhotoRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoRVAdapter.ViewHolder {
        val binding: ItemOpenstreamPhotoLayoutBinding = ItemOpenstreamPhotoLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoRVAdapter.ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    inner class ViewHolder(val binding: ItemOpenstreamPhotoLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {

        }

    }
}