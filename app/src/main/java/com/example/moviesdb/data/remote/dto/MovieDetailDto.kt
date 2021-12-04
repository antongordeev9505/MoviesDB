package com.example.moviesdb.data.remote.dto

import com.example.moviesdb.domain.model.MovieDetails

data class MovieDetailDto(
    val backdrop_path: String?,
    val belongs_to_collection: Any?,
    val budget: Int?,
    val genres: List<Genre>,
    val id: Int?,
    val original_title: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: Any?,
    val release_date: String?,
    val revenue: Int?,
    val tagline: String?,
    val vote_average: Double?,
    val vote_count: Int?
) {

    fun toMovieDetails(): MovieDetails {
        return MovieDetails(
            backdrop_path = backdrop_path,
            belongs_to_collection = belongs_to_collection,
            budget = budget,
            genres = genres.map { it.toGenre() },
            id = id,
            original_title = original_title,
            overview = overview,
            popularity = popularity,
            poster_path = poster_path,
            release_date = release_date,
            revenue = revenue,
            tagline = tagline,
            vote_average = vote_average,
            vote_count = vote_count
        )
    }

    data class Genre(
        val id: Int,
        val name: String
    ) {
        fun toGenre(): MovieDetails.Genre {
            return MovieDetails.Genre(
                id = id,
                name = name
            )
        }
    }
}