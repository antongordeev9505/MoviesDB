package com.example.moviesdb.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesdb.databinding.GenreItemBinding
import com.example.moviesdb.domain.model.MovieDetails

class GenreAdapter() :
    ListAdapter<MovieDetails.Genre, GenreAdapter.GenreViewHolder>(
        Comparator()
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): GenreAdapter.GenreViewHolder {
        val binding =
            GenreItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return GenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class GenreViewHolder(private val binding: GenreItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(genre: MovieDetails.Genre) {
            binding.apply {
                genreTextview.text = genre.name
            }
        }
    }

    class Comparator : DiffUtil.ItemCallback<MovieDetails.Genre>() {
        override fun areItemsTheSame(oldItem: MovieDetails.Genre, newItem: MovieDetails.Genre) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MovieDetails.Genre, newItem: MovieDetails.Genre) =
            oldItem == newItem
    }
}