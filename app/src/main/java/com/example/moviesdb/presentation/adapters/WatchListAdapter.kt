package com.example.moviesdb.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesdb.R
import com.example.moviesdb.databinding.MovieMainScreenItemBinding
import com.example.moviesdb.domain.model.Movie
import com.example.moviesdb.util.createAndShowDialog
import com.example.moviesdb.util.useGlide

class WatchListAdapter(private val listener: OnItemClickListener) :
    ListAdapter<Movie, WatchListAdapter.ViewHolder>(
        Comparator()
    ) {

    companion object {
        const val IMAGE_PATH_PREFIX = "https://image.tmdb.org/t/p/w300"
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val binding =
            MovieMainScreenItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }

        holder.itemView.setOnClickListener {
            currentItem.id?.let { id -> listener.onItemClick(id) }
        }

        holder.itemView.setOnLongClickListener {
            createAndShowDialog(holder.itemView.context,
                "Delete ${currentItem.original_title} from watch list?",
                onPositiveAction = { currentItem.id?.let { id -> listener.deleteMovie(id) } })
            return@setOnLongClickListener true
        }
    }

    class ViewHolder(private val binding: MovieMainScreenItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.apply {
                title.text = movie.original_title

                useGlide(
                    itemView.context,
                    "$IMAGE_PATH_PREFIX${movie.poster_path}",
                    image
                )
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(id: Int)
        fun deleteMovie(id: Int)
    }

    class Comparator : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
            oldItem == newItem
    }
}