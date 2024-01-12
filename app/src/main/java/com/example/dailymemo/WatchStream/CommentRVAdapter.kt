package com.example.dailymemo.WatchStream

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dailymemo.OpenStream.OpenStreamRVAdapter
import com.example.dailymemo.OpenStream.PhotoRVAdapter
import com.example.dailymemo.databinding.ItemCommentBinding
import com.example.dailymemo.databinding.ItemMystreamLayoutBinding
import com.example.dailymemo.databinding.ItemOpenstreamLayoutBinding

class CommentRVAdapter : RecyclerView.Adapter<CommentRVAdapter.ViewHolder>() {

    interface MyItemClickListener {
        fun onMenuClick(menu: ImageView)
    }

    private lateinit var mitemClickListener : CommentRVAdapter.MyItemClickListener
    fun setMyItemClickListener(itemClickListener: CommentRVAdapter.MyItemClickListener) {
        mitemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentRVAdapter.ViewHolder {
        val binding: ItemCommentBinding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentRVAdapter.ViewHolder, position: Int) {
        holder.binding.menuBarIv.setOnClickListener { mitemClickListener.onMenuClick(holder.binding.menuBarIv) }
    }

    override fun getItemCount(): Int = 3

    inner class ViewHolder(val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {

            }
        }

}