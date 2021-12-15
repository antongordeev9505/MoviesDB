package com.example.moviesdb.presentation.detail_movie

import androidx.lifecycle.*
import com.example.moviesdb.domain.model.*
import com.example.moviesdb.domain.use_case.*
import com.example.moviesdb.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
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
    private val getAllListsUseCase: GetAllListItems
) : ViewModel() {

    fun insertMovieToList(
        movieId: Int,
        listId: Int = 0
    ): LiveData<Resource<String>> = liveData {
        emitSource(
            insertMovieToListUseCase.invoke(movieId, listId).asLiveData()
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

    val lists: StateFlow<Resource<List<CustomList>>> = flow {
        getAllListsUseCase.invoke().collect {
            emit(it)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Resource.Loading
    )
}