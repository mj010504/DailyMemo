package com.example.dailymemo.OpenStream

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.dailymemo.MyStream.MyStreamRVAdapter
import com.example.dailymemo.databinding.ItemMystreamPhotoLayoutBinding
import com.example.dailymemo.databinding.ItemOpenstreamLayoutBinding
import com.example.dailymemo.databinding.ItemOpenstreamPhotoLayoutBinding


class PhotoRVAdapter(postImg : List<String>): RecyclerView.Adapter<PhotoRVAdapter.ViewHolder>() {

    val images = postImg

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
        holder.binding.photoCv.setOnClickListener {mitemClickListener.onStreamClick()}
    }

    override fun getItemCount(): Int = images.size
    inner class ViewHolder(val binding: ItemOpenstreamPhotoLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
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