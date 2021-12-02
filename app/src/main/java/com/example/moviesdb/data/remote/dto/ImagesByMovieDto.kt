package com.example.moviesdb.data.remote.dto

import com.example.moviesdb.domain.model.ImageByMovie
import com.example.moviesdb.domain.model.Movie

data class ImagesByMovieDto(
    val backdrops: List<Backdrop>,
    val id: Int
) {
    data class Backdrop(
        val aspect_ratio: Double,
        val file_path: String,
        val height: Int,
        val iso_639_1: Any,
        val vote_average: Double,
        val vote_count: Int,
        val width: Int
    ) {
        fun toImageByMovie(): ImageByMovie {
            return ImageByMovie(
                file_path = file_path
            )
        }
    }
}