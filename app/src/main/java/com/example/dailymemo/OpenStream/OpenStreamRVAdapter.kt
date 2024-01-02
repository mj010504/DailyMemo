package com.example.dailymemo.OpenStream

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dailymemo.MyStream.MyPhotoRVAdapter
import com.example.dailymemo.databinding.ItemMystreamLayoutBinding
import com.example.dailymemo.databinding.ItemMystreamPhotoLayoutBinding
import com.example.dailymemo.databinding.ItemOpenstreamLayoutBinding

class OpenStreamRVAdapter : RecyclerView.Adapter<OpenStreamRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OpenStreamRVAdapter.ViewHolder {
        val binding: ItemOpenstreamLayoutBinding = ItemOpenstreamLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OpenStreamRVAdapter.ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    inner class ViewHolder(val binding: ItemOpenstreamLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            binding.openstreamPhotoRv.apply {
                adapter = PhotoRVAdapter()
                layoutManager = LinearLayoutManager(binding.openstreamPhotoRv.context, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
            }
        }

    }

}