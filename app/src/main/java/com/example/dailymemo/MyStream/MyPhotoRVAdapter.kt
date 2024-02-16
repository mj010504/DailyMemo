package com.example.dailymemo.MyStream

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dailymemo.R
import com.example.dailymemo.databinding.ItemMystreamLayoutBinding
import com.example.dailymemo.databinding.ItemMystreamPhotoLayoutBinding
import java.nio.charset.StandardCharsets

class MyPhotoRVAdapter(postImg : List<String>) : RecyclerView.Adapter<MyPhotoRVAdapter.ViewHolder>() {

    val images = postImg

    interface MyItemClickListener {
        fun onStreamClick()
    }


    private lateinit var mitemClickListener : MyItemClickListener
    fun seMyItemClickListener(itemClickListener: MyItemClickListener) {
        mitemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPhotoRVAdapter.ViewHolder {
        val binding: ItemMystreamPhotoLayoutBinding = ItemMystreamPhotoLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyPhotoRVAdapter.ViewHolder, position: Int) {
        holder.bind(position)
        holder.binding.photoCv.setOnClickListener { mitemClickListener.onStreamClick() }
    }

    override fun getItemCount(): Int = images.size

    inner class ViewHolder(val binding: ItemMystreamPhotoLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            val bitmap = stringToBitmap(images[pos])
            binding.photoIv.setImageBitmap(bitmap)
        }

    }
}

    private fun stringToBitmap(base64: String) : Bitmap {
        val encodeByte = Base64.decode(base64, Base64.NO_WRAP)

        return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
    }