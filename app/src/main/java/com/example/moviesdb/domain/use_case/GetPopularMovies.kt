package com.example.moviesdb.domain.use_case

import com.example.moviesdb.domain.model.Movie
import com.example.moviesdb.domain.repository.PopularMoviesRepository
import com.example.moviesdb.util.Resource
import kotlinx.coroutines.flow.Flow

class GetPopularMovies(private val repository: PopularMoviesRepository) {

    operator fun invoke(): Flow<Resource<List<Movie>>> =
        repository.getAllPopularMovies()
}