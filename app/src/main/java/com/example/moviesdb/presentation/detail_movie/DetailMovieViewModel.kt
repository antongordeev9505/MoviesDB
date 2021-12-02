package com.example.moviesdb.presentation.detail_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.example.moviesdb.domain.model.Movie
import com.example.moviesdb.domain.use_case.GetRecommendation
import com.example.moviesdb.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val getRecommendationUseCase: GetRecommendation
) : ViewModel() {

    fun getRecommendation(
        movieId: Int
    ): LiveData<Resource<List<Movie>>> = liveData {
        emitSource(
            getRecommendationUseCase.invoke(movieId).asLiveData())
    }
}