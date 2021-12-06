package com.example.moviesdb.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesdb.R
import com.example.moviesdb.databinding.CastItemBinding
import com.example.moviesdb.domain.model.CastByMovie


class CastAdapter() :
    ListAdapter<CastByMovie.Cast, CastAdapter.ViewHolder>(
        Comparator()
    ) {

    companion object {
        const val IMAGE_PATH_PREFIX = "https://image.tmdb.org/t/p/w300"
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val binding =
            CastItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class ViewHolder(private val binding: CastItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cast: CastByMovie.Cast) {
            binding.apply {
                actor.text = cast.name
                character.text = cast.character


                    Glide.with(itemView.context)
                        .load("$IMAGE_PATH_PREFIX${cast.profile_path}")
                        .placeholder(R.drawable.poster_image)
                        .error(R.drawable.poster_image)
                        .into(image)
            }
        }
    }

    class Comparator : DiffUtil.ItemCallback<CastByMovie.Cast>() {
        override fun areItemsTheSame(oldItem: CastByMovie.Cast, newItem: CastByMovie.Cast) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CastByMovie.Cast, newItem: CastByMovie.Cast) =
            oldItem == newItem
    }
}