package com.example.moviesdb.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesdb.databinding.CastItemBinding
import com.example.moviesdb.databinding.CustomListItemBinding
import com.example.moviesdb.domain.model.CastByMovie
import com.example.moviesdb.domain.model.CustomList
import com.example.moviesdb.util.useGlide

class CustomListsAdapter() :
    ListAdapter<CustomList, CustomListsAdapter.ViewHolder>(
        Comparator()
    ) {

    companion object {
        const val IMAGE_PATH_PREFIX = "https://image.tmdb.org/t/p/w300"
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val binding =
            CustomListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class ViewHolder(private val binding: CustomListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listItem: CustomList) {
            binding.apply {
                customListTitle.text = listItem.title
            }
        }
    }

    class Comparator : DiffUtil.ItemCallback<CustomList>() {
        override fun areItemsTheSame(oldItem: CustomList, newItem: CustomList) =
            oldItem.idList == newItem.idList

        override fun areContentsTheSame(oldItem: CustomList, newItem: CustomList) =
            oldItem == newItem
    }
}