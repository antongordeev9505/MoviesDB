package com.example.moviesdb.domain.use_case

import android.util.Log
import com.example.moviesdb.domain.model.CustomList
import com.example.moviesdb.domain.repository.MainMovieRepository
import com.example.moviesdb.util.Resource
import kotlinx.coroutines.flow.Flow

class InsertCustomListItem(private val repository: MainMovieRepository) {

    suspend operator fun invoke(listTitle: String) {
        repository.insertCustomListItem(
            CustomList(
                (0..10000).random(),
                listTitle
            )
        )
    }
}