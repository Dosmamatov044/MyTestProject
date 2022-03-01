package com.example.mytestproject.ui.images.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mytestproject.R
import com.example.mytestproject.databinding.ImagesItemBinding
import com.example.mytestproject.ui.images.models.ImagesModel

class ImagesAdapter : RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder>() {

    var images: List<ImagesModel> = emptyList()
        set(newValue) {
            val diffCallback = ImagesDiffCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }


    override fun getItemCount(): Int = images.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ImagesItemBinding.inflate(inflater, parent, false)



        return ImagesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        val user = images[position]
        with(holder.binding) {
            holder.itemView.tag = user


            if (user.images.isNotBlank()) {
                Glide.with(image.context)
                    .load(user.images)
                    .circleCrop()
                    .placeholder(R.drawable.no_photography_24)
                    .error(R.drawable.no_photography_24)
                    .into(image)
            } else {
                Glide.with(image.context).clear(image)
                image.setImageResource(R.drawable.no_photography_24)
            }
        }
    }


    class ImagesViewHolder(
        val binding: ImagesItemBinding
    ) : RecyclerView.ViewHolder(binding.root)


    class ImagesDiffCallback(
        private val oldList: List<ImagesModel>,
        private val newList: List<ImagesModel>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
            val oldImages = oldList[oldItem]
            val newImages = newList[newItem]

            return oldImages.id == newImages.id
        }

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
            val oldImages = oldList[oldItem]
            val newImages = newList[newItem]

            return oldImages == newImages
        }


    }
}