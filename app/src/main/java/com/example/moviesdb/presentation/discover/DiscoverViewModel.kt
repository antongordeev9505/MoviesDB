package com.example.moviesdb.presentation.discover

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviesdb.domain.model.Movie
import com.example.moviesdb.domain.use_case.DiscoverMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val discoverMoviesUseCase: DiscoverMovies
) : ViewModel() {

    fun discoverMovies(withCast: String): LiveData<PagingData<Movie>> = liveData {
        emitSource(
            discoverMoviesUseCase.invoke(withCast).cachedIn(viewModelScope).asLiveData()
        )
    }
}