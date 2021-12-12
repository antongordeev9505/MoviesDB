package com.example.moviesdb.domain.use_case

import com.example.moviesdb.domain.repository.DetailMovieRepository
import com.example.moviesdb.util.Resource
import kotlinx.coroutines.flow.Flow

class CheckMovieInWatchList(private val repository: DetailMovieRepository) {

    suspend operator fun invoke(movieId: Int): Flow<Resource<Boolean>> =
        repository.checkMovieInWatchList(movieId)
}