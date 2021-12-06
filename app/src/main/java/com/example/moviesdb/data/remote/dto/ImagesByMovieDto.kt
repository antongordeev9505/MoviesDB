package com.example.moviesdb.data.remote.dto

import com.example.moviesdb.domain.model.CastByMovie
import com.example.moviesdb.domain.model.ImagesByMovie

data class ImagesByMovieDto(
    val backdrops: List<Backdrop>,
    val id: Int
) {
    fun toImagesByMovie(): ImagesByMovie {
        return ImagesByMovie(
            backdrops = backdrops.map { it.toBackdropByMovie() }
        )
    }

    data class Backdrop(
        val aspect_ratio: Double,
        val file_path: String,
        val height: Int,
        val iso_639_1: Any,
        val vote_average: Double,
        val vote_count: Int,
        val width: Int
    ) {
        fun toBackdropByMovie(): ImagesByMovie.Backdrop {
            return ImagesByMovie.Backdrop(
                aspect_ratio = aspect_ratio,
                file_path = file_path,
                height = height,
                width = width
            )
        }
    }
}