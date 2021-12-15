package com.example.moviesdb.domain.use_case

import com.example.moviesdb.domain.repository.MainMovieRepository

class DeleteList(private val repository: MainMovieRepository) {

    suspend operator fun invoke(listId: Int) =
        repository.deleteList(listId)
}