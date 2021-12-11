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

    fun getGenres(): LiveData<Resource<Genres>> = liveData {
        emitSource(
            getGenresUseCase.invoke().asLiveData()
        )
    }

    private val _movies = MutableLiveData<PagingData<Movie>>()
    val movies: LiveData<PagingData<Movie>> = _movies

    fun getData(
        releaseYear: Int?,
        sortBy: String = "popularity.desc",
        minVoteCount: Int = 0,
        withGenre: String = "",
        voteAverage: Int = 0
    ) {
        viewModelScope.launch {
            discoverMoviesUseCase.invoke(releaseYear, sortBy, minVoteCount, withGenre, voteAverage).cachedIn(viewModelScope)
                .collect {
                    _movies.value = it
                }
        }
    }
}