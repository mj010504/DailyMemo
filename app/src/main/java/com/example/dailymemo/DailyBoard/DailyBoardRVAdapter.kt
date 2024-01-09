package com.example.dailymemo.DailyBoard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dailymemo.databinding.ItemDailyBoardBinding

class DailyBoardRVAdapter(private val photoList: ArrayList<Int>) : RecyclerView.Adapter<DailyBoardRVAdapter.ViewHolder>() {

    interface ItemClickListener{
        fun removePhoto()
    }

    private lateinit var itemClickListener: ItemClickListener
    fun setITemClickListener(mitemClickListener: ItemClickListener) {
        itemClickListener = mitemClickListener
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): DailyBoardRVAdapter.ViewHolder {
        val binding: ItemDailyBoardBinding = ItemDailyBoardBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DailyBoardRVAdapter.ViewHolder, position: Int) {
        holder.bind(photoList[position])
    }

    override fun getItemCount(): Int = photoList.size

    inner class ViewHolder(val binding: ItemDailyBoardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(photo : Int) {
            binding.dailyBoardIv.setImageResource(photo!!)
        }

        fun removeItem() {
            binding.deleteView.visibility = View.VISIBLE
            binding.deleteExpectedTv.visibility = View.VISIBLE
        }
    }


}