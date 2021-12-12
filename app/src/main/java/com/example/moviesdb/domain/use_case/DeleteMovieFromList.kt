package com.example.moviesdb.domain.use_case

import com.example.moviesdb.domain.repository.MainMovieRepository

class DeleteMovieFromList(private val repository: MainMovieRepository) {

    suspend operator fun invoke(movieId: Int) =
        repository.deleteMovie(movieId)
}