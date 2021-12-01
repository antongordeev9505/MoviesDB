package com.example.moviesdb.presentation

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviesdb.data.local.MovieEntity
import com.example.moviesdb.domain.model.Movie
import com.example.moviesdb.domain.use_case.GetPopularMovies
import com.example.moviesdb.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    private val getPopularMovies: GetPopularMovies
) : ViewModel()
{
//    fun getPopularMovies(): LiveData<Resource<List<Movie>>> = liveData {
//        emitSource(
//            getPopularMovies.invoke().asLiveData()
//        )
//    }

    fun getPopularMovies(): LiveData<PagingData<MovieEntity>> = liveData {
        emitSource(
            getPopularMovies.invoke().cachedIn(viewModelScope).asLiveData()
        )
    }
}