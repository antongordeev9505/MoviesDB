package com.example.moviesdb.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.moviesdb.R
import com.example.moviesdb.databinding.MovieItemBinding
import com.example.moviesdb.domain.model.Movie

class PopularMoviesAdapter() :
    ListAdapter<Movie, PopularMoviesAdapter.PopularMoviesViewHolder>(
        PopularMoviesComparator()
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): PopularMoviesViewHolder {
        val binding =
            MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return PopularMoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularMoviesViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class PopularMoviesViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.apply {
                titleTextView.text = movie.original_title

                Glide.with(itemView)
                    .load(movie.poster_path)
                    .centerCrop()
                    .placeholder(R.drawable.ic_baseline_image_24)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_baseline_error_24)
                    .into(moviePosterImageView)
            }
        }
    }

    class PopularMoviesComparator : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
            oldItem.original_title == newItem.original_title

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
            oldItem == newItem
    }


}

