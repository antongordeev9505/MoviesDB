package com.example.moviesdb.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moviesdb.domain.model.Movie

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey val id: Int?,
    val original_title: String?,
    val poster_path: String?,
    val overview: String?,
    val vote_average: Double?
) {
    fun toMovie(): Movie {
        return Movie(
            id = id,
            original_title = original_title,
            poster_path = poster_path,
            overview = overview,
            vote_average = vote_average,
            genre_ids = null,
            popularity = null,
            release_date = null,
            vote_count = null
        )
    }
}