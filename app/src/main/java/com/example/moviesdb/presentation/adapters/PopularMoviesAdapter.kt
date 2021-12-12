package com.example.moviesdb.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.moviesdb.R
import com.example.moviesdb.databinding.MovieItemBinding
import com.example.moviesdb.domain.model.Movie
import com.example.moviesdb.util.useGlide

class PopularMoviesAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<Movie, PopularMoviesAdapter.PopularMoviesViewHolder>(
        PopularMoviesComparator()
    ) {

    companion object {
        const val POSTER_IMAGE_PATH_PREFIX = "https://image.tmdb.org/t/p/w300"
    }

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

    inner class PopularMoviesViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition

                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)

                    if (item != null) {
                        item.id?.let { id -> listener.onItemClick(id) }
                    }
                }
            }
        }

        fun bind(movie: Movie) {
            binding.apply {
                title.text = movie.original_title
                overview.text = movie.overview

                if (movie.vote_average == 0.0) {
                    voteAverage.isVisible = false
                } else {
                    voteAverage.text = movie.vote_average.toString()
                }

                useGlide(
                    itemView.context,
                    "$POSTER_IMAGE_PATH_PREFIX${movie.poster_path}",
                    moviePosterImageView
                )
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(id: Int)
    }

    class PopularMoviesComparator : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
            oldItem == newItem
    }
}

