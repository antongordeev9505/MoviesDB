package com.example.moviesdb.presentation.discover

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviesdb.domain.model.Movie
import com.example.moviesdb.domain.use_case.DiscoverMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val discoverMoviesUseCase: DiscoverMovies
) : ViewModel() {

//    fun discoverMovies(withCast: String): LiveData<PagingData<Movie>> = liveData {
//        emitSource(
//            discoverMoviesUseCase.invoke(withCast).cachedIn(viewModelScope).asLiveData()
//        )
//    }


    private val _movies = MutableLiveData<PagingData<Movie>>()
    val movies: LiveData<PagingData<Movie>> = _movies

//    init {
//        viewModelScope.launch {
//            discoverMoviesUseCase.invoke("500").onEach {
//                _movies.value = it
//            }
//        }
//    }

    fun getData(parametr1: String, parametr2: String = "popularity.desc") {
        viewModelScope.launch {
            discoverMoviesUseCase.invoke(parametr1, parametr2).cachedIn(viewModelScope).collect {
                _movies.value = it
            }
        }
    }

//    private val currentActor = MutableLiveData(DEFAULT_QUERY)
////    private val sorting = MutableLiveData(DEFAULT_QUERY)
//
//    val movies = Transformations.switchMap(currentActor) {
//        discoverMoviesUseCase.invoke(it).cachedIn(viewModelScope).asLiveData()
//    }
//
//    fun byActor(actorId: String) {
//        currentActor.value = actorId
//    }
}