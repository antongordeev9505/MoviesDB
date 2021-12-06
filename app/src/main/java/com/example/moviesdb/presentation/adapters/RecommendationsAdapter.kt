package com.example.moviesdb.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesdb.R
import com.example.moviesdb.databinding.RecommendationItemBinding
import com.example.moviesdb.domain.model.Movie

class RecommendationsAdapter() :
    ListAdapter<Movie, RecommendationsAdapter.ViewHolder>(
        Comparator()
    ) {

    companion object {
        const val IMAGE_PATH_PREFIX = "https://image.tmdb.org/t/p/w300"
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val binding =
            RecommendationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class ViewHolder(private val binding: RecommendationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.apply {
                title.text = movie.original_title

                Glide.with(itemView.context)
                    .load("$IMAGE_PATH_PREFIX${movie.poster_path}")
                    .placeholder(R.drawable.poster_image)
                    .error(R.drawable.poster_image)
                    .into(image)
            }
        }
    }

    class Comparator : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
            oldItem == newItem
    }
}