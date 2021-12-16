package com.example.moviesdb.presentation.popular_searchmovies

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviesdb.domain.model.Movie
import com.example.moviesdb.domain.use_case.GetPopularMovies
import com.example.moviesdb.domain.use_case.SearchMoviesByQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

private const val SEARCH_DELAY_MILLIS = 500L
private const val MIN_QUERY_LENGTH = 2

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    private val getPopularMovies: GetPopularMovies,
    private val searchMoviesByQuery: SearchMoviesByQuery
) : ViewModel()
{

    private var searchJob: Job? = null

    val moviesMediatorData = MediatorLiveData<PagingData<Movie>>()

    private val _searchMoviesLiveData: LiveData<PagingData<Movie>>
    private val _popularMoviesLiveData: LiveData<PagingData<Movie>> =
        getPopularMovies()

    private val _searchFieldTextLiveData = MutableLiveData<String>()

    init {
        _searchMoviesLiveData = Transformations.switchMap(
            _searchFieldTextLiveData) {
            getMovieByQuery(it)
        }

        moviesMediatorData.addSource(_popularMoviesLiveData) {
            moviesMediatorData.value = it
        }
    }

    private fun showPopularMoviesOnly() {
        viewModelScope.launch {
            moviesMediatorData.removeSource(_searchMoviesLiveData)
            moviesMediatorData.removeSource(_popularMoviesLiveData)

            moviesMediatorData.addSource(_popularMoviesLiveData) {
                moviesMediatorData.value = it
            }

        }
    }

    fun onSearchQuery(query: String) {
        if (query.isEmpty()) {
            showPopularMoviesOnly()
        }
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DELAY_MILLIS)
            if (query.length > MIN_QUERY_LENGTH) {
                moviesMediatorData.addSource(_searchMoviesLiveData) {
                    moviesMediatorData.value = it
                }
                _searchFieldTextLiveData.value = query
                moviesMediatorData.removeSource(_searchMoviesLiveData)
            }
        }
    }

     private fun getMovieByQuery(query: String): LiveData<PagingData<Movie>> = liveData {
        emitSource(
            searchMoviesByQuery.invoke(query).cachedIn(viewModelScope).asLiveData()
        )
    }

    private fun getPopularMovies(): LiveData<PagingData<Movie>> = liveData {
        emitSource(
            getPopularMovies.invoke().cachedIn(viewModelScope).asLiveData()
        )
    }

    override fun onCleared() {
        super.onCleared()
        searchJob?.cancel()
    }
}