package com.example.moviesdb.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.moviesdb.R

fun useGlide(
    context: Context,
    image: String,
    view: ImageView
) {
    Glide.with(context)
        .load(image)
        .placeholder(R.drawable.poster_image)
        .error(R.drawable.poster_image)
        .into(view)
}