package com.example.moviesdb.domain.model

class Genres(
    val genres: List<Genre>
) {
    data class Genre(
        val id: Int,
        val name: String
    )
}
