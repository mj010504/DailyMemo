package com.example.dailymemo.WatchStream.Comment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.dailymemo.databinding.ItemCommentBinding

class CommentRVAdapter : RecyclerView.Adapter<CommentRVAdapter.ViewHolder>() {

    interface MyItemClickListener {
        fun onMenuClick(menu: ConstraintLayout, position: Int)
    }

    private lateinit var mitemClickListener : MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        mitemClickListener = itemClickListener
    }

    private var comments = mutableListOf<String>()

    fun addItem(item : String) {
        comments.add(item)
        notifyItemInserted(comments.size - 1)
    }

    fun removeItem(position: Int) {
        if (position >= 0 && position < comments.size) {
            comments.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemCommentBinding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.menuBarIv.setOnClickListener { mitemClickListener.onMenuClick(holder.binding.rootView, position) }
        holder.bind(comments[position])
    }

    override fun getItemCount(): Int = comments.size

    inner class ViewHolder(val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: String) {
                binding.commentContentTv.text = comment
            }
        }


}