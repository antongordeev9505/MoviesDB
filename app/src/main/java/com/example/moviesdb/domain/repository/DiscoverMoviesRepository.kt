package com.example.moviesdb.domain.repository

import androidx.paging.PagingData
import com.example.moviesdb.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface DiscoverMoviesRepository {

    fun discoverMovies(withCast: String): Flow<PagingData<Movie>>
}