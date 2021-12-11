package com.example.moviesdb.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesdb.databinding.GenreChipItemBinding
import com.example.moviesdb.domain.model.Genres

class AllGenresAdapter() :
    ListAdapter<Genres.Genre, AllGenresAdapter.ViewHolder>(
        Comparator()
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val binding =
            GenreChipItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class ViewHolder(private val binding: GenreChipItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(genre: Genres.Genre) {
            binding.apply {
                chip.text = genre.name
            }
        }
    }

    class Comparator : DiffUtil.ItemCallback<Genres.Genre>() {
        override fun areItemsTheSame(oldItem: Genres.Genre, newItem: Genres.Genre) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Genres.Genre, newItem: Genres.Genre) =
            oldItem == newItem
    }
}