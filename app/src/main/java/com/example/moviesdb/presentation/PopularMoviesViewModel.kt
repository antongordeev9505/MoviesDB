package com.example.moviesdb.presentation

import androidx.lifecycle.*
import com.example.moviesdb.domain.model.Movie
import com.example.moviesdb.domain.use_case.GetPopularMovies
import com.example.moviesdb.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    private val getPopularMovies: GetPopularMovies
) : ViewModel()
{
    fun getPopularMovies(): LiveData<Resource<List<Movie>>> = liveData {
        emitSource(
            getPopularMovies.invoke().asLiveData()
        )
    }
}