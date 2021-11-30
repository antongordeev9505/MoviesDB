package com.example.moviesdb.domain.repository

import androidx.paging.PagingData
import com.example.moviesdb.domain.model.Movie
import com.example.moviesdb.util.Resource
import kotlinx.coroutines.flow.Flow

interface PopularMoviesRepository {

//    fun getAllPopularMovies(): Flow<Resource<List<Movie>>>
        fun getAllPopularMovies(): Flow<PagingData<Movie>>

}