package com.example.moviesdb.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moviesdb.domain.model.Movie

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val original_title: String,
    val poster_path: String
) {
    fun toMovie(): Movie {
        return Movie(
            original_title = original_title,
            poster_path = poster_path
        )
    }
}