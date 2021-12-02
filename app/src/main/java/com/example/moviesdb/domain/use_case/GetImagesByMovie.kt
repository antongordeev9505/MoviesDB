package com.example.moviesdb.domain.use_case

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.example.moviesdb.domain.model.ImageByMovie
import com.example.moviesdb.domain.model.Movie
import com.example.moviesdb.domain.repository.DetailMovieRepository
import com.example.moviesdb.util.Resource
import kotlinx.coroutines.flow.Flow

class GetImagesByMovie(private val repository: DetailMovieRepository) {

//    operator fun invoke(movieId: Int): LiveData<Resource<List<ImageByMovie>>> =
//        liveData {
//            emitSource(
//                repository.getImagesByMovie(movieId).asLiveData()
//            )
//        }

    operator fun invoke(movieId: Int): Flow<Resource<List<ImageByMovie>>> =
        repository.getImagesByMovie(movieId)
}