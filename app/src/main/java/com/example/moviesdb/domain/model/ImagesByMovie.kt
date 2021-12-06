package com.example.moviesdb.domain.model

data class ImagesByMovie(
    val backdrops: List<Backdrop>
) {
    data class Backdrop(
        val aspect_ratio: Double,
        val file_path: String,
        val height: Int,
        val width: Int
    )
}