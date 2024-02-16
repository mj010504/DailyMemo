package com.example.dailymemo.MyStream

import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dailymemo.DailyBoard.DailyBoardRVAdapter
import com.example.dailymemo.MyStream.Retrofit.Response.post
import com.example.dailymemo.PostViewModel
import com.example.dailymemo.R
import com.example.dailymemo.WatchStream.WatchStreamFragment
import com.example.dailymemo.databinding.ItemDailyBoardBinding
import com.example.dailymemo.databinding.ItemMystreamLayoutBinding
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class MyStreamRVAdapter(activity : FragmentActivity,listSize: Int, postList : ArrayList<post>, fragmentManager: FragmentManager) : RecyclerView.Adapter<MyStreamRVAdapter.ViewHolder>() {

    var postList : ArrayList<post> = postList
    var listSize = listSize
    var fragmentManager = fragmentManager
    val act = activity

    private lateinit var viewModel: PostViewModel


    interface MyItemClickListener {
        fun onMenuClick()
    }

    private lateinit var mitemClickListener : MyItemClickListener
    fun seMyItemClickListener(itemClickListener: MyItemClickListener) {
        mitemClickListener = itemClickListener
    }

    fun removeItem(position: Int) {
        if (position >= 0 && position < postList.size) {
            postList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount)
        }
    }

    fun getPostId(position : Int) : Int = postList[position].postId

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): MyStreamRVAdapter.ViewHolder {
        val binding: ItemMystreamLayoutBinding = ItemMystreamLayoutBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyStreamRVAdapter.ViewHolder, position: Int) {
        holder.bind(position)
        holder.binding.menuBarIv.setOnClickListener { mitemClickListener.onMenuClick() }
        holder.binding.menuBarLayout.setOnClickListener { mitemClickListener.onMenuClick() }
    }

    override fun getItemCount(): Int  = postList.size
    inner class ViewHolder(val binding: ItemMystreamLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pos : Int) {

            val date = convertDateFormat(postList[pos].createdAt)
            binding.dateTv.text = date

            binding.likeCountTv.text = postList[pos].likes.toString()
            binding.commentCountTv.text = postList[pos].comments.toString()
            if(postList[pos].isLike) {
                binding.likeIv.setImageResource(R.drawable.like_on_ic)
            }

            if(postList[pos].isPublic == false) {
                binding.lockIc.visibility = VISIBLE
            }
            else {
                binding.lockIc.visibility = INVISIBLE
            }



            binding.mystreamRv.apply {
                val myPhotoRVAdapter = MyPhotoRVAdapter(postList[pos].postImg)
                adapter = myPhotoRVAdapter
                layoutManager = LinearLayoutManager(binding.mystreamRv.context, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)

                myPhotoRVAdapter.seMyItemClickListener(object: MyPhotoRVAdapter.MyItemClickListener{
                    override fun onStreamClick() {
                        moveToFragment(postList[pos])
                     findNavController().navigate(R.id.watchStreamFragment)
                    }

                })
            }
        }


    }


    private fun moveToFragment(post: post) {
        viewModel = ViewModelProvider(act).get(PostViewModel::class.java)

        // postData 갱신
        viewModel.post = post
        viewModel.isMyStream = true
    }

    fun convertDateFormat(inputDate: String): String {
        val inputFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputFormatter = SimpleDateFormat("yyyy년 M월 d일", Locale.getDefault())

            val date = inputFormatter.parse(inputDate)
            return outputFormatter.format(date!!)
    }

}