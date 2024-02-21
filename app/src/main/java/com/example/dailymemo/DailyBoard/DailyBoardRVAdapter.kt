    package com.example.dailymemo.DailyBoard

    import android.content.Context
    import android.graphics.Bitmap
    import android.graphics.BitmapFactory
    import android.net.Uri
    import android.util.Base64
    import android.util.Log
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import androidx.recyclerview.widget.LinearLayoutManager
    import androidx.recyclerview.widget.RecyclerView
    import com.bumptech.glide.Glide
    import com.example.dailymemo.DailyBoard.Retrofit.Response.Diary
    import com.example.dailymemo.DailyBoard.Retrofit.Response.DiaryPhoto
    import com.example.dailymemo.R
    import com.example.dailymemo.databinding.ItemDailyBoardBinding


    class DailyBoardRVAdapter(private val context: Context, diaryList: ArrayList<Diary>) : RecyclerView.Adapter<DailyBoardRVAdapter.ViewHolder>() {

        var diaryList = diaryList

        interface ItemClickListener{

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
            // 각각의 사진을 매핑
            var currentPosition = 0
            for (diary in diaryList) {
                for (diaryPhoto in diary.diaryPhotoList) {
                    if (currentPosition == position) {
                        holder.bind(diaryPhoto)
                        return
                    }
                    currentPosition++
                }
            }
        }
        fun getdiaryPhotoIdByPosition(position: Int): Int {
            var currentPosition = 0

            for (diary in diaryList) {
                for (diaryPhoto in diary.diaryPhotoList) {
                    if (currentPosition == position) {
                        return diaryPhoto.id // 해당 포지션의 diaryPhotoId 반환
                    }
                    currentPosition++
                }
            }

            return -1 // 유효하지 않은 포지션인 경우 -1 반환 또는 다른 적절한 값
        }

        fun getStatus(position: Int): Boolean {
            var currentPosition = 0

            for (diary in diaryList) {
                for (diaryPhoto in diary.diaryPhotoList) {
                    if (currentPosition == position) {
                        return diaryPhoto.status // 해당 포지션의 diaryPhotoId 반환
                    }
                    currentPosition++
                }
            }
            return false
        }

        fun udpateStatus(position: Int) {
            var currentPosition = 0

            for (diary in diaryList) {
                for (diaryPhoto in diary.diaryPhotoList) {
                    if (currentPosition == position) {
                        diaryPhoto.status = !diaryPhoto.status
                        notifyItemChanged(position)
                    }
                    currentPosition++
                }
            }

        }

        override fun getItemCount(): Int {
            var size = 0

           for(diary in diaryList) {
               for(diaryPhoto in diary.diaryPhotoList) {
                   size += 1
               }
           }


            return size
        }


        inner class ViewHolder(val binding: ItemDailyBoardBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(diaryPhoto : DiaryPhoto) {
                val bitmap = stringToBitmap(diaryPhoto.url)
                binding.dailyBoardIv.setImageBitmap(bitmap)

                if(diaryPhoto.status == true) {
                    binding.deleteView.visibility = View.VISIBLE
                }
            }



        }


    }

    private fun stringToBitmap(base64: String) : Bitmap {
        val encodeByte = Base64.decode(base64, Base64.NO_WRAP)

        return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
    }

