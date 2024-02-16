package com.example.dailymemo.WatchStream.Comment

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.dailymemo.OpenStream.Retrofit.Response.CommentResult

import com.example.dailymemo.R
import com.example.dailymemo.databinding.ItemCommentBinding

import java.io.File


class CommentRVAdapter(activity: FragmentActivity,
                       private var commentList: ArrayList<CommentResult>, var listSize: Int
) : RecyclerView.Adapter<CommentRVAdapter.ViewHolder>() {

    val act = activity
    val userProfile = getProfieImage()


    interface MyItemClickListener {
        fun onMenuClick(menu: ConstraintLayout, position: Int, commentText : String)
    }

    private lateinit var mitemClickListener : MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        mitemClickListener = itemClickListener
    }





    fun addItem(item :CommentResult) {
        commentList.add(item)
        notifyItemInserted(commentList.size - 1)

    }

    fun removeItem(commentId: Int) {
        for ((index, comment) in commentList.withIndex()) {
            if(comment.commentId == commentId) {
                commentList.removeAt(index)
                notifyItemRemoved(index)
                notifyItemRangeChanged(index, itemCount)
                break
            }
        }


    }

    fun updateItem(commentId: Int, newComment: String) {

        for ((index, comment) in commentList.withIndex()) {
                    if (comment.commentId == commentId) {
                        comment.detail = newComment
                        notifyItemChanged(index)
                        break  // 원하는 comment를 찾았으니 반복문 종료
                    }
                }

    }

    fun getCommentId(pos : Int) : Int {
        return commentList[pos].commentId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemCommentBinding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.menuBarIv.setOnClickListener { mitemClickListener.onMenuClick(holder.binding.rootView, position, holder.binding.commentContentTv.text.toString() ) }
        holder.binding.menuBarLayout.setOnClickListener {  mitemClickListener.onMenuClick(holder.binding.rootView, position, holder.binding.commentContentTv.text.toString()) }
        holder.bind(commentList[position])
    }

    override fun getItemCount(): Int = commentList.size

    inner class ViewHolder(val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(comment: CommentResult) {
                binding.commentContentTv.text = comment.detail
                binding.userNicknameTv.text = comment.nickName

                if(userProfile != null && comment.isAuthor) {
                    binding.userProfileIv.setImageDrawable(userProfile)
                }
                else {
                    binding.userProfileIv.setImageResource(R.drawable.basic_user_profile)
                    binding.menuBarIv.visibility = INVISIBLE
                    binding.menuBarLayout.visibility = INVISIBLE
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