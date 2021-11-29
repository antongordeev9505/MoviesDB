package com.example.moviesdb.domain.repository

import com.example.moviesdb.domain.model.Movie
import com.example.moviesdb.util.Resource
import kotlinx.coroutines.flow.Flow

interface PopularMoviesRepository {

    fun getAllPopularMovies(): Flow<Resource<List<Movie>>>
}