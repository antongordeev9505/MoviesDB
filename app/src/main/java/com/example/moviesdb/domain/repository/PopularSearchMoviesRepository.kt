package com.example.moviesdb.domain.repository

import androidx.paging.PagingData
import com.example.moviesdb.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface PopularSearchMoviesRepository {

    fun getAllPopularMovies(): Flow<PagingData<Movie>>

    fun searchMoviesByQuery(query: String): Flow<PagingData<Movie>>
}