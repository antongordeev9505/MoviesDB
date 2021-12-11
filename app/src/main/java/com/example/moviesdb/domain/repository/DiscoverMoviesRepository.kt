package com.example.moviesdb.domain.repository

import androidx.paging.PagingData
import com.example.moviesdb.domain.model.Genres
import com.example.moviesdb.domain.model.Movie
import com.example.moviesdb.util.Resource
import kotlinx.coroutines.flow.Flow

interface DiscoverMoviesRepository {

    fun discoverMovies(
        releaseYear: Int?,
        sortBy: String,
        minVoteCount: Int,
        withGenre: String,
        voteAverage: Int
    ): Flow<PagingData<Movie>>

    fun getGenres(): Flow<Resource<Genres>>
}