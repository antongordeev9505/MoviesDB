package com.example.moviesdb.domain.use_case

import com.example.moviesdb.domain.model.Genres
import com.example.moviesdb.domain.repository.DiscoverMoviesRepository
import com.example.moviesdb.util.Resource
import kotlinx.coroutines.flow.Flow

class GetGenres(private val repository: DiscoverMoviesRepository) {

    operator fun invoke(): Flow<Resource<Genres>> =
        repository.getGenres()
}