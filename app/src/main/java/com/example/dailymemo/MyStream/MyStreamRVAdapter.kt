package com.example.dailymemo.MyStream

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dailymemo.DailyBoard.DailyBoardRVAdapter
import com.example.dailymemo.R
import com.example.dailymemo.databinding.ItemDailyBoardBinding
import com.example.dailymemo.databinding.ItemMystreamLayoutBinding

class MyStreamRVAdapter : RecyclerView.Adapter<MyStreamRVAdapter.ViewHolder>() {

    interface MyItemClickListener {
        fun onMenuClick()
    }

    private lateinit var mitemClickListener : MyItemClickListener
    fun seMyItemClickListener(itemClickListener: MyItemClickListener) {
        mitemClickListener = itemClickListener
    }

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

    override fun getItemCount(): Int = 4

    inner class ViewHolder(val binding: ItemMystreamLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pos : Int) {
            binding.dateTv.text = "2024년 1월 2일"
            binding.likeCountTv.text = "5"
            binding.mystreamRv.apply {
                var myPhotoRVAdapter = MyPhotoRVAdapter(pos)
                adapter = myPhotoRVAdapter
                layoutManager = LinearLayoutManager(binding.mystreamRv.context, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)

                myPhotoRVAdapter.seMyItemClickListener(object: MyPhotoRVAdapter.MyItemClickListener{
                    override fun onStreamClick() {
                        findNavController().navigate(R.id.watchStreamFragment)
                    }

                })
            }
        }

    }
}