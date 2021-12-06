package com.example.moviesdb.domain.use_case

import com.example.moviesdb.domain.model.ImagesByMovie
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

    operator fun invoke(movieId: Int): Flow<Resource<ImagesByMovie>> =
        repository.getImagesByMovie(movieId)
}