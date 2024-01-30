package com.example.dailymemo.WatchStream.Comment

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.dailymemo.R
import com.example.dailymemo.databinding.ItemCommentBinding

import java.io.File


class CommentRVAdapter(activity: FragmentActivity) : RecyclerView.Adapter<CommentRVAdapter.ViewHolder>() {

    val act = activity
    val userProfile = getProfieImage()

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
        holder.binding.menuBarLayout.setOnClickListener {  mitemClickListener.onMenuClick(holder.binding.rootView, position) }
        holder.bind(comments[position])
    }

    override fun getItemCount(): Int = comments.size

    inner class ViewHolder(val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: String) {
                binding.commentContentTv.text = comment
                if(userProfile != null) {
                    binding.userProfileIv.setImageDrawable(userProfile)
                }
            else {
                    binding.userProfileIv.setImageResource(R.drawable.basic_user_profile)
                }
        }

    }

    private fun getProfieImage() : Drawable? {
        val preferences = act.getPreferences(Context.MODE_PRIVATE)
        val filePath = preferences.getString("user_profile_image_path", "") ?: ""

        if (filePath.isNotEmpty()) {
            // 파일 경로가 비어 있지 않은 경우에만 실행
            val file = File(filePath)
            if (file.exists()) {
                // 파일이 존재하는 경우에만 실행
                val drawable = Drawable.createFromPath(filePath)
                return drawable
            } else {
                return null
            }
        } else {
           return null
        }
    }


}