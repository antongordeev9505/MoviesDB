package com.example.moviesdb.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter(diffCallback: DiffUtil.ItemCallback<Any>) :
    ListAdapter<Any, RecyclerView.ViewHolder>(diffCallback) {

//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        val currentItem = getItem(position)
//        if (currentItem != null) {
//            holder.bind(currentItem)
//        }
//    }
}