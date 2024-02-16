package com.example.dailymemo.OpenStream

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dailymemo.MyStream.MyPhotoRVAdapter
import com.example.dailymemo.MyStream.MyStreamRVAdapter
import com.example.dailymemo.MyStream.Retrofit.Response.post
import com.example.dailymemo.PostViewModel
import com.example.dailymemo.R
import com.example.dailymemo.databinding.ItemMystreamLayoutBinding
import com.example.dailymemo.databinding.ItemMystreamPhotoLayoutBinding
import com.example.dailymemo.databinding.ItemOpenstreamLayoutBinding
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class OpenStreamRVAdapter(activity: FragmentActivity, listSize: Int, postList : ArrayList<post>, fragmentManager: FragmentManager) : RecyclerView.Adapter<OpenStreamRVAdapter.ViewHolder>() {

    var postList : ArrayList<post> = postList
    var listSize = listSize
    var fragmentManager = fragmentManager
    val act = activity

    private lateinit var viewModel: PostViewModel

    interface MyItemClickListener {
        fun onStreamClick()
    }

    private lateinit var mitemClickListener : OpenStreamRVAdapter.MyItemClickListener
    fun setMyItemClickListener(itemClickListener: OpenStreamRVAdapter.MyItemClickListener) {
        mitemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OpenStreamRVAdapter.ViewHolder {
        val binding: ItemOpenstreamLayoutBinding = ItemOpenstreamLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OpenStreamRVAdapter.ViewHolder, position: Int) {
        holder.bind(position)
        holder.binding.openstreamPhotoRv.setOnClickListener {
            mitemClickListener.onStreamClick()
        }
    }

    override fun getItemCount(): Int = listSize


    inner class ViewHolder(val binding: ItemOpenstreamLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            val date = convertDateFormat(postList[pos].createdAt)
            binding.dateTv.text = date

            binding.userNicknameTv.text = postList[pos].nickname

            binding.likeCountTv.text = postList[pos].likes.toString()
            binding.commentCountTv.text = postList[pos].comments.toString()
            if(postList[pos].isLike) {
                binding.likeIv.setImageResource(R.drawable.like_on_ic)
            }

            binding.openstreamPhotoRv.apply {
                val photoRVAdapter = PhotoRVAdapter(postList[pos].postImg)
                adapter = photoRVAdapter
                layoutManager = LinearLayoutManager(binding.openstreamPhotoRv.context, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)


                photoRVAdapter.setMyItemClickListener(object: PhotoRVAdapter.MyItemClickListener{
                    override fun onStreamClick() {
                        findNavController().navigate(R.id.watchStreamFragment)
                        moveToFragment(postList[pos])
                    }

                })
            }
        }

    }

    private fun moveToFragment(post: post) {
        viewModel = ViewModelProvider(act).get(PostViewModel::class.java)

        // postData 갱신
        viewModel.post = post
        viewModel.isMyStream = false
    }


    fun convertDateFormat(inputDate: String): String {
        val inputFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputFormatter = SimpleDateFormat("yyyy년 M월 d일", Locale.getDefault())

        val date = inputFormatter.parse(inputDate)
        return outputFormatter.format(date!!)
    }
}

