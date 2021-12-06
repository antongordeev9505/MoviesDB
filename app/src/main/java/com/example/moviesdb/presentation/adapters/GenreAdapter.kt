package com.example.moviesdb.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesdb.databinding.GenreItemBinding
import com.example.moviesdb.domain.model.MovieDetails

class GenreAdapter() :
    ListAdapter<MovieDetails.Genre, GenreAdapter.ViewHolder>(
        Comparator()
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val binding =
            GenreItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class ViewHolder(private val binding: GenreItemBinding) :
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