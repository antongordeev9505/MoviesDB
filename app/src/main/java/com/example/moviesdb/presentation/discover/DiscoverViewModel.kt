package com.example.moviesdb.presentation.discover

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviesdb.domain.model.Genres
import com.example.moviesdb.domain.model.Movie
import com.example.moviesdb.domain.use_case.DiscoverMovies
import com.example.moviesdb.domain.use_case.GetGenres
import com.example.moviesdb.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val discoverMoviesUseCase: DiscoverMovies,
    private val getGenresUseCase: GetGenres
) : ViewModel() {

//    fun discoverMovies(withCast: String): LiveData<PagingData<Movie>> = liveData {
//        emitSource(
//            discoverMoviesUseCase.invoke(withCast).cachedIn(viewModelScope).asLiveData()
//        )
//    }

    fun getGenres(): LiveData<Resource<Genres>> = liveData {
        emitSource(
            getGenresUseCase.invoke().asLiveData()
        )
    }


    private val _movies = MutableLiveData<PagingData<Movie>>()
    val movies: LiveData<PagingData<Movie>> = _movies

//    init {
//        viewModelScope.launch {
//            discoverMoviesUseCase.invoke("500").onEach {
//                _movies.value = it
//            }
//        }
//    }

    fun getData(withCast: String, sortBy: String = "popularity.desc", minVoteCount: Int = 100) {
        viewModelScope.launch {
            discoverMoviesUseCase.invoke(withCast, sortBy, minVoteCount).cachedIn(viewModelScope).collect {
                _movies.value = it
            }
        }
    }
}