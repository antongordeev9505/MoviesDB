package com.example.moviesdb.presentation.detail_movie

import androidx.lifecycle.*
import com.example.moviesdb.domain.model.CastByMovie
import com.example.moviesdb.domain.model.ImagesByMovie
import com.example.moviesdb.domain.model.Movie
import com.example.moviesdb.domain.model.MovieDetails
import com.example.moviesdb.domain.use_case.*
import com.example.moviesdb.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val getRecommendationUseCase: GetRecommendation,
    private val getImagesByMovieUseCase: GetImagesByMovie,
    private val getCastByMovieUseCase: GetCastByMovie,
    private val getMovieDetailsUseCase: GetMovieDetails,
    private val insertMovieToListUseCase: InsertMovieToList,
    private val checkMovieUseCase: CheckMovieInWatchList,
    private val deleteMovieUseCase: DeleteMovieFromList,
) : ViewModel() {

    fun insertMovieToList(
        movieId: Int
    ): LiveData<Resource<String>> = liveData {
        emitSource(
            insertMovieToListUseCase.invoke(movieId).asLiveData()
        )
    }

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

    fun checkMovie(
        movieId: Int
    ): LiveData<Resource<Boolean>> = liveData {
        emitSource(
            checkMovieUseCase.invoke(movieId).asLiveData()
        )
    }

    fun deleteMovie(movieId: Int) {
        viewModelScope.launch {
            deleteMovieUseCase.invoke(movieId)
        }
    }
}