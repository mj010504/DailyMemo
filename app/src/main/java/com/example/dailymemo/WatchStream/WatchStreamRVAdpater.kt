package com.example.dailymemo.WatchStream

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dailymemo.MyStream.Retrofit.Response.post
import com.example.dailymemo.OpenStream.PhotoRVAdapter
import com.example.dailymemo.databinding.ItemMystreamLayoutBinding
import com.example.dailymemo.databinding.ItemOpenstreamLayoutBinding
import com.example.dailymemo.databinding.ItemWatchStreamLayoutBinding

class WatchStreamRVAdpater(post : post) : RecyclerView.Adapter<WatchStreamRVAdpater.ViewHolder>() {

    val post = post
    val images = post.postImg
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WatchStreamRVAdpater.ViewHolder {
        val binding: ItemWatchStreamLayoutBinding =
            ItemWatchStreamLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WatchStreamRVAdpater.ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = images.size

    inner class ViewHolder(val binding: ItemWatchStreamLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            val bitmap = stringToBitmap(images[pos])
            binding.dailyBoardIv.setImageBitmap(bitmap)
        }

    }
}

    private fun stringToBitmap(base64: String): Bitmap {
        val encodeByte = Base64.decode(base64, Base64.NO_WRAP)

        return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
    }

