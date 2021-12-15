package com.example.moviesdb.domain.use_case

import com.example.moviesdb.domain.repository.MainMovieRepository
import com.example.moviesdb.util.Resource
import kotlinx.coroutines.flow.Flow

class InsertMovieToList(private val repository: MainMovieRepository) {

    suspend operator fun invoke(movieId: Int, listId: Int): Flow<Resource<String>> =
        repository.insertMovie(movieId, listId)
}