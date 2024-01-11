package com.example.dailymemo.OpenStream

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.dailymemo.MyStream.MyStreamRVAdapter
import com.example.dailymemo.databinding.ItemMystreamPhotoLayoutBinding
import com.example.dailymemo.databinding.ItemOpenstreamLayoutBinding
import com.example.dailymemo.databinding.ItemOpenstreamPhotoLayoutBinding


class PhotoRVAdapter: RecyclerView.Adapter<PhotoRVAdapter.ViewHolder>() {


    interface MyItemClickListener {
        fun onStreamClick()
    }

    private lateinit var mitemClickListener : MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        mitemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemOpenstreamPhotoLayoutBinding = ItemOpenstreamPhotoLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
        holder.binding.photoIv.setOnClickListener {mitemClickListener.onStreamClick()}
    }

    override fun getItemCount(): Int = 4
    inner class ViewHolder(val binding: ItemOpenstreamPhotoLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {

        }

    }
}