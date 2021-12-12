package com.example.moviesdb.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesdb.domain.model.Movie
import com.example.moviesdb.domain.use_case.DeleteMovieFromList
import com.example.moviesdb.domain.use_case.GetMoviesFromWatchList
import com.example.moviesdb.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val moviesFromWatchListUseCase: GetMoviesFromWatchList,
    private val deleteMovieUseCase: DeleteMovieFromList
) : ViewModel() {

    private val _movies = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading)
    val movies: StateFlow<Resource<List<Movie>>> = _movies

    init {
        viewModelScope.launch {
            moviesFromWatchListUseCase.invoke().collect {
                _movies.value = it
            }
        }
    }

    //или так

//    val movies: StateFlow<Resource<List<Movie>>> = flow {
//        moviesFromWatchListUseCase.invoke().collect {
//            emit(it)
//        }
//    }.stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(5000),
//        initialValue = Resource.Loading
//    )

    fun deleteMovie(movieId: Int) {
        viewModelScope.launch {
            deleteMovieUseCase.invoke(movieId)
        }
    }
}