package com.example.dailymemo.OpenStream

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dailymemo.MyStream.MyPhotoRVAdapter
import com.example.dailymemo.MyStream.MyStreamRVAdapter
import com.example.dailymemo.R
import com.example.dailymemo.databinding.ItemMystreamLayoutBinding
import com.example.dailymemo.databinding.ItemMystreamPhotoLayoutBinding
import com.example.dailymemo.databinding.ItemOpenstreamLayoutBinding

class OpenStreamRVAdapter : RecyclerView.Adapter<OpenStreamRVAdapter.ViewHolder>() {

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

    override fun getItemCount(): Int = 4


    inner class ViewHolder(val binding: ItemOpenstreamLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            binding.openstreamPhotoRv.apply {
                val photoRVAdapter = PhotoRVAdapter()
                adapter = photoRVAdapter
                layoutManager = LinearLayoutManager(binding.openstreamPhotoRv.context, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)


                photoRVAdapter.setMyItemClickListener(object: PhotoRVAdapter.MyItemClickListener{
                    override fun onStreamClick() {
                        findNavController().navigate(R.id.watchStreamFragment)
                    }

                })
            }
        }

    }

}