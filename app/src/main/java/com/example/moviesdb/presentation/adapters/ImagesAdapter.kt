package com.example.moviesdb.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesdb.R
import com.example.moviesdb.databinding.ImageItemBinding
import com.example.moviesdb.domain.model.ImagesByMovie
import com.example.moviesdb.util.useGlide

class ImagesAdapter() :
    ListAdapter<ImagesByMovie.Backdrop, ImagesAdapter.ViewHolder>(
        Comparator()
    ) {

    companion object {
        const val IMAGE_PATH_PREFIX = "https://image.tmdb.org/t/p/w500"
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val binding =
            ImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class ViewHolder(private val binding: ImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(image: ImagesByMovie.Backdrop) {
            binding.apply {
                useGlide(
                    itemView.context,
                    "$IMAGE_PATH_PREFIX${image.file_path}",
                    imageItem
                )
            }
        }
    }

    class Comparator : DiffUtil.ItemCallback<ImagesByMovie.Backdrop>() {
        override fun areItemsTheSame(oldItem: ImagesByMovie.Backdrop, newItem: ImagesByMovie.Backdrop) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: ImagesByMovie.Backdrop, newItem: ImagesByMovie.Backdrop) =
            oldItem == newItem
    }
}