package com.example.moviesdb.presentation.list_movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesdb.domain.model.Movie
import com.example.moviesdb.domain.use_case.DeleteMovieFromList
import com.example.moviesdb.domain.use_case.GetAllListItems
import com.example.moviesdb.domain.use_case.GetMoviesFromWatchList
import com.example.moviesdb.domain.use_case.InsertCustomListItem
import com.example.moviesdb.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListMovieViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesFromWatchList,
    private val deleteMovieUseCase: DeleteMovieFromList
) : ViewModel() {

    private val _movies = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading)
    val movies: StateFlow<Resource<List<Movie>>> = _movies

    init {
        viewModelScope.launch {
            getMoviesUseCase.invoke().collect {
                _movies.value = it
            }
        }
    }

    fun deleteMovie(movieId: Int) {
        viewModelScope.launch {
            deleteMovieUseCase.invoke(movieId)
        }
    }
}