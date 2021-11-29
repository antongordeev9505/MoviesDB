package com.example.moviesdb.data.remote.dto

import com.example.moviesdb.data.local.MovieEntity

data class MovieDto(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
) {
    fun toMovieEntity(): MovieEntity {
        return MovieEntity(
            id = id,
            original_title = original_title,
            poster_path = poster_path
        )
    }
}