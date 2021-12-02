package com.example.moviesdb.domain.use_case

import com.example.moviesdb.domain.model.CastByMovie
import com.example.moviesdb.domain.repository.DetailMovieRepository
import com.example.moviesdb.util.Resource
import kotlinx.coroutines.flow.Flow

class GetCastByMovie(private val repository: DetailMovieRepository) {

    operator fun invoke(movieId: Int): Flow<Resource<CastByMovie>> =
        repository.getCastByMovie(movieId)
}