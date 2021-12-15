package com.example.moviesdb.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesdb.databinding.MovieFromListItemBinding
import com.example.moviesdb.databinding.MovieItemBinding
import com.example.moviesdb.databinding.MovieMainScreenItemBinding
import com.example.moviesdb.domain.model.Movie
import com.example.moviesdb.util.createAndShowDialog
import com.example.moviesdb.util.useGlide

class ListMovieAdapter(private val listener: OnItemClickListener) :
    ListAdapter<Movie, ListMovieAdapter.ViewHolder>(
        Comparator()
    ) {

    companion object {
        const val IMAGE_PATH_PREFIX = "https://image.tmdb.org/t/p/w300"
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val binding =
            MovieFromListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }

        holder.itemView.setOnClickListener {
            currentItem.id?.let { id -> listener.onItemCLick(id) }
        }

        holder.itemView.setOnLongClickListener {
            createAndShowDialog(holder.itemView.context,
                "Delete ${currentItem.original_title} from the list?",
                onPositiveAction = { currentItem.id?.let { id -> listener.deleteMovie(id) } })
            return@setOnLongClickListener true
        }
    }

    class ViewHolder(private val binding: MovieFromListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.apply {
                title.text = movie.original_title
                voteAverage.text = movie.vote_average.toString()
                overview.text = movie.overview

                useGlide(
                    itemView.context,
                    "$IMAGE_PATH_PREFIX${movie.poster_path}",
                    moviePosterImageView
                )
            }
        }
    }

    interface OnItemClickListener {
        fun onItemCLick(movieId: Int)
        fun deleteMovie(movieId: Int)
    }

    class Comparator : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
            oldItem == newItem
    }
}