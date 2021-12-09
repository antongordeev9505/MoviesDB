package com.example.moviesdb.domain.use_case

import androidx.paging.PagingData
import com.example.moviesdb.domain.model.Movie
import com.example.moviesdb.domain.repository.DiscoverMoviesRepository
import kotlinx.coroutines.flow.Flow

class DiscoverMovies(private val repository: DiscoverMoviesRepository) {

    operator fun invoke(withCast: String, sortBy: String = "popularity.desc"): Flow<PagingData<Movie>> =
        repository.discoverMovies(withCast, sortBy)
}