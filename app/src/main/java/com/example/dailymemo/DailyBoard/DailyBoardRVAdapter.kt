    package com.example.dailymemo.DailyBoard

    import android.content.Context
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import androidx.recyclerview.widget.LinearLayoutManager
    import androidx.recyclerview.widget.RecyclerView
    import com.example.dailymemo.R
    import com.example.dailymemo.databinding.ItemDailyBoardBinding

    class DailyBoardRVAdapter(private val photoList: ArrayList<Int>) : RecyclerView.Adapter<DailyBoardRVAdapter.ViewHolder>() {

        interface ItemClickListener{

        }

        private lateinit var itemClickListener: ItemClickListener
        fun setITemClickListener(mitemClickListener: ItemClickListener) {
            itemClickListener = mitemClickListener
        }

        private var isPhoto = false


        override fun onCreateViewHolder(
            viewGroup: ViewGroup,
            viewType: Int
        ): DailyBoardRVAdapter.ViewHolder {
            val binding: ItemDailyBoardBinding = ItemDailyBoardBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: DailyBoardRVAdapter.ViewHolder, position: Int) {
            holder.bind(photoList[position])
            holder.onPhotoClick()

        }

        override fun getItemCount(): Int = photoList.size

        fun countUpdate() {

        }


        inner class ViewHolder(val binding: ItemDailyBoardBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(photo : Int) {
                binding.dailyBoardIv.setImageResource(photo!!)
            }

            fun removeItem() {
                binding.deleteView.visibility = View.VISIBLE
                binding.deleteExpectedTv.visibility = View.VISIBLE
            }

            fun onPhotoClick() {

                binding.dailyBoardIv.setOnClickListener {
                    if(isPhoto == true) {
                    isPhoto = false
                    binding.deleteView.visibility = View.INVISIBLE
                    binding.userProfileIv.visibility = View.INVISIBLE
                    binding.streamNicknameTv.visibility = View.INVISIBLE
                    binding.countLayout.visibility = View.INVISIBLE
                }


                    if(isPhoto == false) {
                        isPhoto = true
                        binding.deleteView.visibility = View.VISIBLE
                        binding.userProfileIv.visibility = View.VISIBLE
                        binding.streamNicknameTv.visibility = View.VISIBLE
                        binding.countLayout.visibility = View.VISIBLE

                        var currentPos = adapterPosition
                        var total = itemCount

                        binding.countTv.text = "$currentPos/$total"

                    }
                }

            }
        }



    }