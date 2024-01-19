    package com.example.dailymemo.DailyBoard

    import android.content.Context
    import android.net.Uri
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import androidx.recyclerview.widget.LinearLayoutManager
    import androidx.recyclerview.widget.RecyclerView
    import com.bumptech.glide.Glide
    import com.example.dailymemo.R
    import com.example.dailymemo.databinding.ItemDailyBoardBinding


    class DailyBoardRVAdapter(private val context: Context,private val photoList: ArrayList<Uri>) : RecyclerView.Adapter<DailyBoardRVAdapter.ViewHolder>() {

        interface ItemClickListener{
            fun onPhotoClick()
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
            holder.binding.dailyBoardIv.setOnClickListener { itemClickListener.onPhotoClick() }
        }

        override fun getItemCount(): Int = photoList.size


        inner class ViewHolder(val binding: ItemDailyBoardBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(imageUri : Uri) {
                Glide.with(context)
                    .load(imageUri)
                    .into(binding.dailyBoardIv)
            }

            fun removeItem() {
                binding.deleteView.visibility = View.VISIBLE
                binding.deleteExpectedTv.visibility = View.VISIBLE
            }


        }


    }