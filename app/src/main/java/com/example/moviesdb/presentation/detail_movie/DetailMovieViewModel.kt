package com.example.moviesdb.presentation.detail_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.example.moviesdb.domain.model.CastByMovie
import com.example.moviesdb.domain.model.ImagesByMovie
import com.example.moviesdb.domain.model.Movie
import com.example.moviesdb.domain.model.MovieDetails
import com.example.moviesdb.domain.use_case.GetCastByMovie
import com.example.moviesdb.domain.use_case.GetImagesByMovie
import com.example.moviesdb.domain.use_case.GetMovieDetails
import com.example.moviesdb.domain.use_case.GetRecommendation
import com.example.moviesdb.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val getRecommendationUseCase: GetRecommendation,
    private val getImagesByMovieUseCase: GetImagesByMovie,
    private val getCastByMovieUseCase: GetCastByMovie,
    private val getMovieDetailsUseCase: GetMovieDetails
) : ViewModel() {

    fun getRecommendation(
        movieId: Int
    ): LiveData<Resource<List<Movie>>> = liveData {
        emitSource(
            getRecommendationUseCase.invoke(movieId).asLiveData())
    }

    fun getImagesByMovie(
        movieId: Int
    ): LiveData<Resource<ImagesByMovie>> = liveData {
        emitSource(
            getImagesByMovieUseCase.invoke(movieId).asLiveData()
        )
    }

    fun getCastByMovie(
        movieId: Int
    ): LiveData<Resource<CastByMovie>> = liveData {
        emitSource(
            getCastByMovieUseCase.invoke(movieId).asLiveData()
        )
    }

    fun getMovieDetails(
        movieId: Int
    ): LiveData<Resource<MovieDetails>> = liveData {
        emitSource(
            getMovieDetailsUseCase.invoke(movieId).asLiveData()
        )
    }
}