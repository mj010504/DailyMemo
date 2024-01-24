package com.example.dailymemo.MyStream

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dailymemo.R
import com.example.dailymemo.databinding.ItemMystreamLayoutBinding
import com.example.dailymemo.databinding.ItemMystreamPhotoLayoutBinding

class MyPhotoRVAdapter(private val parentPos: Int) : RecyclerView.Adapter<MyPhotoRVAdapter.ViewHolder>() {

    val images = listOf(R.drawable.daily1,R.drawable.daily2, R.drawable.daily3, R.drawable.daily1)

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

    override fun getItemCount(): Int = 4

    inner class ViewHolder(val binding: ItemMystreamPhotoLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            binding.photoIv.setImageResource(images[parentPos])
        }

    }
}