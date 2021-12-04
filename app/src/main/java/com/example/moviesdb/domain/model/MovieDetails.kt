package com.example.moviesdb.domain.model


data class MovieDetails(
    val backdrop_path: String?,
    val belongs_to_collection: Any?,
    val budget: Int?,
    val genres: List<Genre>?,
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
    data class Genre(
        val id: Int,
        val name: String
    )
}