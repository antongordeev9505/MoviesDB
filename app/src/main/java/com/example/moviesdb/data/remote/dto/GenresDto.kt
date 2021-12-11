package com.example.moviesdb.data.remote.dto

import com.example.moviesdb.domain.model.Genres

data class GenresDto(
    val genres: List<Genre>
) {

    fun toListOfGenres(): Genres {
        return Genres(
            genres = genres.map { it.toGenre() }
        )
    }

    data class Genre(
        val id: Int,
        val name: String
    ) {

        fun toGenre(): Genres.Genre {
            return Genres.Genre(
                id = id,
                name = name
            )
        }
    }
}